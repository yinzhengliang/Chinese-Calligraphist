package data.dfeatures;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import core.sketch.Point;
import core.sketch.Stroke;
import data.StrokeLoader;
import data.StrokeSaver;

public class RubineFeatures {
//	static StrokeLoader loader = new StrokeLoader();
	
	public void test() {
		
//		loader.setDocument("file");
//		loader.setStrokes();
		 
	}
	
	public static void main(String... args) {
		File[] files = new File("C:/Users/Yin/Desktop/basic_strokes").listFiles();
		//File[] files = new File("/media/yin/4276E4DF76E4D4A7/Users/Yin/Desktop/m_test/compound_strokes").listFiles();
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
//		System.out.println("left: " + stroke.getBoundingBox().getLeft());
//		System.out.println("right: " + stroke.getBoundingBox().getRight());
//		System.out.println("up: " + stroke.getBoundingBox().getUp());
//		System.out.println("down: " + stroke.getBoundingBox().getDown());
//		System.out.println("height: " + stroke.getBoundingBox().getHeight());
//		System.out.println("width: " + stroke.getBoundingBox().getWidth());
		Double left = stroke.getBoundingBox().getLeft();
//		Double right = stroke.getBoundingBox().getRight();
		Double up = stroke.getBoundingBox().getUp();
//		Double down = stroke.getBoundingBox().getDown();
		Double height = stroke.getBoundingBox().getHeight();
		Double width = stroke.getBoundingBox().getWidth();
		for (Point point : stroke.getPoints()) {
			point.move(-left, -up);
		}
		
		double alpha = (height > width? height : width);
		alpha = 40.0 / alpha;
		
		for (Point point : stroke.getPoints()) {
			point.scaleFromOrigin(alpha, alpha);
		}
		
		stroke.recalcBoundingBox();
		stroke.recalcLength();
		
		stroke = resample(stroke, 40);
		
		strokes.clear();
		strokes.add(stroke);
		
		StrokeSaver saver = new StrokeSaver();
		saver.save(strokes, file.getParent() + "/new_" + file.getName());
		
//		System.out.println("left: " + stroke.getBoundingBox().getLeft());
//		System.out.println("right: " + stroke.getBoundingBox().getRight());
//		System.out.println("up: " + stroke.getBoundingBox().getUp());
//		System.out.println("down: " + stroke.getBoundingBox().getDown());
//		System.out.println("height: " + stroke.getBoundingBox().getHeight());
//		System.out.println("width: " + stroke.getBoundingBox().getWidth());
		

//		System.out.println("length: " + stroke.recalcLength());
		
		
	}
	
	public static Stroke resample(Stroke stroke, int N) {
		double length = stroke.getStrokeLength() / N;
		List<Point> points = new ArrayList<Point>();
		double sum_length = 0.0;
		Point last_point = stroke.getPoints().get(0);
		points.add(last_point);
		for (Point point : stroke.getPoints()) {
			sum_length += point.distanceTo(last_point);
			if (sum_length >= length) {
				sum_length -= length;
				double alpha = 1.0 - sum_length / point.distanceTo(last_point);
				last_point = new Point(point.getX() * alpha + last_point.getX() * (1 - alpha), 
						point.getY() * alpha + last_point.getY() * (1 - alpha),
						point.getTime() * alpha + last_point.getTime() * (1 - alpha),
						point.getId());
				points.add(last_point);
				
				while (sum_length >= length) {
					alpha = length / point.distanceTo(last_point);
					last_point = new Point(point.getX() * alpha + last_point.getX() * (1 - alpha), 
							point.getY() * alpha + last_point.getY() * (1 - alpha),
							point.getTime() * alpha + last_point.getTime() * (1 - alpha),
							point.getId());
					points.add(last_point);
					sum_length -= length;
				}
			}
			last_point = point;
		}
		points.add(last_point);
		return new Stroke(points);
		
	}
}
