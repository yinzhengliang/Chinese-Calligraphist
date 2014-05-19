package recognizer;

import java.util.List;

import core.sketch.Interpretation;
import core.sketch.Stroke;

public interface IRecognizer {
	public Preprocessor preprocesser = new Preprocessor();
	
	public abstract List<Interpretation> recognize(Stroke stroke);
	
	public abstract void preprocess(Stroke stroke);
}
