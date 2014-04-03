package recognizer;

import java.util.List;

import core.sketch.Interpretation;
import core.sketch.Stroke;

public interface IRecognizer {
	public abstract List<Interpretation> recognize(Stroke stroke);
}
