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

public class Pie {
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
		shapeElement.setAttribute("name", "Pie");
		shapeElement.setAttribute("type", "Stroke");

		Element componentListElement = doc.createElement("components");
		shapeElement.appendChild(componentListElement);

		Element componentElement = doc.createElement("component");
		componentListElement.appendChild(componentElement);
		componentElement.setAttribute("name", "Pie");
		componentElement.setAttribute("type", "Observation");
		componentElement.setAttribute("alias", "curve1");

		Element feedbackListElement = doc.createElement("feedbacks");
		shapeElement.appendChild(feedbackListElement);

		Element feedbackElement1 = doc.createElement("feedback");
		feedbackListElement.appendChild(feedbackElement1);
		feedbackElement1.setAttribute("check", "Above");
		feedbackElement1.setAttribute("para1", "curve1");
		feedbackElement1.setAttribute("para1Spec", "startPoint");
		feedbackElement1.setAttribute("para2", "curve1");
		feedbackElement1.setAttribute("para2Spec", "endPoint");
		feedbackElement1.setAttribute("feedbackString", "Pie should be written from upright to downleft");

		Element feedbackElement2 = doc.createElement("feedback");
		feedbackListElement.appendChild(feedbackElement2);
		feedbackElement2.setAttribute("check", "Right");
		feedbackElement2.setAttribute("para1", "curve1");
		feedbackElement2.setAttribute("para1Spec", "startPoint");
		feedbackElement2.setAttribute("para2", "curve1");
		feedbackElement2.setAttribute("para2Spec", "endPoint");
		feedbackElement2.setAttribute("feedbackString", "Pie should start from upright corner");

		Element misClassifyListElement = doc.createElement("misClassifys");
		shapeElement.appendChild(misClassifyListElement);

		Element pieMisWan = doc.createElement("misClassify");
		misClassifyListElement.appendChild(pieMisWan);
		pieMisWan.setAttribute("name", "Wan");
		pieMisWan.setAttribute("feedbackString", "Pie and Wan looks similar. but Pie is more flat, and Wan is vertical and thin");

		Element pieMisTi = doc.createElement("misClassify");
		misClassifyListElement.appendChild(pieMisTi);
		pieMisTi.setAttribute("name", "Ti");
		pieMisTi.setAttribute("feedbackString", "Pie and Ti looks similar, but Pie writes from topright to downleft, while Ti is from downleft to upright; Pie is as more curve, and Ti is a more line");

		Element pieMisHeng = doc.createElement("misClassify");
		misClassifyListElement.appendChild(pieMisHeng);
		pieMisHeng.setAttribute("name", "Heng");
		pieMisHeng.setAttribute("feedbackString", "Note that Heng is a horizontal straight line, but Pie is a curve");

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
