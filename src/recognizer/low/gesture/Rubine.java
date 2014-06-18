package recognizer.low.gesture;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
import recognizer.IRecognizer;

public class Rubine implements IRecognizer {
	private String modelFilePath = Constant.MODEL_DIR + "Rubine/" + "Rubine_Final_Weight_lol.txt";
	private boolean neuralNetwork = false;
	private Map<String, Map<String, Double>> weights = null;

	public Rubine() {
		weights = calcWeight(modelFilePath);
	}
	
	public void useNN(boolean nn) {
		neuralNetwork = nn;
	}
	
	@Override
	public List<Interpretation> recognize(Stroke stroke) {
//		if (neuralNetwork) return NNRubine(stroke);
//		return originalRubine(stroke);
		
		
		Set<Interpretation> recognitionSet = new TreeSet<Interpretation>();
		Map<String, Double> features = calculateFeatures(stroke);
		
		for (Map.Entry<String, Map<String, Double>> entry : weights.entrySet()) {
			String Class = entry.getKey();
			Double Confidence = 0.0;
			for (Map.Entry<String, Double> weight : entry.getValue().entrySet()) {
				String featureName = weight.getKey();
				if (featureName.equals("f0")) Confidence += weight.getValue();
				else Confidence += features.get(featureName) * weight.getValue();
			}
			recognitionSet.add(new Interpretation(Class, Confidence));
		}
		List<Interpretation> recognitionResult = new ArrayList<Interpretation>(recognitionSet);
		return recognitionResult;
	}
	
	public List<Interpretation> NNRubine(Stroke stroke) {
		Set<Interpretation> recognitionSet = new TreeSet<Interpretation>();
		
		//TODO change the "file" to some config constance
		Map<String, Map<String, Double>> lowLevel = getWeight("File");
		Map<String, Double> features = calculateFeatures(stroke);
		features.put("Bias", 1.0);
		Map<String, Double> hiddenLayer = new HashMap<String, Double>();
		for (Map.Entry<String, Map<String, Double>> entry : lowLevel.entrySet()) {
			String hiddenUnitName = entry.getKey();
			Double hiddenUnitValue = 0.0;
			for (Map.Entry<String, Double> weight : entry.getValue().entrySet()) {
				String featureName = weight.getKey();
				hiddenUnitValue += features.get(featureName) * weight.getValue();
			}
			hiddenLayer.put(hiddenUnitName, hiddenUnitValue);
		}
		
		//TODO change the "file" to some config constance
		hiddenLayer.put("Bias", 1.0);
		Map<String, Map<String, Double>> highLevel = getWeight("File");
		for (Map.Entry<String, Map<String, Double>> entry : highLevel.entrySet()) {
			String Class = entry.getKey();
			Double Confidence = 0.0;
			for (Map.Entry<String, Double> weight : entry.getValue().entrySet()) {
				String hiddenUnitName = weight.getKey();
				Confidence += features.get(hiddenUnitName) * weight.getValue();
			}
			recognitionSet.add(new Interpretation(Class, Confidence));
		}
		
		List<Interpretation> recognitionResult = new ArrayList<Interpretation>(recognitionSet);
		stroke.setInterpretations(recognitionResult);
		return recognitionResult;
	}
	
	public List<Interpretation> originalRubine(Stroke stroke) {
		Set<Interpretation> recognitionSet = new TreeSet<Interpretation>();
		//TODO change the "file" to some config constance
		Map<String, Map<String, Double>> weights = getWeight("File");
		Map<String, Double> features = calculateFeatures(stroke);
		
		for (Map.Entry<String, Map<String, Double>> entry : weights.entrySet()) {
			String Class = entry.getKey();
			Double Confidence = 0.0;
			for (Map.Entry<String, Double> weight : entry.getValue().entrySet()) {
				String featureName = weight.getKey();
				Confidence += features.get(featureName) * weight.getValue();
			}
			recognitionSet.add(new Interpretation(Class, Confidence));
		}
		List<Interpretation> recognitionResult = new ArrayList<Interpretation>(recognitionSet);
		stroke.setInterpretations(recognitionResult);
		return recognitionResult;
	}
	
	public Map<String, Map<String, Double>> calcWeight(String modelFile) {
		Map<String, Map<String, Double>> weightMatrix = new HashMap<String, Map<String, Double>>();
		try (BufferedReader file = new BufferedReader(new FileReader(modelFile))) {
			String line;
			while ((line = file.readLine()) != null) {
				String[] pair = line.split("\t");
				String Class = pair[0];
				
				Map<String, Double> map = new HashMap<String, Double>();
				for (int i = 1; i < pair.length; i++) {
					map.put(String.format("f%d", i-1), Double.parseDouble(pair[i]));
				}
				weightMatrix.put(Class, map);
			}
		} catch (IOException e) {
			System.out.println("Rubine Weight file not found");
			e.printStackTrace();
		}
		return weightMatrix;
	}
	
	
//	!!!!!!!!!!!!!!!!!!
	public Map<String, Map<String, Double>> getWeight(String modelFile) {
		Map<String, Map<String, Double>> weightMatrix = new HashMap<String, Map<String, Double>>();
		try (BufferedReader file = new BufferedReader(new FileReader(modelFile))) {
			String line;
			while ((line = file.readLine()) != null) {
				String[] pair = line.split("\t");
				String Class = pair[0];
				String Feature = pair[1];
				Double Weight = Double.parseDouble(pair[2]);
				Map<String, Double> weight;
				if ((weight = weightMatrix.get(Class)) == null) {
					weight = new HashMap<String, Double>();
					weightMatrix.put(Class, weight);
				}
				weight.put(Feature, Weight);
			}
		} catch (IOException e) {
			System.out.println("Rubine Weight file not found");
			e.printStackTrace();
		}
		return weightMatrix;
	}

