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

public class Dollar implements IRecognizer {
	private String templates_folder = Constant.TEMPLATE_DIR + "OneDollar/" + "templates";
	private Map<String, List<Stroke>> templates = new HashMap<String, List<Stroke>>();
	
	private Stroke stroke_to_recognize = new Stroke();
	private double PHI = 0.618; // Golden ratio: (-1 + sqrt(5)) / 2
	private double theta_Alpha = 0.785; // 45 degree, Pi / 4
	private double theta_Beta = -0.785;
	private double theta = 0.035; // 2 degree, Pi / 180
	private double sqrt_two = 1.414;
	
	public Dollar() {
		loadTemplates();
	}
	
	@Override
	public List<Interpretation> recognize(Stroke stroke) {
		// TODO Auto-generated method stub
		preprocess(stroke);
		
		Set<Interpretation> interpretations = new TreeSet<Interpretation>();
		for (Map.Entry<String, List<Stroke>> entry : templates.entrySet()) {
			String name = entry.getKey();
			double min_distance = Double.MAX_VALUE;
			for (Stroke template : entry.getValue()) {
				double distance = distance_at_best_angle(stroke_to_recognize.getPoints(), template, theta_Alpha, theta_Beta, theta);
				min_distance = (min_distance < distance ? min_distance : distance);
			}
			
			double confidence =  1.0 - sqrt_two * min_distance / 40.0;
			Interpretation inter = new Interpretation(name, confidence);
			interpretations.add(inter);
		}
		List<Interpretation> results = new ArrayList<Interpretation>();
//		results.addAll(interpretations);
		for (Interpretation interpretation : interpretations) {
//			double confidence = interpretation.getConfidence();
//			if (confidence > 0.5) {
//				interpretation.setConfidence(confidence);
				results.add(interpretation);
//			}
		}
		return results;
	}

	@Override
	public void preprocess(Stroke stroke) {
		// TODO Auto-generated method stub
		stroke_to_recognize.clear();
		stroke_to_recognize.copy(stroke);
		stroke_to_recognize = IRecognizer.preprocesser.resample(stroke_to_recognize, 40);
		
		double angle = IRecognizer.preprocesser.indicative_angle(stroke_to_recognize.getPoints());
		List<Point> newPoints = IRecognizer.preprocesser.rotate_by(stroke_to_recognize.getPoints(), angle);
		stroke_to_recognize.clear();
		stroke_to_recognize.setPoints(newPoints);
		stroke_to_recognize = IRecognizer.preprocesser.translate(stroke_to_recognize);
		stroke_to_recognize = IRecognizer.preprocesser.scale(stroke_to_recognize, 40);
		
	}
	
	private double distance(List<Point> points, List<Point> template) {
		double d = 0.0;
		for (int i = 0; i < 40; i++) {
			d += points.get(i).distanceTo(template.get(i));
		}
		return d / points.size();
	}

	private double distance_at_angle(List<Point> points, Stroke template, double theta) {
		List<Point> newPoints = IRecognizer.preprocesser.rotate_by(points, theta);
		double d = distance(newPoints, template.getPoints());
		return d;
	}
	
	private double distance_at_best_angle(List<Point> points, Stroke template, double theta_A, double theta_B, double theta_Delta) {
		double x1 = PHI * theta_A + (1 - PHI) * theta_B;
		double x2 = (1 - PHI) * theta_A + PHI * theta_B;
		double f1 = distance_at_angle(points, template, x1);
		double f2 = distance_at_angle(points, template, x2);
		while(Math.abs(theta_B - theta_A) > theta_Delta) {
			if (f1 < f2) {
				theta_B = x2;
				x2 = x1;
				f2 = f1;
				x1 = PHI * theta_A + (1 - PHI) * theta_B;
				f1 = distance_at_angle(points, template, x1);
			} else {
				theta_A = x1;
				x1 = x2;
				f1 = f2;
				x2 = (1 - PHI) * theta_A + PHI * theta_B;
				f2 = distance_at_angle(points, template, x2);
			}
		}
		if (f1 > f2) f1 = f2;
		return f1;
	}
	
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
}
