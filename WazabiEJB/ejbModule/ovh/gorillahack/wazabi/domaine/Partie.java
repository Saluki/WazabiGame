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
import javax.persistence.PrimaryKeyJoinColumn;
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

	@OneToMany(mappedBy = "partie")
	private List<Carte> cartes;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_courant")
	private JoueurPartie courant;

	@OneToMany(mappedBy = "partie")
	private List<JoueurPartie> joueursParties;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_partie")
	private List<Carte> pioche;

	public Partie(String nom, Date timestamp_creation, Sens sens, Joueur vainqueur, List<Carte> cartes,
			JoueurPartie courant, Status statut) {
		super();
		this.nom = nom;
		this.timestamp_creation = timestamp_creation;
		this.sens = sens;
		this.vainqueur = vainqueur;
		this.cartes = cartes;
		this.courant = courant;
		this.statut = statut;
	}

	public Partie() {
		super();
		cartes = new ArrayList<Carte>();
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

	public List<Carte> getCartes() {
		return cartes;
	}

	public void setCartes(List<Carte> cartes) {
		this.cartes = cartes;
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

	public void setPioche(List<Carte> pioche) {
		this.pioche = pioche;
	}

}
