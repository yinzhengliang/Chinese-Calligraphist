package test;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import constants.Constant;
import weka.classifiers.Evaluation;
import weka.classifiers.misc.SerializedClassifier;
import weka.core.Instances;
import weka.core.Utils;

public class Weka {
	public static void main(String[] args) throws Exception {
		File file = new File("C:\\Users\\Yin\\Desktop\\2.model");

		SerializedClassifier m_classifier = new SerializedClassifier();

		m_classifier.setModelFile(file);

		FileReader trainreader = new FileReader(Constant.RESOURCE_PATH + "MySolutionFeatureExtraction/myResultTT.arff");

		Instances train = new Instances(trainreader);

		m_classifier.setOptions(Utils.splitOptions("-L 0.3 -M 0.2 -N 500 -V 0 -S 0 -E 20 -H a"));
//		m_classifier.buildClassifier(null);

		train.setClassIndex(train.numAttributes() - 1);
		// test.setClassIndex(test.numAttributes() - 1);


		// mlp.buildClassifier(train);
		// System.out.println(test.numAttributes());
		// System.out.println(test.numClasses());
		// System.out.println(test.numInstances());
		// for () {
		// ;
		// }

		// Instance inst = new DenseInstance(87);

		
			List<String> classNames = new ArrayList<String>();
			for (int i = 0; i < train.classAttribute().numValues(); i++) {
				classNames.add(train.classAttribute().value(i));
			}

		
//		System.out.println(inst.attribute(1));
		// double pred = mlp.classifyInstance(inst);
		// double[] dist = mlp.distributionForInstance(inst);

		// System.out.println(pred);
		// System.out.println(dist);

		 Evaluation eval = new Evaluation(train);

		 eval.evaluateModel(m_classifier, train);
		 System.out.println(eval.toSummaryString("\nResults\n======\n", false));
		trainreader.close();
		// testreader.close();
	}

	/**
	 * public static void main(String[] args) throws Exception { FileReader trainreader = new
	 * FileReader(Constant.RESOURCE_PATH + "MySolutionFeatureExtraction/myResultTT.arff"); // FileReader testreader =
	 * new FileReader(Constant.RESOURCE_PATH + // "MySolutionFeatureExtraction/myResultTT.arff");
	 * 
	 * Instances train = new Instances(trainreader); // Instances test = new Instances(testreader);
	 * 
	 * train.setClassIndex(train.numAttributes() - 1); // test.setClassIndex(test.numAttributes() - 1);
	 * 
	 * MultilayerPerceptron mlp = new MultilayerPerceptron();
	 * mlp.setOptions(Utils.splitOptions("-L 0.3 -M 0.2 -N 500 -V 0 -S 0 -E 20 -H a"));
	 * 
	 * // mlp.buildClassifier(train); // System.out.println(test.numAttributes()); //
	 * System.out.println(test.numClasses()); // System.out.println(test.numInstances()); // for () { // ; // }
	 * 
	 * // Instance inst = new DenseInstance(87);
	 * 
	 * Instance inst = new Instance(train.instance(250)); System.out.println(inst.attribute(1)); // double pred =
	 * mlp.classifyInstance(inst); // double[] dist = mlp.distributionForInstance(inst);
	 * 
	 * // System.out.println(pred); // System.out.println(dist);
	 * 
	 * // Evaluation eval = new Evaluation(train);
	 * 
	 * // eval.evaluateModel(mlp, test); // System.out.println(eval.toSummaryString("\nResults\n======\n", false));
	 * trainreader.close(); // testreader.close(); }
	 */
}
