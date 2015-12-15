package ovh.gorillahack.wazabi.domaine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "JOUEURS_PARTIE", schema = "WAZABI", uniqueConstraints = @UniqueConstraint(columnNames = {
		"joueur_id_joueur", "partie_id_partie" }) )
public class JoueurPartie implements Serializable {
	private static final long serialVersionUID = 2497302449327247869L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_joueur_partie;
	@Column
	private int ordre_joueur;
	@Column
	private int compteur_sauts;
	@Column
	private boolean estActif;

	@ManyToOne
	@JoinColumn(nullable = false)
	@PrimaryKeyJoinColumn(name = "id_joueur")
	private Joueur joueur;

	@ManyToOne
	@JoinColumn(nullable = false)
	@PrimaryKeyJoinColumn(name = "id_partie")
	private Partie partie;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_joueur_partie")
	private List<De> des;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_joueur_partie")
	private List<Carte> cartes;

	public JoueurPartie(int ordre_joueur, int compteur_sauts, List<De> des, List<Carte> cartes) {
		super();
		this.ordre_joueur = ordre_joueur;
		this.compteur_sauts = compteur_sauts;
		this.des = des;
		this.cartes = cartes;
	}

	public JoueurPartie() {
		super();
		des = new ArrayList<>();
		cartes = new ArrayList<>();
	}

	public int getOrdre_joueur() {
		return ordre_joueur;
	}

	public void setOrdre_joueur(int ordre_joueur) {
		this.ordre_joueur = ordre_joueur;
	}

	public int getCompteur_sauts() {
		return compteur_sauts;
	}

	public void setCompteur_sauts(int compteur_sauts) {
		this.compteur_sauts = compteur_sauts;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + compteur_sauts;
		result = prime * result + id_joueur_partie;
		result = prime * result + ordre_joueur;
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
		JoueurPartie other = (JoueurPartie) obj;
		if (compteur_sauts != other.compteur_sauts)
			return false;
		if (id_joueur_partie != other.id_joueur_partie)
			return false;
		if (ordre_joueur != other.ordre_joueur)
			return false;
		return true;
	}

	public Joueur getJoueur() {
		return joueur;
	}

	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}

	public Partie getPartie() {
		return partie;
	}

	public void setPartie(Partie partie) {
		this.partie = partie;
	}

	public List<De> getDes() {
		return des;
	}

	public List<Carte> getCartes() {
		return cartes;
	}

	public void setDes(List<De> des) {
		this.des = des;
	}

	public void setCartes(List<Carte> cartes) {
		this.cartes = cartes;
	}

	public int getId_joueur_partie() {
		return id_joueur_partie;
	}
	
	public boolean estActif() {
		return estActif;
	}

	public void setEstActif(boolean estActif) {
		this.estActif = estActif;
	}
}