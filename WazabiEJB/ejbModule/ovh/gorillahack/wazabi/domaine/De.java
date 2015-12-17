package ovh.gorillahack.wazabi.domaine;

import java.io.Serializable;

import javax.persistence.*;

import ovh.gorillahack.wazabi.domaine.Face.Valeur;

/**
 * Une instance d'un dé, contient le résultat du lancer
 * @author Alexandre
 *
 */
@Entity
@Table(name="DE", schema="WAZABI")
public class De implements Serializable {
	private static final long serialVersionUID = 3574469498462219396L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_de;
	
	@Enumerated(EnumType.STRING)
	@Column
	private Valeur valeur;

	public De(Valeur valeur) {
		super();
		this.valeur = valeur;
	}
	
	public De() {
		super();
		this.valeur=Valeur.WAZABI;
	}

	public int getId_de() {
		return id_de;
	}

	public void setId_de(int id_de) {
		this.id_de = id_de;
	}

	public Valeur getValeur() {
		return valeur;
	}

	public void setValeur(Valeur valeur) {
		this.valeur = valeur;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id_de;
		result = prime * result + ((valeur == null) ? 0 : valeur.hashCode());
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
		De other = (De) obj;
		if (id_de != other.id_de)
			return false;
		if (valeur != other.valeur)
			return false;
		return true;
	}
	
	public String toString() {
		return "Dé (valeur : "+ getValeur()+ " )";
	}
}
