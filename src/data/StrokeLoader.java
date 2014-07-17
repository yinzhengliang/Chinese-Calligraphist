package data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.sketch.Point;
import core.sketch.Stroke;


public class StrokeLoader {
	// attribute
	private static String filename = "";
	private List<Stroke> strokes = new ArrayList<Stroke>();
	private Document doc = null;
	
	public StrokeLoader() {
		;
	}
	
	public StrokeLoader(String pathname) {
		;
	}
	
	public void setDocument(String path) {
		filename = path;
		try {
			File file = new File(filename);
			if (file == null) System.out.println("!@#");
			DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
			doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void setDocument(File file) {
		if (file == null) return;
		try {
			DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
			doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
//	public static void main(String[] args) {
//		getDocument();
//		setStrokes();
//		System.out.println(strokes.toString());
//	}
	
	public void setStrokes() {
		List<Point> points = setPoints();
		NodeList stroke_nodes = doc.getElementsByTagName("stroke");
		for (int i = 0; i < stroke_nodes.getLength(); i++) {
			Node stroke_node = stroke_nodes.item(i);
			if (stroke_node.getNodeType() == Node.ELEMENT_NODE) {
				Stroke cur_stroke = new Stroke();
				Element stroke = (Element) stroke_node;
				NodeList point_nodes = stroke.getChildNodes();
				for (int j = 0; j < point_nodes.getLength(); j++) {
					Node point_node = point_nodes.item(j);
					String id = ((Element)point_node).getTextContent();
					Point point = findPoint(points, id);
					if (point != null) cur_stroke.addPoint(point);
				}
				strokes.add(cur_stroke);
			}
		}
	}
	
	private Point findPoint(List<Point> points, String id) {
		for (Point point : points) {
			if (point.getId().equals(id)) return point;
		}
		return null;
	}

	public List<Point> setPoints() {
		List<Point> points = new ArrayList<Point>();
		
		NodeList point_nodes = doc.getElementsByTagName("point");
		
		for (int i = 0; i < point_nodes.getLength(); i++) {
			Node point_node = point_nodes.item(i);
			if (point_node.getNodeType() == Node.ELEMENT_NODE) {
				Element point_element = (Element) point_node;
				double x = Double.parseDouble(point_element.getAttribute("x"));
				double y = Double.parseDouble(point_element.getAttribute("y"));
				Double time = Double.parseDouble(point_element.getAttribute("time"));
				String id = point_element.getAttribute("id");
				points.add(new Point(x, y, time, id));
			}
		}
		return points;
	}
	
	public List<Stroke> getStrokes() {
		return strokes;
	}
	
}
