package ovh.gorillahack.wazabi.dao;

import java.io.FileNotFoundException;
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
import ovh.gorillahack.wazabi.domaine.Face;
import ovh.gorillahack.wazabi.domaine.Face.Valeur;
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
		// TODO Auto-generated constructor stub
	}

	public boolean chargerXML() {
		try {
			ClassLoader loader = getClass().getClassLoader();
			InputStream xmlFile = loader.getResourceAsStream("wazabi.xml");
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

			gestionPartie.setMin_joueurs(minJoueurs.intValue());
			gestionPartie.setMax_joueurs(maxJoueurs.intValue());
			gestionPartie.setNbCartesParJoueurs(nbCartesParJoueur.intValue());
			gestionPartie.setNbCartesTotal(nbCartesTotal.intValue());

			// on importe les dés et leurs faces
			Node deNode = (Node) xpath.compile("/wazabi/de").evaluate(document, XPathConstants.NODE);
			Number nbDesParJoueur = (Number) xpath.compile("@nbParJoueur").evaluate(deNode, XPathConstants.NUMBER);
			Number nbTotalDes = (Number) xpath.compile("@nbTotalDes").evaluate(deNode, XPathConstants.NUMBER);

			gestionPartie.setNbDesParJoueur(nbDesParJoueur.intValue());
			deDaoImpl.creerDes(nbTotalDes.intValue());

			NodeList desNodes = (NodeList) xpath.compile("./face").evaluate(deNode, XPathConstants.NODESET);
			for (int i = 0; i < desNodes.getLength(); i++) {
				Node faceNode = desNodes.item(i);
				String valeur = (String) xpath.compile("@identif").evaluate(faceNode, XPathConstants.STRING);
				Number nbFaces = (Number) xpath.compile("@nbFaces").evaluate(faceNode, XPathConstants.NUMBER);
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
			NodeList cartesNodes = (NodeList) xpath.compile("./carte").evaluate(wazabiNode, XPathConstants.NODESET);
			//on crée une hashmap afin de ne créer qu'une seule fois chaque CarteEffet
			HashMap<Integer, CarteEffet> hashmap = new HashMap<Integer, CarteEffet>();
			ArrayList<Carte> paquetDeCarte = new ArrayList<>();
			
			for (int i = 0; i < cartesNodes.getLength(); i++) {
				Node node = cartesNodes.item(i);
				String description = ((String) xpath.compile("normalize-space(.)").evaluate(node,
						XPathConstants.STRING));
				Number cout = (Number) xpath.compile("@cout").evaluate(node, XPathConstants.NUMBER);
				String effet = (String) xpath.compile("@effet").evaluate(node, XPathConstants.STRING);
				Number codeEffet = (Number) xpath.compile("@codeEffet").evaluate(node, XPathConstants.NUMBER);
				Number nb = (Number) xpath.compile("@nb").evaluate(node, XPathConstants.NUMBER);
				CarteEffet carteEffet = null;
				if (hashmap.containsKey(codeEffet.intValue())) {
					carteEffet = hashmap.get(codeEffet.intValue());
				} else {
					carteEffet = new CarteEffet(codeEffet.intValue(), effet, description);
					carteEffetDaoImpl.enregistrer(carteEffet);
					hashmap.put(codeEffet.intValue(), carteEffet);
				}
				paquetDeCarte.addAll(creerCartes(cout.intValue(), carteEffet, nb.intValue()));
			}
			gestionPartie.setJeuDeCarte(paquetDeCarte);

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
	
	public List<Carte> creerCartes(int cout, CarteEffet carteEffet, int nombre) {
		ArrayList<Carte> list = new ArrayList<>();
		for (int i = 0; i < nombre; i++) {
			Carte carte = new Carte(carteEffet, cout);
			list.add(carte);
		}
		return list;
	}

}
