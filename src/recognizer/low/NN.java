package recognizer.low;

import java.io.FileNotFoundException;
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
import core.sketch.Stroke;
import recognizer.IRecognizer;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;
import recognizer.low.gesture.Long;
import recognizer.low.gesture.Rubine;
import recognizer.low.template.Dollar;
import recognizer.low.template.Hausdroff;

public class NN implements IRecognizer {

	private MultilayerPerceptron mlp = new MultilayerPerceptron();
	private Rubine rubine = new Rubine();
	private Long LONG = new Long();
	private Hausdroff huasdorff = new Hausdroff();
	private Dollar onedollar = new Dollar();
	private Instances train;

	public NN() {
		try {
			train = new Instances(new FileReader(Constant.TrainingData));
			train.setClassIndex(train.numAttributes() - 1);
			mlp.setOptions(Utils.splitOptions("-L 0.3 -M 0.2 -N 500 -V 0 -S 0 -E 20 -H a"));
			mlp.buildClassifier(train);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Interpretation> recognize(Stroke stroke) {

		List<String> className = getClasses();

		Map<String, Double> features = LONG.calculateFeatures(stroke);
		Instance inst = new Instance(87);
		inst.setDataset(train);

		int index = 0;
		inst.setValue(index++, features.get("f1"));
		inst.setValue(index++, features.get("f2"));
		inst.setValue(index++, features.get("f3"));
		inst.setValue(index++, features.get("f4"));
		inst.setValue(index++, features.get("f5"));
		inst.setValue(index++, features.get("f6"));
		inst.setValue(index++, features.get("f7"));
		inst.setValue(index++, features.get("f8"));
		inst.setValue(index++, features.get("f9"));
		inst.setValue(index++, features.get("f10"));
		inst.setValue(index++, features.get("f11"));
		inst.setValue(index++, features.get("f12"));
		inst.setValue(index++, features.get("f13"));
		inst.setValue(index++, features.get("f14"));
		inst.setValue(index++, features.get("f15"));
		inst.setValue(index++, features.get("f16"));
		inst.setValue(index++, features.get("f17"));
		inst.setValue(index++, features.get("f18"));
		inst.setValue(index++, features.get("f19"));
		inst.setValue(index++, features.get("f20"));
		inst.setValue(index++, features.get("f21"));
		inst.setValue(index++, features.get("f22"));

		Map<String, Double> rValue = getMap(rubine.recognize(stroke));
		Map<String, Double> lValue = getMap(LONG.recognize(stroke));
		Map<String, Double> hValue = getMap(huasdorff.recognize(stroke));
		Map<String, Double> dValue = getMap(onedollar.recognize(stroke));

		for (String Class : className) {
			if (rValue.containsKey(Class)) {
				inst.setValue(index++, rValue.get(Class));
			} else {
				inst.setValue(index++, 0.0);
			}
		}

		for (String Class : className) {
			if (lValue.containsKey(Class)) {
				inst.setValue(index++, lValue.get(Class));
			} else {
				inst.setValue(index++, 0.0);
			}
		}

		for (String Class : className) {
			if (hValue.containsKey(Class)) {
				inst.setValue(index++, hValue.get(Class));
			} else {
				inst.setValue(index++, 0.0);
			}
		}

		for (String Class : className) {
			if (dValue.containsKey(Class)) {
				inst.setValue(index++, dValue.get(Class));
			} else {
				inst.setValue(index++, 0.0);
			}
		}

//		inst.setValue(index, 0.0);
//		
//		inst.setClassMissing();

		List<Interpretation> result = new ArrayList<Interpretation>();

		try {
//			double pred = mlp.classifyInstance(inst);
			double[] dist = mlp.distributionForInstance(inst);
			Set<Interpretation> resultSet = new TreeSet<Interpretation>();
//			System.out.println(pred);
			for (int i = 0; i < dist.length; i++)
				resultSet.add(new Interpretation(train.classAttribute().value(i), dist[i]));
			result.addAll(resultSet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private Map<String, Double> getMap(List<Interpretation> interpretations) {
		Map<String, Double> retValue = new HashMap<String, Double>();
		Double MINVALUE = Double.MAX_VALUE;
		Double MAXVALUE = Double.MIN_VALUE;
		for (Interpretation i : interpretations) {
			Double value = i.getConfidence();
			if (value < MINVALUE)
				MINVALUE = value;
			if (value > MAXVALUE)
				MAXVALUE = value;
			retValue.put(i.getName(), i.getConfidence());
		}
		
		Double normalizer = MAXVALUE - MINVALUE;
		for (Map.Entry<String, Double> entry : retValue.entrySet()) {
			Double value = (entry.getValue() - MINVALUE) / normalizer;
			retValue.put(entry.getKey(), value);
		}
		return retValue;
	}

	private List<String> getClasses() {
		List<String> classNames = new ArrayList<String>();
		for (int i = 0; i < train.classAttribute().numValues(); i++) {
			classNames.add(train.classAttribute().value(i));
		}
		return classNames;
	}

	@Override
	public void preprocess(Stroke stroke) {
		// TODO Auto-generated method stub

	}

}
