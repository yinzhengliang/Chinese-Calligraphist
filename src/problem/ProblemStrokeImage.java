package problem;

public class ProblemStrokeImage {
	private String strokeName = "";
	private String imagePath = "";
	
	public ProblemStrokeImage(String name, String path) {
		strokeName = name;
		imagePath = path;
	}
	
	public String getImagePath() {
		return imagePath;
	}
	
	public String getName() {
		return strokeName;
	}
}