	public Map<String, Double> calculateFeatures(Stroke stroke) {
		Map<String, Double> feature = new HashMap<String, Double>();

		List<Double> xList = new ArrayList<Double>();
		List<Double> yList = new ArrayList<Double>();
		List<Double> tList = new ArrayList<Double>(); 

		List<Double> xDeltaList = new ArrayList<Double>();
		List<Double> yDeltaList = new ArrayList<Double>();
		List<Double> tDeltaList = new ArrayList<Double>();

		List<Double> xDeltaSquareList = new ArrayList<Double>();
		List<Double> yDeltaSquareList = new ArrayList<Double>();
		List<Double> tDeltaSquareList = new ArrayList<Double>();

		List<Double> rotations = new ArrayList<Double>();

		for (Point point : stroke.getPoints()) {
			xList.add(point.getX());
			yList.add(point.getY());
			tList.add(point.getTime());
		}

		for (int i = 0; i < xList.size() - 1; i++) {
			Double xDiff = xList.get(i + 1) - xList.get(i);
			Double yDiff = yList.get(i + 1) - yList.get(i);
			Double tDiff = tList.get(i + 1) - tList.get(i);
			xDeltaList.add(xDiff);
			yDeltaList.add(yDiff);
			tDeltaList.add(tDiff);
			xDeltaSquareList.add(xDiff * xDiff);
			yDeltaSquareList.add(yDiff * yDiff);
			tDeltaSquareList.add(tDiff * tDiff);
		}

		for (int i = 0; i < xDeltaList.size() - 1; i++) {
			Double xStart = xDeltaList.get(i);
			Double yStart = yDeltaList.get(i);
			Double xEnd = xDeltaList.get(i + 1);
			Double yEnd = yDeltaList.get(i + 1);
			Double angle = 0.0;
			Double demoninator = xStart * xEnd + yStart * yEnd;
			if (demoninator >= 0 && demoninator < 0.001) {
				demoninator = 0.001;
			} else if (demoninator < 0 && demoninator > -0.001) {
				demoninator = -0.001;
			}
			angle = 1.0 * (xEnd * yStart - xStart * yEnd) / demoninator;
			rotations.add(Math.atan(angle));
		}

		// Calculate f1: Cosine of angle alpha
		Double xDistance = xList.get(2) - xList.get(0);
		Double yDistance = yList.get(2) - yList.get(0);
		Double hDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);
		hDistance = (hDistance > 0.001 ? hDistance : 0.001);
		feature.put("f1", 1.0 * xDistance / hDistance);

		// Calculate f2: Sine of angle alpha
		feature.put("f2", 1.0 * yDistance / hDistance);

		// Calculate f3: Length of Bounding Box Diagonal
		feature.put("f3", stroke.getBoundingBox().getDiagonal());

		// Calculate f4: arctan of the bounding box diagonal angle
		Double xDiff = stroke.getBoundingBox().getWidth();
		xDiff = (xDiff > 0.001) ? xDiff : 0.001;
		feature.put("f4", Math.atan2(stroke.getBoundingBox().getHeight(), xDiff));
		
		// Calculate f5: Length from the start point to the end point
		Double s_e_xDiff = xList.get(xList.size() - 1) - xList.get(0);
		Double s_e_yDiff = yList.get(yList.size() - 1) - yList.get(0);
		Double s_e_hDiff = Math.sqrt(s_e_xDiff * s_e_xDiff + s_e_yDiff * s_e_yDiff);
		feature.put("f5", s_e_hDiff);
		
		// Calculate f6: Cosine of start end angle
		s_e_hDiff = (s_e_hDiff > 0.001) ? s_e_hDiff : 0.001;
		feature.put("f6", s_e_xDiff / s_e_hDiff);
		
		// Calculate f7: Sine of start end angle
		feature.put("f7", s_e_yDiff / s_e_hDiff);

		// Calculate f8: length of the stroke
		feature.put("f8", stroke.getStrokeLength());
		
		// Calculate f9: total angle traversed
		Double totalRotation = 0.0;
		Double totalAbsoluteRotation = 0.0;
		Double sharpness = 0.0;
		for (Double rotation : rotations) {
			totalRotation += rotation;
			totalAbsoluteRotation += Math.abs(rotation);
			sharpness += Math.abs(rotation * rotation);
		}
		feature.put("f9", totalRotation);
		
		// Calculate f10: total absolute angle rotation
		feature.put("f10", totalAbsoluteRotation);
		
		// Calculate f11: sharpness, total squared rotation
		feature.put("f11", sharpness);
		
//		// Calculate f12: maximum speed
//		Double maxSpeed = 0.0;
//		for (int i = 0; i < xDeltaSquareList.size(); i++) {
//			Double demoninator = tDeltaSquareList.get(i);
//			if (demoninator < 0.001) demoninator = 1.0;
//			Double tempSpeed = 1.0 * (xDeltaSquareList.get(i) + yDeltaSquareList.get(i)) / demoninator;
//			maxSpeed = (maxSpeed > tempSpeed)? maxSpeed : tempSpeed;
//		}
//		feature.put("f12", maxSpeed);
//		// Calculate f113: total time used
//		feature.put("f13", tList.get(tList.size() - 1) - tList.get(0));

		return feature;
	}

	@Override
	public void preprocess(Stroke stroke) {
		// TODO Auto-generated method stub
		
	}

}
