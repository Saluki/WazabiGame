package ovh.gorillahack.wazabi.domaine;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "JOUEURS_PARTIE", schema = "WAZABI")
public class JoueurPartie {
	@EmbeddedId
	private JoueurPartiePK pk;
	@Column
	private int ordre_joueur;
	@Column
	private int compteur_sauts;
	

	public JoueurPartiePK getPk() {
		return pk;
	}

	@Embeddable
	class JoueurPartiePK implements Serializable {
		private static final long serialVersionUID = 3698746362647339758L;
		
		@ManyToOne
		@PrimaryKeyJoinColumn(name="id_joueur")
		private Joueur joueur;
		@ManyToOne
		@PrimaryKeyJoinColumn(name="id_partie")
		private Partie partie;

		public JoueurPartiePK() {
		}

		public JoueurPartiePK(Joueur joueur, Partie partie) {
			this.joueur = joueur;
			this.partie = partie;
		}

		public Joueur getJoueur() {
			return joueur;
		}

		public void setJoueur(Joueur joueur) {
			this.joueur = joueur;
		}

		public Partie getPartie() {
			return partie;
		}

		public void setPartie(Partie partie) {
			this.partie = partie;
		}

		//auto generated
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((joueur == null) ? 0 : joueur.hashCode());
			result = prime * result + ((partie == null) ? 0 : partie.hashCode());
			return result;
		}

		//auto generated
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			JoueurPartiePK other = (JoueurPartiePK) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (joueur == null) {
				if (other.joueur != null)
					return false;
			} else if (!joueur.equals(other.joueur))
				return false;
			if (partie == null) {
				if (other.partie != null)
					return false;
			} else if (!partie.equals(other.partie))
				return false;
			return true;
		}

		private JoueurPartie getOuterType() {
			return JoueurPartie.this;
		}

	}

	public JoueurPartie(int ordre_joueur, int compteur_sauts) {
		super();
		this.ordre_joueur = ordre_joueur;
		this.compteur_sauts = compteur_sauts;
	}

	public JoueurPartie() {
		super();
	}

	public int getOrdre_joueur() {
		return ordre_joueur;
	}

	public void setOrdre_joueur(int ordre_joueur) {
		this.ordre_joueur = ordre_joueur;
	}

	public int getCompteur_sauts() {
		return compteur_sauts;
	}

	public void setCompteur_sauts(int compteur_sauts) {
		this.compteur_sauts = compteur_sauts;
	}

	public void setPk(JoueurPartiePK pk) {
		this.pk = pk;
	}
}