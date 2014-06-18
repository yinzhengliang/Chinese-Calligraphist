package recognizer.low.template;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import constants.Constant;
import core.sketch.Interpretation;
import core.sketch.Point;
import core.sketch.Stroke;
import data.StrokeLoader;
import recognizer.IRecognizer;

public class Hausdroff implements IRecognizer {
	private Map<String, List<Stroke>> templates = new HashMap<String, List<Stroke>>();
	private Stroke stroke_to_recognize = new Stroke();
	private String templates_folder = Constant.TEMPLATE_DIR + "Hausdorff/" + "templates";
	
	public Hausdroff() {
		loadTemplates();
	}
	
	@Override
	public List<Interpretation> recognize(Stroke stroke) {
		// TODO Auto-generated method stub
		preprocess(stroke);
		
		Set<Interpretation> interpretations = new TreeSet<Interpretation>();
		double normalizer = Double.MAX_VALUE;
		for (Map.Entry<String, List<Stroke>> entry : templates.entrySet()) {
			String name = entry.getKey();
			double distance = 0.0;
			double min_distance = Double.MAX_VALUE;
			for (Stroke template : entry.getValue()) {
				double cur_distance = h_distance(template, stroke_to_recognize);
				distance += cur_distance;
				min_distance = (min_distance < cur_distance? min_distance : cur_distance);
				normalizer = (normalizer < cur_distance? normalizer : cur_distance);
			}
			interpretations.add(new Interpretation(name,  (distance / entry.getValue().size() + min_distance) / 2.0));
//			interpretations.add(new Interpretation(name,  min_distance));
		}
		List<Interpretation> results = new ArrayList<Interpretation>();
		
//		normalizer += 1;
//		System.out.println(normalizer);
		for (Interpretation interpretation : interpretations) {
			double confidence = normalizer / interpretation.getConfidence();
//			if (confidence > 0.5) {
				interpretation.setConfidence(confidence);
//			}
		}
		results.addAll(interpretations);
		interpretations.clear();
		interpretations.addAll(results);
		results.clear();
		results.addAll(interpretations);
		return results;
	}

	@Override
	public void preprocess(Stroke stroke) {
		// TODO Auto-generated method stub
		stroke_to_recognize.clear();
		stroke_to_recognize.copy(stroke);
		stroke_to_recognize = IRecognizer.preprocesser.translate(stroke_to_recognize);
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
//	            System.out.println("Directory: " + file.getAbsolutePath());
	            String name = file.getName();
	            templates.put(name, new ArrayList<Stroke>());
	            
	            for (File subfile : file.listFiles()) {
	            	StrokeLoader loader = new StrokeLoader();
	 	    		loader.setDocument(subfile);
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
	
	private double distance_point_to_stroke(Point point, Stroke stroke) {
		double distance = 0.0;
		distance = point.distanceTo(stroke.getPoints().get(0));
		for (Point targetPoint : stroke.getPoints()) {
			double cur_distance = point.distanceTo(targetPoint);
			distance = (distance < cur_distance? distance : cur_distance);
		}
		return distance;
	}
	
	private double h_distance(Stroke strokeA, Stroke strokeB) {
		double distance = 0.0;
		
		for (Point point : strokeA.getPoints()) {
			double cur_distance = distance_point_to_stroke(point, strokeB);
			distance = (distance > cur_distance? distance : cur_distance);
		}

		for (Point point : strokeB.getPoints()) {
			double cur_distance = distance_point_to_stroke(point, strokeA);
			distance = (distance > cur_distance? distance : cur_distance);
		}

		return distance;
	}

}
  