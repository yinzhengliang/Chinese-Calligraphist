package domain;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import core.sketch.BoundingBox;
import core.sketch.Stroke;

public class DomainShape {
	private List<Constraint> constraints = new ArrayList<Constraint>();
	private List<DomainShape> components = new ArrayList<DomainShape>();
	private List<Stroke> strokes;

	private String name = "";
	private ShapeType.Type type = ShapeType.Type.Unset;

	private BoundingBox bbd = new BoundingBox();

	public DomainShape() {

	}

	public DomainShape(String xmlSource) {

	}

	public BoundingBox getBoundingBox() {
		return bbd;
	}

	public List<Stroke> getStrokes() {
		return strokes;
	}
}
