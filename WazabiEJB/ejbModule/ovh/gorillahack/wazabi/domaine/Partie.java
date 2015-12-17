package ovh.gorillahack.wazabi.domaine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "PARTIES", schema = "WAZABI")
public class Partie implements Serializable {
	private static final long serialVersionUID = -4647647034257120291L;
	
	public enum Sens {
		HORAIRE, ANTIHORAIRE
	}

	public enum Status {
		COMMENCE, PAS_COMMENCE, EN_ATTENTE, ANNULEE
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_partie;
	@Column(length = 20, nullable = false)
	private String nom;

	@Column(nullable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date timestamp_creation;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Sens sens;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Status statut;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_vainqueur")
	private Joueur vainqueur;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_courant")
	private JoueurPartie courant;

	@OneToMany(mappedBy = "partie")
	private List<JoueurPartie> joueursParties;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_partie")
	private List<Carte> pioche;
	
	@Column(name="ordre_pioche")
	private int ordrePioche;

	public Partie(String nom, Date timestamp_creation, Sens sens, Joueur vainqueur, List<Carte> pioche,
			JoueurPartie courant, Status statut) {
		super();
		this.nom = nom;
		this.timestamp_creation = timestamp_creation;
		this.sens = sens;
		this.vainqueur = vainqueur;
		if (pioche != null) {
			this.pioche = pioche;
			this.ordrePioche = pioche.size();
		} else {
			this.pioche = new ArrayList<>();
		}
		this.courant = courant;
		this.statut = statut;
	}

	public Partie() {
		super();
		pioche = new ArrayList<Carte>();
	}

	public int getId_partie() {
		return id_partie;
	}

	public void setId_partie(int id_partie) {
		this.id_partie = id_partie;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Date getTimestamp_creation() {
		return timestamp_creation;
	}

	public void setTimestamp_creation(Date timestamp_creation) {
		this.timestamp_creation = timestamp_creation;
	}

	public Sens getSens() {
		return sens;
	}

	public void setSens(Sens sens) {
		this.sens = sens;
	}

	public Joueur getVainqueur() {
		return vainqueur;
	}

	public void setVainqueur(Joueur vainqueur) {
		this.vainqueur = vainqueur;
	}

	public JoueurPartie getCourant() {
		return courant;
	}

	public void setCourant(JoueurPartie courant) {
		this.courant = courant;
	}

	public Status getStatut() {
		return statut;
	}

	public void setStatut(Status statut) {
		this.statut = statut;
	}

	public List<JoueurPartie> getJoueursParties() {
		return joueursParties;
	}

	public void setJoueursParties(List<JoueurPartie> joueursParties) {
		this.joueursParties = joueursParties;
	}

	public List<Carte> getPioche() {
		return pioche;
	}

	public void ajouterCarteALaPioche(Carte carte) {
		carte.setOrdre_pioche(ordrePioche);
		this.pioche.add(carte);
		ordrePioche++;
	}

	public Carte piocher() {
		if(!this.pioche.isEmpty()){
			Carte c = this.pioche.remove(0);
			c.setOrdre_pioche(ordrePioche);
			return c;
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((courant == null) ? 0 : courant.hashCode());
		result = prime * result + id_partie;
		result = prime * result + ((joueursParties == null) ? 0 : joueursParties.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((pioche == null) ? 0 : pioche.hashCode());
		result = prime * result + ((sens == null) ? 0 : sens.hashCode());
		result = prime * result + ((statut == null) ? 0 : statut.hashCode());
		result = prime * result + ((timestamp_creation == null) ? 0 : timestamp_creation.hashCode());
		result = prime * result + ((vainqueur == null) ? 0 : vainqueur.hashCode());
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
		Partie other = (Partie) obj;
		if (courant == null) {
			if (other.courant != null)
				return false;
		} else if (!courant.equals(other.courant))
			return false;
		if (id_partie != other.id_partie)
			return false;
		if (joueursParties == null) {
			if (other.joueursParties != null)
				return false;
		} else if (!joueursParties.equals(other.joueursParties))
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (pioche == null) {
			if (other.pioche != null)
				return false;
		} else if (!pioche.equals(other.pioche))
			return false;
		if (sens != other.sens)
			return false;
		if (statut != other.statut)
			return false;
		if (timestamp_creation == null) {
			if (other.timestamp_creation != null)
				return false;
		} else if (!timestamp_creation.equals(other.timestamp_creation))
			return false;
		if (vainqueur == null) {
			if (other.vainqueur != null)
				return false;
		} else if (!vainqueur.equals(other.vainqueur))
			return false;
		return true;
	}
}
