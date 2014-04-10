/*
 * Created by JFormDesigner on Mon Apr 07 21:35:18 CDT 2014
 */

package ui.ChineseCalligraphist;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

import javax.swing.*;

/**
 * @author Zhengliang Yin
 */
public class mainUI extends JFrame {
	/**
	 * 
	 */
	
	private boolean draw = false;
	private List<Point> points = new ArrayList<Point>();
	
	private static final long serialVersionUID = 1L;
	public mainUI() {
		initComponents();
	}

	private void usingRubineMouseClicked(MouseEvent e) {
		// TODO add your code here
		feedbackText.repaint();
		feedbackText.setText("Rubine Mouse: " + usingRubine.isSelected() + "\n" );
		//feedbackPanel.repaint();
		//feedbackText.repaint();
	}

	private void sketchPanelMousePressed(MouseEvent e) {
		// TODO add your code here
		draw = true;
		points.clear();
		points.add(e.getPoint());
		
		sketchPanel.removeAll();
//		Graphics g = new Graphics();
		//sketchPanel.add(new Graphics());
	}

	private void sketchPanelMouseDragged(MouseEvent e) {
		// TODO add your code here
		if (draw == true) {
			Point lastP = points.get(points.size() - 1);
			sketchPanel.getGraphics().drawLine(lastP.x, lastP.y, e.getX(), e.getY());
			points.add(e.getPoint());
			sketchPanel.repaint();
		}
	}

	private void sketchPanelMouseReleased(MouseEvent e) {
		// TODO add your code here
		draw = false;
		points.add(e.getPoint());
		sketchPanel.repaint();
	}

	private void usingLongMouseClicked(MouseEvent e) {
		// TODO add your code here
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
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
//		feedbackArea = new JScrollPane() {
//			@Override
//			public void paint(Graphics g) {
//				paintComponents(g);
//			}
//		};
		feedbackText = new JTextArea();
//		feedbackText = new JTextArea() {
//			@Override
//			public void paint(Graphics g) {
//				super.paintComponents(g);
//			}
//		};
		toggleButton1 = new JToggleButton();
		prevButton = new JButton();
		nextButton = new JButton();
		newButton = new JButton();
		saveButton = new JButton();
		clearButton = new JButton();
		undoButton = new JButton();
		redoButton = new JButton();
		sketchPanel = new JPanel();
//		sketchPanel = new JPanel() {
//			@Override
//			public void paint(Graphics g) {
//				super.paint(g);
//			}
//		};
		resultPanel = new JScrollPane();
		resultList = new JList();
		resultText = new JLabel();

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

			// JFormDesigner evaluation mark
			recognizerPanel.setBorder(new javax.swing.border.CompoundBorder(
				new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
					"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
					javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
					java.awt.Color.red), recognizerPanel.getBorder())); recognizerPanel.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

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
				usingRubine.setBounds(new Rectangle(new Point(0, 15), usingRubine.getPreferredSize()));

				//---- usingLong ----
				usingLong.setText("Long");
				usingLong.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						usingLongMouseClicked(e);
					}
				});
				usedRecognizerPanel.add(usingLong);
				usingLong.setBounds(new Rectangle(new Point(68, 15), usingLong.getPreferredSize()));

				//---- usingOneDollar ----
				usingOneDollar.setText("One Dollar");
				usedRecognizerPanel.add(usingOneDollar);
				usingOneDollar.setBounds(new Rectangle(new Point(126, 15), usingOneDollar.getPreferredSize()));

				//---- usingHausdroff ----
				usingHausdroff.setText("Hausdroff");
				usedRecognizerPanel.add(usingHausdroff);
				usingHausdroff.setBounds(new Rectangle(new Point(210, 15), usingHausdroff.getPreferredSize()));

				//---- usingPaleo ----
				usingPaleo.setText("PaleoSketch");
				usedRecognizerPanel.add(usingPaleo);
				usingPaleo.setBounds(new Rectangle(new Point(292, 15), usingPaleo.getPreferredSize()));
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
				displayPaleo.setBounds(new Rectangle(new Point(508, 15), displayPaleo.getPreferredSize()));

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
				feedbackText.setEditable(false);
				feedbackText.setBackground(new Color(255, 173, 82, 26));
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

		//---- undoButton ----
		undoButton.setText("Undo");

		//---- redoButton ----
		redoButton.setText("Redo");

		//======== sketchPanel ========
		{
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

		//======== resultPanel ========
		{
			resultPanel.setViewportView(resultList);
		}

		//---- resultText ----
		resultText.setText("R-Result ");
		resultText.setBackground(new Color(204, 204, 255));

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
									.addGroup(contentPaneLayout.createParallelGroup()
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
										.addGroup(contentPaneLayout.createSequentialGroup()
											.addComponent(sketchPanel, GroupLayout.PREFERRED_SIZE, 560, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
											.addGroup(contentPaneLayout.createParallelGroup()
												.addComponent(resultPanel, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
												.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
													.addComponent(resultText, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
													.addGap(17, 17, 17)))))
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(feedbackPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
							.addGroup(contentPaneLayout.createParallelGroup()
								.addComponent(sketchPanel, GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
								.addGroup(contentPaneLayout.createSequentialGroup()
									.addGap(5, 5, 5)
									.addComponent(resultText, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
									.addGap(1, 1, 1)
									.addComponent(resultPanel)))
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
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
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
	private JTextArea feedbackText;
	private JToggleButton toggleButton1;
	private JButton prevButton;
	private JButton nextButton;
	private JButton newButton;
	private JButton saveButton;
	private JButton clearButton;
	private JButton undoButton;
	private JButton redoButton;
	private JPanel sketchPanel;
	private JScrollPane resultPanel;
	private JList resultList;
	private JLabel resultText;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
