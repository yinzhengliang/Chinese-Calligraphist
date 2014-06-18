package ui.problemframe.problem;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;

import problem.ProblemStrokeImage;
import constants.Constant;

public class ProblemImagePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4107922047269669322L;

	static List<ImagePanel> panels = new ArrayList<ImagePanel>();
	static List<JPanel> backColor = new ArrayList<JPanel>();
	ImageIcon bgImage = new ImageIcon(Constant.BACKGROUND_IMAGE_PATH + Constant.PAPER_SMALL_ICON);

	/**
	 * Create the panel.
	 */
	public ProblemImagePanel() {

		initialize();
	}

	private void initialize() {
		setLayout(null);
		for (int i = 0; i < 12; i++) {
			panels.add(new ImagePanel());
			panels.get(i).setBounds(5 + i * 52, 2, 52, 69);
			panels.get(i).setOpaque(false);
			add(panels.get(i));
		}

		for (int i = 0; i < 12; i++) {
			JLabel bgLabel = new JLabel(bgImage);
			bgLabel.setBounds(5 + i * 52, 7, 52, 60);
			add(bgLabel);
		}

		for (int i = 0; i < 12; i++) {
			backColor.add(new JPanel());
			backColor.get(i).setBackground(Color.white);
			backColor.get(i).setBounds(5 + i * 52, 2, 52, 69);
			add(backColor.get(i));
		}

		setBackground(Color.white);

		// panels.get(5).setIcon(new ImageIcon("C:/Users/Yin/Desktop/1231.png"));
		// backColor.get(0).setBackground(Color.blue);
		// backColor.get(5).setBackground(Color.green);
	}

	public static void reset(List<ProblemStrokeImage> strokeImages) {
		for (int i = 0; i < 12; i++) {
			backColor.get(i).setBackground(Color.white);
			panels.get(i).clear();
		}

		backColor.get(0).setBackground(Color.blue);

		for (int i = 0; i < strokeImages.size(); i++) {
			String path = strokeImages.get(i).getImagePath();
			panels.get(i).setIcon(new ImageIcon(path), path);
		}
	}

	public static void strokeBackgroundFeedback(int position, boolean flag) {
		if (flag)
			backColor.get(position).setBackground(Color.green);
		else
			backColor.get(position).setBackground(Color.red);
		panels.get(position).update();
	}

	public static void undo(int position) {
		backColor.get(0).setBackground(Color.blue);
		panels.get(0).update();
		backColor.get(position).setBackground(Color.white);
		panels.get(position).update();
	}
	
	public static void clearBackground() {
		for (int i = 1; i < panels.size(); i++) {
			backColor.get(i).setBackground(Color.white);
			panels.get(i).update();
		}
		backColor.get(0).setBackground(Color.blue);
		panels.get(0).update();
	}

	public static void check(int size) {
		boolean allright = true;
		for (int i = 1; i < size; i++) {
			if (backColor.get(i).getBackground() == Color.green) continue;
			allright = false;
			break;
		}
		if (allright) {
			backColor.get(0).setBackground(Color.green);
			panels.get(0).update();
		} else {
			backColor.get(0).setBackground(Color.red);
			panels.get(0).update();
		}
	}

}
