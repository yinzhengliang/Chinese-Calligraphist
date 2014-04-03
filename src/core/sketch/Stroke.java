package core.sketch;

import java.util.ArrayList;
import java.util.List;

public class Stroke {
	List<Point> points = new ArrayList<Point>();
	List<Interpretation> interpretation = new ArrayList<Interpretation>();
	BoundingBox bbd = new BoundingBox();
	Double length = 0.0;
	//Interpretation best = new Interpretation();
	
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
}
