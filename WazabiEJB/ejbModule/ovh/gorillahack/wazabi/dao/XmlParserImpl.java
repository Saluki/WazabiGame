package ovh.gorillahack.wazabi.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
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

import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.CarteEffet;
import ovh.gorillahack.wazabi.domaine.CarteEffet.Input;
import ovh.gorillahack.wazabi.domaine.Face;
import ovh.gorillahack.wazabi.domaine.Face.Valeur;
import ovh.gorillahack.wazabi.exception.XmlParsingException;
import ovh.gorillahack.wazabi.usecases.GestionPartie;

@Local
@Stateless
public class XmlParserImpl {
	@EJB
	private FaceDaoImpl faceDaoImpl;
	@EJB
	private CarteDaoImpl carteDaoImpl;
	@EJB
	private CarteEffetDaoImpl carteEffetDaoImpl;
	@EJB
	private GestionPartie gestionPartie;
	@EJB
	private DeDaoImpl deDaoImpl;

	public XmlParserImpl() {
	}

	/**
	 * Charge le fichier wazabi.xml contenant la paramétrisation de la partie,
	 * insère les valeurs dans la DB au moyen des DAO et insère le jeu de carte
	 * dans le singleton GestionPartie.
	 * 
	 * @throws XmlParsingException
	 */
	public void chargerXML() throws XmlParsingException {
		ClassLoader loader = getClass().getClassLoader();
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		XPath xpath = XPathFactory.newInstance().newXPath();

		// le fichier xml doit être dans le dossier source pour être chargé.
		try (InputStream xmlFile = loader.getResourceAsStream("wazabi.xml");) {
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document document = builder.parse(xmlFile);

			Node wazabiNode = (Node) xpath.compile("wazabi").evaluate(document, XPathConstants.NODE);

			// on importe les paramètres généraux
			parseParametres(xpath, wazabiNode);

			// on importe les dés et leurs faces
			Node deNode = (Node) xpath.compile("/wazabi/de").evaluate(document, XPathConstants.NODE);
			parseDes(xpath, deNode);

			// on importe les cartes
			parseCartes(xpath, wazabiNode);

		} catch (IOException | ParserConfigurationException | SAXException | XPathExpressionException e) {
			throw new XmlParsingException(e);
		}
	}

	/**
	 * Parse tous les paramètres de partie contenus dans le fichier XML et les
	 * sets dans GestionPartie.
	 * 
	 * @param xpath
	 *            Une instance de l'évaluateur XPath.
	 * @param wazabiNode
	 *            Le noeud racine de notre XML.
	 * @throws XPathExpressionException
	 */
	private void parseParametres(XPath xpath, Node wazabiNode) throws XPathExpressionException {
		Number nbCartesParJoueur = (Number) xpath.compile("@nbCartesParJoueur").evaluate(wazabiNode,
				XPathConstants.NUMBER);
		Number nbCartesTotal = (Number) xpath.compile("@nbCartesTotal").evaluate(wazabiNode, XPathConstants.NUMBER);
		Number minJoueurs = (Number) xpath.compile("@minJoueurs").evaluate(wazabiNode, XPathConstants.NUMBER);
		Number maxJoueurs = (Number) xpath.compile("@maxJoueurs").evaluate(wazabiNode, XPathConstants.NUMBER);

		gestionPartie.setMin_joueurs(minJoueurs.intValue());
		gestionPartie.setMax_joueurs(maxJoueurs.intValue());
		gestionPartie.setNbCartesParJoueurs(nbCartesParJoueur.intValue());
		gestionPartie.setNbCartesTotal(nbCartesTotal.intValue());
	}

