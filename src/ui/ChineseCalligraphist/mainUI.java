/*
 * Created by JFormDesigner on Mon Apr 07 21:35:18 CDT 2014
 */

package ui.ChineseCalligraphist;

import core.sketch.Interpretation;
import core.sketch.Stroke;
import core.sketch.Point;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.*;

import recognizer.low.gesture.Rubine;
import recognizer.low.gesture.Long;
import recognizer.low.template.Dollar;
import recognizer.low.template.Hausdroff;
import ui.feedback.FeedbackResult;

/**
 * @author Zhengliang Yin
 */
public class mainUI extends JFrame {
	/**
	 * 
	 */
	

	private DefaultListModel<FeedbackResult> listModel = new DefaultListModel<FeedbackResult>();
 
	//private List<Point> points = new ArrayList<Point>();
	private List<Stroke> strokes = new ArrayList<Stroke>();
	private Stack<Stroke> undoStrokes = new Stack<Stroke>();
//	private Hausdroff recognizer = new Hausdroff();
//	private Dollar recognizer = new Dollar();
//	private Rubine recognizer = new Rubine();
	private Long recognizer = new Long();

	private static final long serialVersionUID = 1L;
  
	public mainUI() {
		initComponents();
	}

	private void usingRubineMouseClicked(MouseEvent e) {
		// TODO add your code here
//		feedbackText.add("Rubine Mouse: " + usingRubine.isSelected());
//		listModel.addElement("Rubine Mouse: " + usingRubine.isSelected());
		System.out.println(feedbackText.getComponent(feedbackText.getComponentCount() - 1));
//		feedbackText.append("Rubine Mouse: " + usingRubine.isSelected() + "\n");
//		feedbackText.selectAll();
//		feedbackText.setCaretPosition(feedbackText.getDocument().getLength());
	}

	private void sketchPanelMousePressed(MouseEvent e) {
		// TODO add your code here
		sketchPanel.repaint();
		//points.clear();
		strokes.add(new Stroke());
		undoStrokes.clear();
		strokes.get(strokes.size() - 1).addPoint(new Point(e.getX(), e.getY()));

		// sketchPanel.removeAll();
		// Graphics g = new Graphics();
		// sketchPanel.add(new Graphics());
	}

	private void sketchPanelMouseDragged(MouseEvent e) {
		// TODO add your code here
//			Point lastP = points.get(points.size() - 1);
//			sketchPanel.getGraphics().drawLine(lastP.x, lastP.y, e.getX(), e.getY());
			strokes.get(strokes.size() - 1).addPoint(new Point(e.getX(), e.getY()));
			sketchPanel.repaint();
			// sketchPanel.repaint();
			// feedbackText.append("X: " + e.getX() + " Y: " + e.getY() + "\n");
	}

	private void sketchPanelMouseReleased(MouseEvent e) {
		// TODO add your code here
		System.out.println("======================result=====================");
		List<Interpretation> interpretations = recognizer.recognize(strokes.get(strokes.size() - 1));
		for (Interpretation interpretation : interpretations) {
			System.out.println(interpretation.getName() + ":\t" + interpretation.getConfidence());
		}
		System.out.println();
		System.out.println();
		System.out.println();
//		feedbackText.append(interpretations.get(0).getName() + "\n");
//		feedbackText.add(new JTextArea(interpretations.get(0).getName() + "\n"));
		listModel.addElement(new FeedbackResult());
		
		sketchPanel.repaint();
	}

	private void usingLongMouseClicked(MouseEvent e) {
		// TODO add your code here
	}
	

	private void clearButtonActionPerformed(ActionEvent e) {
		strokes.clear();
		undoStrokes.clear();
		sketchPanel.repaint();
	}
	
	private void undoButtonActionPerformed(ActionEvent e) {
		if (strokes.size() > 0) {
			undoStrokes.add(strokes.get(strokes.size() - 1));
			strokes.remove(strokes.size() - 1);
			sketchPanel.repaint();
		}
	}
	
