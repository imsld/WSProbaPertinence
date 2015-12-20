import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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
	private List<Integer> nbservicePerResultCos = new ArrayList<Integer>();
	private List<Integer> nbservicePerResultLi = new ArrayList<Integer>();
	private List<Integer> nbservicePerResultEj = new ArrayList<Integer>();
	private List<Integer> nbservicePerResultJs = new ArrayList<Integer>();
	private List<Integer> nbservicePerResultLog = new ArrayList<Integer>();

	private String pathResult;
	private String fileResult;
	private String fileRelevanceSet;
	private DocumentBuilderFactory factory;
	private DocumentBuilder builder;
	private Document document;
	private Element racine;
	private NodeList racineNoeuds;

	public Request(String pathResult, String pathRelevanceSet, int requestID,
			String requestName) {
		super();
		this.requestID = requestID;
		this.requestName = requestName;
		this.pathResult = pathResult;

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

					serviceNode
							.setAttribute("ID_Sequ", Integer.toString(i + 1));

				}				
			}
			document.getDocumentElement().normalize();
			writeInFile(document, xmlFile);

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
			document.getDocumentElement().normalize();
			writeInFile(document, xmlFile);
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
			document.getDocumentElement().normalize();
			writeInFile(document, xmlFile);
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
			document.getDocumentElement().normalize();
			writeInFile(document, xmlFile);

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
			document.getDocumentElement().normalize();
			writeInFile(document, xmlFile);
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

	public List<Integer> getNbservicePerResultCos() {
		return nbservicePerResultCos;
	}

	public List<Integer> getNbservicePerResultLi() {
		return nbservicePerResultLi;
	}

	public List<Integer> getNbservicePerResultEj() {
		return nbservicePerResultEj;
	}

	public List<Integer> getNbservicePerResultJs() {
		return nbservicePerResultJs;
	}

	public List<Integer> getNbservicePerResultLog() {
		return nbservicePerResultLog;
	}

	public void setNbservicePerResultCos(List<Integer> nbservicePerResultCos) {
		this.nbservicePerResultCos = nbservicePerResultCos;
	}

	public void setNbservicePerResultLi(List<Integer> nbservicePerResultLi) {
		this.nbservicePerResultLi = nbservicePerResultLi;
	}

	public void setNbservicePerResultEj(List<Integer> nbservicePerResultEj) {
		this.nbservicePerResultEj = nbservicePerResultEj;
	}

	public void setNbservicePerResultJs(List<Integer> nbservicePerResultJs) {
		this.nbservicePerResultJs = nbservicePerResultJs;
	}

	public void setNbservicePerResultLog(List<Integer> nbservicePerResultLog) {
		this.nbservicePerResultLog = nbservicePerResultLog;
	}

	private void writeInFile(Document document, String filePath) {

		File xmlFile = null;

		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer;

		try {
			transformer = transformerFactory.newTransformer();
			// for pretty print
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(document);

			// write to console or file
			// StreamResult console = new StreamResult(System.out);
			StreamResult file = new StreamResult(new File(filePath));

			// write data
			// transformer.transform(source, console);
			transformer.transform(source, file);

			xmlFile = new File(filePath);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