	/**
	 * Parse les définitions de dés et des faces contenues dans le XML et insère
	 * le nombre spécifié dans la DB et enregistre les définitions des faces
	 * dans la DB, ces dernières servant à calculer les probabilités que cette
	 * face soit obtenue au lancer.
	 * 
	 * @param xpath
	 *            Une instance de l'évaluateur XPath.
	 * @param deNode
	 *            Le noeud wazabi/de de notre XML.
	 * @throws XPathExpressionException
	 * @throws XmlParsingException
	 *             Si un identifiant de face inconnu est rencontré.
	 */
	private void parseDes(XPath xpath, Node deNode) throws XPathExpressionException, XmlParsingException {
		Number nbDesParJoueur = (Number) xpath.compile("@nbParJoueur").evaluate(deNode, XPathConstants.NUMBER);
		Number nbTotalDes = (Number) xpath.compile("@nbTotalDes").evaluate(deNode, XPathConstants.NUMBER);

		gestionPartie.setNbDesParJoueur(nbDesParJoueur.intValue());
		deDaoImpl.creerDes(nbTotalDes.intValue());

		// on itère sur chaque face du xml et on les enregistre dans la DB
		NodeList facesNodes = (NodeList) xpath.compile("./face").evaluate(deNode, XPathConstants.NODESET);
		for (int i = 0; i < facesNodes.getLength(); i++) {
			Node faceNode = facesNodes.item(i);
			Number nbFaces = (Number) xpath.compile("@nbFaces").evaluate(faceNode, XPathConstants.NUMBER);

			String valeur = (String) xpath.compile("@identif").evaluate(faceNode, XPathConstants.STRING);
			Face face = null;
			// on utilise l'attribut identif pour déterminer la face
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
				throw new XmlParsingException("Identifiant de face inconnu : " + valeur);
			}
			if (face != null) {
				// on enregistre dans la DB
				faceDaoImpl.enregistrer(face);
			}
		}
	}

	/**
	 * Parse les définitions de cartes contenues dans le XML, crée un jeu de
	 * carte correspondant et le set dans GestionPartie.
	 * 
	 * @param xpath
	 *            Une instance de l'évaluateur XPath.
	 * @param wazabiNode
	 *            Le noeud racine de notre XML.
	 * @throws XPathExpressionException
	 * @throws XmlParsingException
	 */
	private void parseCartes(XPath xpath, Node wazabiNode) throws XPathExpressionException, XmlParsingException {
		NodeList cartesNodes = (NodeList) xpath.compile("./carte").evaluate(wazabiNode, XPathConstants.NODESET);
		// on crée une hashmap afin de ne créer qu'une seule fois
		// chaque CarteEffet, ceux ci correspondant à un type de carte
		// (unique donc)
		HashMap<Integer, CarteEffet> hashmap = new HashMap<Integer, CarteEffet>();
		ArrayList<Carte> paquetDeCarte = new ArrayList<>();

		// on itère sur les différents types de carte
		for (int i = 0; i < cartesNodes.getLength(); i++) {
			Node node = cartesNodes.item(i);
			String description = (xpath.compile("normalize-space(.)").evaluate(node));
			Number cout = (Number) xpath.compile("@cout").evaluate(node, XPathConstants.NUMBER);
			String effet = xpath.compile("@effet").evaluate(node);
			Number codeEffet = (Number) xpath.compile("@codeEffet").evaluate(node, XPathConstants.NUMBER);
			String inputString = xpath.compile("@input").evaluate(node);
			Number nb = (Number) xpath.compile("@nb").evaluate(node, XPathConstants.NUMBER);

			CarteEffet carteEffet = null;
			if (hashmap.containsKey(codeEffet.intValue())) {
				carteEffet = hashmap.get(codeEffet.intValue());
			} else {
				if (inputString == "") {
					carteEffet = new CarteEffet(codeEffet.intValue(), effet, description, cout.intValue());
				} else if (inputString.equals("aucun") || inputString.equals("sens") || inputString.equals("joueur")) {
					Input input = Input.valueOf(inputString.toUpperCase());
					carteEffet = new CarteEffet(codeEffet.intValue(), effet, description, cout.intValue(), input);
				} else {
					throw new XmlParsingException("Type d'input pour les cartes non renconnu : " + inputString);
				}
				// on enregistre les types de carte dans la DB.
				carteEffetDaoImpl.enregistrer(carteEffet);
				hashmap.put(codeEffet.intValue(), carteEffet);
			}
			paquetDeCarte.addAll(creerCartes(carteEffet, nb.intValue()));
		}
		// on ne les enregistre pas dans la DB, cela se fait à la création d'une
		// partie, les cartes étant liées à celle-ci.
		gestionPartie.setJeuDeCarte(paquetDeCarte);
	}

	/**
	 * Crée un certain nombre de cartes.
	 * 
	 * @param carteEffet
	 *            Le type de cartes désiré.
	 * @param nombre
	 *            Le nombre de cartes désiré.
	 * @return Une liste contenant les cartes demandées.
	 */
	private List<Carte> creerCartes(CarteEffet carteEffet, int nombre) {
		ArrayList<Carte> list = new ArrayList<>(nombre);
		for (int i = 0; i < nombre; i++) {
			Carte carte = new Carte(carteEffet);
			list.add(carte);
		}
		return list;
	}

}
