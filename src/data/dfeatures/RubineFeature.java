package data.dfeatures;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import constants.Constant;
import recognizer.low.gesture.Rubine;
import core.sketch.Stroke;
import data.StrokeLoader;

public class RubineFeature {
	private static String output_folder = Constant.MODEL_DIR + "Rubine/" + "Rubine.txt";
	
	private static String training_folder = Constant.MODEL_DIR + "Rubine/" + "Rubine";
	private static Map<String, List<Stroke>> templates = new HashMap<String, List<Stroke>>();
	
	
	public static void loadTemplates() {
		File[] files = new File(training_folder).listFiles();
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
	
	public static void main(String []args) {
		loadTemplates();
		RubineFeatures();
	}
	
	
	
	public static void RubineFeatures() {
		Rubine rubine = new Rubine();
		
		int totalnum = 0;
		Map<String, Map<String, Double>> matrix  = new HashMap<String, Map<String, Double>>();
		
		
		for (Map.Entry<String, List<Stroke>> entry : templates.entrySet()) {
			Map<String, List<Double>> data = new HashMap<String, List<Double>>();
			totalnum += (entry.getValue().size() - 1);
			for (Stroke template : entry.getValue()) {
				Map<String, Double> features = rubine.calculateFeatures(template);
				for (Map.Entry<String, Double> feature : features.entrySet()) {
					List<Double> values = data.get(feature.getKey());
					if (values == null) {
						values = new ArrayList<Double>();
						data.put(feature.getKey(), values);
					}
					values.add(feature.getValue());
				}
			}
			
			matrix = addMatrix(matrix, getCovMatrix(data));
		}
		
		List<String> featureNames = featureName(matrix.keySet());
		
		for (String name : featureNames) {
			for (String s_name : featureNames) {
				Double cov_number = matrix.get(name).get(s_name) / totalnum;
				matrix.get(name).put(s_name, cov_number);
			}
		}
		
		write(matrix);
	}
	
	public static List<String> featureName(Set<String> data) {
		List<String> retVal = new ArrayList<String>();
		for (int i = 0; i < data.size(); i++) {
			retVal.add(String.format("f%d", i+1));
		}
		return retVal;
	}
	
	public static Map<String, Map<String, Double>> getCovMatrix(Map<String, List<Double>> data) {
		Map<String, Map<String, Double>> retVal = new HashMap<String, Map<String, Double>>();
		List<String> featureNames = featureName(data.keySet());
		for (String name : featureNames) {
			Map<String, Double> map = new HashMap<String, Double>();
			for (String s_name : featureNames) {
				map.put(s_name, 0.0);
			}
			retVal.put(name, map);
		}
		
		// mean
		Map<String, Double> means = new HashMap<String, Double> ();
		for (Map.Entry<String, List<Double>> feature : data.entrySet()) {
			Double mean = 0.0;
			for (Double num : feature.getValue()) {
				mean += num;
			}
			mean /= feature.getValue().size();
			means.put(feature.getKey(), mean);
		}
		
		// maybe later use double array
		for (String name : featureNames) {
			for (String s_name : featureNames) {
				Double cov_number = 0.0;
				for (int i = 0; i < data.get(s_name).size(); i++) {
					cov_number += ((data).get(name).get(i) - means.get(name)) * (data.get(s_name).get(i) - means.get(name));
				}
				//retVal.get(name).get(s_name);
				retVal.get(name).put(s_name, cov_number);
			}
		}
		
		return retVal;
	}
	
	public static Map<String, Map<String, Double>> addMatrix(Map<String, Map<String, Double>> m, Map<String, Map<String, Double>> s_m) {
		List<String> featureNames = featureName(s_m.keySet());
		
		if (m.isEmpty()) {
			for (String name : featureNames) {
				Map<String, Double> map = new HashMap<String, Double>();
				for (String s_name : featureNames) {
					map.put(s_name, 0.0);
				}
				m.put(name, map);
			}
		}
		
		for (String name : featureNames) {
			for (String s_name : featureNames) {
				Double cov_number = m.get(name).get(s_name) + s_m.get(name).get(s_name);
				m.get(name).put(s_name, cov_number);
			}
		}
		return m;
	}
	
	public static void write(Map<String, Map<String, Double>> data) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(output_folder));
			List<String> featureNames = featureName(data.keySet());
			writer.write("name\t");
			for (String name : featureNames) writer.write(name + "\t");
			writer.newLine();
			for (String name : featureNames) {
				writer.write(name + "\t");
				for (String s_name : featureNames) {
					writer.write(String.format("%.2f,",data.get(s_name).get(name)));
				}
				writer.write(";");
				writer.newLine();
			}
			
			
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void dump_to_json() {
		
	}
	
	
}
