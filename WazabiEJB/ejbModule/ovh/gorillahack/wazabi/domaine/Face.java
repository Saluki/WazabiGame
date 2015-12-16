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
	private static final long serialVersionUID = -5666122132375907572L;

	public enum Valeur {
		WAZABI, PIOCHE, DE, AUTRE
	}

	@Id
	@Enumerated(EnumType.STRING)
	private final Valeur valeur_face;

	@Column(name = "nbFaces")
	private final int nbFaces;

	public Face(Valeur valeur_face, int nbFaces) {
		this.valeur_face = valeur_face;
		this.nbFaces = nbFaces;
	}

	public Face() {
		super();
		this.valeur_face = Valeur.AUTRE;
		this.nbFaces = 0;
	}

	public Valeur getValeur_face() {
		return valeur_face;
	}

	public int getNbFaces() {
		return nbFaces;
	}
}
