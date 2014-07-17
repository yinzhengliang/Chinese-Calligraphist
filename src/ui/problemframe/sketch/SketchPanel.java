package ui.problemframe.sketch;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import recognizer.high.Ladder;
import constants.Constant;
import ui.ChineseCalligraphist.ChineseCalligraphistGUI;
import ui.problemframe.feedback.FeedbackPanel;
import ui.problemframe.problem.ProblemImagePanel;
import core.sketch.Point;
import core.sketch.Stroke;
import data.StrokeLoader;

import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class SketchPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 157659378636291634L;

	private static List<Stroke> strokes = new ArrayList<Stroke>();
	private static Stack<Stroke> undoStrokes = new Stack<Stroke>();
	private static List<Stroke> hightlighted = new ArrayList<Stroke>();
	static Integer iii = 0;
	
//	private static List<Stroke> s = new ArrayList<Stroke>();
	
	
	
	static JLabel canvas = new JLabel() {
		/**
		 * 
		 */
		private static final long serialVersionUID = -6722900382295591182L;

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			for (Stroke stroke : strokes) {
				g.setColor(Color.black);
				if (hightlighted.contains(stroke)) {
					g.setColor(new Color(47, 158, 221));
				}
				if (stroke.getPoints().size() > 2) {
					Point s = stroke.getPoints().get(0);
					for (Point p : stroke.getPoints()) {
						Graphics2D g2 = (Graphics2D) g;
						g2.setStroke(new BasicStroke(3));
						// g2.draw(new Line2D.Float(30, 20, 80, 90));

						g2.drawLine((int) p.getX(), (int) p.getY(), (int) s.getX(), (int) s.getY());
						s = p;
					}
				}
			}
		}
	};

	/**
	 * Create the panel.
	 */
	public SketchPanel() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				strokes.add(new Stroke());
				undoStrokes.clear();
				strokes.get(strokes.size() - 1).addPoint(new Point(e.getX(), e.getY()));
				canvas.repaint();
//				strokes.add(s.get(strokes.size()));
//				canvas.repaint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				 FeedbackPanel.recognize(strokes);
				// TODO

				/***********************

				
				// set id and time
//				for (Stroke stroke : strokes) {
				Stroke stroke = strokes.get(strokes.size() - 1);
					for (Point point : stroke.getPoints()) {
						point.generateRandomId();
						point.generateTime(iii++);
					}
					StrokeSaver saver = new StrokeSaver();
					List<Stroke> ssss = new ArrayList<Stroke>();
					ssss.add(stroke);
					saver.save(ssss, Constant.RESOURCE_PATH + "TestData/" + iii + ".xml");
//				}

				***********************/

				canvas.repaint();
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				strokes.get(strokes.size() - 1).addPoint(new Point(e.getX(), e.getY()));
				canvas.repaint();
			}
		});

		setLayout(null);

		initialize();
	}

	public void initialize() {
		canvas.setIcon(new ImageIcon(Constant.BACKGROUND_IMAGE_PATH + Constant.PAPER_ICON));
		canvas.setBounds(1, 1, 638, 429);
		add(canvas);

		setPreferredSize(new Dimension(640, 432));

		setCursor();
		
//		getDrawing();
	}

	private void getDrawing() {
		StrokeLoader loader = new StrokeLoader();
		String dir = "C:/Users/Yin/Desktop/Untitled Folder/";
		String path = dir + "sousa-301-[2014-07-08-22-32-02.993375]-chinese-calligraphist-student-2-lesson-1-numbers_quiz/301/4142/47188.xml";
		loader.setDocument(path);
		loader.setStrokes();
//		s = loader.getStrokes();
		System.out.println("");
	}

	public void setCursor() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.createImage(Constant.PEN_ICON_PATH + Constant.PEN_ICON);
		java.awt.Point hotSpot = new java.awt.Point(0, 30);

		Cursor cursor = toolkit.createCustomCursor(image, hotSpot, "pencil");
		setCursor(cursor);
	}

	public static void undo() {
		if (strokes.size() > 0) {
			undoStrokes.add(strokes.get(strokes.size() - 1));
			Stroke toremove = strokes.remove(strokes.size() - 1);
			canvas.repaint();
			FeedbackPanel.undo(toremove);
			ChineseCalligraphistGUI.ladder.undo(strokes);
			ProblemImagePanel.undo(strokes.size() + 1);
		}

	}

	public static void redo() {
		if (undoStrokes.size() > 0) {
			strokes.add(undoStrokes.pop());
			FeedbackPanel.recognize(strokes);
			canvas.repaint();
		}
	}

	public static void clear() {
		strokes.clear();
		undoStrokes.clear();
		canvas.repaint();
		FeedbackPanel.clear();
		ProblemImagePanel.clearBackground();
	}

	public static void highlightStrokes(List<Stroke> h_strokes) {
		hightlighted.clear();
		hightlighted.addAll(h_strokes);
		canvas.repaint();
	}
}
