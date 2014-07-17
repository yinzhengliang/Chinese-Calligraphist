package domain.hardcode;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Han {
	private static Document doc = null;

	public static void setDocument() {
		try {
			DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
			doc = dBuilder.newDocument();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void save(String filepath) {
		setDocument();
		Element shapeElement = doc.createElement("Hypothesis");
		doc.appendChild(shapeElement);
		//
		shapeElement.setAttribute("name", "Han");
		shapeElement.setAttribute("type", "Character");

		Element componentListElement = doc.createElement("components");
		shapeElement.appendChild(componentListElement);

		Element componentElement = doc.createElement("component");
		componentListElement.appendChild(componentElement);
		componentElement.setAttribute("name", "SanDianShui");
		componentElement.setAttribute("type", "Radical");
		componentElement.setAttribute("alias", "sandianshui");

		Element componentElement2 = doc.createElement("component");
		componentListElement.appendChild(componentElement2);
		componentElement2.setAttribute("name", "You");
		componentElement2.setAttribute("type", "Character");
		componentElement2.setAttribute("alias", "you");

		// constraint
		Element constraintListElement = doc.createElement("constraints");
		shapeElement.appendChild(constraintListElement);

		Element constraint1 = doc.createElement("constraint");
		constraintListElement.appendChild(constraint1);
		constraint1.setAttribute("check", "Left");
		constraint1.setAttribute("para1", "sandianshui");
		constraint1.setAttribute("para1Spec", "Right");
		constraint1.setAttribute("para2", "you");
		constraint1.setAttribute("para2Spec", "Left");
		// constraint1.setAttribute("feedbackString", "");

		Element constraint2 = doc.createElement("constraint");
		constraintListElement.appendChild(constraint2);
		constraint2.setAttribute("check", "Above");
		constraint2.setAttribute("para1", "sandianshui");
		constraint2.setAttribute("para1Spec", "Top");
		constraint2.setAttribute("para2", "you");
		constraint2.setAttribute("para2Spec", "Top");
		// constraint2.setAttribute("feedbackString", "");

		Element constraint3 = doc.createElement("constraint");
		constraintListElement.appendChild(constraint3);
		constraint3.setAttribute("check", "Below");
		constraint3.setAttribute("para1", "sandianshui");
		constraint3.setAttribute("para1Spec", "Bottom");
		constraint3.setAttribute("para2", "you");
		constraint3.setAttribute("para2Spec", "Bottom");
		// constraint3.setAttribute("feedbackString", "");

		// ============================ change as order =======================
		Element feedbackListElement = doc.createElement("feedbacks");
		shapeElement.appendChild(feedbackListElement);

		Element feedbackElement1 = doc.createElement("feedback");
		feedbackListElement.appendChild(feedbackElement1);
		feedbackElement1.setAttribute("check", "Before");
		feedbackElement1.setAttribute("para1", "sandianshui");
		feedbackElement1.setAttribute("para2", "you");
		feedbackElement1.setAttribute("feedbackString", "SanDianShui radical should be completed before writing You");

		// write the content into xml file
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);

			// StreamResult result = new StreamResult(new File("C:\\Users\\Yin\\Desktop\\testing.xml"));
			StreamResult result = new StreamResult(new File(filepath));
			transformer.transform(source, result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		save("C:/Users/Yin/Desktop/Heng1.xml");

	}
}
