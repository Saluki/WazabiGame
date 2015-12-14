package ovh.gorillahack.wazabi.domaine;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="DE", schema="WAZABI")
public class De implements Serializable {
	public enum Valeur {WAZABI, PIOCHE, DE}
	
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
	
	
}
