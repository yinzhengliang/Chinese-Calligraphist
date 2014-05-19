package core.sketch;

public class Point {
	double x = 0.0;
	double y = 0.0;
	double time = 0.0;
	String id = "";
	
	public Point() {
	}

	public Point(double x, double y, double time, String id) {
		this.x = x;
		this.y = y;
		this.time = time;
		this.id = id;
	}
	
	public Point(double x, double y, double time) {
		this.x = x;
		this.y = y;
		this.time = time;
	}
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void move(double delta_x, double delta_y) {
		x += delta_x;
		y += delta_y;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getTime() {
		return time;
	}
	
	public String getId() {
		return id;
	}
	
	public Double distanceTo(Point target) {
		Double distance = 0.0;
		Double xDiff = this.x - target.x;
		Double yDiff = this.y - target.y;
		distance = Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
		return distance;
	}
	
	public void scaleFromOrigin(double x_multi, double y_multi) {
		x *= x_multi;
		y *= y_multi;
	}
}
