package ovh.gorillahack.wazabi.domaine;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Table de définition des faces de dé et leurs pourcentages
 * 
 * @author Alexandre
 *
 */
@Entity
@Table(name = "FACE", schema = "WAZABI")
public class Face implements Serializable {
	public enum Valeur {
		WAZABI, PIOCHE, DE
	}

	@Id
	@Enumerated(EnumType.STRING)
	private final Valeur valeur_face;
	
	@Column(name="nbFaces")
	private final int nbFaces;

	public Face(Valeur valeur_face, int nbFaces) {
		this.valeur_face = valeur_face;
		this.nbFaces = nbFaces;
	}

	public Valeur getValeur_face() {
		return valeur_face;
	}

	public int getNbFaces() {
		return nbFaces;
	}
}
