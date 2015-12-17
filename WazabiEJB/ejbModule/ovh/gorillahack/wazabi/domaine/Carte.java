package ovh.gorillahack.wazabi.domaine;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ovh.gorillahack.wazabi.domaine.CarteEffet.Input;

@Entity
@Table(name = "CARTES", schema = "WAZABI")
public class Carte implements Serializable {
	private static final long serialVersionUID = -6077579153718005022L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_carte;

	@OneToOne
	@JoinColumn(nullable = false, name="code_effet")
	private CarteEffet carteEffet;

	@Column
	private int ordre_pioche;

	public Carte(CarteEffet carteEffet, int ordre_pioche) {
		super();
		this.carteEffet = carteEffet;
		this.ordre_pioche = ordre_pioche;
	}

	public Carte(CarteEffet carteEffet) {
		super();
		this.carteEffet = carteEffet;
	}

	public Carte() {
		super();
	}

	public int getId_carte() {
		return id_carte;
	}

	public void setId_carte(int id_carte) {
		this.id_carte = id_carte;
	}

	public CarteEffet getCarteEffet() {
		return carteEffet;
	}

	public void setCarteEffet(CarteEffet carteEffet) {
		this.carteEffet = carteEffet;
	}

	public int getCout() {
		return carteEffet.getCout();
	}

	public int getOrdre_pioche() {
		return ordre_pioche;
	}

	public void setOrdre_pioche(int ordre_pioche) {
		this.ordre_pioche = ordre_pioche;
	}

	public Input getInput() {
		return carteEffet.getInput();
	}
	
	public int getCodeEffet() {
		return carteEffet.getCode_effet();
	}
	
	public String getEffet() {
		return carteEffet.getEffet();
	}
	
	public String getDescription() {
		return carteEffet.getDescription();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((carteEffet == null) ? 0 : carteEffet.hashCode());
		result = prime * result + id_carte;
		result = prime * result + ordre_pioche;
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
		Carte other = (Carte) obj;
		if (carteEffet == null) {
			if (other.carteEffet != null)
				return false;
		} else if (!carteEffet.equals(other.carteEffet))
			return false;
		if (id_carte != other.id_carte)
			return false;
		if (ordre_pioche != other.ordre_pioche)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Carte [id_carte=" + id_carte + ", carteEffet=" + carteEffet + ", ordre_pioche=" + ordre_pioche + "]";
	}

	


}
