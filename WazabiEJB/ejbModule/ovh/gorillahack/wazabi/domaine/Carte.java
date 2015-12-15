package ovh.gorillahack.wazabi.domaine;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

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

	@Column(nullable = false)
	private boolean input;

	@Column(nullable = false)
	private int cout;

	@ManyToOne
	@PrimaryKeyJoinColumn(name = "id_partie")
	private Partie partie;

	@Column
	private int ordre_pioche;

	public Carte(CarteEffet carteEffet, int cout, Partie partie, int ordre_pioche) {
		super();
		this.carteEffet = carteEffet;
		this.cout = cout;
		this.partie = partie;
		this.ordre_pioche = ordre_pioche;
	}

	public Carte(CarteEffet carteEffet, int cout) {
		super();
		this.carteEffet = carteEffet;
		this.cout = cout;
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
		return cout;
	}

	public void setCout(int cout) {
		this.cout = cout;
	}

	public Partie getPartie() {
		return partie;
	}

	public void setPartie(Partie partie) {
		this.partie = partie;
	}

	public int getOrdre_pioche() {
		return ordre_pioche;
	}

	public void setOrdre_pioche(int ordre_pioche) {
		this.ordre_pioche = ordre_pioche;
	}

	public boolean isInput() {
		return input;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((carteEffet == null) ? 0 : carteEffet.hashCode());
		result = prime * result + cout;
		result = prime * result + id_carte;
		result = prime * result + (input ? 1231 : 1237);
		result = prime * result + ordre_pioche;
		result = prime * result + ((partie == null) ? 0 : partie.hashCode());
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
		if (cout != other.cout)
			return false;
		if (id_carte != other.id_carte)
			return false;
		if (input != other.input)
			return false;
		if (ordre_pioche != other.ordre_pioche)
			return false;
		if (partie == null) {
			if (other.partie != null)
				return false;
		} else if (!partie.equals(other.partie))
			return false;
		return true;
	}

	


}
