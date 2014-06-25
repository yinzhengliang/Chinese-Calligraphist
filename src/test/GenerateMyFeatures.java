package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import recognizer.low.gesture.Long;
import constants.Constant;
import core.sketch.Interpretation;
import core.sketch.Stroke;
import data.StrokeLoader;

public class GenerateMyFeatures {
	private static List<String> classes = new ArrayList<String>();
	// class -> list of files (map (stroke info xml) -> map all different recog results (recog name to xml))
	private static Map<String, Map<String, Map<String, String>>> allfiles = new HashMap<String, Map<String, Map<String, String>>>();
	private static Long LONG = new Long();

	public static void main(String[] args) {
		String folder = Constant.RESOURCE_PATH + "TestData/";
		File[] files = new File(folder).listFiles();
		process(files, null);
		classes.addAll(allfiles.keySet());

		String outputFolder = Constant.RESOURCE_PATH + "MySolutionFeatureExtraction/";

		output(outputFolder);

	}

	private static void output(String outputFolder) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(outputFolder + "myResultTT.txt"));

			printHead(writer);
			writer.write("@DATA\n");
			for (Map.Entry<String, Map<String, Map<String, String>>> entry : allfiles.entrySet()) {
				String className = entry.getKey();
				for (Entry<String, Map<String, String>> instance : entry.getValue().entrySet()) {
					String strokeXMLPath = Constant.RESOURCE_PATH + "TestData/" + className + "/" + instance.getKey();
					String rubineResult = instance.getValue().get("rubine");
					String dollarResult = instance.getValue().get("dollar");
					String longResult = instance.getValue().get("long");
					String hausdorffResult = instance.getValue().get("hausdorff");

					StrokeLoader loader = new StrokeLoader();
					System.out.println(strokeXMLPath);
					loader.setDocument(new File(strokeXMLPath));
					loader.setStrokes();
					List<Stroke> strokes = loader.getStrokes();

					if (strokes.size() > 1) {
						System.out.println("!!!!!!!!!!!!!!!!More Than One Stroke!!!!!!!!!!!!!!!!");
					}

					Stroke stroke = strokes.get(0);

					Map<String, Double> features = LONG.calculateFeatures(stroke);
					// write feature value

					
					
					writer.write(features.get("f1").toString() + ",");
					writer.write(features.get("f2").toString() + ",");
					writer.write(features.get("f3").toString() + ",");
					writer.write(features.get("f4").toString() + ",");
					writer.write(features.get("f5").toString() + ",");
					writer.write(features.get("f6").toString() + ",");
					writer.write(features.get("f7").toString() + ",");
					writer.write(features.get("f8").toString() + ",");
					writer.write(features.get("f9").toString() + ",");
					writer.write(features.get("f10").toString() + ",");
					writer.write(features.get("f11").toString() + ",");
					writer.write(features.get("f12").toString() + ",");
					writer.write(features.get("f13").toString() + ",");
					writer.write(features.get("f14").toString() + ",");
					writer.write(features.get("f15").toString() + ",");
					writer.write(features.get("f16").toString() + ",");
					writer.write(features.get("f17").toString() + ",");
					writer.write(features.get("f18").toString() + ",");
					writer.write(features.get("f19").toString() + ",");
					writer.write(features.get("f20").toString() + ",");
					writer.write(features.get("f21").toString() + ",");
					writer.write(features.get("f22").toString() + ",");

					Map<String, String> rubineValues = getValues(rubineResult);
					Map<String, String> longValues = getValues(longResult);
					Map<String, String> hausdorffValues = getValues(hausdorffResult);
					Map<String, String> dollaralues = getValues(dollarResult);

					for (String Class : classes) {
						if (rubineValues.containsKey(Class)) {
							writer.write(rubineValues.get(Class) + ",");
						}
						else {
							writer.write("0.0,");
						}
					}

					for (String Class : classes) {
						if (longValues.containsKey(Class)) {
							writer.write(longValues.get(Class) + ",");
						}
						else {
							writer.write("0.0,");
						}
					}

					for (String Class : classes) {
						if (hausdorffValues.containsKey(Class)) {
							writer.write(hausdorffValues.get(Class) + ",");
						}
						else {
							writer.write("0.0,");
						}
					}

					for (String Class : classes) {
						if (dollaralues.containsKey(Class)) {
							writer.write(dollaralues.get(Class) + ",");
						}
						else {
							writer.write("0.0,");
						}
					}

					// output write or wrong
//					for (String Class : classes) {
//						if (Class.equals(className))
//							writer.write("1\t");
//						else
//							writer.write("0\t");
//					}
					writer.write(className);
					writer.newLine();
				}
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("resource")
	private static Map<String, String> getValues(String path) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(path));
		Map<String, String> retValue = new HashMap<String, String>();
		Map<String, Double> values = new HashMap<String, Double>();
		String line;
		Double MINVALUE = Double.MAX_VALUE;
		Double MAXVALUE = Double.MIN_VALUE;
		while ((line = reader.readLine()) != null) {
			String Class = line.split(":\t")[0];
			Double value = Double.parseDouble(line.split(":\t")[1]);
			if (value < MINVALUE)
				MINVALUE = value;
			if (value > MAXVALUE)
				MAXVALUE = value;
			values.put(Class, value);
		}
		reader.close();
		Double normalizer = MAXVALUE - MINVALUE;
		for (Map.Entry<String, Double> entry : values.entrySet()) {
			Double value = (entry.getValue() - MINVALUE) / normalizer;
			retValue.put(entry.getKey(), value.toString());
		}
		// check
		if (retValue.size() != 16) {
			System.out.println("no all available!!=!!=!!=!!+!!=!!=!!");
			System.out.println(path);
		}
		return retValue;
	}

	private static void printHead(BufferedWriter writer) throws IOException {
		writer.write("@ATTRIBUTE f1\tREAL\n");
		writer.write("@ATTRIBUTE f2\tREAL\n");
		writer.write("@ATTRIBUTE f3\tREAL\n");
		writer.write("@ATTRIBUTE f4\tREAL\n");
		writer.write("@ATTRIBUTE f5\tREAL\n");
		writer.write("@ATTRIBUTE f6\tREAL\n");
		writer.write("@ATTRIBUTE f7\tREAL\n");
		writer.write("@ATTRIBUTE f8\tREAL\n");
		writer.write("@ATTRIBUTE f9\tREAL\n");
		writer.write("@ATTRIBUTE f10\tREAL\n");
		writer.write("@ATTRIBUTE f11\tREAL\n");
		writer.write("@ATTRIBUTE f12\tREAL\n");
		writer.write("@ATTRIBUTE f13\tREAL\n");
		writer.write("@ATTRIBUTE f14\tREAL\n");
		writer.write("@ATTRIBUTE f15\tREAL\n");
		writer.write("@ATTRIBUTE f16\tREAL\n");
		writer.write("@ATTRIBUTE f17\tREAL\n");
		writer.write("@ATTRIBUTE f18\tREAL\n");
		writer.write("@ATTRIBUTE f19\tREAL\n");
		writer.write("@ATTRIBUTE f20\tREAL\n");
		writer.write("@ATTRIBUTE f21\tREAL\n");
		writer.write("@ATTRIBUTE f22\tREAL\n");

		for (String Class : classes) {
			writer.write("@ATTRIBUTE rubine_" + Class + "\tREAL");
			writer.newLine();
		}

		for (String Class : classes) {
			writer.write("@ATTRIBUTE long_" + Class + "\tREAL");
			writer.newLine();
		}

		for (String Class : classes) {
			writer.write("@ATTRIBUTE hausdorff_" + Class + "\tREAL");
			writer.newLine();
		}

		for (String Class : classes) {
			writer.write("@ATTRIBUTE dollar_" + Class + "\tREAL");
			writer.newLine();
		}

		writer.write("@ATTRIBUTE class\t{");
		for (String Class : classes) {
			writer.write(Class + ",");
		}
		writer.write("}");
		writer.newLine();

		writer.newLine();
	}

	private static void process(File[] files, Map<String, Map<String, String>> list) {
		for (File file : files) {
			if (file.isDirectory()) {
				String className = file.getName();
				Map<String, Map<String, String>> testInstances = new HashMap<String, Map<String, String>>();
				allfiles.put(className, testInstances);
				process(file.listFiles(), testInstances); // Calls same method again.
			} else {
				generate(file, list);
			}
		}
	}

	private static void generate(File file, Map<String, Map<String, String>> list) {
		String fileName = file.getName();
		if (fileName.startsWith("dollar")) {
			String strokeDataName = fileName.split("_")[1];
			Map<String, String> result = list.get(strokeDataName);
			if (result == null) {
				result = new HashMap<String, String>();
				list.put(strokeDataName, result);
			}
			result.put("dollar", file.getAbsolutePath());
		} else if (fileName.startsWith("hausdorff")) {
			String strokeDataName = fileName.split("_")[1];
			Map<String, String> result = list.get(strokeDataName);
			if (result == null) {
				result = new HashMap<String, String>();
				list.put(strokeDataName, result);
			}
			result.put("hausdorff", file.getAbsolutePath());
		} else if (fileName.startsWith("long")) {
			String strokeDataName = fileName.split("_")[1];
			Map<String, String> result = list.get(strokeDataName);
			if (result == null) {
				result = new HashMap<String, String>();
				list.put(strokeDataName, result);
			}
			result.put("long", file.getAbsolutePath());
		} else if (fileName.startsWith("rubine")) {
			String strokeDataName = fileName.split("_")[1];
			Map<String, String> result = list.get(strokeDataName);
			if (result == null) {
				result = new HashMap<String, String>();
				list.put(strokeDataName, result);
			}
			result.put("rubine", file.getAbsolutePath());
		} else {
			Map<String, String> result = list.get(fileName);
			if (result == null) {
				result = new HashMap<String, String>();
				list.put(fileName, result);
			}
		}
	}
}
