package ui.ChineseCalligraphist;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import recognizer.low.NN;
import recognizer.high.Ladder;

import ui.problemframe.ProblemFrame;

import javax.swing.JTabbedPane;

public class ChineseCalligraphistGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3000445710487189521L;
	private JTabbedPane contentPane;
	public static NN recognizer = new NN();
	public static Ladder ladder = new Ladder();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChineseCalligraphistGUI frame = new ChineseCalligraphistGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ChineseCalligraphistGUI() {
		setResizable(false);
		setTitle("Chinese Calligraphist");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1001, 650);
		contentPane = new JTabbedPane();
		// setContentPane(contentPane);
		getContentPane().add(contentPane);

		JPanel p = new JPanel();
		p.add(new JLabel("welcome"));
		contentPane.addTab("Login", p);
		contentPane.addTab("Practice Session", new ProblemFrame());
		contentPane.addTab("Homework Session", new JPanel());

		// setPreferredSize(new Dimension(1000, 600));
	}
}
