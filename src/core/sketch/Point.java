package core.sketch;

public class Point {
	double x = 0.0;
	double y = 0.0;
	double time = 0.0;
	
	public Point() {
	}

	public Point(double x, double y, double time) {
		this.x = x;
		this.y = y;
		this.time = time;
	}
	
	public void move(double delta_x, double delta_y) {
		x += delta_x;
		y += delta_y;
	}
	
	public Double getX() {
		return x;
	}
	
	public Double getY() {
		return y;
	}
	
	public Double getTime() {
		return time;
	}
	
	public Double distanceTo(Point target) {
		Double distance = 0.0;
		Double xDiff = this.x - target.x;
		Double yDiff = this.y - target.y;
		distance = Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
		return distance;
	}
}
