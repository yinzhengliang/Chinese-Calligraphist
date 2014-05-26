package data.dfeatures;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import recognizer.IRecognizer;
import core.sketch.Point;
import core.sketch.Stroke;
import data.StrokeLoader;
import data.StrokeSaver;

public class OneDollarTemplateGenerator {
	// static StrokeLoader loader = new StrokeLoader();

	public void test() {

		// loader.setDocument("file");
		// loader.setStrokes();

	}

	public static void main(String... args) {
//		File[] files = new File("C:/Users/Yin/Desktop/One_Dollar/to_generate").listFiles();
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

		stroke_to_recognize = IRecognizer.preprocesser.resample(stroke_to_recognize, 40);
		
		double angle = IRecognizer.preprocesser.indicative_angle(stroke_to_recognize.getPoints());
		List<Point> newPoints = IRecognizer.preprocesser.rotate_by(stroke_to_recognize.getPoints(), angle);
		stroke_to_recognize.clear();
		stroke_to_recognize.setPoints(newPoints);
		stroke_to_recognize = IRecognizer.preprocesser.translate(stroke_to_recognize);
		stroke_to_recognize = IRecognizer.preprocesser.scale(stroke_to_recognize, 40);
		
		strokes.clear();
		strokes.add(stroke_to_recognize);

		StrokeSaver saver = new StrokeSaver();
		saver.save(strokes, file.getParent() + "/new_" + file.getName());
	}
}
