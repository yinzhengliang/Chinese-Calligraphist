package recognizer.high;

import java.util.ArrayList;
import java.util.List;

import ui.ChineseCalligraphist.ChineseCalligraphistGUI;
import ui.problemframe.feedback.FeedbackPanel;
import constants.Constant;
import core.sketch.Interpretation;
import core.sketch.Stroke;
import domain.DomainList;
import domain.DomainShape;
import domain.ShapeType.Type;

public class Ladder {
	private DomainList m_domains;
	private List<DomainShape> m_shapes = new ArrayList<DomainShape>();
	private List<Stroke> m_strokes = new ArrayList<Stroke>();
	List<DomainShape> potentialShapes = new ArrayList<DomainShape>();

	public void clear() {
		m_shapes.clear();
		m_strokes.clear();
	}
	
	public void undo(List<Stroke> strokes) {
		m_shapes.clear();
		m_strokes.clear();
		
		// replay without feedback info
		for (Stroke stroke : strokes) {
			m_strokes.add(stroke);
			Interpretation interpretation = ChineseCalligraphistGUI.recognizer.recognize(stroke).get(0);
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
			m_observation.setBoundingBox(stroke.getBoundingBox());
			// then check other shapes
			
			DomainShape strokeShape = getStrokeShapeWithoutFeedback(m_observation);
			
			m_shapes.add(strokeShape);
			
			// check higher level
			segmentShapesWithoutFeedback();
		
		}
	}
	

	public Ladder() {
		// TODO
//		reset();
		reset(Constant.PROBLEMSET_PATH + "Lesson0/DomainDefinition/Lesson0.xml");
		System.out.println("done building ladder");
	}

	private void reset(String path) {
		m_domains = new DomainList(path);
		getShapeRecursively(m_domains.getShapes(), potentialShapes);
	}

	public void recognize(Stroke stroke, Interpretation interpretation) {
		m_strokes.add(stroke);
		
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
		m_observation.setBoundingBox(stroke.getBoundingBox());
		// then check other shapes
		
		DomainShape strokeShape = getStrokeShape(m_observation);
		
		m_shapes.add(strokeShape);
		
		// check higher level
		segmentShapes();
		
	}

	private void segmentShapes() {
		// TODO Auto-generated method stub
		// m_shapes.add( new higher level shapes)
		boolean retValue = false;
		for (DomainShape shape : potentialShapes) {
			List<DomainShape> components = shape.getComponents();
			if (components.size() == 0) continue;
			List<DomainShape> matches = findComponents(components);
			
			
			if (matches == null) continue;
		
			// check whether fit the constraints. name the shape with alias after pass the constraints
			DomainShape possible_character = new DomainShape();
			possible_character.setName(shape.getName());
			possible_character.setType(shape.getType());
			

			List<String> names = new ArrayList<String>();
			List<String> aliases = new ArrayList<String>();
			List<Type> types = new ArrayList<Type>();
			for (DomainShape component : components) {
				names.add(component.getName());
				aliases.add(component.getAlias());
				types.add(component.getType());
			}
			
			// add components
			for (DomainShape match : matches) {
				// assign alias
				for (int i = 0; i < names.size(); i++) {
					if (match.getName().equals(names.get(i)) && match.getType().equals(types.get(i))) {
						names.remove(i);
						types.remove(i);
						String alias = aliases.remove(i);
						possible_character.addComponent(match, alias);
					}
				}
			}
			possible_character.copyConstraints(shape.getConstraints());
			possible_character.copyFeedbacks(shape.getFeedbacks());
			possible_character.copyMisClassifyWarning(shape.getMisClassfication());

				
			if (checkSatisfy(possible_character, matches)) {
				System.out.println(shape.getName() + " " + shape.getType() + " found");
				m_shapes.add(possible_character);
				for (DomainShape toremove : matches) {
					m_shapes.remove(toremove);
				}
				
				
				FeedbackPanel.addFeedback(possible_character.getType() + ": [" + possible_character.getName() + "] is recognized.", possible_character.getStrokes());
				
				possible_character.completeFeedbacks();
				possible_character.checkFeedback();
				
				retValue = true;
			}
		}
		if (retValue) {
			segmentShapes();
		}
	}


	private boolean checkSatisfy(DomainShape possible_character, List<DomainShape> matches) {
		boolean retValue = false;
		if (!checkSameComponents(matches)) {
			retValue = possible_character.checkConstraints();
		}
		else {
			retValue = possible_character.checkConstraints();
			if (!retValue) retValue = possible_character.checkConstraintsWithShuffledAlias();
		}
		
		if (retValue) {
			for (DomainShape component : matches) {
				possible_character.addStrokes(component.getStrokes());
				possible_character.getBoundingBox().expand(component.getBoundingBox());
			}
		}
		return retValue;
	}

