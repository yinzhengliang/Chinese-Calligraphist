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

import ui.problemframe.feedback.FeedbackPanel;
import constants.Constant;
import core.sketch.BoundingBox;
import core.sketch.Stroke;
import domain.ShapeType.Type;

public class DomainShape {
	private static Document doc = null;

	private List<Constraint> feedbacks = new ArrayList<Constraint>();
	private List<Constraint> constraints = new ArrayList<Constraint>();
	private List<DomainShape> components = new ArrayList<DomainShape>();
	private String alias;
	private List<Stroke> strokes = new ArrayList<Stroke>();
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
		setName(hypothesis.getAttribute("name"));
		switch (hypothesis.getAttribute("type")) {
		case "Observation": {
			setType(ShapeType.Type.Observation);
			break;
		}
		case "Stroke": {
			setType(ShapeType.Type.Stroke);
			break;
		}
		case "Radical": {
			setType(ShapeType.Type.Radical);
			break;
		}
		case "Character": {
			setType(ShapeType.Type.Character);
			break;
		}
		}

		setConstraints();

		setFeedbacks();

		setMisclassificationWarning();
		
		setComponents();

	}
	
	public DomainShape(String xmlSource, String alias) {
		setDocument(xmlSource);

		Element hypothesis = doc.getDocumentElement();
		setName(hypothesis.getAttribute("name"));
		switch (hypothesis.getAttribute("type")) {
		case "Observation": {
			setType(ShapeType.Type.Observation);
			break;
		}
		case "Stroke": {
			setType(ShapeType.Type.Stroke);
			break;
		}
		case "Radical": {
			setType(ShapeType.Type.Radical);
			break;
		}
		case "Character": {
			setType(ShapeType.Type.Character);
			break;
		}
		}
		
		setAlias(alias);

		setConstraints();

		setFeedbacks();

		setMisclassificationWarning();
		
		setComponents();

	}

	private void setMisclassificationWarning() {
		NodeList misClassify_nodes = doc.getElementsByTagName("misClassify");

		for (int i = 0; i < misClassify_nodes.getLength(); i++) {
			Node feedback_node = misClassify_nodes.item(i);
			if (feedback_node.getNodeType() == Node.ELEMENT_NODE) {
				Element misClassify_element = (Element) feedback_node;
				misClassifyWarning.put(misClassify_element.getAttribute("name"),
						misClassify_element.getAttribute("feedbackString"));
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
					case "Radical":
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

					// System.out.println(xmlSource);
					// System.out.println(name);
					// System.out.println(type);
					
					addComponent(new DomainShape(xmlSource), alias);
//					components.add(new DomainShape(xmlSource, alias));

				}

				else {
					DomainShape shape = new DomainShape();
					shape.setName(name);
					shape.setType(ShapeType.Type.Observation);
					shape.setAlias(alias);
					components.add(shape);
				}

				
				// generate a Domain Shape, and add it to the list, also, according to the name and type, find a
				// definition path, and generate then.
				// leave the strokes blank, when see some observations meet all the constraints, add them to it. The
				// strokes can be changable.
			}
		}
	}

	private void setAlias(String alias) {
		this.alias = alias;
	}

	public BoundingBox getBoundingBox() {
		return bbd;
	}

	public List<Stroke> getStrokes() {
		return strokes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ShapeType.Type getType() {
		return type;
	}

	public void setType(ShapeType.Type type) {
		this.type = type;
	}

	public List<DomainShape> getComponents() {
		return components;
	}

	public void copyComponent(List<DomainShape> components) {
		this.components = components;
	}

	public void addStroke(Stroke stroke) {
		strokes.add(stroke);
	}

	public void addStrokes(List<Stroke> strokes) {
		this.strokes.addAll(strokes);
	}

	public DomainShape copy() {
		DomainShape copied = new DomainShape();
		copied.copyFeedbacks(feedbacks);
		copied.copyConstraints(constraints);
		copied.copyMisClassifyWarning(misClassifyWarning);

		// private DomainDefinition m_domain;

		copied.setName(name);
		copied.setType(type);

		copied.copyComponent(components);
		return copied;

	}

	public void copyMisClassifyWarning(Map<String, String> misClassifyWarning) {
		this.misClassifyWarning = misClassifyWarning;
	}

	public void copyConstraints(List<Constraint> constraints) {
		this.constraints = constraints;
	}

	public void copyFeedbacks(List<Constraint> feedbacks) {
		this.feedbacks = feedbacks;
	}

	public List<Constraint> getConstraints() {
		return this.constraints;
	}

	public List<Constraint> getFeedbacks() {
		return this.feedbacks;
	}

	public Map<String, String> getMisClassfication() {
		return this.misClassifyWarning;
	}
	
	public boolean check() {
		//TODO
		return false;
	}

	public void setBoundingBox(BoundingBox boundingBox) {
		this.bbd = new BoundingBox(boundingBox);
		
	}

	public void checkFeedback() {
		boolean wrong = false;
		for (Constraint feedback : feedbacks) {
			if (!feedback.check()) {
				FeedbackPanel.addFeedback(type + ": [" + name + "] Feedback Warning: " + feedback.getFeedbackString(), strokes);
				wrong = true;
			}
		}
		if (!wrong) {
			FeedbackPanel.addFeedback(type + ": [" + name + "] looks good! :)", strokes);
			if (type.equals(Type.Character) || type.equals(Type.Radical)) {
				FeedbackPanel.addFeedback(type + ": [" + name + "] passes all checks, correct technique, Great Writing!", strokes);
			}
		}
		
	}

	public String getAlias() {
		return alias;
	}

	public void addComponent(DomainShape m_shape, String alias) {
		m_shape.setAlias(alias);
		components.add(m_shape);
		
	}

	public void completeFeedbacks() {
		for (Constraint feedback : feedbacks) {
			for (DomainShape component : components) {
				if (feedback.para1String.equals(component.getAlias())) feedback.setPara1(component);
				if (feedback.para2String.equals(component.getAlias())) feedback.setPara2(component);
			}
		}
	}

	public void completeConstraints() {
		for (Constraint constraint : constraints) {
			for (DomainShape component : components) {
				if (constraint.para1String.equals(component.getAlias())) constraint.setPara1(component);
				if (constraint.para2String.equals(component.getAlias())) constraint.setPara2(component);
			}
		}
	}

	public boolean checkConstraints() {
		completeConstraints();
		for (Constraint constraint : constraints) {
			if (!constraint.check()) {
				return false;
			}
		}
		return true;
	}

	public boolean checkConstraintsWithShuffledAlias() {
		for (int i = 0; i < components.size(); i++) {
			for (int j = i; j < components.size(); j++) {
				if ((components.get(i).getName().equals(components.get(j).getName())) && (components.get(i).getType().equals(components.get(j).getType()))) {
					String aliasI = components.get(i).getAlias();
					String aliasJ = components.get(j).getAlias();
					// swap
					components.get(i).setAlias(aliasJ);
					components.get(j).setAlias(aliasI);
					if (checkConstraints()) return true;
					// swap back.
					components.get(i).setAlias(aliasI);
					components.get(j).setAlias(aliasJ);
				}
			}
		}
		return false;
	}
}
