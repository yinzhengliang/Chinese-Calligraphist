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
import ui.ChineseCalligraphist.ChineseCalligraphistGUI;
import ui.problemframe.problem.ProblemDefPanel;
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

	public static void addFeedback(String string, List<Stroke> strokes) {
		listModel.addElement(new FeedbackResult(string, strokes));
	}
	
	public static void recognize(List<Stroke> strokes) {
		List<Interpretation> interpretations = ChineseCalligraphistGUI.recognizer.recognize(strokes.get(strokes.size() - 1));
		for (Interpretation interpretation : interpretations) {
			System.out.println(interpretation.getName() + ":\t" + interpretation.getConfidence());
		}
		System.out.println();
		System.out.println();
		List<Stroke> toadd = new ArrayList<Stroke>();
		toadd.add(strokes.get(strokes.size() - 1));
		listModel.addElement(new FeedbackResult(interpretations.get(0), toadd));
//		String xxx = "";
//		
//		if (strokes.size() == 1) {
//			xxx = "Stroke: [Dian] is recognized.";
//			listModel.addElement(new FeedbackResult(xxx, toadd));
//			xxx = "Stroke: [Dian] looks good.";
//			listModel.addElement(new FeedbackResult(xxx, toadd));
//		} else if (strokes.size() == 2) {
//			xxx = "Stroke: [Dian] is recognized.";
//			listModel.addElement(new FeedbackResult(xxx, toadd));
//			xxx = "Stroke: [Dian] looks good.";
//			listModel.addElement(new FeedbackResult(xxx, toadd));
//		} 
//			else if (strokes.size() == 3) {
//				xxx = "Stroke: [Ti] is recognized.";
//				listModel.addElement(new FeedbackResult(xxx, toadd));
//				xxx = "Stroke: [Ti] looks good.";
//				listModel.addElement(new FeedbackResult(xxx, toadd));
//				
//				List<Stroke> toadd1 = new ArrayList<Stroke>();
//				toadd1.addAll(strokes);
//				
//				xxx = "Radical: [SanDianShui] is recognized! Good!";
//				listModel.addElement(new FeedbackResult(xxx, toadd1));
//
//				xxx = "[SanDianShui] passes all checks! Good Writing! :)";
//				listModel.addElement(new FeedbackResult(xxx, toadd1));
//		}
//			else if (strokes.size() == 4) {
//				xxx = "Stroke: [HengPie] is recognized.";
//				listModel.addElement(new FeedbackResult(xxx, toadd));
//				xxx = "Stroke: [HengPie] looks good.";
//				listModel.addElement(new FeedbackResult(xxx, toadd));
//			}
//		else if (strokes.size() == 5) {
//			xxx = "Stroke: [Na] is recognized.";
//			listModel.addElement(new FeedbackResult(xxx, toadd));
//			xxx = "Stroke: [Na] looks good.";
//			listModel.addElement(new FeedbackResult(xxx, toadd));
//			
//			List<Stroke> toadd2 = new ArrayList<Stroke>();
//			toadd2.add(strokes.get(strokes.size() - 2));
//			toadd2.add(strokes.get(strokes.size() - 1));
//			
//			
//			
//			xxx = "Character: [You] is recognized.";
//			listModel.addElement(new FeedbackResult(xxx, toadd2));
//			xxx = "[You] passes all checks! Great Writing! :)";
//			listModel.addElement(new FeedbackResult(xxx, toadd2));
//			
//			
//			List<Stroke> toadd1 = new ArrayList<Stroke>();
//			toadd1.addAll(strokes);
//			
//			xxx = "Character: [Han] is recognized.";
//			listModel.addElement(new FeedbackResult(xxx, toadd2));
//			xxx = "[Han] passes all checks! Great Writing! :)";
//			listModel.addElement(new FeedbackResult(xxx, toadd2));
//			
//			
//		}

		ProblemDefPanel.setStrokeFeedback(strokes.size(), interpretations.get(0).getName());
		ChineseCalligraphistGUI.ladder.recognize(strokes.get(strokes.size() - 1), interpretations.get(0) );
		// Ladder works now, check higher level info, give feed back
		
	}

	public static void clear() {
		listModel.clear();
	}

	public static void undo(Stroke toremove) {
		List<Integer> removelist = new ArrayList<Integer>();
		for (int i = listModel.size() - 1; i >= 0; i--) {
			if (listModel.get(i).getStrokes().contains(toremove))
				removelist.add(i);
		}

		for (Integer index : removelist) {
			listModel.remove(index);
		}
	}
}
