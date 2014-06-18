package data.dfeatures;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

public class RubineFeatureGenerator {
	private static String invMatrix = Constant.MODEL_DIR + "Rubine/" + "invmatrix1.txt";
	private static String training_folder = Constant.MODEL_DIR + "Rubine/" + "Rubine";
	private static String output_file = Constant.MODEL_DIR + "Rubine/" + "Rubine_Final_Weight_lol.txt";
	private static Map<String, List<Stroke>> templates = new HashMap<String, List<Stroke>>();
	
	private static Rubine rubine = new Rubine();
	
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
		generate();
	}
	
	public RubineFeatureGenerator() {
	}
	
	public static void generate() {
		Map<String, Map<String, Double>> classFeature = new HashMap<String, Map<String, Double>>();
		for (Map.Entry<String, List<Stroke>> entry : templates.entrySet()) {
			Map<String, List<Double>> data = new HashMap<String, List<Double>>();
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
			
			Map<String, Double> means = new HashMap<String, Double> ();
			for (Map.Entry<String, List<Double>> feature : data.entrySet()) {
				Double mean = 0.0;
				for (Double num : feature.getValue()) {
					mean += num;
				}
				mean /= feature.getValue().size();
				means.put(feature.getKey(), mean);
			}
			
			classFeature.put(entry.getKey(), means);
		}
		
		// use mean and matrix to compute
		
		//List<String> featureNames = featureName(matrix.keySet());
		Map<String, Map<String, Double>> matrix = readMatrix();
		
		
		Map<String, Map<String, Double>> weightMatrix = new HashMap<String, Map<String, Double>> ();
		
		for (Map.Entry<String, Map<String, Double>> entry : classFeature.entrySet()) {
			String className = entry.getKey();
			Map<String, Double> meanList = entry.getValue();
			Map<String, Double> weightValue = new HashMap<String, Double>();
			for (Map.Entry<String, Map<String, Double>> inverseEntry : matrix.entrySet()) {
				String featureName = inverseEntry.getKey();
				Map<String, Double> featureValue = inverseEntry.getValue();
				Double value = 0.0;
				for (String name : featureValue.keySet()) {
					value += matrix.get(name).get(featureName) * meanList.get(name);
				}
				weightValue.put(featureName, value);
			}
			weightMatrix.put(className, weightValue);
		}
		
		// calculate w0
		for (Map.Entry<String, Map<String, Double>> entry : weightMatrix.entrySet()) {
			Double weight_0 = 0.0;
			Map<String, Double> weightValue = entry.getValue();
			for (String name : weightValue.keySet()) {
				weight_0 += weightValue.get(name) * classFeature.get(entry.getKey()).get(name);
			}
			weight_0 /= (-2);
			weightValue.put("f0", weight_0);
		}
		
		List<String> featureNames = featureName(matrix.keySet());
		write(weightMatrix, featureNames);
	}
	
	
	
	public static Map<String, Map<String, Double>> readMatrix() {
		try {
			Map<String, Map<String, Double>> matrix = new HashMap<String, Map<String, Double>>();
			BufferedReader reader = new BufferedReader(new FileReader(invMatrix));
			String line = "";
			int i = 0;
			while ((line = reader.readLine()) != null) {
				i++;
				String [] features = line.split(",");
				int j = 0;
				Map<String, Double> map = new HashMap<String, Double>();
				for (String feature : features) {
					j++;
					map.put(String.format("f%d", j), Double.parseDouble(feature));
				}
				matrix.put(String.format("f%d", i), map);
			}
			reader.close();
			return matrix;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static List<String> featureName(Set<String> data) {
		List<String> retVal = new ArrayList<String>();
		for (int i = 0; i <= data.size(); i++) {
			retVal.add(String.format("f%d", i));
		}
		return retVal;
	}
	
	public static void write(Map<String, Map<String, Double>> data, List<String> featureNames) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(output_file));
			writer.write("class\t");
			for (String name : featureNames) writer.write(name + "\t");
			writer.newLine();
			for (String c_name : data.keySet()) {
				writer.write(c_name + "\t");
				for (String s_name : featureNames) {
					writer.write(String.format("%.4f\t",data.get(c_name).get(s_name)));
				}
//				writer.write(";");
				writer.newLine();
			}
			
			
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