	private void redoButtonActionPerformed(ActionEvent e) {
		if (undoStrokes.size() > 0) {
			strokes.add(undoStrokes.pop());
			sketchPanel.repaint();
		}
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Zhengliang Yin
		menuBar = new JMenuBar();
		fileMenu = new JMenu();
		confLoadMenu = new JMenuItem();
		analysisMenu = new JMenu();
		recognizerPanel = new JPanel();
		usedRecognizerPanel = new JPanel();
		usedRecognizerLabel = new JLabel();
		usingRubine = new JCheckBox();
		usingLong = new JCheckBox();
		usingOneDollar = new JCheckBox();
		usingHausdroff = new JCheckBox();
		usingPaleo = new JCheckBox();
		separator4 = new JSeparator();
		displayRecognizerPanel = new JPanel();
		displayRecognizerLabel = new JLabel();
		displayOneDollar = new JRadioButton();
		displayLongOrigin = new JRadioButton();
		displayLongNN = new JRadioButton();
		displayRubineOrigin = new JRadioButton();
		displayRubineNN = new JRadioButton();
		displayPaleo = new JRadioButton();
		displayHausdroff = new JRadioButton();
		separator5 = new JSeparator();
		separator2 = new JSeparator();
		separator3 = new JSeparator();
		separator1 = new JSeparator();
		statusPanel = new JPanel();
		statusText = new JLabel();
		separator6 = new JSeparator();
		feedbackPanel = new JPanel();
		feedbackHead = new JLabel();
		separator7 = new JSeparator();
		separator8 = new JSeparator();
		separator9 = new JSeparator();
		feedbackArea = new JScrollPane();
//		feedbackText = new JList<String>(listModel);
		feedbackText = new JList<FeedbackResult>(listModel);
//		feedbackText = new JList<JTextArea>() {
//			/**
//			 * 
//			 */
//			private static final long serialVersionUID = -3846208201545789570L;
//
//			 @Override
//			 public void paintComponents(Graphics g) {
//				super.paintComponents(g);
//				setBackground(new Color(255, 173, 82, 26));
//			 }
//		};
		toggleButton1 = new JToggleButton();
		prevButton = new JButton();
		nextButton = new JButton();
		newButton = new JButton();
		saveButton = new JButton();
		clearButton = new JButton();
		undoButton = new JButton();
		redoButton = new JButton();
//		sketchPanel = new JPanel();
		sketchPanel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				for (Stroke stroke : strokes) {
					if (stroke.getPoints().size() > 2) {
						Point s = stroke.getPoints().get(0);
						for (Point p : stroke.getPoints()) {
							Graphics2D g2 = (Graphics2D) g;
			                g2.setStroke(new BasicStroke(3));
//			                g2.draw(new Line2D.Float(30, 20, 80, 90));
							
							g2.drawLine((int) p.getX(), (int) p.getY(), (int) s.getX(), (int) s.getY());
							s = p;
						}
					}
				}
			}
		};
		questionPanel = new JPanel();

		//======== this ========
		setResizable(false);
		setTitle("Chinese Calligraphist");
		setMinimumSize(new Dimension(1024, 576));
		setBackground(Color.white);
		setForeground(Color.black);
		Container contentPane = getContentPane();

		//======== menuBar ========
		{

			//======== fileMenu ========
			{
				fileMenu.setText("File");

				//---- confLoadMenu ----
				confLoadMenu.setText("Load Training Configurations");
				fileMenu.add(confLoadMenu);
			}
			menuBar.add(fileMenu);

			//======== analysisMenu ========
			{
				analysisMenu.setText("Analysis");
			}
			menuBar.add(analysisMenu);
		}
		setJMenuBar(menuBar);

		//======== recognizerPanel ========
		{
			Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
			recognizerPanel.setCursor(cursor);
			
//			// JFormDesigner evaluation mark
//			recognizerPanel.setBorder(new javax.swing.border.CompoundBorder(
//				new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
//					"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
//					javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
//					java.awt.Color.red), recognizerPanel.getBorder())); recognizerPanel.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

			recognizerPanel.setLayout(null);

			//======== usedRecognizerPanel ========
			{
				usedRecognizerPanel.setLayout(null);

				//---- usedRecognizerLabel ----
				usedRecognizerLabel.setText("Please choose the recognizer");
				usedRecognizerPanel.add(usedRecognizerLabel);
				usedRecognizerLabel.setBounds(5, 0, 175, 15);

				//---- usingRubine ----
				usingRubine.setText("Rubine");
				usingRubine.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						usingRubineMouseClicked(e);
					}
				});
				usedRecognizerPanel.add(usingRubine);
				usingRubine.setBounds(new Rectangle(new java.awt.Point(0, 15), usingRubine.getPreferredSize()));

				//---- usingLong ----
				usingLong.setText("Long");
				usingLong.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						usingLongMouseClicked(e);
					}
				});
				usedRecognizerPanel.add(usingLong);
				usingLong.setBounds(new Rectangle(new java.awt.Point(68, 15), usingLong.getPreferredSize()));

				//---- usingOneDollar ----
				usingOneDollar.setText("One Dollar");
				usedRecognizerPanel.add(usingOneDollar);
				usingOneDollar.setBounds(new Rectangle(new java.awt.Point(126, 15), usingOneDollar.getPreferredSize()));

				//---- usingHausdroff ----
				usingHausdroff.setText("Hausdroff");
				usedRecognizerPanel.add(usingHausdroff);
				usingHausdroff.setBounds(new Rectangle(new java.awt.Point(210, 15), usingHausdroff.getPreferredSize()));

				//---- usingPaleo ----
				usingPaleo.setText("PaleoSketch");
				usedRecognizerPanel.add(usingPaleo);
				usingPaleo.setBounds(new Rectangle(new java.awt.Point(292, 15), usingPaleo.getPreferredSize()));
				usedRecognizerPanel.add(separator4);
				separator4.setBounds(180, 0, 220, 5);

				{ // compute preferred size
					Dimension preferredSize = new Dimension();
					for(int i = 0; i < usedRecognizerPanel.getComponentCount(); i++) {
						Rectangle bounds = usedRecognizerPanel.getComponent(i).getBounds();
						preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
						preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
					}
					Insets insets = usedRecognizerPanel.getInsets();
					preferredSize.width += insets.right;
					preferredSize.height += insets.bottom;
					usedRecognizerPanel.setMinimumSize(preferredSize);
					usedRecognizerPanel.setPreferredSize(preferredSize);
				}
			}
			recognizerPanel.add(usedRecognizerPanel);
			usedRecognizerPanel.setBounds(0, 0, 395, 45);

			//======== displayRecognizerPanel ========
			{
				displayRecognizerPanel.setLayout(null);

				//---- displayRecognizerLabel ----
				displayRecognizerLabel.setText("Show the recognition of the recognizer");
				displayRecognizerPanel.add(displayRecognizerLabel);
				displayRecognizerLabel.setBounds(0, 0, 230, displayRecognizerLabel.getPreferredSize().height);

				//---- displayOneDollar ----
				displayOneDollar.setText("One Dollar");
				displayRecognizerPanel.add(displayOneDollar);
				displayOneDollar.setBounds(348, 15, displayOneDollar.getPreferredSize().width, 23);

				//---- displayLongOrigin ----
				displayLongOrigin.setText("Long Original");
				displayRecognizerPanel.add(displayLongOrigin);
				displayLongOrigin.setBounds(255, 15, displayLongOrigin.getPreferredSize().width, 23);

				//---- displayLongNN ----
				displayLongNN.setText("Long NN");
				displayRecognizerPanel.add(displayLongNN);
				displayLongNN.setBounds(184, 15, displayLongNN.getPreferredSize().width, 23);

				//---- displayRubineOrigin ----
				displayRubineOrigin.setText("Rubine Original");
				displayRecognizerPanel.add(displayRubineOrigin);
				displayRubineOrigin.setBounds(81, 15, displayRubineOrigin.getPreferredSize().width, 23);

				//---- displayRubineNN ----
				displayRubineNN.setText("Rubine NN");
				displayRecognizerPanel.add(displayRubineNN);
				displayRubineNN.setBounds(0, 15, displayRubineNN.getPreferredSize().width, 23);

				//---- displayPaleo ----
				displayPaleo.setText("PaleoSketch");
				displayRecognizerPanel.add(displayPaleo);
				displayPaleo.setBounds(new Rectangle(new java.awt.Point(508, 15), displayPaleo.getPreferredSize()));

				//---- displayHausdroff ----
				displayHausdroff.setText("Hausdroff");
				displayRecognizerPanel.add(displayHausdroff);
				displayHausdroff.setBounds(429, 15, displayHausdroff.getPreferredSize().width, 23);
				displayRecognizerPanel.add(separator5);
				separator5.setBounds(230, 0, 375, 5);

				{ // compute preferred size
					Dimension preferredSize = new Dimension();
					for(int i = 0; i < displayRecognizerPanel.getComponentCount(); i++) {
						Rectangle bounds = displayRecognizerPanel.getComponent(i).getBounds();
						preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
						preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
					}
					Insets insets = displayRecognizerPanel.getInsets();
					preferredSize.width += insets.right;
					preferredSize.height += insets.bottom;
					displayRecognizerPanel.setMinimumSize(preferredSize);
					displayRecognizerPanel.setPreferredSize(preferredSize);
				}
			}
			recognizerPanel.add(displayRecognizerPanel);
			displayRecognizerPanel.setBounds(415, 0, 610, 45);

			//---- separator2 ----
			separator2.setOrientation(SwingConstants.VERTICAL);
			recognizerPanel.add(separator2);
			separator2.setBounds(395, -5, 5, 60);
			recognizerPanel.add(separator3);
			separator3.setBounds(0, 45, 1024, 5);

			{ // compute preferred size
				Dimension preferredSize = new Dimension();
				for(int i = 0; i < recognizerPanel.getComponentCount(); i++) {
					Rectangle bounds = recognizerPanel.getComponent(i).getBounds();
					preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
					preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
				}
				Insets insets = recognizerPanel.getInsets();
				preferredSize.width += insets.right;
				preferredSize.height += insets.bottom;
				recognizerPanel.setMinimumSize(preferredSize);
				recognizerPanel.setPreferredSize(preferredSize);
			}
		}

		//======== statusPanel ========
		{
			statusPanel.setBackground(new Color(51, 255, 102));
			statusPanel.setLayout(null);

			//---- statusText ----
			statusText.setText("sadfasfaer");
			statusPanel.add(statusText);
			statusText.setBounds(0, 0, 1020, 25);
			statusPanel.add(separator6);
			separator6.setBounds(0, 0, 1025, 5);

			{ // compute preferred size
				Dimension preferredSize = new Dimension();
				for(int i = 0; i < statusPanel.getComponentCount(); i++) {
					Rectangle bounds = statusPanel.getComponent(i).getBounds();
					preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
					preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
				}
				Insets insets = statusPanel.getInsets();
				preferredSize.width += insets.right;
				preferredSize.height += insets.bottom;
				statusPanel.setMinimumSize(preferredSize);
				statusPanel.setPreferredSize(preferredSize);
			}
		}

		//======== feedbackPanel ========
		{
			Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
			feedbackPanel.setCursor(cursor);
			feedbackPanel.setBackground(new Color(255, 255, 153));
			feedbackPanel.setLayout(null);

			//---- feedbackHead ----
			feedbackHead.setText("Feedbacks");
			feedbackPanel.add(feedbackHead);
			feedbackHead.setBounds(10, 5, 240, feedbackHead.getPreferredSize().height);
			feedbackPanel.add(separator7);
			separator7.setBounds(5, 25, 295, 12);
			feedbackPanel.add(separator8);
			separator8.setBounds(15, 30, 305, 12);
			feedbackPanel.add(separator9);
			separator9.setBounds(30, 35, 305, 12);

			//======== feedbackArea ========
			{
				feedbackArea.setBackground(new Color(240, 10, 240, 68));

				//---- feedbackText ----
				
//				feedbackText.setEditable(false);
//				feedbackText.setBackground(new Color(255, 173, 82, 26));
				feedbackArea.setViewportView(feedbackText);
			}
			feedbackPanel.add(feedbackArea);
			feedbackArea.setBounds(5, 40, 340, 385);

			{ // compute preferred size
				Dimension preferredSize = new Dimension();
				for(int i = 0; i < feedbackPanel.getComponentCount(); i++) {
					Rectangle bounds = feedbackPanel.getComponent(i).getBounds();
					preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
					preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
				}
				Insets insets = feedbackPanel.getInsets();
				preferredSize.width += insets.right;
				preferredSize.height += insets.bottom;
				feedbackPanel.setMinimumSize(preferredSize);
				feedbackPanel.setPreferredSize(preferredSize);
			}
		}

		//---- toggleButton1 ----
		toggleButton1.setText("View/Draw");

		//---- prevButton ----
		prevButton.setText("Prev");

		//---- nextButton ----
		nextButton.setText("Next");

		//---- newButton ----
		newButton.setText("New");

		//---- saveButton ----
		saveButton.setText("Save");

		//---- clearButton ----
		clearButton.setText("Clear");
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearButtonActionPerformed(e);
			}
		});

		//---- undoButton ----
		undoButton.setText("Undo");
		undoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				undoButtonActionPerformed(e);
			}
		});

		//---- redoButton ----
		redoButton.setText("Redo");
		redoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				redoButtonActionPerformed(e);
			}
		});

		//======== sketchPanel ========
		{
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Image image = toolkit.createImage("C:/Users/Yin/workspace/Chinese-Calligraphist/images/pencil.png");
			java.awt.Point hotSpot = new java.awt.Point(0,30);
			
			Cursor cursor = toolkit.createCustomCursor(image, hotSpot, "pencil");
			sketchPanel.setCursor(cursor);
			
			sketchPanel.setBackground(new Color(84, 255, 239));
			sketchPanel.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					sketchPanelMousePressed(e);
				}
				@Override
				public void mouseReleased(MouseEvent e) {
					sketchPanelMouseReleased(e);
				}
			});
			sketchPanel.addMouseMotionListener(new MouseMotionAdapter() {
				@Override
				public void mouseDragged(MouseEvent e) {
					sketchPanelMouseDragged(e);
				}
			});
			sketchPanel.setLayout(null);

			{ // compute preferred size
				Dimension preferredSize = new Dimension();
				for(int i = 0; i < sketchPanel.getComponentCount(); i++) {
					Rectangle bounds = sketchPanel.getComponent(i).getBounds();
					preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
					preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
				}
				Insets insets = sketchPanel.getInsets();
				preferredSize.width += insets.right;
				preferredSize.height += insets.bottom;
				sketchPanel.setMinimumSize(preferredSize);
				sketchPanel.setPreferredSize(preferredSize);
			}
		}

		//======== questionPanel ========
		{
			questionPanel.setBackground(Color.white);
			questionPanel.setLayout(null);

			{ // compute preferred size
				Dimension preferredSize = new Dimension();
				for(int i = 0; i < questionPanel.getComponentCount(); i++) {
					Rectangle bounds = questionPanel.getComponent(i).getBounds();
					preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
					preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
				}
				Insets insets = questionPanel.getInsets();
				preferredSize.width += insets.right;
				preferredSize.height += insets.bottom;
				questionPanel.setMinimumSize(preferredSize);
				questionPanel.setPreferredSize(preferredSize);
			}
		}

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addGroup(contentPaneLayout.createParallelGroup()
						.addComponent(statusPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addGroup(contentPaneLayout.createSequentialGroup()
									.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
										.addGroup(contentPaneLayout.createSequentialGroup()
											.addComponent(toggleButton1)
											.addGap(7, 7, 7)
											.addComponent(prevButton, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
											.addGap(12, 12, 12)
											.addComponent(nextButton, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
											.addGap(7, 7, 7)
											.addComponent(newButton, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
											.addGap(3, 3, 3)
											.addComponent(saveButton, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
											.addGap(10, 10, 10)
											.addComponent(clearButton, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
											.addGap(6, 6, 6)
											.addComponent(undoButton, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
											.addGap(6, 6, 6)
											.addComponent(redoButton, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE))
										.addComponent(sketchPanel, GroupLayout.DEFAULT_SIZE, 654, Short.MAX_VALUE)
										.addComponent(questionPanel, GroupLayout.DEFAULT_SIZE, 654, Short.MAX_VALUE))
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(feedbackPanel, GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE))
								.addComponent(separator1, GroupLayout.PREFERRED_SIZE, 1024, GroupLayout.PREFERRED_SIZE)
								.addComponent(recognizerPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(0, 0, Short.MAX_VALUE)))
					.addContainerGap())
		);
		contentPaneLayout.setVerticalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addComponent(separator1, GroupLayout.PREFERRED_SIZE, 5, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(recognizerPanel, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
					.addGap(1, 1, 1)
					.addGroup(contentPaneLayout.createParallelGroup()
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addComponent(questionPanel, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(sketchPanel, GroupLayout.PREFERRED_SIZE, 290, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(contentPaneLayout.createParallelGroup()
								.addComponent(toggleButton1)
								.addComponent(prevButton)
								.addComponent(nextButton)
								.addComponent(newButton)
								.addComponent(saveButton)
								.addComponent(clearButton)
								.addComponent(undoButton)
								.addComponent(redoButton)))
						.addComponent(feedbackPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(statusPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		setLocationRelativeTo(getOwner());

		//---- DisplayRecognizer ----
		ButtonGroup DisplayRecognizer = new ButtonGroup();
		DisplayRecognizer.add(displayOneDollar);
		DisplayRecognizer.add(displayLongOrigin);
		DisplayRecognizer.add(displayLongNN);
		DisplayRecognizer.add(displayRubineOrigin);
		DisplayRecognizer.add(displayRubineNN);
		DisplayRecognizer.add(displayPaleo);
		DisplayRecognizer.add(displayHausdroff);
		// //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY
	// //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Zhengliang Yin
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem confLoadMenu;
	private JMenu analysisMenu;
	private JPanel recognizerPanel;
	private JPanel usedRecognizerPanel;
	private JLabel usedRecognizerLabel;
	private JCheckBox usingRubine;
	private JCheckBox usingLong;
	private JCheckBox usingOneDollar;
	private JCheckBox usingHausdroff;
	private JCheckBox usingPaleo;
	private JSeparator separator4;
	private JPanel displayRecognizerPanel;
	private JLabel displayRecognizerLabel;
	private JRadioButton displayOneDollar;
	private JRadioButton displayLongOrigin;
	private JRadioButton displayLongNN;
	private JRadioButton displayRubineOrigin;
	private JRadioButton displayRubineNN;
	private JRadioButton displayPaleo;
	private JRadioButton displayHausdroff;
	private JSeparator separator5;
	private JSeparator separator2;
	private JSeparator separator3;
	private JSeparator separator1;
	private JPanel statusPanel;
	private JLabel statusText;
	private JSeparator separator6;
	private JPanel feedbackPanel;
	private JLabel feedbackHead;
	private JSeparator separator7;
	private JSeparator separator8;
	private JSeparator separator9;
	private JScrollPane feedbackArea;
//	private JTextArea feedbackText;
//	private JList<String> feedbackText;
	private JList<FeedbackResult> feedbackText;
	private JToggleButton toggleButton1;
	private JButton prevButton;
	private JButton nextButton;
	private JButton newButton;
	private JButton saveButton;
	private JButton clearButton;
	private JButton undoButton;
	private JButton redoButton;
	private JPanel sketchPanel;
	private JPanel questionPanel;
	// JFormDesigner - End of variables declaration //GEN-END:variables
}
