package ui.problemframe;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import ui.load.LessonLoadDialog;
import ui.problemframe.control.ControlPanel;
import ui.problemframe.feedback.FeedbackPanel;
import ui.problemframe.problem.ProblemDefPanel;
import ui.problemframe.problem.ProblemImagePanel;
import ui.problemframe.sketch.SketchPanel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ProblemFrame extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8463764201828964481L;
	private Border blackline = BorderFactory.createLineBorder(Color.black);

	/**
	 * Create the frame.
	 */
	public ProblemFrame() {

		// setBounds(100, 100, 450, 300);

		setBorder(new EmptyBorder(5, 5, 5, 5));

		setJMenuBar();

		SketchPanel sketchPanel = new SketchPanel();
		sketchPanel.setBounds(0, 160, 640, 431);
		sketchPanel.setBorder(blackline);
		add(sketchPanel);

		FeedbackPanel feedbackPanel = new FeedbackPanel();
		feedbackPanel.setBounds(640, 160, 351, 431);
		feedbackPanel.setBorder(blackline);
		add(feedbackPanel);

		setPreferredSize(new Dimension(1000, 600));

		ProblemDefPanel problemDefPanel = new ProblemDefPanel();
		problemDefPanel.setBounds(-1, 20, 995, 67);
		problemDefPanel.setBorder(blackline);
		add(problemDefPanel);

		ControlPanel controlPanel = new ControlPanel();
		controlPanel.setBounds(640, 87, 351, 73);
		controlPanel.setBorder(blackline);
		add(controlPanel);

		ProblemImagePanel problemImagePanel = new ProblemImagePanel();
		problemImagePanel.setBounds(-1, 87, 641, 73);
		problemImagePanel.setBorder(blackline);
		add(problemImagePanel);

	}

	private void setJMenuBar() {
		setLayout(null);
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(-1, -1, 994, 21);
		menuBar.setBorder(blackline);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LessonLoadDialog a = new LessonLoadDialog();
				a.setVisible(true);
			}
		});
		mnFile.add(mntmNew);
		add(menuBar);
	}
}
