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

public class BaoGaiTou {
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
		shapeElement.setAttribute("name", "BaoGaiTou");
		shapeElement.setAttribute("type", "Radical");

		Element componentListElement = doc.createElement("components");
		shapeElement.appendChild(componentListElement);

		Element componentElement = doc.createElement("component");
		componentListElement.appendChild(componentElement);
		componentElement.setAttribute("name", "Dian");
		componentElement.setAttribute("type", "Stroke");
		componentElement.setAttribute("alias", "dian");

		Element componentElement2 = doc.createElement("component");
		componentListElement.appendChild(componentElement2);
		componentElement2.setAttribute("name", "Shu");
		componentElement2.setAttribute("type", "Stroke");
		componentElement2.setAttribute("alias", "shu");

		Element componentElement3 = doc.createElement("component");
		componentListElement.appendChild(componentElement3);
		componentElement3.setAttribute("name", "HengGou");
		componentElement3.setAttribute("type", "Stroke");
		componentElement3.setAttribute("alias", "henggou");

		// constraint
		Element constraintListElement = doc.createElement("constraints");
		shapeElement.appendChild(constraintListElement);

		Element constraint1 = doc.createElement("constraint");
		constraintListElement.appendChild(constraint1);
		constraint1.setAttribute("check", "Above");
		constraint1.setAttribute("para1", "dian");
		constraint1.setAttribute("para1Spec", "Bottom");
		constraint1.setAttribute("para2", "shu");
		constraint1.setAttribute("para2Spec", "Top");
		// constraint1.setAttribute("feedbackString", "");
		
		Element constraint2 = doc.createElement("constraint");
		constraintListElement.appendChild(constraint2);
		constraint2.setAttribute("check", "Coincide");
		constraint2.setAttribute("para1", "shu");
		constraint2.setAttribute("para1Spec", "startPoint");
		constraint2.setAttribute("para2", "gou");
		constraint2.setAttribute("para2Spec", "startPoint");
		constraint2.setAttribute("around", "10");
		// constraint2.setAttribute("feedbackString", "");

		// ============================ change as order =======================
		Element feedbackListElement = doc.createElement("feedbacks");
		shapeElement.appendChild(feedbackListElement);

		Element feedbackElement1 = doc.createElement("feedback");
		feedbackListElement.appendChild(feedbackElement1);
		feedbackElement1.setAttribute("check", "Before");
		feedbackElement1.setAttribute("para1", "dian");
		feedbackElement1.setAttribute("para2", "shu");
		feedbackElement1.setAttribute("feedbackString", "Dian should be written at first");

		Element feedbackElement2 = doc.createElement("feedback");
		feedbackListElement.appendChild(feedbackElement2);
		feedbackElement2.setAttribute("check", "Before");
		feedbackElement2.setAttribute("para1", "shu");
		feedbackElement2.setAttribute("para2", "henggou");
		feedbackElement2.setAttribute("feedbackString", "Shu should be written before HengGou");

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
