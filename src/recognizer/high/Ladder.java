package recognizer.high;

import java.util.ArrayList;
import java.util.List;

import core.sketch.Interpretation;
import core.sketch.Stroke;
import domain.DomainList;
import domain.DomainShape;
import domain.ShapeType.Type;

public class Ladder {
	private DomainList m_domains;
	private List<DomainShape> m_shapes;
	private List<Stroke> m_strokes;
	List<DomainShape> potentialShapes = new ArrayList<DomainShape>();

	public Ladder() {
		// TODO
//		reset();
		
	}

	private void reset(String path) {
		m_domains = new DomainList(path);
		getShapeRecursively(m_domains.getShapes(), potentialShapes);
	}

	public void recognize(Stroke stroke, Interpretation interpretation) {
		DomainShape m_observation = new DomainShape();
		m_observation.setName(interpretation.getName());
		m_observation.setType(Type.Observation);
		m_observation.addStroke(stroke);
		for (DomainShape shape : potentialShapes) {
			if (shape.getName().equals(m_observation.getName()) && shape.getType().equals(m_observation.getType())) {
				m_observation.copyComponent(shape.getComponents());
				m_observation.copyConstraints(shape.getConstraints());
				m_observation.copyFeedbacks(shape.getFeedbacks());
				m_observation.copyMisClassifyWarning(shape.getMisClassfication());
			}
		}
		// then check other shapes

	}

	private List<DomainShape> getShapeRecursively(List<DomainShape> shapes, List<DomainShape> potentialShapes) {
		for (DomainShape shape : shapes) {
			String name = shape.getName();
			Type type = shape.getType();
			boolean alreadyIn = false;
			for (DomainShape exist : potentialShapes) {
				if (exist.getName().equals(name) && exist.getType().equals(type)) {
					alreadyIn = true;
					break;
				}
			}
			if (!alreadyIn) {
				potentialShapes.add(shape.copy());
			}
			getShapeRecursively(shape.getComponents(), potentialShapes);
		}
		return potentialShapes;
	}

	public void addStroke(Stroke stroke) {
		m_strokes.add(stroke);
	}
}