	private boolean checkSameComponents(List<DomainShape> matches) {
		// Bug to fix, try to return list of pairs (sets) of same component, so that we can do shuffle alias inside the set
		List<String> names = new ArrayList<String>();
		List<Type> types = new ArrayList<Type>();
		
		for (DomainShape shape : matches) {
			for (int i = 0; i < names.size(); i++) {
				if (shape.getName().equals(names.get(i)) && shape.getType().equals(types.get(i))) return true;
			}
			names.add(shape.getName());
			types.add(shape.getType());
		}
		return false;
	}

	private List<DomainShape> findComponents(List<DomainShape> components) {
		// bug here is that only check the first found components, need to fix it.
		List<DomainShape> find = new ArrayList<DomainShape>();
		if (components.size() > m_shapes.size()) return null;
		for (DomainShape tohave : components) {
			for (DomainShape candidates : m_shapes) {
				if (!find.contains(candidates)) {
					if (tohave.getType().equals(candidates.getType()) && tohave.getName().equals(candidates.getName())) {
						find.add(candidates);
						break;
					}
				}
			}
		}
		if (find.size() < components.size()) return null;
		return find;
	}

	private DomainShape getStrokeShape(DomainShape m_observation) {
		DomainShape m_strokeShape = new DomainShape();
		for (DomainShape shape : potentialShapes) {
			List<DomainShape> components = shape.getComponents();
			for (DomainShape component : components) {
				if (component.getName().equals(m_observation.getName()) && component.getType().equals(m_observation.getType())) {

					m_strokeShape.addStrokes(m_observation.getStrokes());
					m_strokeShape.setName(m_observation.getName());
					m_strokeShape.setType(Type.Stroke);
					m_strokeShape.setBoundingBox(m_observation.getBoundingBox());

					m_strokeShape.addComponent(m_observation, component.getAlias());
					m_strokeShape.copyConstraints(shape.getConstraints());
					m_strokeShape.copyFeedbacks(shape.getFeedbacks());
					m_strokeShape.copyMisClassifyWarning(shape.getMisClassfication());
				}
			}
		}
		
		m_strokeShape.completeFeedbacks();
		
		m_strokeShape.checkFeedback();
		
		return m_strokeShape;
	}
	
	
	private DomainShape getStrokeShapeWithoutFeedback(DomainShape m_observation) {
		DomainShape m_strokeShape = new DomainShape();
		for (DomainShape shape : potentialShapes) {
			List<DomainShape> components = shape.getComponents();
			for (DomainShape component : components) {
				if (component.getName().equals(m_observation.getName()) && component.getType().equals(m_observation.getType())) {

					m_strokeShape.addStrokes(m_observation.getStrokes());
					m_strokeShape.setName(m_observation.getName());
					m_strokeShape.setType(Type.Stroke);
					m_strokeShape.setBoundingBox(m_observation.getBoundingBox());

					m_strokeShape.addComponent(m_observation, component.getAlias());
					m_strokeShape.copyConstraints(shape.getConstraints());
					m_strokeShape.copyFeedbacks(shape.getFeedbacks());
					m_strokeShape.copyMisClassifyWarning(shape.getMisClassfication());
				}
			}
		}
		
		m_strokeShape.completeFeedbacks();
		
		return m_strokeShape;
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
	
	private void segmentShapesWithoutFeedback() {
		// m_shapes.add( new higher level shapes)
		boolean changed = false;
		for (DomainShape shape : potentialShapes) {
			List<DomainShape> components = shape.getComponents();
			if (components.size() == 0) continue;
			List<DomainShape> matches = findComponents(components);
			
			
			if (matches == null) continue;
		
			// check whether fit the constraints. name the shape with alias after pass the constraints
			DomainShape possible_character = new DomainShape();
			possible_character.setName(shape.getName());
			possible_character.setType(shape.getType());
			

			List<String> names = new ArrayList<String>();
			List<String> aliases = new ArrayList<String>();
			List<Type> types = new ArrayList<Type>();
			for (DomainShape component : components) {
				names.add(component.getName());
				aliases.add(component.getAlias());
				types.add(component.getType());
			}
			
			// add components
			for (DomainShape match : matches) {
				// assign alias
				for (int i = 0; i < names.size(); i++) {
					if (match.getName().equals(names.get(i)) && match.getType().equals(types.get(i))) {
						names.remove(i);
						types.remove(i);
						String alias = aliases.remove(i);
						possible_character.addComponent(match, alias);
					}
				}
			}
			possible_character.copyConstraints(shape.getConstraints());
			possible_character.copyFeedbacks(shape.getFeedbacks());
			possible_character.copyMisClassifyWarning(shape.getMisClassfication());

				
			if (checkSatisfy(possible_character, matches)) {
				m_shapes.add(possible_character);
				for (DomainShape toremove : matches) {
					m_shapes.remove(toremove);
				}
				
				changed = true;
				possible_character.completeFeedbacks();
				
			}
		}
		
		if (changed) {
			segmentShapesWithoutFeedback();
		}
	}

}
