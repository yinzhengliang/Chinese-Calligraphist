package problem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import constants.Constant;

public class Problem {
	private String character;
	private String problemText;
	private String meaning;
	private String pronounce;
	private Integer strokeNumber;
	private List<String> strokes = new ArrayList<String>();
	private List<String> radicals = new ArrayList<String>();
	private String characterIconPath;

	private List<ProblemStrokeImage> strokeImages = new ArrayList<ProblemStrokeImage>();

	public String getProblemText() {
		return problemText;
	}

	public String getMeaning() {
		return meaning;
	}

	public String getPronounce() {
		return pronounce;
	}

	public Integer getStrokeNumber() {
		return strokeNumber;
	}

	public List<String> getStrokes() {
		return strokes;
	}

	public List<String> getRadicals() {
		return radicals;
	}

	public String getCharacterIconPath() {
		return characterIconPath;
	}

	public List<ProblemStrokeImage> getStrokeImages() {
		return strokeImages;
	}

	@SuppressWarnings("resource")
	public Problem(File root) {
		String rootPath = root.getAbsolutePath();
		characterIconPath = rootPath + Constant.PROBLEM_CHARACTER_MID_ICON;

		try {
			BufferedReader description = new BufferedReader(
					new FileReader(rootPath + Constant.PROBLEM_DESCRIPTION_FILE));
			String line;
			while ((line = description.readLine()) != null) {
				String[] pair = line.split(":");
				switch (pair[0]) {
				case "character": {
					character = pair[1];
					break;
				}
				case "problemText": {
					problemText =  pair[1];
					break;
				}
				case "meaning": {
					meaning = pair[1];
					break;
				}
				case "pronounce": {
					pronounce = pair[1];
					break;
				}
				case "strokeNumber": {
					strokeNumber = Integer.parseInt(pair[1]);
					break;
				}
				case "Strokes": {
					for (String stroke : pair[1].split("\t"))
						strokes.add(stroke);
					break;
				}
				case "Radicals": {
					for (String radical : pair[1].split("\t"))
						radicals.add(radical);
					break;
				}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		strokeImages.clear();
		strokeImages.add(new ProblemStrokeImage(character, rootPath + Constant.PROBLEM_CHARACTER_SMALL_ICON));
		for (int i = 0; i < strokeNumber; i++) {
			strokeImages.add(new ProblemStrokeImage(strokes.get(i), rootPath + String.format("/%d.png", i + 1)));
		}

	}
}
