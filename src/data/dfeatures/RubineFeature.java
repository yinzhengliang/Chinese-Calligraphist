package data.dfeatures;

import java.io.File;
import java.util.List;

import recognizer.IRecognizer;
import core.sketch.Point;
import core.sketch.Stroke;
import data.StrokeLoader;
import data.StrokeSaver;

public class RubineFeature {
	private static String training_folder = "";
	private static String output_folder = "";
	
	
	public static void main(String []args) {
		File[] files = new File("/media/yin/4276E4DF76E4D4A7/Users/Yin/Desktop/One_Dollar/test/to_generate").listFiles();
		process(files);
	}
	
	public static void process(File[] files) {
		for (File file : files) {
			if (file.isDirectory()) {
				System.out.println("Directory: " + file.getAbsolutePath());
				process(file.listFiles()); // Calls same method again.
			} else {
				System.out.println("File: " + file.getAbsolutePath());
				generate(file);
			}
		}
	}
	
	public static void generate(File file) {
		StrokeLoader loader = new StrokeLoader();
		loader.setDocument(file);
		loader.setStrokes();
		List<Stroke> strokes = loader.getStrokes();
		if (strokes.size() > 1) {
			System.out.println("!!!!!!!!!!!!!!!!More Than One Stroke!!!!!!!!!!!!!!!!");
		}

		Stroke stroke = strokes.get(0);
		Stroke stroke_to_recognize = new Stroke();
		stroke_to_recognize.copy(stroke);		

		
		
		

		StrokeSaver saver = new StrokeSaver();
		saver.save(strokes, file.getParent() + "/new_" + file.getName());
	}
	
	public RubineFeature() {
		
	}
	
	public static void dump_to_json() {
		
	}
	
	
}
