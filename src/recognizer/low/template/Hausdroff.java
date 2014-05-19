package recognizer.low.template;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import core.sketch.Interpretation;
import core.sketch.Point;
import core.sketch.Stroke;
import data.StrokeLoader;
import data.StrokeSaver;
import recognizer.IRecognizer;
import recognizer.Preprocessor;

public class Hausdroff implements IRecognizer {
	Map<String, List<Stroke>> templates = null;
	private String templates_folder = "C:/Users/Yin/Desktop/basic_strokes";
	
	public Hausdroff() {
		loadTemplates();
	}
	
	@Override
	public List<Interpretation> recognize(Stroke stroke) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void preprocess(Stroke stroke) {
		// TODO Auto-generated method stub
		Stroke stroke_to_recognize = new Stroke();
		stroke_to_recognize = IRecognizer.preprocesser.translate(stroke);
		stroke_to_recognize = IRecognizer.preprocesser.scale(stroke_to_recognize, 40);
		stroke_to_recognize = IRecognizer.preprocesser.resample(stroke_to_recognize, 40);
	}
	
	// first try, not 40*40 pixels
	// second try, 40 by 40 pixels
	
	public void loadTemplates() {
		File[] files = new File(templates_folder).listFiles();
		//File[] files = new File("/media/yin/4276E4DF76E4D4A7/Users/Yin/Desktop/m_test/compound_strokes").listFiles();
		
		for (File file : files) {
	        if (file.isDirectory()) {
	            System.out.println("Directory: " + file.getAbsolutePath());
	            String name = file.getName();
	            templates.put(name, new ArrayList<Stroke>());
	            
	            for (File subfile : file.listFiles()) {
	            	 StrokeLoader loader = new StrokeLoader();
	 	    		loader.setDocument(file);
	 	    		loader.setStrokes();
	 	    		List<Stroke> strokes = loader.getStrokes();
	 	    		if (strokes.size() > 1) {
	 	    			System.out.println("!!!!!!!!!!!!!!!!More Than One Stroke!!!!!!!!!!!!!!!!");
	 	    		}
	 	    		Stroke stroke = strokes.get(0);
	 	    		templates.get(name).add(stroke);
	            }
	        } else {
	        	
	        }
	    }
		
		
	}
	
//	public 
}
  