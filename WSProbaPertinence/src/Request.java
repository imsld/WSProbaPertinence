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
	private String pathRelevanceSet;
	private List<Service> listResultCos = new ArrayList<Service>();
	private List<Service> listResultLi = new ArrayList<Service>();
	private List<Service> listResultEj = new ArrayList<Service>();
	private List<Service> listResultJs = new ArrayList<Service>();
	private List<Service> listResultLog = new ArrayList<Service>();
	private String pathResult;
	private String fileResult;
	private DocumentBuilderFactory factory;
	private DocumentBuilder builder;
	private Document document;
	private Element racine;
	private NodeList racineNoeuds;

	public Request(String pathResult, String pathRelevanceSet, int requestID, String requestName) {
		super();
		this.requestID = requestID;
		this.requestName = requestName;
		this.pathResult = pathResult;
		this.pathRelevanceSet = pathRelevanceSet;

		factory = DocumentBuilderFactory.newInstance();
		fileResult = pathResult + "\\" + requestName;
		//fileRelevanceSet = pathRelevanceSet + "\\";//cherché la requette correspondante
		
		setResultCos(fileResult + "\\resulatscos.xml");
		setResultLi(fileResult + "\\resulatali.xml");
		setResultEj(fileResult + "\\resultatej.xml");
		setResultJs(fileResult + "\\resultatjs.xml");
		setResultLog(fileResult + "\\resulatalog.xml");
	}

	private void setResultCos(String xmlFile) {

		try {
			System.out.println(xmlFile);
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
							.getAttribute("Cosine"));

					Service service = new Service(iDService, iDRequete,
							scoreInput, scoreOutput, moyenne);
					listResultCos.add(service);
				}
			}

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

	}

	public void setResultLi(String xmlFile) {
		try {
			System.out.println(xmlFile);
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
					listResultCos.add(service);
				}
			}

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

	}

	public void setResultEj(String xmlFile) {
		try {
			System.out.println(xmlFile);
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
					listResultCos.add(service);
				}
			}

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

	}

	public void setResultJs(String xmlFile) {
		try {
			System.out.println(xmlFile);
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
					listResultCos.add(service);
				}
			}

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

	}

	public void setResultLog(String xmlFile) {
		try {
			System.out.println(xmlFile);
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
					listResultCos.add(service);
				}
			}

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

	}

	public int getRequestID() {
		return requestID;
	}

	public String getRequestName() {
		return requestName;
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
}
