package ovh.gorillahack.wazabi.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
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

public class XmlParserImpl {

	public XmlParserImpl() {
	}

	/**
	 * Charge le fichier wazabi.xml contenant la paramétrisation de la partie,
	 * insère les valeurs dans la DB au moyen des DAO et insère le jeu de carte
	 * dans le singleton GestionPartie.
	 * 
	 * @throws XmlParsingException
	 */
	//A DETRUIRE
	public Map<String, Map<String, ?>> chargerXML() throws XmlParsingException {
		ClassLoader loader = getClass().getClassLoader();
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		XPath xpath = XPathFactory.newInstance().newXPath();

		// le fichier xml doit être dans le dossier source pour être chargé.
		try (InputStream xmlFile = loader.getResourceAsStream("wazabi.xml");) {
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document document = builder.parse(xmlFile);

			// on importe les dés et leurs faces
			Node deNode = (Node) xpath.compile("/wazabi/de").evaluate(document, XPathConstants.NODE);

		} catch (IOException | ParserConfigurationException | SAXException | XPathExpressionException e) {
			throw new XmlParsingException(e);
		}
		return null;
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
	 * @throws XmlParsingException 
	 */
	public Map<String, Integer> parseParametres() throws XmlParsingException {
		ClassLoader loader = getClass().getClassLoader();
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		XPath xpath = XPathFactory.newInstance().newXPath();

		// le fichier xml doit être dans le dossier source pour être chargé.
		try (InputStream xmlFile = loader.getResourceAsStream("wazabi.xml");) {
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document document = builder.parse(xmlFile);

			Node node = (Node) xpath.compile("wazabi").evaluate(document, XPathConstants.NODE);

			Map<String, Integer> params = new HashMap<String, Integer>();
			Number nbCartesParJoueur = (Number) xpath.compile("@nbCartesParJoueur").evaluate(node,
					XPathConstants.NUMBER);
			Number nbCartesTotal = (Number) xpath.compile("@nbCartesTotal").evaluate(node, XPathConstants.NUMBER);
			Number minJoueurs = (Number) xpath.compile("@minJoueurs").evaluate(node, XPathConstants.NUMBER);
			Number maxJoueurs = (Number) xpath.compile("@maxJoueurs").evaluate(node, XPathConstants.NUMBER);
	
			params.put("MIN_JOUEURS", minJoueurs.intValue());
			params.put("MAX_JOUEURS", maxJoueurs.intValue());
			params.put("NB_CARTES_PAR_JOUEUR", nbCartesParJoueur.intValue());
			params.put("NB_CARTES_TOTAL", nbCartesTotal.intValue());
			
			node = (Node) xpath.compile("/wazabi/de").evaluate(document, XPathConstants.NODE);
			Number nbDesParJoueur = (Number) xpath.compile("@nbParJoueur").evaluate(node, XPathConstants.NUMBER);
			Number nbTotalDes = (Number) xpath.compile("@nbTotalDes").evaluate(node, XPathConstants.NUMBER);
			params.put("NB_DES_PAR_JOUEUR", nbDesParJoueur.intValue());
			params.put("NB_DES_TOTAL", nbTotalDes.intValue());	
			return params;		
		} catch (IOException | ParserConfigurationException | SAXException | XPathExpressionException e) {
			throw new XmlParsingException(e);
		}
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
	public List<Face> parseDes() throws XmlParsingException {
		List<Face> faces = new ArrayList<Face>();
		
		ClassLoader loader = getClass().getClassLoader();
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		XPath xpath = XPathFactory.newInstance().newXPath();

		// le fichier xml doit être dans le dossier source pour être chargé.
		try (InputStream xmlFile = loader.getResourceAsStream("wazabi.xml");) {
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document document = builder.parse(xmlFile);

			// on importe les dés et leurs faces
			Node deNode = (Node) xpath.compile("/wazabi/de").evaluate(document, XPathConstants.NODE);
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
					faces.add(face);
				}
			}
			return faces;
		} catch (IOException | ParserConfigurationException | SAXException | XPathExpressionException e) {
			throw new XmlParsingException(e);
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
	public Map<CarteEffet,Integer> parseCartesEffet() throws XmlParsingException {
		ClassLoader loader = getClass().getClassLoader();
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		XPath xpath = XPathFactory.newInstance().newXPath();

		// le fichier xml doit être dans le dossier source pour être chargé.
		try (InputStream xmlFile = loader.getResourceAsStream("wazabi.xml");) {
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document document = builder.parse(xmlFile);
			Node wazabiNode = (Node) xpath.compile("wazabi").evaluate(document, XPathConstants.NODE);

			
			NodeList cartesNodes = (NodeList) xpath.compile("./carte").evaluate(wazabiNode, XPathConstants.NODESET);
			// on crée une hashmap afin de ne créer qu'une seule fois
			// chaque CarteEffet, ceux ci correspondant à un type de carte
			// (unique donc)
			HashMap<Integer, CarteEffet> hashmap = new HashMap<Integer, CarteEffet>();
			
			//Map a renvoyer: La carte comme clé et le nombre de ce type de carte dans le paquet
			Map<CarteEffet, Integer> cartesEffet = new HashMap<CarteEffet, Integer>();
	
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
				if (!hashmap.containsKey(codeEffet.intValue())) {
					carteEffet = hashmap.get(codeEffet.intValue());
					if (inputString == "") {
						carteEffet = new CarteEffet(codeEffet.intValue(), effet, description, cout.intValue());
					} else if (inputString.equals("aucun") || inputString.equals("sens") || inputString.equals("joueur")) {
						Input input = Input.valueOf(inputString.toUpperCase());
						carteEffet = new CarteEffet(codeEffet.intValue(), effet, description, cout.intValue(), input);
					} else {
						throw new XmlParsingException("Type d'input pour les cartes non renconnu : " + inputString);
					}
					// on enregistre les types de carte dans la DB.
					//carteEffetDaoImpl.enregistrer(carteEffet);
					hashmap.put(codeEffet.intValue(), carteEffet);
					cartesEffet.put(carteEffet, nb.intValue());
				}
			}
			return cartesEffet;
		} catch (IOException | ParserConfigurationException | SAXException | XPathExpressionException e) {
			throw new XmlParsingException(e);
		}
	}
	
	public List<Carte> parseCarte(Map<CarteEffet, Integer> map){
		List<Carte> cartes = new ArrayList<Carte>();
		for(CarteEffet ce: map.keySet()){
			for(int i = 0; i<map.get(ce);i++)
				cartes.add(new Carte(ce));
		}
		return cartes;
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

}
