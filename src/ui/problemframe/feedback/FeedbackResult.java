package ui.problemframe.feedback;

import java.util.ArrayList;
import java.util.List;

import core.sketch.Interpretation;
import core.sketch.Stroke;

public class FeedbackResult {
	private String text = "test";
	private List<Stroke> strokes = new ArrayList<Stroke>();

	public String toString() {
		return text;
	}

	public FeedbackResult() {
		
	}
	
	public FeedbackResult(Interpretation interpretation, List<Stroke> recognizedStrokes) {
		setStrokes(recognizedStrokes);
		text = "Stroke: " + interpretation.getName() + " is recognized.";
	}

	public List<Stroke> getStrokes() {
		return strokes;
	}

	public void setStrokes(List<Stroke> strokes) {
		this.strokes = strokes;
	}
	
}
