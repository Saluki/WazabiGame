package ovh.gorillahack.wazabi.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ovh.gorillahack.wazabi.domaine.Face;
import ovh.gorillahack.wazabi.domaine.Face.Valeur;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.JoueurPartie;
import ovh.gorillahack.wazabi.domaine.Partie;
import ovh.gorillahack.wazabi.domaine.Partie.Sens;
import ovh.gorillahack.wazabi.domaine.Partie.Status;

@SuppressWarnings("serial")
@Stateful
@Local(Dao.class)
@LocalBean
public class PartieDaoImpl extends DaoImpl<Partie> {
	protected static int ordre = 0;
	@EJB
	private JoueurPartieDaoImpl joueurPartieDao;
	@EJB
	private CarteDaoImpl carteDaoImpl;
	@EJB
	private DeDaoImpl deDaoImpl;
	@EJB
	private FaceDaoImpl faceDaoImpl;

	public PartieDaoImpl() {
		super(Partie.class);
	}

	@PersistenceContext(unitName = "wazabi")
	private EntityManager entityManager;

	private boolean chargerXML() {
		try {
			InputStream xmlFile = new FileInputStream("wazabi.xml");
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document document = builder.parse(xmlFile);

			XPath xpath = XPathFactory.newInstance().newXPath();
			Node wazabiNode = (Node) xpath.compile("wazabi").evaluate(document, XPathConstants.NODE);

			// on importe les paramètres généraux
			Number nbCartesParJoueur = (Number) xpath.compile("@nbCartesParJoueur").evaluate(wazabiNode,
					XPathConstants.NUMBER);
			Number nbCartesTotal = (Number) xpath.compile("@nbCartesTotal").evaluate(wazabiNode, XPathConstants.NUMBER);
			Number minJoueurs = (Number) xpath.compile("@minJoueurs").evaluate(wazabiNode, XPathConstants.NUMBER);
			Number maxJoueurs = (Number) xpath.compile("@maxJoueurs").evaluate(wazabiNode, XPathConstants.NUMBER);

			// on importe les dés et leurs faces
			Node deNode = (Node) xpath.compile("/wazabi/de").evaluate(document, XPathConstants.NODE);
			Number nbDesParJoueur = (Number) xpath.compile("@nbParJoueur").evaluate(deNode, XPathConstants.NUMBER);
			Number nbTotalDes = (Number) xpath.compile("@nbTotalDes").evaluate(deNode, XPathConstants.NUMBER);

			NodeList desNodes = (NodeList) xpath.compile(".face").evaluate(deNode, XPathConstants.NODESET);
			for (int i = 0; i < desNodes.getLength(); i++) {
				Node faceNode = desNodes.item(i);
				String valeur = (String) xpath.compile("@identif").evaluate(faceNode, XPathConstants.STRING);
				Number nbFaces = (Number) xpath.compile("@nbTotalDes").evaluate(faceNode, XPathConstants.NUMBER);
				Face face = null;
				switch (valeur) {
				case "w":
					face = new Face(Valeur.WAZABI, nbFaces.intValue());
					break;
				case "d":
					face = new Face(Valeur.DE, nbFaces.intValue());
					break;
				case "c":
					face = new Face(Valeur.PIOCHE, nbFaces.intValue());
					break;
				default:
					//
				}
				if (face != null) {
					faceDaoImpl.enregistrer(face);
				}
			}

			// on importe les cartes
			NodeList cartesNodes = (NodeList) xpath.compile(".carte").evaluate(wazabiNode, XPathConstants.NODESET);
			for (int i = 0; i < cartesNodes.getLength(); i++) {
				Node node = cartesNodes.item(i);
				String description = (String) xpath.compile(".").evaluate(node, XPathConstants.STRING);
				Number cout = (Number) xpath.compile("@cout").evaluate(node, XPathConstants.NUMBER);
				String effet = (String) xpath.compile("@effet").evaluate(node, XPathConstants.STRING);
				Number codeEffet = (Number) xpath.compile("@codeEfet").evaluate(node, XPathConstants.NUMBER);
				Number nb = (Number) xpath.compile("@nb").evaluate(node, XPathConstants.NUMBER);

				carteDaoImpl.creerCartes(description, cout.intValue(), codeEffet.intValue(), effet, nb.intValue());
				
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return true;
	}

	public Partie creerUnePartie(String nom) {
		chargerXML();
		return super.enregistrer(new Partie(nom, new Date(), Sens.HORAIRE, null, null, null, Status.EN_ATTENTE));
	}

	public Partie rejoindrePartie(Joueur j) {
		JoueurPartie jp = new JoueurPartie(ordre++, 0, deDaoImpl.getDes(j), carteDaoImpl.getCartes(j));
		Partie p = getPartieCourante();
		if (p == null || p.getStatut() == Partie.Status.PAS_COMMENCE) {
			return null;
		}
		jp.setPartie(p);
		jp.setJoueur(j);
		joueurPartieDao.enregistrer(jp);
		return super.enregistrer(p);
	}

	public List<Partie> afficherHistorique(Joueur j) {
		return super.liste("SELECT p FROM Partie p WHERE EXISTS("
				+ "SELECT jp FROM JoueurPartie jp WHERE jp.joueur = ?1 AND jp.partie = p.id_partie)", j);
	}

	public Partie getPartieCourante() {
		return super.recherche("SELECT p FROM Partie p WHERE p.id_partie = (SELECT MAX(p.id_partie) FROM Partie p)");
	}
}
