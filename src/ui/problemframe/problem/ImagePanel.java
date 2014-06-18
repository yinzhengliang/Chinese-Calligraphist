package ui.problemframe.problem;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7556000138401103785L;
	private ImageIcon icon = null;
	private JLabel label = new JLabel();
	private String iconPath = "";

	/**
	 * Create the panel.
	 */
	public ImagePanel() {
		add(label);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		label.setIcon(icon);
	}

	public void clear() {
		icon = null;
		iconPath = "";
		repaint();
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public void setIcon(ImageIcon icon, String path) {
		iconPath = path;
		this.icon = icon;
		repaint();
	}
	
	public void update() {
		icon = new ImageIcon(iconPath);
		repaint();
	}
}
