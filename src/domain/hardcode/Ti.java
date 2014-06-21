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

public class Ti {
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
		shapeElement.setAttribute("name", "Ti");
		shapeElement.setAttribute("type", "Stroke");

		Element componentListElement = doc.createElement("components");
		shapeElement.appendChild(componentListElement);

		Element componentElement = doc.createElement("component");
		componentListElement.appendChild(componentElement);
		componentElement.setAttribute("name", "Ti");
		componentElement.setAttribute("type", "Observation");
		componentElement.setAttribute("alias", "curve1");

		Element feedbackListElement = doc.createElement("feedbacks");
		shapeElement.appendChild(feedbackListElement);

		Element feedbackElement1 = doc.createElement("feedback");
		feedbackListElement.appendChild(feedbackElement1);
		feedbackElement1.setAttribute("check", "Below");
		feedbackElement1.setAttribute("para1", "line1");
		feedbackElement1.setAttribute("para1Spec", "startPoint");
		feedbackElement1.setAttribute("para2", "line1");
		feedbackElement1.setAttribute("para2Spec", "endPoint");
		feedbackElement1.setAttribute("feedbackString", "Ti should be written from downleft to upright");

		Element feedbackElement2 = doc.createElement("feedback");
		feedbackListElement.appendChild(feedbackElement2);
		feedbackElement2.setAttribute("check", "Left");
		feedbackElement2.setAttribute("para1", "line1");
		feedbackElement2.setAttribute("para1Spec", "startPoint");
		feedbackElement2.setAttribute("para2", "line1");
		feedbackElement2.setAttribute("para2Spec", "endPoint");
		feedbackElement2.setAttribute("feedbackString", "Ti should start from downleft");

		Element misClassifyListElement = doc.createElement("misClassifys");
		shapeElement.appendChild(misClassifyListElement);

		Element tiMisPie = doc.createElement("misClassify");
		misClassifyListElement.appendChild(tiMisPie);
		tiMisPie.setAttribute("name", "Pie");
		tiMisPie.setAttribute("feedbackString", "Ti and Pie looks similar. but Pie is top-down and right-left curve, and Ti is bottom-up and left-right line");

		Element tiMisHeng = doc.createElement("misClassify");
		misClassifyListElement.appendChild(tiMisHeng);
		tiMisHeng.setAttribute("name", "Heng");
		tiMisHeng.setAttribute("feedbackString", "Ti and Heng looks similar, Ti is not horizontal, but with some positive slope");

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
