package domain.hardcode;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import core.sketch.Point;
import core.sketch.Stroke;

public class ShapeDefiner {
	private Document doc = null;

	public void setDocument() {
		try {
			DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
			doc = dBuilder.newDocument();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void save(String shapeDefString, String filepath) {
		setDocument();
		// Element sketchElement = doc.createElement("sketch");
		// doc.appendChild(sketchElement);
		//
		// sketchElement.setAttribute("author", "2390");
		// sketchElement.setAttribute("id", UUID.randomUUID().toString());
		// sketchElement.setAttribute("type", "SRL");
		//
		// for (Stroke stroke : strokes) {
		// Element strokeElement = doc.createElement("stroke");
		// sketchElement.appendChild(strokeElement);
		// strokeElement.setAttribute("id", UUID.randomUUID().toString());
		// strokeElement.setAttribute("visible", "true");
		//
		// for (Point point : stroke.getPoints()) {
		// Element pointElement = doc.createElement("point");
		// sketchElement.appendChild(pointElement);
		// pointElement.setAttribute("id", point.getId());
		// pointElement.setAttribute("time", String.format("%s", point.getTime()));
		// pointElement.setAttribute("x", String.format("%s", (int) point.getX()));
		// pointElement.setAttribute("y", String.format("%s", (int) point.getY()));
		//
		// Element argElement = doc.createElement("arg");
		// strokeElement.appendChild(argElement);
		// argElement.setAttribute("type", "point");
		// argElement.appendChild(doc.createTextNode(point.getId()));
		//
		// }
		// }
		//
		// // write the content into xml file
		// try {
		// TransformerFactory transformerFactory = TransformerFactory.newInstance();
		// Transformer transformer = transformerFactory.newTransformer();
		// DOMSource source = new DOMSource(doc);
		//
		// // StreamResult result = new StreamResult(new File("C:\\Users\\Yin\\Desktop\\testing.xml"));
		// StreamResult result = new StreamResult(new File(filepath));
		// transformer.transform(source, result);
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// }
	}
}
