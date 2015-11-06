package com.game.action;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import java.awt.Color;

public class Control extends JDialog {
	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public Control() {
		setBackground(SystemColor.textHighlight);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	     this.setTitle("CONTROLS");
		this.setVisible(true);
		setBounds(100, 100, 450, 170);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(SystemColor.textHighlight);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblHwllo = new JLabel("USE A,D OR ARROW KEYS TO MOVE THE PLAYER.");
			lblHwllo.setBounds(28, 15, 312, 33);
			contentPanel.add(lblHwllo);
		}
		{
			JLabel lblHello = new JLabel("USE LEFT MOUSE BUTTON TO FIRE BULLETS.");
			lblHello.setBackground(SystemColor.textHighlight);
			lblHello.setBounds(28, 40, 294, 33);
			contentPanel.add(lblHello);
		}
		{
			JLabel lblHello_1 = new JLabel("USE RIGHT MOUSE BUTTON TO FIRE ROCKETS.");
			lblHello_1.setBounds(28, 65, 294, 33);
			contentPanel.add(lblHello_1);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(SystemColor.textHighlight);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setForeground(new Color(102, 51, 51));
				okButton.setBackground(new Color(0, 204, 153));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

}
