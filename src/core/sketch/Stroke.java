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
		setPoints(points);
	}
	
	public void addPoint(Point point) {
		update(point);
		points.add(point);
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
	
	
	public BoundingBox recalcBoundingBox() {
		bbd.clear();
		for (Point point : points) {
			bbd.update(point);
		}
		return bbd;
	}
	
	public double recalcLength() {
		length = 0.0;
		Point lastPoint = points.get(0);
		for (Point point : points) {
			length += lastPoint.distanceTo(point);
			lastPoint = point;
		}
		return length;
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
	
	public void clear() {
		points.clear();
		interpretation.clear();
		bbd.clear();
		length = 0.0;
	}
	
	public void setPoints(List<Point> points) {
		this.points.clear();
		this.points.addAll(points);	
		recalcBoundingBox();
		recalcLength();
	}
}
