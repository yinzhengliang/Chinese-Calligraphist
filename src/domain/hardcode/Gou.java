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

public class Gou {
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
		shapeElement.setAttribute("name", "Gou");
		shapeElement.setAttribute("type", "Stroke");

		Element componentListElement = doc.createElement("components");
		shapeElement.appendChild(componentListElement);

		Element componentElement = doc.createElement("component");
		componentListElement.appendChild(componentElement);
		componentElement.setAttribute("name", "Gou");
		componentElement.setAttribute("type", "Observation");
		componentElement.setAttribute("alias", "stroke1");

		Element feedbackListElement = doc.createElement("feedbacks");
		shapeElement.appendChild(feedbackListElement);

		Element feedbackElement1 = doc.createElement("feedback");
		feedbackListElement.appendChild(feedbackElement1);
		feedbackElement1.setAttribute("check", "Above");
		feedbackElement1.setAttribute("para1", "stroke1");
		feedbackElement1.setAttribute("para1Spec", "startPoint");
		feedbackElement1.setAttribute("para2", "stroke1");
		feedbackElement1.setAttribute("para2Spec", "endPoint");
		feedbackElement1.setAttribute("feedbackString", "Gou should be written from up to down");

		Element misClassifyListElement = doc.createElement("misClassifys");
		shapeElement.appendChild(misClassifyListElement);

		Element gouMisShu = doc.createElement("misClassify");
		misClassifyListElement.appendChild(gouMisShu);
		gouMisShu.setAttribute("name", "Shu");
		gouMisShu.setAttribute("feedbackString", "Shu and Gou looks similar, both updown stroke. But Shu is line, and Gou is curve with hook at bottom");

		Element gouMisWan = doc.createElement("misClassify");
		misClassifyListElement.appendChild(gouMisWan);
		gouMisWan.setAttribute("name", "Wan");
		gouMisWan.setAttribute("feedbackString", "Wan and Gou looks similar, both updown curve, but Wan doesn't have hook at bottom, which Gou does");

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
