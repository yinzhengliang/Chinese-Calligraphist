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

public class SanDianShui {
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
		shapeElement.setAttribute("name", "SanDianShui");
		shapeElement.setAttribute("type", "Radical");

		Element componentListElement = doc.createElement("components");
		shapeElement.appendChild(componentListElement);

		Element componentElement = doc.createElement("component");
		componentListElement.appendChild(componentElement);
		componentElement.setAttribute("name", "Dian");
		componentElement.setAttribute("type", "Stroke");
		componentElement.setAttribute("alias", "dian1");

		Element componentElement2 = doc.createElement("component");
		componentListElement.appendChild(componentElement2);
		componentElement2.setAttribute("name", "Dian");
		componentElement2.setAttribute("type", "Stroke");
		componentElement2.setAttribute("alias", "dian2");

		Element componentElement3 = doc.createElement("component");
		componentListElement.appendChild(componentElement3);
		componentElement3.setAttribute("name", "Ti");
		componentElement3.setAttribute("type", "Stroke");
		componentElement3.setAttribute("alias", "ti");

		// constraint
		Element constraintListElement = doc.createElement("constraints");
		shapeElement.appendChild(constraintListElement);

		Element constraint1 = doc.createElement("constraint");
		constraintListElement.appendChild(constraint1);
		constraint1.setAttribute("check", "Above");
		constraint1.setAttribute("para1", "dian1");
		constraint1.setAttribute("para1Spec", "Bottom");
		constraint1.setAttribute("para2", "dian2");
		constraint1.setAttribute("para2Spec", "Top");
		// constraint1.setAttribute("feedbackString", "");

		Element constraint2 = doc.createElement("constraint");
		constraintListElement.appendChild(constraint2);
		constraint2.setAttribute("check", "Above");
		constraint2.setAttribute("para1", "dian2");
		constraint2.setAttribute("para1Spec", "Bottom");
		constraint2.setAttribute("para2", "ti");
		constraint2.setAttribute("para2Spec", "Top");
		// constraint2.setAttribute("feedbackString", "");

		Element constraint3 = doc.createElement("constraint");
		constraintListElement.appendChild(constraint3);
		constraint3.setAttribute("check", "SameX");
		constraint3.setAttribute("para1", "dian1");
		constraint3.setAttribute("para1Spec", "Left");
		constraint3.setAttribute("para2", "dian2");
		constraint3.setAttribute("para2Spec", "Left");
		constraint3.setAttribute("around", "5");
		// constraint3.setAttribute("feedbackString", "");

		Element constraint4 = doc.createElement("constraint");
		constraintListElement.appendChild(constraint4);
		constraint4.setAttribute("check", "SameX");
		constraint4.setAttribute("para1", "dian1");
		constraint4.setAttribute("para1Spec", "Right");
		constraint4.setAttribute("para2", "dian2");
		constraint4.setAttribute("para2Spec", "Right");
		constraint4.setAttribute("around", "5");
		// constraint4.setAttribute("feedbackString", "");

		Element constraint5 = doc.createElement("constraint");
		constraintListElement.appendChild(constraint5);
		constraint5.setAttribute("check", "SameX");
		constraint5.setAttribute("para1", "dian1");
		constraint5.setAttribute("para1Spec", "Left");
		constraint5.setAttribute("para2", "ti");
		constraint5.setAttribute("para2Spec", "Left");
		constraint5.setAttribute("around", "10");
		// constraint5.setAttribute("feedbackString", "");

		Element constraint6 = doc.createElement("constraint");
		constraintListElement.appendChild(constraint6);
		constraint6.setAttribute("check", "SameX");
		constraint6.setAttribute("para1", "dian1");
		constraint6.setAttribute("para1Spec", "Right");
		constraint6.setAttribute("para2", "ti");
		constraint6.setAttribute("para2Spec", "Right");
		constraint6.setAttribute("around", "10");
		// constraint6.setAttribute("feedbackString", "");

		// ============================ change as order =======================
		Element feedbackListElement = doc.createElement("feedbacks");
		shapeElement.appendChild(feedbackListElement);

		Element feedbackElement1 = doc.createElement("feedback");
		feedbackListElement.appendChild(feedbackElement1);
		feedbackElement1.setAttribute("check", "Before");
		feedbackElement1.setAttribute("para1", "dian1");
		feedbackElement1.setAttribute("para2", "dian2");
		feedbackElement1.setAttribute("feedbackString", "Dian at top should be written before Dian at middle");

		Element feedbackElement2 = doc.createElement("feedback");
		feedbackListElement.appendChild(feedbackElement2);
		feedbackElement2.setAttribute("check", "Before");
		feedbackElement2.setAttribute("para1", "dian2");
		feedbackElement2.setAttribute("para2", "ti");
		feedbackElement2.setAttribute("feedbackString", "Ti at bottom should be written at last");

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
