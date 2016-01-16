import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Request {
	private int requestID;
	private String requestName;
	private List<String> listRelevantSet = new ArrayList<String>();

	private List<Service> listResultCos = new ArrayList<Service>();
	private List<Service> listResultLi = new ArrayList<Service>();
	private List<Service> listResultEj = new ArrayList<Service>();
	private List<Service> listResultJs = new ArrayList<Service>();
	private List<Service> listResultLog = new ArrayList<Service>();
	private List<Service> listResultatProb = new ArrayList<Service>();
	
	private List<Double> pourcentageServicePerResultCos = new ArrayList<Double>();
	private List<Double> pourcentageServicePerResultLi = new ArrayList<Double>();
	private List<Double> pourcentageServicePerResultEj = new ArrayList<Double>();
	private List<Double> pourcentageServicePerResultJs = new ArrayList<Double>();
	private List<Double> pourcentageServicePerResultLog = new ArrayList<Double>();

	
	int nbrService = 0;

	private double probabilite = 0;

	//private String pathResult;
	private String fileResult;
	private String fileRelevanceSet;
	private DocumentBuilderFactory factory;
	private DocumentBuilder builder;
	private Document document;
	private Element racine;
	private NodeList racineNoeuds;

	private int nombreCluster;

	public void trierListMapp() {

		for (int i = 0; i < listResultatProb.size() - 1; i++) {
			double score1 = listResultatProb.get(i).calcul_D_ProbFuse;
			for (int j = i + 1; j < listResultatProb.size(); j++) {
				double score2 = listResultatProb.get(j).calcul_D_ProbFuse;
				if (score2 > score1) {
					listResultatProb.add(i, listResultatProb.remove(j));
					score1 = score2;
				}
			}
		}

	}

	public Request(String pathResult, String pathRelevanceSet, int requestID,
			String requestName, int k) {
		super();
		this.requestID = requestID;
		this.requestName = requestName;
		//this.pathResult = pathResult;
		// this.probabilite = probabilite;

		this.nombreCluster = k;

		factory = DocumentBuilderFactory.newInstance();
		fileResult = pathResult + "\\" + requestName;
		fileRelevanceSet = pathRelevanceSet + "\\"
				+ getRequestRelevanteSet(pathRelevanceSet, requestName);

		setlistRelevantSet(fileRelevanceSet);

		setResultCos(fileResult + "\\resulatscos.xml");
		setResultLi(fileResult + "\\resulatali.xml");
		setResultEj(fileResult + "\\resultatej.xml");
		setResultJs(fileResult + "\\resultatjs.xml");
		setResultLog(fileResult + "\\resulatalog.xml");
		setPourcentage();

	}

	public void setPourcentage() {
		// Initialisation des tableaux de pourcentage de services pertinant pour
		// chaque cluster, cela pour les 5 méthodes différentes
		// on a 5 tableau (le nombre de méthodes), où la taille des tableau est
		// identique (le nombre de cluster). et dans chaque case de tableau on
		// sauvegarde le pourcentage de services pertinant du cluster
		nbrService = listResultCos.size();

		pourcentageServicePerResultCos.clear();
		pourcentageServicePerResultCos = calculMethodeCluster(listResultCos);

		pourcentageServicePerResultEj.clear();
		pourcentageServicePerResultEj = calculMethodeCluster(listResultEj);

		pourcentageServicePerResultJs.clear();
		pourcentageServicePerResultJs = calculMethodeCluster(listResultJs);

		pourcentageServicePerResultLi.clear();
		pourcentageServicePerResultLi = calculMethodeCluster(listResultLi);

		pourcentageServicePerResultLog.clear();
		pourcentageServicePerResultLog = calculMethodeCluster(listResultLog);
	}

	public double getProbabilite() {
		return probabilite;
	}

	public void setProbabilite(double probabilite) {
		this.probabilite = probabilite;
	}

	private void setlistRelevantSet(String fileRelevanceSet) {

		File repertoireRelevantSet = new File(fileRelevanceSet);
		File[] f = repertoireRelevantSet.listFiles();
		int totalResult = repertoireRelevantSet.listFiles().length;
		for (int i = 0; i < totalResult; i++) {
			if (f[i].isFile()) {
				listRelevantSet.add(f[i].getName());
			}
		}
	}

	private void setResultCos(String xmlFile) {

		try {
			builder = factory.newDocumentBuilder();
			document = builder.parse(xmlFile);
			racine = document.getDocumentElement();
			racineNoeuds = racine.getChildNodes();
			Element serviceNode;
			String iDService, iDRequete;
			double scoreInput, scoreOutput, moyenne;
			int nbRacineNoeuds = racineNoeuds.getLength();
			for (int i = 0; i < nbRacineNoeuds; i++) {
				if (racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {

					serviceNode = (Element) racineNoeuds.item(i);
					iDService = serviceNode.getAttribute("ID");
					iDService = iDService.substring(iDService.indexOf('*') + 1,
							iDService.length());
					iDRequete = requestName;
					scoreInput = Double.parseDouble(serviceNode
							.getAttribute("Input"));
					scoreOutput = Double.parseDouble(serviceNode
							.getAttribute("Output"));
					moyenne = Double.parseDouble(serviceNode
							.getAttribute("Cosine"));

					Service service = new Service(iDService, iDRequete,
							scoreInput, scoreOutput, moyenne);
					listResultCos.add(service);
				}
			}
			/*
			 * document.getDocumentElement().normalize(); writeInFile(document,
			 * xmlFile);
			 */

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

	}

	public void setResultLi(String xmlFile) {
		try {
			builder = factory.newDocumentBuilder();
			document = builder.parse(xmlFile);
			racine = document.getDocumentElement();
			racineNoeuds = racine.getChildNodes();
			Element serviceNode;
			String iDService, iDRequete;
			double scoreInput, scoreOutput, moyenne;
			int nbRacineNoeuds = racineNoeuds.getLength();
			for (int i = 0; i < nbRacineNoeuds; i++) {
				if (racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {

					serviceNode = (Element) racineNoeuds.item(i);
					iDService = serviceNode.getAttribute("ID");
					iDRequete = requestName;
					scoreInput = Double.parseDouble(serviceNode
							.getAttribute("Input"));
					scoreOutput = Double.parseDouble(serviceNode
							.getAttribute("Output"));
					moyenne = Double.parseDouble(serviceNode
							.getAttribute("InformationLoss"));

					Service service = new Service(iDService, iDRequete,
							scoreInput, scoreOutput, moyenne);
					listResultLi.add(service);

				}
			}
			/*
			 * document.getDocumentElement().normalize(); writeInFile(document,
			 * xmlFile);
			 */
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

	}

	public void setResultEj(String xmlFile) {
		try {
			builder = factory.newDocumentBuilder();
			document = builder.parse(xmlFile);
			racine = document.getDocumentElement();
			racineNoeuds = racine.getChildNodes();
			Element serviceNode;
			String iDService, iDRequete;
			double scoreInput, scoreOutput, moyenne;
			int nbRacineNoeuds = racineNoeuds.getLength();
			for (int i = 0; i < nbRacineNoeuds; i++) {
				if (racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {

					serviceNode = (Element) racineNoeuds.item(i);
					iDService = serviceNode.getAttribute("ID");
					iDRequete = requestName;
					scoreInput = Double.parseDouble(serviceNode
							.getAttribute("Input"));
					scoreOutput = Double.parseDouble(serviceNode
							.getAttribute("Output"));
					moyenne = Double.parseDouble(serviceNode
							.getAttribute("ExtendedJaccard"));

					Service service = new Service(iDService, iDRequete,
							scoreInput, scoreOutput, moyenne);
					listResultEj.add(service);
				}
			}
			/*
			 * document.getDocumentElement().normalize(); writeInFile(document,
			 * xmlFile);
			 */
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

	}

	public void setResultJs(String xmlFile) {
		try {
			builder = factory.newDocumentBuilder();
			document = builder.parse(xmlFile);
			racine = document.getDocumentElement();
			racineNoeuds = racine.getChildNodes();
			Element serviceNode;
			String iDService, iDRequete;
			double scoreInput, scoreOutput, moyenne;
			int nbRacineNoeuds = racineNoeuds.getLength();
			for (int i = 0; i < nbRacineNoeuds; i++) {
				if (racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {

					serviceNode = (Element) racineNoeuds.item(i);
					iDService = serviceNode.getAttribute("ID");
					iDRequete = requestName;
					scoreInput = Double.parseDouble(serviceNode
							.getAttribute("Input"));
					scoreOutput = Double.parseDouble(serviceNode
							.getAttribute("Output"));
					moyenne = Double.parseDouble(serviceNode
							.getAttribute("JansonShanon"));

					Service service = new Service(iDService, iDRequete,
							scoreInput, scoreOutput, moyenne);
					listResultJs.add(service);
				}
			}
			/*
			 * document.getDocumentElement().normalize(); writeInFile(document,
			 * xmlFile);
			 */

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

	}

	public void setResultLog(String xmlFile) {
		try {
			builder = factory.newDocumentBuilder();
			document = builder.parse(xmlFile);
			racine = document.getDocumentElement();
			racineNoeuds = racine.getChildNodes();
			Element serviceNode;
			String iDService, iDRequete;
			double scoreInput, scoreOutput, moyenne;
			int nbRacineNoeuds = racineNoeuds.getLength();
			for (int i = 0; i < nbRacineNoeuds; i++) {
				if (racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {

					serviceNode = (Element) racineNoeuds.item(i);
					iDService = serviceNode.getAttribute("ID");
					iDRequete = requestName;
					scoreInput = Double.parseDouble(serviceNode
							.getAttribute("Input"));
					scoreOutput = Double.parseDouble(serviceNode
							.getAttribute("Output"));
					moyenne = Double.parseDouble(serviceNode
							.getAttribute("Logique"));

					Service service = new Service(iDService, iDRequete,
							scoreInput, scoreOutput, moyenne);
					listResultLog.add(service);
				}
			}
			/*
			 * document.getDocumentElement().normalize(); writeInFile(document,
			 * xmlFile);
			 */
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

	}

	private String getRequestRelevanteSet(String pathRelevanceSet,
			String requestName) {
		String requestNameRelevantSet = null;
		String name;
		File repertoireRelevantSet = new File(pathRelevanceSet);
		File[] f = repertoireRelevantSet.listFiles();
		int totalResult = repertoireRelevantSet.listFiles().length;
		for (int i = 0; i < totalResult; i++) {
			if (f[i].isDirectory()) {
				name = f[i].getName();
				name = name.substring(name.indexOf('-') + 1, name.length());
				if (requestName.equals(name))
					requestNameRelevantSet = f[i].getName();
			}
		}
		return requestNameRelevantSet;
	}

	public int getRequestID() {
		return requestID;
	}

	public String getRequestName() {
		return requestName;
	}

	public List<String> getListRelevantSet() {
		return listRelevantSet;
	}

	public List<Service> getResultCos() {
		return listResultCos;
	}

	public List<Service> getResultLi() {
		return listResultLi;
	}

	public List<Service> getResultEj() {
		return listResultEj;
	}

	public List<Service> getResultJs() {
		return listResultJs;
	}

	public List<Service> getResultLog() {
		return listResultLog;
	}

	public List<Double> getPourcentageServicePerResultCos() {
		return pourcentageServicePerResultCos;
	}

	public List<Double> getPourcentageServicePerResultLi() {
		return pourcentageServicePerResultLi;
	}

	public List<Double> getPourcentageServicePerResultEj() {
		return pourcentageServicePerResultEj;
	}

	public List<Double> getPourcentageServicePerResultJs() {
		return pourcentageServicePerResultJs;
	}

	public List<Double> getPourcentageServicePerResultLog() {
		return pourcentageServicePerResultLog;
	}

	public void setPourcentageServicePerResultCos(
			List<Double> pourcentageServicePerResultCos) {
		this.pourcentageServicePerResultCos = pourcentageServicePerResultCos;
	}

	public void setPourcentageServicePerResultLi(
			List<Double> pourcentageServicePerResultLi) {
		this.pourcentageServicePerResultLi = pourcentageServicePerResultLi;
	}

	public void setPourcentageServicePerResultEj(
			List<Double> pourcentageServicePerResultEj) {
		this.pourcentageServicePerResultEj = pourcentageServicePerResultEj;
	}

	public void setPourcentageServicePerResultJs(
			List<Double> pourcentageServicePerResultJs) {
		this.pourcentageServicePerResultJs = pourcentageServicePerResultJs;
	}

	public void setPourcentageServicePerResultLog(
			List<Double> pourcentageServicePerResultLog) {
		this.pourcentageServicePerResultLog = pourcentageServicePerResultLog;
	}

	/*
	 * private void writeInFile(Document document, String filePath) {
	 * 
	 * File xmlFile = null;
	 * 
	 * TransformerFactory transformerFactory = TransformerFactory
	 * .newInstance(); Transformer transformer;
	 * 
	 * try { transformer = transformerFactory.newTransformer(); // for pretty
	 * print transformer.setOutputProperty(OutputKeys.INDENT, "yes"); DOMSource
	 * source = new DOMSource(document);
	 * 
	 * // write to console or file // StreamResult console = new
	 * StreamResult(System.out); StreamResult file = new StreamResult(new
	 * File(filePath));
	 * 
	 * // write data // transformer.transform(source, console);
	 * transformer.transform(source, file);
	 * 
	 * xmlFile = new File(filePath);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * }
	 */
	private List<Double> calculMethodeCluster(List<Service> listSerParMethode) {

		// Méthode qui retourne un tbaleau de valuer de pourcentage de service
		// pertinant dans chaque cluster. Exemple: si on a 14 services divisés
		// sur 3 Clusters, le resultat de cette méthode sera un tableau de
		// taille
		// 3 dont chaque case contient le pourcentage de services pertinant de
		// chaque cluster

		int nbtotal = 0;

		int borneSup = -1;
		List<Double> PSerPertinant = new ArrayList<Double>();

		// Calculer la taille des cluster d'une manière équitable
		// exp : Si on 14 services et le nombre de cluster est de 3, on doit
		// avoir la proportion suivante:5,5,4
		int rest = nbrService % nombreCluster;
		int interval = (nbrService / nombreCluster);
		if (rest != 0)
			rest = rest + interval - 1;

		for (int pos = 0; pos < nombreCluster; pos++) {
			int borneInf = borneSup + 1;

			if (rest != 0) {
				borneSup = borneInf + interval;
				rest--;
			} else
				borneSup = borneInf + interval - 1;

			if ((borneSup + 1) > (nbrService - 1)) {
				borneSup = (nbrService - 1);
			}

			int totalServicePertinantInInterval = 0;

			if (nbtotal != listRelevantSet.size())
				for (int j = borneInf; j <= borneSup; j++) {
					String IDService = listSerParMethode.get(j).getIDService();
					listSerParMethode.get(j).setPositionK(pos);
					IDService = IDService.substring(IDService.indexOf('*') + 1,
							IDService.length());
					if (listRelevantSet.contains(IDService)) {
						totalServicePertinantInInterval = totalServicePertinantInInterval + 1;
						listSerParMethode.get(j).setPertinant(true);
					}
				}
			for (int j = borneInf; j <= borneSup; j++) {
				listSerParMethode.get(j).setPositionK(pos + 1);
			}
			nbtotal = nbtotal + totalServicePertinantInInterval;
			double taille = (borneSup - borneInf) + 1;
			double p = ((totalServicePertinantInInterval) / (taille));
			PSerPertinant.add(p);
		}
		return PSerPertinant;
	}

	public int getNombreCluster() {
		return nombreCluster;
	}

	public void setNombreCluster(int nombreCluster) {
		this.nombreCluster = nombreCluster;
	}
	public List<Service> getListResultatProb() {
		return listResultatProb;
	}

	public void setListResultatProb(List<Service> listResultatProb) {
		this.listResultatProb = listResultatProb;
	}

}
