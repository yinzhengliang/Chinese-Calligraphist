package ui.problemframe.control;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JButton;

import problem.Problem;
import ui.problemframe.feedback.FeedbackPanel;
import ui.problemframe.problem.ProblemDefPanel;
import ui.problemframe.sketch.SketchPanel;
import constants.Constant;
import core.sketch.Stroke;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Cursor;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ControlPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1714438953763202845L;

	private JButton undoButton = new JButton(new ImageIcon(Constant.BUTTON_ICON_PATH
			+ Constant.BUTTON_UNDO_UNSELECT_ICON));
	private JButton redoButton = new JButton(new ImageIcon(Constant.BUTTON_ICON_PATH
			+ Constant.BUTTON_REDO_UNSELECT_ICON));
	private JButton prevButton = new JButton(new ImageIcon(Constant.BUTTON_ICON_PATH
			+ Constant.BUTTON_PREV_UNSELECT_ICON));
	private JButton nextButton = new JButton(new ImageIcon(Constant.BUTTON_ICON_PATH
			+ Constant.BUTTON_NEXT_UNSELECT_ICON));
	private JButton clearButton = new JButton(new ImageIcon(Constant.BUTTON_ICON_PATH
			+ Constant.BUTTON_CLEAR_UNSELECT_ICON));

	private List<Stroke> strokes = new ArrayList<Stroke>();

	/**
	 * Create the panel.
	 */
	public ControlPanel() {
		setBackground(Color.WHITE);
		intialize();

	}

	private void intialize() {
		setLayout(null);

		Cursor cursor = new Cursor(Cursor.HAND_CURSOR);

		undoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SketchPanel.undo();
			}
		});

		redoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SketchPanel.redo();
			}
		});
		prevButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ProblemDefPanel.prev();
				SketchPanel.clear();
			}
		});

		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ProblemDefPanel.next();
				SketchPanel.clear();
			}
		});

		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SketchPanel.clear();
			}
		});

		prevButton.setBounds(5, 11, 60, 50);
		undoButton.setBounds(75, 11, 60, 50);
		clearButton.setBounds(145, 11, 60, 50);
		redoButton.setBounds(215, 11, 60, 50);
		nextButton.setBounds(285, 11, 60, 50);

		add(undoButton);
		add(redoButton);
		add(prevButton);
		add(nextButton);
		add(clearButton);

		undoButton.setBorderPainted(false);
		undoButton.setRolloverIcon(new ImageIcon(Constant.BUTTON_ICON_PATH + Constant.BUTTON_UNDO_SELECTED_ICON));

		redoButton.setBorderPainted(false);
		redoButton.setRolloverIcon(new ImageIcon(Constant.BUTTON_ICON_PATH + Constant.BUTTON_REDO_SELECTED_ICON));

		prevButton.setBorderPainted(false);
		prevButton.setRolloverIcon(new ImageIcon(Constant.BUTTON_ICON_PATH + Constant.BUTTON_PREV_SELECTED_ICON));

		nextButton.setBorderPainted(false);
		nextButton.setRolloverIcon(new ImageIcon(Constant.BUTTON_ICON_PATH + Constant.BUTTON_NEXT_SELECTED_ICON));

		clearButton.setBorderPainted(false);
		clearButton.setRolloverIcon(new ImageIcon(Constant.BUTTON_ICON_PATH + Constant.BUTTON_CLEAR_SELECTED_ICON));

		undoButton.setCursor(cursor);
		redoButton.setCursor(cursor);
		prevButton.setCursor(cursor);
		nextButton.setCursor(cursor);
		clearButton.setCursor(cursor);
	}

	public static void setLesson(String lesson) {
		File[] files = new File(lesson + "Characters").listFiles();
		List<Problem> problems = new ArrayList<Problem>();
		for (File file : files) {
			problems.add(new Problem(file));
		}
		ProblemDefPanel.setProblems(problems);
	}

	public List<Stroke> getStrokes() {
		return strokes;
	}

	public void setStrokes(List<Stroke> strokes) {
		this.strokes = strokes;
	}
}
