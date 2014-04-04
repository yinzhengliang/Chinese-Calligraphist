package recognizer;

import java.util.List;

import core.sketch.Interpretation;
import core.sketch.Stroke;

public interface IRecognizer {
	public static Preprosessor processer = new Preprosessor();
	
	public abstract List<Interpretation> recognize(Stroke stroke);
	
	public abstract void preprosessing(Stroke stroke);
}
