package com.game.action;



import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.Color;
import java.net.URL;

public class Start extends JFrame {



	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	
					Start s = new Start();
		s.setVisible(true);
	}


	public Start() {
		getContentPane().setForeground(new Color(0, 0, 0));
		getContentPane().setBackground(SystemColor.textHighlight);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 520, 320);
		getContentPane().setLayout(null);
		this.setTitle("FIGHT FOR GLORY");
	    this.setLocationRelativeTo(null);
        this.setResizable(false);
        new Music4();
      //title image 
      URL titleImg = this.getClass().getResource("/com/game/action/resources/images/title5.png");
      ImageIcon titlem=new ImageIcon(titleImg);
      JLabel j1=new JLabel(titlem);
      j1.setBounds(100, 10, titlem.getIconWidth(),titlem.getIconHeight());
      getContentPane().add(j1);
	// image
      URL solImg = this.getClass().getResource("/com/game/action/resources/images/enemy.gif");
      ImageIcon sol1=new ImageIcon(solImg);
      JLabel j2=new JLabel(sol1);
      j2.setBounds(345,55, sol1.getIconWidth(),sol1.getIconHeight());
      getContentPane().add(j2);
      
     URL tankImg = this.getClass().getResource("/com/game/action/resources/images/tank3.gif");
     ImageIcon tank=new ImageIcon(tankImg);
      JLabel j3=new JLabel(tank);
      j3.setBounds(15,70, tank.getIconWidth(),tank.getIconHeight());
      getContentPane().add(j3);
      
      
   //quote
      URL qImg = this.getClass().getResource("/com/game/action/resources/images/title4.png");
      ImageIcon q1=new ImageIcon(qImg);
      JLabel j4=new JLabel(q1);
      j4.setBounds(110,240, q1.getIconWidth(),q1.getIconHeight());
      getContentPane().add(j4);

      
      
		JButton btnStartGame = new JButton("START GAME");
		btnStartGame.setForeground(new Color(102, 51, 51));
		btnStartGame.setBackground(new Color(0, 204, 153));
		btnStartGame.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnStartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Music4.clip.stop();
				new Window();
			}
		});
		btnStartGame.setBounds(192, 70, 114, 23);
		getContentPane().add(btnStartGame);
		
		JButton btnNewButton = new JButton("PROFILE");
		btnNewButton.setBackground(new Color(0, 204, 153));
		btnNewButton.setForeground(new Color(102, 51, 51));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Profile();
			}
		});
		btnNewButton.setBounds(192, 110, 114, 23);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("CONTROLS");
		btnNewButton_1.setBackground(new Color(0, 204, 153));
		btnNewButton_1.setForeground(new Color(102, 51, 51));
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				new Control();
			}
		});
		btnNewButton_1.setBounds(192, 150, 114, 23);
		getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("EXIT");
		btnNewButton_2.setBackground(new Color(0, 204, 153));
		btnNewButton_2.setForeground(new Color(102, 51, 51));
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnNewButton_2.setBounds(192, 190, 114, 23);
		getContentPane().add(btnNewButton_2);
	

}
	}
