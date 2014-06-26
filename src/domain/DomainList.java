package domain;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class DomainList {
	private static Document doc = null;

	private String domainListLoadingPath = "";

	private DomainDefinition m_domains = new DomainDefinition();

	private List<String> shapeNames = new ArrayList<String>();

	private List<DomainShape> m_shapes = new ArrayList<DomainShape>();

	// public static void main(String[] args) {
	// DomainShape test = new DomainShape(
	// "C:/Users/Yin/workspace/Chinese-Calligraphist/resources/ShapeDefinition/Character/SanDianShui/SanDianShui.xml");
	// System.out.println("!!!");
	// }

	public DomainList(String path) {
		domainListLoadingPath = path;
		setDocument(domainListLoadingPath);
		setDomainDefinition();
		setShapes();
	}

	private void setShapes() {
		for (String path : m_domains.getList().values()) {
			m_shapes.add(new DomainShape(path));
		}

	}

	private void setDomainDefinition() {
		NodeList entryList = doc.getElementsByTagName("entry");
		for (int i = 0; i < entryList.getLength(); i++) {
			Node entry = entryList.item(i);
			if (entry.getNodeType() == Node.ELEMENT_NODE) {
				Element entry_element = (Element) entry;
				shapeNames.add(entry_element.getAttribute("name"));
				m_domains.addEntry(entry_element.getAttribute("name"), entry_element.getAttribute("path"));
			}
		}
	}

	public static void setDocument(String xmlSource) {
		try {
			File file = new File(xmlSource);
			DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
			doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public List<DomainShape> getShapes() {
		return m_shapes;
	}

	public void setShapes(List<DomainShape> m_shapes) {
		this.m_shapes = m_shapes;
	}

}
