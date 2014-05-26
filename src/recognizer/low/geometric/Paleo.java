package recognizer.low.geometric;

import java.util.List;


import core.sketch.Interpretation;
import core.sketch.Stroke;
import recognizer.IRecognizer;


public class Paleo implements IRecognizer {

	public static void main(String []args) {
		srl.core.sketch.Sketch ssketch = new srl.core.sketch.Sketch();
		srl.core.sketch.Stroke sstroke = new srl.core.sketch.Stroke();
		
		for(int i = 0; i < 20; i++) {
			sstroke.addPoint(new srl.core.sketch.Point(i, i));
		}
		
		ssketch.add(sstroke);
		
		srl.recognition.paleo.PaleoSketchRecognizer recognizer = new srl.recognition.paleo.PaleoSketchRecognizer(srl.recognition.paleo.PaleoConfig.allOn());
		
		srl.recognition.IRecognitionResult result = recognizer.recognize(ssketch.getFirstStroke());
		
		System.out.println(result.getBestShape().getInterpretation().label);
		
		
	}
	
	@Override
	public List<Interpretation> recognize(Stroke stroke) {
		// TODO Auto-generated method stub
		srl.core.sketch.Sketch ssketch = new srl.core.sketch.Sketch();
		srl.core.sketch.Stroke sstroke = new srl.core.sketch.Stroke();
		
		for(int i = 0; i < 20; i++) {
			sstroke.addPoint(new srl.core.sketch.Point(i, i));
		}
		
		ssketch.add(sstroke);
		
		srl.recognition.paleo.PaleoSketchRecognizer recognizer = new srl.recognition.paleo.PaleoSketchRecognizer(srl.recognition.paleo.PaleoConfig.allOn());
		
		srl.recognition.IRecognitionResult result = recognizer.recognize(ssketch.getFirstStroke());
		
		System.out.println(result.getBestShape().getName());
		
		return null;
	}

	@Override
	public void preprocess(Stroke stroke) {
		// TODO Auto-generatesd method stub
		
	}

}
