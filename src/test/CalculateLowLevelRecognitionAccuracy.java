package test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import recognizer.low.gesture.Rubine;
import recognizer.low.gesture.Long;
import recognizer.low.template.Dollar;
import recognizer.low.template.Hausdroff;
import constants.Constant;
import core.sketch.Interpretation;
import core.sketch.Stroke;
import data.StrokeLoader;


public class CalculateLowLevelRecognitionAccuracy {
	private static Rubine rubine = new Rubine();
	private static Long LONG = new Long();
	private static Hausdroff huasdorff = new Hausdroff();
	private static Dollar onedollar = new Dollar();
	
	public static void main(String[] args) {
		String path = Constant.RESOURCE_PATH + "TestData/";
		
		File[] files = new File(path).listFiles();
		process(files);
	}
	
	public static void process(File[] files) {
	    for (File file : files) {
	        if (file.isDirectory()) {
	            process(file.listFiles()); // Calls same method again.
	        } else {
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
		
		List<Interpretation> rI = rubine.recognize(stroke);
		List<Interpretation> lI = LONG.recognize(stroke);
		List<Interpretation> hI = huasdorff.recognize(stroke);
		List<Interpretation> dI = onedollar.recognize(stroke);
		
		write(rI, file.getParent() + "/rubine_" + file.getName());
		write(lI, file.getParent() + "/long_" + file.getName());
		write(hI, file.getParent() + "/hausdorff_" + file.getName());
		write(dI, file.getParent() + "/dollar_" + file.getName());
		
		
		
	}

	@SuppressWarnings("resource")
	private static void write(List<Interpretation> I, String S) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(S));
			for (Interpretation i : I) {
				writer.write(i.getName() + ":\t" + i.getConfidence());
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
