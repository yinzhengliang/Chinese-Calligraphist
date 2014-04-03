package core.sketch;

import java.util.ArrayList;
import java.util.List;

public class Stroke {
	List<Point> points = new ArrayList<Point>();
	List<Interpretation> interpretation = new ArrayList<Interpretation>();
	BoundingBox bbd = new BoundingBox();
	Double length = 0.0;
	//Interpretation best = new Interpretation();
	
	public Stroke() {
	}
	
	public Stroke(List<Point> points) {
		this.points.addAll(points);	
		for (Point point : points) {
			update(point);
		}
	}
	
	public void addPoint(Point point) {
		points.add(point);
		update(point);
	}
	
	public void update(Point point) {
		updateBoundingBox(point);
		updateLength(point);
	}
	
	public void updateBoundingBox(Point point) {
		bbd.update(point);
	}
	
	public void updateLength(Point point) {
		if (points.size() == 0) return;
		Point lastPoint = points.get(points.size() - 1);
		length += lastPoint.distanceTo(point);
	}
	
	public List<Point> getPoints() {
		return points;
	}
	
	public BoundingBox getBoundingBox() {
		return bbd;
	}
	
	public Double getStrokeLength() {
		return length;
	}
	
	public void setInterpretations(List<Interpretation> interpretation) {
		this.interpretation.clear();
		this.interpretation.addAll(interpretation);
	}
	
	public List<Interpretation> getInterpretation() {
		return interpretation;
	}
}
