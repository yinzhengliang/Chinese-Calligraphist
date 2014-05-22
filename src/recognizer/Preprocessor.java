package recognizer;

import java.util.ArrayList;
import java.util.List;

import core.sketch.Point;
import core.sketch.Stroke;

public class Preprocessor {
	public Stroke resample(Stroke stroke, int N) {
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
		stroke.clear();
		stroke.setPoints(points);
		return stroke;
	}
	
	public void rotation(Stroke stroke) {
		
	}
	
	public Stroke scale(Stroke stroke, int size) {
		Double height = stroke.getBoundingBox().getHeight();
		Double width = stroke.getBoundingBox().getWidth();
		double alpha = (height > width? height : width);
		alpha = size / alpha;
		
		for (Point point : stroke.getPoints()) {
			point.scaleFromOrigin(alpha, alpha);
		}
		
		stroke.recalcBoundingBox();
		stroke.recalcLength();
		return stroke;
	}
	
	public Stroke translate(Stroke stroke) {
		Double left = stroke.getBoundingBox().getLeft();
		Double up = stroke.getBoundingBox().getUp();
		for (Point point : stroke.getPoints()) {
			point.move(-left, -up);
		}
		stroke.recalcBoundingBox();
		stroke.recalcLength();
		return stroke;
	}

	public Point centroid(List<Point> points) {
		double center_x = 0.0;
		double center_y = 0.0;
		for (Point point : points) {
			center_x += point.getX();
			center_y += point.getY();
		}
		return new Point(center_x, center_y);
	}
	
	public double indicative_angle(List<Point> points) {
		Point center = centroid(points);
		double angle = Math.atan2(center.getY() - points.get(0).getY(), center.getX() - points.get(0).getX());
		return angle;
	}
	
	public List<Point> rotate_by(List<Point> points, double angle) {
		Point center = centroid(points);
		List<Point> newPoints = new ArrayList<Point>();
		for (Point point : points) {
			double x = (point.getX() - center.getX()) * Math.cos(angle) - (point.getY() - center.getY()) * Math.sin(angle) + center.getX();
			double y = (point.getX() - center.getX()) * Math.sin(angle) + (point.getY() - center.getY()) * Math.cos(angle) + center.getY();
			newPoints.add(new Point(x, y, point.getTime(), point.getId()));
		}
		return newPoints;
	}
	
}
