package ui.problemframe.feedback;

import java.awt.Color;
import java.awt.Cursor;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;

import core.sketch.Interpretation;
import core.sketch.Stroke;
import recognizer.low.gesture.Long;
import ui.problemframe.problem.ProblemDefPanel;
import ui.problemframe.problem.ProblemImagePanel;
import ui.problemframe.sketch.SketchPanel;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class FeedbackPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3350754534634144912L;
	private static DefaultListModel<FeedbackResult> listModel = new DefaultListModel<FeedbackResult>();
	private final JList<FeedbackResult> feedbackText = new JList<FeedbackResult>(listModel);

	private static Long recognizer = new Long();

	/**
	 * Create the panel.
	 */

	public FeedbackPanel() {
		initialize();

	}

	public void initialize() {
		setBackground();
		setContentPanel();
	}

	private void setBackground() {
		setBackground(new Color(255, 173, 82, 26));
		setLayout(null);
	}

	private void setContentPanel() {
		JLabel feedbackHead = new JLabel("Feedbacks");
		add(feedbackHead);
		feedbackHead.setBounds(10, 5, 240, feedbackHead.getPreferredSize().height);
		JSeparator separator7 = new JSeparator();
		JSeparator separator8 = new JSeparator();
		JSeparator separator9 = new JSeparator();
		add(separator7);
		separator7.setBounds(5, 25, 295, 12);
		add(separator8);
		separator8.setBounds(15, 30, 305, 12);
		add(separator9);
		separator9.setBounds(30, 35, 305, 12);
		// ---- feedbackText ----
		JScrollPane scrollPane = new JScrollPane();
		feedbackText.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		feedbackText.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) {
					SketchPanel.highlightStrokes(listModel.get(feedbackText.getSelectedIndex()).getStrokes());
				}
			}
		});
		scrollPane.setViewportView(feedbackText);
		
		scrollPane.setBounds(5, 40, 340, 385);
		Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
		scrollPane.setCursor(cursor);
		add(scrollPane);
	}

	public static void recognize(List<Stroke> strokes) {
		List<Interpretation> interpretations = recognizer.recognize(strokes.get(strokes.size() - 1));
		for (Interpretation interpretation : interpretations) {
			System.out.println(interpretation.getName() + ":\t" + interpretation.getConfidence());
		}
		System.out.println();
		System.out.println();
		List<Stroke> toadd = new ArrayList<Stroke>();
		toadd.add(strokes.get(strokes.size() - 1));
		listModel.addElement(new FeedbackResult(interpretations.get(0), toadd));
		ProblemDefPanel.setStrokeFeedback(strokes.size(), interpretations.get(0).getName());
	}

	public static void clear() {
		listModel.clear();
	}

	public static void undo(Stroke toremove) {
		List<Integer> removelist = new ArrayList<Integer>();
		for (int i = listModel.size() - 1; i >= 0; i--) {
			if (listModel.get(i).getStrokes().contains(toremove)) removelist.add(i);
		}
		
		for (Integer index : removelist) {
			listModel.remove(index);
		}
	}
}
