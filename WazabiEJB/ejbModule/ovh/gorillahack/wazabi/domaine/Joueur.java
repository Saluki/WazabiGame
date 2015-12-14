package ovh.gorillahack.wazabi.domaine;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "JOUEURS", schema = "WAZABI")
public class Joueur implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_joueur;
	@Column(nullable = false, unique = true, length=20)
	private String pseudo;
	@Column(nullable = false, length=250)
	private String mot_de_passe;
	
	public Joueur(String pseudo, String mot_de_passe) {
		super();
		this.pseudo = pseudo;
		this.mot_de_passe = mot_de_passe;
	}

	public int getId_joueur() {
		return id_joueur;
	}

	public void setId_joueur(int id_joueur) {
		this.id_joueur = id_joueur;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getMot_de_passe() {
		return mot_de_passe;
	}

	public void setMot_de_passe(String mot_de_passe) {
		this.mot_de_passe = mot_de_passe;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id_joueur;
		result = prime * result + ((mot_de_passe == null) ? 0 : mot_de_passe.hashCode());
		result = prime * result + ((pseudo == null) ? 0 : pseudo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Joueur other = (Joueur) obj;
		if (id_joueur != other.id_joueur)
			return false;
		if (mot_de_passe == null) {
			if (other.mot_de_passe != null)
				return false;
		} else if (!mot_de_passe.equals(other.mot_de_passe))
			return false;
		if (pseudo == null) {
			if (other.pseudo != null)
				return false;
		} else if (!pseudo.equals(other.pseudo))
			return false;
		return true;
	}
	
	
	
}
