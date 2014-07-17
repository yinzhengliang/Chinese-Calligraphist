package core.sketch;

public class BoundingBox {
	double left = Double.MAX_VALUE;
	double right = Double.MIN_VALUE;
	double up = Double.MAX_VALUE;
	double down = Double.MIN_VALUE;
	
	public Double getUp() {
		return up;
	}
	
	public Double getDown() {
		return down;
	}
	
	public Double getLeft() {
		return left;
	}
	
	public Double getRight() {
		return right;
	}
	
	public Double getWidth() {
		return right - left;
	}
	
	public Double getHeight() {
		return down - up;
	}
	
	public Double getDiagonal() {
		return Math.sqrt(Math.pow(getWidth(), 2)+Math.pow(getHeight(), 2));
	}
	
	
	public BoundingBox() {	
	}
	
	public BoundingBox(Double x, Double y) {
		left = right = x;
		up = down = y;
	}
	
	public void update(Double x, Double y) {
		if (x < left) left = x;
		if (x > right) right = x;
		if (y < up) up = y;
		if (y > down) down = y;
	}
	
	public BoundingBox(Point p) {
		left = right = p.getX();
		up = down = p.getY();
	}
	
	public BoundingBox(BoundingBox boundingBox) {
		this.left = boundingBox.left;
		this.right = boundingBox.right;
		this.up = boundingBox.up;
		this.down = boundingBox.down;
	}
	
	public void expand(BoundingBox boundingBox) {
		this.left = (this.left < boundingBox.left ? this.left : boundingBox.left);
		this.right = (this.right > boundingBox.right ? this.right : boundingBox.right);
		this.up = (this.up < boundingBox.up ? this.up : boundingBox.up);
		this.down = (this.down > boundingBox.down ? this.down : boundingBox.down);
	}

	public void update(Point p) {
		update(p.getX(), p.getY());
	}
	
	public void clear() {
		left = Double.MAX_VALUE;
		right = Double.MIN_VALUE;
		up = Double.MAX_VALUE;
		down = Double.MIN_VALUE;
	}
}
