package domain;

import java.net.URL;
import java.util.List;

import core.sketch.BoundingBox;
import core.sketch.Stroke;

public class DomainShape {
	private enum Type {Character, Radical, Stroke, Unset};
	
	private String name = "";
	private Type type = Type.Unset;
	private List<Stroke> storkes;
	
	private BoundingBox bbd = new BoundingBox();
	
	private List<DomainShape> components;
	
	private List<Constraint> constraints;
}
