package ui.problemframe.problem;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import constants.Constant;
import problem.Problem;

import java.awt.Color;

public class ProblemDefPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8688327844824238789L;

	private static List<Problem> problems = new ArrayList<Problem>();
	private static int problemIndex = -1;

	// displays
	private static JLabel problemNumber = new JLabel();
	private static JLabel characterIcon = new JLabel();
	private static JLabel problemText = new JLabel(
			"Please write the character д following the stroke order instructions.");
	private static JLabel meaning = new JLabel("Meaning: to write, writing");
	private static JLabel pronoun = new JLabel("Pronounce: Xie");
	private static JLabel strokeCount = new JLabel("Stroke Count: 5");
	private static JLabel strokeType = new JLabel("Stroke Type: Shu HengGou Heng ShuZheZheGou");
	private static JLabel radicals = new JLabel("Radicals: Cover, And");

	/**
	 * Create the panel.
	 */
	public ProblemDefPanel() {
		setBackground(new Color(255, 255, 255));
		initialize();

	}

	public void initialize() {
		setLayout(null);
		setPreferredSize(new Dimension(1000, 70));
		Border border = BorderFactory.createLineBorder(Color.black);

		problemNumber.setIcon(new ImageIcon(
				"C:/Users/Yin/workspace/Chinese-Calligraphist/resources/Images/problemCountImages/Problem1.png"));
		problemNumber.setBorder(border);
		problemNumber.setBounds(1, 1, 100, 65);
		add(problemNumber);

		characterIcon
				.setIcon(new ImageIcon(
						"C:/Users/Yin/Pictures/P1_Character.png"));
		characterIcon.setBorder(border);
		characterIcon.setBounds(101, 1, 100, 65);
		add(characterIcon);

		problemText.setBounds(203, 1, 787, 33);
		problemText.setBorder(border);
		add(problemText);

		meaning.setBounds(203, 34, 206, 32);
		add(meaning);

		pronoun.setBounds(413, 34, 100, 32);
		add(pronoun);

		strokeCount.setBounds(513, 34, 100, 32);
		add(strokeCount);

		strokeType.setBounds(623, 34, 193, 32);
		add(strokeType);

		radicals.setBounds(826, 34, 164, 32);
		add(radicals);
	}

	public static void setProblem(int index) {
		if (index >= problems.size() || index < 0) {
			return;
		}
		Problem currentProblem = problems.get(index);
		setProblemNumberImage(currentProblem);
		CharacterIconImage(currentProblem);
		setProblemText(currentProblem);
		ProblemImagePanel.reset(currentProblem.getStrokeImages());
	}

	private static void setProblemNumberImage(Problem problem) {
		// TODO set path
		problemNumber.setIcon(new ImageIcon(Constant.PROBLEMCOUNT_IMGAGE_PATH
				+ String.format("Problem%d.png", problemIndex + 1)));
	}

	private static void CharacterIconImage(Problem problem) {
		String characterIconPath = problem.getCharacterIconPath();
		characterIcon.setIcon(new ImageIcon(characterIconPath));
	}

	private static void setProblemText(Problem problem) {
		problemText.setText("<html>" + problem.getProblemText() + "</html>");
		meaning.setText("<html>" + "Definition: " + problem.getMeaning() + "</html>");
		pronoun.setText("<html>" + "Pronounce: " + problem.getPronounce() + "</html>");
		strokeCount.setText("<html>" + "Stroke Count: " + problem.getStrokeNumber().toString() + "</html>");
		strokeType.setText("<html>" + "Strokes:");
		for (String stroke : problem.getStrokes())
			strokeType.setText(strokeType.getText() + stroke + " ");
		strokeType.setText(strokeType.getText() + "</html>");
		radicals.setText("<html>" + "Radicals:");
		for (String radical : problem.getRadicals())
			radicals.setText(radicals.getText() + radical + " ");
		radicals.setText(radicals.getText() + "</html>");
	}

	public static void setProblems(List<Problem> problemList) {
		problems.clear();
		problems.addAll(problemList);
		problemIndex = 0;
		setProblem(problemIndex);
	}

	public static void prev() {
		if (problemIndex > 0) {
			problemIndex--;
			setProblem(problemIndex);
		}
	}

	public static void next() {
		if (problemIndex < problems.size()) {
			problemIndex++;
			setProblem(problemIndex);
		}
	}

	public static boolean setStrokeFeedback(int position, String name) {
		boolean retValue = false;
		name = "\"" + name + "\"";
		retValue = ProblemImagePanel.strokeBackgroundFeedback(position, problems.get(problemIndex).getStrokeImages().get(position).getName().equals(name));
		if (position == problems.get(problemIndex).getStrokeNumber() && retValue == true) {
			retValue = ProblemImagePanel.check(position + 1);
		}
		return retValue;
	}
}
