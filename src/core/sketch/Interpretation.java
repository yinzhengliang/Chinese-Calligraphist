package core.sketch;

public class Interpretation implements Comparable<Interpretation> {
	private String name;
	private Double confidence;
	
	public Interpretation() {
	}

	public Interpretation(String name, Double confidence) {
		this.name = name;
		this.confidence = confidence;
	}
	
	public double getConfidence() {
		return confidence;
	}
	
	public void setConfidence(Double confidence) {
		this.confidence = confidence;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public int compareTo(Interpretation other) {
		if(this.confidence==other.confidence){
            return this.name.compareTo(other.name);
        }
        return (int) ((other.confidence-this.confidence) * 100000);
	}
}
