package domain;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import constants.Constant;
import core.sketch.BoundingBox;
import core.sketch.Stroke;

public class DomainShape {
	private static Document doc = null;

	private List<Constraint> feedbacks = new ArrayList<Constraint>();
	private List<Constraint> constraints = new ArrayList<Constraint>();
	private List<DomainShape> components = new ArrayList<DomainShape>();
	private List<Stroke> strokes;
	private Map<String, String> misClassifyWarning = new HashMap<String, String>();

	private DomainDefinition m_domain;

	private String name = "";
	private ShapeType.Type type = ShapeType.Type.Unset;

	private BoundingBox bbd = new BoundingBox();

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

	public DomainShape() {

	}

	public DomainShape(String xmlSource) {
		setDocument(xmlSource);

		Element hypothesis = doc.getDocumentElement();
		name = hypothesis.getAttribute("name");
		switch (hypothesis.getAttribute("type")) {
		case "Observation": {
			type = ShapeType.Type.Observation;
			break;
		}
		case "Stroke": {
			type = ShapeType.Type.Stroke;
			break;
		}
		case "Radical": {
			type = ShapeType.Type.Radical;
			break;
		}
		case "Character": {
			type = ShapeType.Type.Character;
			break;
		}
		}

		setComponents();

		setConstraints();

		setFeedbacks();

		setMisclassificationWarning();

	}

	private void setMisclassificationWarning() {
		NodeList misClassify_nodes = doc.getElementsByTagName("misClassify");

		for (int i = 0; i < misClassify_nodes.getLength(); i++) {
			Node feedback_node = misClassify_nodes.item(i);
			if (feedback_node.getNodeType() == Node.ELEMENT_NODE) {
				Element misClassify_element = (Element) feedback_node;
				misClassifyWarning.put(misClassify_element.getAttribute("name"), misClassify_element.getAttribute("feedbackString"));
			}
		}
	}

	private void setFeedbacks() {
		NodeList feedback_nodes = doc.getElementsByTagName("feedback");

		for (int i = 0; i < feedback_nodes.getLength(); i++) {
			Map<String, String> feedback = new HashMap<String, String>();
			Node feedback_node = feedback_nodes.item(i);
			if (feedback_node.getNodeType() == Node.ELEMENT_NODE) {
				Element feedback_element = (Element) feedback_node;
				feedback.put("check", feedback_element.getAttribute("check"));
				feedback.put("para1Spec", feedback_element.getAttribute("para1Spec"));
				feedback.put("para2Spec", feedback_element.getAttribute("para2Spec"));
				feedback.put("around", feedback_element.getAttribute("around"));
				feedback.put("feedback", feedback_element.getAttribute("feedbackString"));
				feedback.put("para1", feedback_element.getAttribute("para1"));
				feedback.put("para2", feedback_element.getAttribute("para2"));

				feedbacks.add(new Constraint(feedback));
			}
		}

	}

	private void setConstraints() {

		NodeList constraint_nodes = doc.getElementsByTagName("constraint");

		for (int i = 0; i < constraint_nodes.getLength(); i++) {
			Map<String, String> constraint = new HashMap<String, String>();
			Node constraint_node = constraint_nodes.item(i);
			if (constraint_node.getNodeType() == Node.ELEMENT_NODE) {
				Element constraint_element = (Element) constraint_node;
				constraint.put("check", constraint_element.getAttribute("check"));
				constraint.put("para1Spec", constraint_element.getAttribute("para1Spec"));
				constraint.put("para2Spec", constraint_element.getAttribute("para2Spec"));
				constraint.put("around", constraint_element.getAttribute("around"));
				constraint.put("feedback", constraint_element.getAttribute("feedbackString"));
				constraint.put("para1", constraint_element.getAttribute("para1"));
				constraint.put("para2", constraint_element.getAttribute("para2"));

				constraints.add(new Constraint(constraint));
			}
		}
	}

	private void setComponents() {

		NodeList component_nodes = doc.getElementsByTagName("component");

		for (int i = 0; i < component_nodes.getLength(); i++) {
			Node component_node = component_nodes.item(i);
			if (component_node.getNodeType() == Node.ELEMENT_NODE) {
				Element component_element = (Element) component_node;

				String name = component_element.getAttribute("name");
				String type = component_element.getAttribute("type");
				String alias = component_element.getAttribute("alias");

				if (!type.equals("Observation")) {
					String xmlFolder = "";
					switch (type) {
					case "Raical":
					case "Character": {
						xmlFolder = Constant.RADICAL_CHARACTER_DEFINE_DIR;
						break;
					}
					case "Stroke": {
						xmlFolder = Constant.STROKE_SHAPE_DEFINE_DIR;
						break;
					}
					}

					String xmlSource = xmlFolder + name + "/" + name + ".xml";

					System.out.println(xmlSource);
					System.out.println(name);
					System.out.println(type);
					components.add(new DomainShape(xmlSource));
				}

				// generate a Domain Shape, and add it to the list, also, according to the name and type, find a
				// definition path, and generate then.
				// leave the strokes blank, when see some observations meet all the constraints, add them to it. The
				// strokes can be changable.
			}
		}
	}

	public BoundingBox getBoundingBox() {
		return bbd;
	}

	public List<Stroke> getStrokes() {
		return strokes;
	}
}
