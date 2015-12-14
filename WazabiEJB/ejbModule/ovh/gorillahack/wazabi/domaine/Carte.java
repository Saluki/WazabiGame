package ovh.gorillahack.wazabi.domaine;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="CARTES", schema="WAZABI")
public class Carte {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_carte;
	
	@Column (nullable=false)
	private int code_effet;
	
	@Column(nullable=false)
	private int cout;
	
	@ManyToOne
	@PrimaryKeyJoinColumn(name="id_partie")
	private Partie partie;
	
	@Column
	private int ordre_pioche;

	public Carte(int code_effet, int cout, Partie partie, int ordre_pioche) {
		super();
		this.code_effet = code_effet;
		this.cout = cout;
		this.partie = partie;
		this.ordre_pioche = ordre_pioche;
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
	public int getCode_effet() {
		return code_effet;
	}
	public void setCode_effet(int code_effet) {
		this.code_effet = code_effet;
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
	
	
	
}
