package ovh.gorillahack.wazabi.domaine;

import javax.persistence.*;

@Entity
@Table(name="DE", schema="WAZABI")
public class De {
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
	
	
}
