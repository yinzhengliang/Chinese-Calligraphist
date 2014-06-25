package domain;

import java.util.List;
import java.util.Map;

import core.sketch.Point;
import core.sketch.Stroke;

public class Constraint {
	DomainShape para1 = null;
	DomainShape para2 = null;
	String feedback = "";
	String check = "";
	String para1Spec = "";
	String para2Spec = "";
	Integer around = 0;

	public Constraint() {
		// TODO
	}

	public Constraint(Map<String, String> constraint) {
		String check = constraint.get("check");
		if (!check.equals(""))
			this.check = check;

		String para1Spec = constraint.get("para1Spec");
		if (!para1Spec.equals(""))
			this.para1Spec = para1Spec;

		String para2Spec = constraint.get("para2Spec");
		if (!para2Spec.equals(""))
			this.para2Spec = para2Spec;

		String around = constraint.get("around");
		if (!around.equals(""))
			this.around = Integer.parseInt(around);

		String feedback = constraint.get("feedback");
		if (!feedback.equals(""))
			this.feedback = feedback;

	}

	public void setPara1(DomainShape component) {
		para1 = component;
	}

	public void setPara2(DomainShape component) {
		para2 = component;
	}

	public boolean check() {
		boolean retValue = false;
		switch (check) {
		case "Above": {
			retValue = Above();
			break;
		}
		case "Below": {
			retValue = Below();
			break;
		}
		case "Left": {
			retValue = Left();
			break;
		}
		case "Right": {
			retValue = Right();
			break;
		}
		case "SameX": {
			retValue = SameX();
			break;
		}
		case "SameY": {
			retValue = SameY();
			break;
		}
		case "Before": {
			retValue = Before();
			break;
		}
		case "After": {
			retValue = After();
			break;
		}
		// other cases
		}
		return retValue;
	}

	private boolean After() {
		// this need extra info about stroke list to find the order of strokes
		List<Stroke> para1Strokes = para1.getStrokes();
		List<Stroke> para2Strokes = para2.getStrokes();
		
		Stroke stroke1 = para1Strokes.get(para1Strokes.size() - 1);
		Stroke stroke2 = para2Strokes.get(0);
		return (stroke1.serialNumber > stroke2.serialNumber);
	}

	private boolean Before() {
		List<Stroke> para1Strokes = para1.getStrokes();
		List<Stroke> para2Strokes = para2.getStrokes();
		
		Stroke stroke1 = para1Strokes.get(para1Strokes.size() - 1);
		Stroke stroke2 = para2Strokes.get(0);
		return (stroke1.serialNumber < stroke2.serialNumber);
	}

	private boolean Above() {
		double para1SpecValue = 0.0;
		double para2SpecValue = 0.0;
		if (para1Spec.equals("startPoint") || para1Spec.equals("endPoint")) {
			Point para1SpecPoint = getParaSpecPoint(para1, para1Spec);
			para1SpecValue = para1SpecPoint.getY();
		} else {
			para1SpecValue = getParaSpecValue(para1, para1Spec);
		}
		if (para2Spec.equals("startPoint") || para2Spec.equals("endPoint")) {
			Point para2SpecPoint = getParaSpecPoint(para2, para2Spec);
			para2SpecValue = para2SpecPoint.getY();
		} else {
			para2SpecValue = getParaSpecValue(para2, para2Spec);
		}
		return (para1SpecValue > para2SpecValue);
	}

	private boolean Below() {
		double para1SpecValue = 0.0;
		double para2SpecValue = 0.0;
		if (para1Spec.equals("startPoint") || para1Spec.equals("endPoint")) {
			Point para1SpecPoint = getParaSpecPoint(para1, para1Spec);
			para1SpecValue = para1SpecPoint.getY();
		} else {
			para1SpecValue = getParaSpecValue(para1, para1Spec);
		}
		if (para2Spec.equals("startPoint") || para2Spec.equals("endPoint")) {
			Point para2SpecPoint = getParaSpecPoint(para2, para2Spec);
			para2SpecValue = para2SpecPoint.getY();
		} else {
			para2SpecValue = getParaSpecValue(para2, para2Spec);
		}
		return (para1SpecValue < para2SpecValue);
	}

