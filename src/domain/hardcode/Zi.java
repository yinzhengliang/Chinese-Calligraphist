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

public class Zi {
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
		shapeElement.setAttribute("name", "Zi");
		shapeElement.setAttribute("type", "Character");

		Element componentListElement = doc.createElement("components");
		shapeElement.appendChild(componentListElement);

		Element componentElement = doc.createElement("component");
		componentListElement.appendChild(componentElement);
		componentElement.setAttribute("name", "BaoGaiTou");
		componentElement.setAttribute("type", "Radical");
		componentElement.setAttribute("alias", "baogaitou");

		Element componentElement2 = doc.createElement("component");
		componentListElement.appendChild(componentElement2);
		componentElement2.setAttribute("name", "Child");
		componentElement2.setAttribute("type", "Character");
		componentElement2.setAttribute("alias", "child");

		// constraint
		Element constraintListElement = doc.createElement("constraints");
		shapeElement.appendChild(constraintListElement);

		Element constraint1 = doc.createElement("constraint");
		constraintListElement.appendChild(constraint1);
		constraint1.setAttribute("check", "Above");
		constraint1.setAttribute("para1", "baogaitou");
		constraint1.setAttribute("para1Spec", "Top");
		constraint1.setAttribute("para2", "child");
		constraint1.setAttribute("para2Spec", "Top");
		// constraint1.setAttribute("feedbackString", "");

		Element constraint2 = doc.createElement("constraint");
		constraintListElement.appendChild(constraint2);
		constraint2.setAttribute("check", "Above");
		constraint2.setAttribute("para1", "baogaitou");
		constraint2.setAttribute("para1Spec", "Bottom");
		constraint2.setAttribute("para2", "Child");
		constraint2.setAttribute("para2Spec", "Bottom");
		// constraint2.setAttribute("feedbackString", "");

		// Element constraint3 = doc.createElement("constraint");
		// constraintListElement.appendChild(constraint3);
		// constraint3.setAttribute("check", "Left");
		// constraint3.setAttribute("para1", "child");
		// constraint3.setAttribute("para1Spec", "Bottom");
		// constraint3.setAttribute("para2", "you");
		// constraint3.setAttribute("para2Spec", "Bottom");
		// // constraint3.setAttribute("feedbackString", "");

		// ============================ change as order =======================
		Element feedbackListElement = doc.createElement("feedbacks");
		shapeElement.appendChild(feedbackListElement);

		Element feedbackElement1 = doc.createElement("feedback");
		feedbackListElement.appendChild(feedbackElement1);
		feedbackElement1.setAttribute("check", "Before");
		feedbackElement1.setAttribute("para1", "baogaitou");
		feedbackElement1.setAttribute("para2", "child");
		feedbackElement1.setAttribute("feedbackString", "BaoGaiTou radical should be completed before writing Child under it");

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
