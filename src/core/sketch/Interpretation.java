package core.sketch;

public class Interpretation implements Comparable {
	private String name;
	private Double confidence;
	
	public Interpretation() {
	}
	
	public Interpretation(String name, Double confidence) {
		this.name = name;
		this.confidence = confidence;
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
}