	private boolean Left() {
		double para1SpecValue = 0.0;
		double para2SpecValue = 0.0;
		if (para1Spec.equals("startPoint") || para1Spec.equals("endPoint")) {
			Point para1SpecPoint = getParaSpecPoint(para1, para1Spec);
			para1SpecValue = para1SpecPoint.getX();
		} else {
			para1SpecValue = getParaSpecValue(para1, para1Spec);
		}
		if (para2Spec.equals("startPoint") || para2Spec.equals("endPoint")) {
			Point para2SpecPoint = getParaSpecPoint(para2, para2Spec);
			para2SpecValue = para2SpecPoint.getX();
		} else {
			para2SpecValue = getParaSpecValue(para2, para2Spec);
		}
		return (para1SpecValue < para2SpecValue);
	}

	private boolean Right() {
		double para1SpecValue = 0.0;
		double para2SpecValue = 0.0;
		if (para1Spec.equals("startPoint") || para1Spec.equals("endPoint")) {
			Point para1SpecPoint = getParaSpecPoint(para1, para1Spec);
			para1SpecValue = para1SpecPoint.getX();
		} else {
			para1SpecValue = getParaSpecValue(para1, para1Spec);
		}
		if (para2Spec.equals("startPoint") || para2Spec.equals("endPoint")) {
			Point para2SpecPoint = getParaSpecPoint(para2, para2Spec);
			para2SpecValue = para2SpecPoint.getX();
		} else {
			para2SpecValue = getParaSpecValue(para2, para2Spec);
		}
		return (para1SpecValue > para2SpecValue);
	}

	private double getParaSpecValue(DomainShape shape, String spec) {
		double retValue = 0.0;
		switch (spec) {
		case "Bottom": {
			retValue = shape.getBoundingBox().getDown();
			break;
		}
		case "Top": {
			retValue = shape.getBoundingBox().getUp();
			break;
		}
		case "Left": {
			retValue = shape.getBoundingBox().getLeft();
			break;
		}
		case "Right": {
			retValue = shape.getBoundingBox().getRight();
			break;
		}
		// other cases
		}
		return retValue;

	}

	private Point getParaSpecPoint(DomainShape shape, String spec) {
		Point retPoint = null;
		List<Stroke> strokes = shape.getStrokes();
		switch (spec) {
		case "startPoint": {
			retPoint = strokes.get(0).getPoints().get(0);
			break;
		}
		case "endPoint": {
			Stroke stroke = strokes.get(strokes.size() - 1);
			List<Point> points = stroke.getPoints();
			retPoint = points.get(points.size() - 1);
			break;
		}
		}
		return retPoint;
	}

	private boolean SameX() {
		double para1SpecValue = 0.0;
		double para2SpecValue = 0.0;
		if (para1Spec.equals("startPoint") || para1Spec.equals("endPoint")) {
			Point para1SpecPoint = getParaSpecPoint(para1, para1Spec);
			para1SpecValue = para1SpecPoint.getX();
		} else {
			para1SpecValue = getParaSpecValue(para1, para1Spec);
		}
		if (para2Spec.equals("startPoint") || para2Spec.equals("endPoint")) {
			Point para2SpecPoint = getParaSpecPoint(para2, para2Spec);
			para2SpecValue = para2SpecPoint.getX();
		} else {
			para2SpecValue = getParaSpecValue(para2, para2Spec);
		}
		return (Math.abs(para1SpecValue - para2SpecValue) < around);
	}

	private boolean SameY() {
		double para1SpecValue = 0.0;
		double para2SpecValue = 0.0;
		if (para1Spec.equals("startPoint") || para1Spec.equals("endPoint")) {
			Point para1SpecPoint = getParaSpecPoint(para1, para1Spec);
			para1SpecValue = para1SpecPoint.getY();
		} else {
			para1SpecValue = getParaSpecValue(para1, para1Spec);
		}
		if (para2Spec.equals("startPoint") || para2Spec.equals("endPoint")) {
			Point para2SpecPoint = getParaSpecPoint(para2, para2Spec);
			para2SpecValue = para2SpecPoint.getY();
		} else {
			para2SpecValue = getParaSpecValue(para2, para2Spec);
		}
		return (Math.abs(para1SpecValue - para2SpecValue) < around);
	}
}
