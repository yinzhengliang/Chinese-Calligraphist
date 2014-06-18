package ui.load;

import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;

import ui.problemframe.control.ControlPanel;
import constants.Constant;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LessonLoadDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5156395507430278087L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			LessonLoadDialog dialog = new LessonLoadDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public LessonLoadDialog() {
		setTitle("Lesson Selection");
		setBounds(100, 100, 434, 98);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 324, 57);
		contentPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Please Choose A Lesson");
		lblNewLabel.setBounds(10, 11, 128, 35);
		contentPanel.add(lblNewLabel);

		DefaultComboBoxModel<String> lessons = new DefaultComboBoxModel<String>();
		final JComboBox<String> comboBox = new JComboBox<String>(lessons);
		comboBox.setBounds(148, 11, 166, 35);

		File[] files = new File(Constant.PROBLEMSET_PATH).listFiles();
		for (File file : files) {
			lessons.addElement(file.getName());
		}

		contentPanel.add(comboBox);
		{
			JOptionPane buttonPane = new JOptionPane();
			buttonPane.setBorder(null);
			buttonPane.setBounds(331, 0, 93, 57);
			getContentPane().add(buttonPane);
			buttonPane.setLayout(null);
			{
				JButton okButton = new JButton("Select");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						ControlPanel.setLesson(Constant.PROBLEMSET_PATH + comboBox.getSelectedItem() + "/");
						setVisible(false);
						dispose();
					}
				});
				okButton.setBounds(0, 5, 83, 23);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						dispose();
					}
				});
				cancelButton.setBounds(0, 31, 83, 23);
				buttonPane.add(cancelButton);
			}
		}
	}
}
