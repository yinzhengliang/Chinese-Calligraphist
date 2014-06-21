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

public class Wan {
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
		shapeElement.setAttribute("name", "Wan");
		shapeElement.setAttribute("type", "Stroke");

		Element componentListElement = doc.createElement("components");
		shapeElement.appendChild(componentListElement);

		Element componentElement = doc.createElement("component");
		componentListElement.appendChild(componentElement);
		componentElement.setAttribute("name", "Wan");
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
		feedbackElement1.setAttribute("feedbackString", "Wan should be written from top to down");

		Element feedbackElement2 = doc.createElement("feedback");
		feedbackListElement.appendChild(feedbackElement2);
		feedbackElement2.setAttribute("check", "SameX");
		feedbackElement2.setAttribute("para1", "curve1");
		feedbackElement2.setAttribute("para1Spec", "startPoint");
		feedbackElement2.setAttribute("para2", "curve1");
		feedbackElement2.setAttribute("para2Spec", "endPoint");
		feedbackElement2.setAttribute("around", "10");
		feedbackElement2.setAttribute("feedbackString", "Wan should not be too flat");

		Element misClassifyListElement = doc.createElement("misClassifys");
		shapeElement.appendChild(misClassifyListElement);

		Element wanMisShu = doc.createElement("misClassify");
		misClassifyListElement.appendChild(wanMisShu);
		wanMisShu.setAttribute("name", "Shu");
		wanMisShu.setAttribute("feedbackString", "Wan is curve and Shu is vertical line");

		Element wanMisGou = doc.createElement("misClassify");
		misClassifyListElement.appendChild(wanMisGou);
		wanMisGou.setAttribute("name", "Gou");
		wanMisGou.setAttribute("feedbackString", "Wan and Gou looks similar, but Wan doesn't have hook at bottom");
		
		Element wanMisPie = doc.createElement("misClassify");
		misClassifyListElement.appendChild(wanMisPie);
		wanMisPie.setAttribute("name", "Pie");
		wanMisPie.setAttribute("feedbackString", "Wan and Pie looks similar, but Pie is more flat and Wan is more vertical and thin");
		
		Element wanMisNa = doc.createElement("misClassify");
		misClassifyListElement.appendChild(wanMisNa);
		wanMisNa.setAttribute("name", "Na");
		wanMisNa.setAttribute("feedbackString", "Wan and Na are different, Na is more flat and left to right concave curve, and Wan is updown convex curve");
		
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
