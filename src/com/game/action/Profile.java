package com.game.action;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.TextArea;

import javax.swing.JTextArea;


public class Profile extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	
 static String put;
 private JTextArea textArea;

	// JDBC driver name and database URL
	  static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	  static final String DB_URL = "jdbc:mysql://localhost:3307/game";

	  //  Database credentials
	  static final String USER = "root";
	  static final String PASS = "root";
/**
	 * Create the frame.
	 */
	public Profile() {
		
		this.setTitle("PROFILE");
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.textHighlight);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel lblEnter = new JLabel("ENTER NAME");
		lblEnter.setBounds(24, 26, 91, 27);
		contentPane.add(lblEnter);
		
		textField = new JTextField();
		textField.setBounds(101, 26, 202, 27);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(129, 71, 44, -300);
		contentPane.add(btnNewButton);
		
		JButton btnOk = new JButton("OK");
		btnOk.setForeground(new Color(102, 51, 51));
		btnOk.setBackground(new Color(0, 204, 153));
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executequery();
				dispose();
			}
		});
		btnOk.setBounds(313, 28, 89, 23);
		contentPane.add(btnOk);
		
		JButton btnHigh = new JButton("HIGH SCORE");
		btnHigh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getscore();
			}
		});
		btnHigh.setForeground(new Color(102, 51, 51));
		btnHigh.setBackground(new Color(0, 204, 153));
		btnHigh.setBounds(123, 86, 104, 23);
		contentPane.add(btnHigh);
		
		JLabel lblName = new JLabel("  NAME");
		lblName.setBounds(37, 117, 44, 14);
		contentPane.add(lblName);
		
		JLabel lblScore = new JLabel("  SCORE");
		lblScore.setBounds(240, 117, 63, 14);
		contentPane.add(lblScore);
		
		 textArea = new JTextArea();
		textArea.setBounds(37, 142, 274, 109);
		contentPane.add(textArea);
		
		JButton btnCreatedb = new JButton("CREATEDB");
		btnCreatedb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// create database and table
				createdb();
				createtab();
			}
		});
		btnCreatedb.setForeground(new Color(102, 51, 51));
		btnCreatedb.setBackground(new Color(0, 204, 153));
		btnCreatedb.setBounds(324, 183, 104, 23);
		contentPane.add(btnCreatedb);
	}
	
	

	  public void executequery(){
	/*
	* sql connect
	 */
	  put=textField.getText();
	 }
	  // create database
	  public void createdb(){
      // JDBC driver name and database URL
		  final String JDBC_DRIVER1 = "com.mysql.jdbc.Driver";  
		  final String DB_URL1 = "jdbc:mysql://localhost:3307/";

		 //  Database credentials
		  final String USER1 = "root";
		  final String PASS1 = "root";
		 Connection conn = null;
		 Statement stmt = null;
		 try{
		    //STEP 2: Register JDBC driver
		    Class.forName(JDBC_DRIVER1);

		    //STEP 3: Open a connection
		    System.out.println("Connecting to database...");
		    conn = DriverManager.getConnection(DB_URL1, USER1, PASS1);

		    //STEP 4: Execute a query
		    System.out.println("Creating database...");
		    stmt = conn.createStatement();
		    
		    String sql = "CREATE DATABASE GAME";
		    stmt.executeUpdate(sql);
		    System.out.println("Database created successfully...");
		 }catch(SQLException se){
		    //Handle errors for JDBC
		    se.printStackTrace();
		 }catch(Exception e){
		    //Handle errors for Class.forName
		    e.printStackTrace();
		 }finally{
		    //finally block used to close resources
		    try{
		       if(stmt!=null)
		          stmt.close();
		    }catch(SQLException se2){
		    }// nothing we can do
		    try{
		       if(conn!=null)
		          conn.close();
		    }catch(SQLException se){
		       se.printStackTrace();
		    }//end finally try
		 }//end try
		}
	  
	  // create table
	  public void createtab(){
		  Connection conn = null;
		  Statement stmt = null;
		  try{
		     //STEP 2: Register JDBC driver
		     Class.forName(JDBC_DRIVER);

		     //STEP 3: Open a connection
		     System.out.println("Connecting to a selected database...");
		     conn = DriverManager.getConnection(DB_URL, USER, PASS);
		     System.out.println("Connected database successfully...");
		     
		     //STEP 4: Execute a query
		     System.out.println("Creating table in given database...");
		     stmt = conn.createStatement();
		     
		     String sql = "CREATE TABLE GAMESCORE(name VARCHAR(100),score INTEGER)"; 

		     stmt.executeUpdate(sql);
		     System.out.println("Created table in given database...");
		  }catch(SQLException se){
		     //Handle errors for JDBC
		     se.printStackTrace();
		  }catch(Exception e){
		     //Handle errors for Class.forName
		     e.printStackTrace();
		  }finally{
		     //finally block used to close resources
		     try{
		        if(stmt!=null)
		           conn.close();
		     }catch(SQLException se){
		     }// do nothing
		     try{
		        if(conn!=null)
		           conn.close();
		     }catch(SQLException se){
		        se.printStackTrace();
		     }//end finally try
		  }//end try
		 
		  
	  }
	  public void getscore(){
		  
		  // sql coding
		  
		  Connection conn = null;
		  Statement stmt = null;
		  ResultSet rs=null;
		  try{
		     //STEP 2: Register JDBC driver
		     Class.forName(JDBC_DRIVER);

		     //STEP 3: Open a connection
		     System.out.println("Connecting to a selected database...");
		     conn = DriverManager.getConnection(DB_URL, USER, PASS);
		     System.out.println("Connected database successfully...");
		     
		     //STEP 4: Execute a query
		     System.out.println("retrieving records");
		     stmt = conn.createStatement();
		 	rs=stmt.executeQuery("select * from gamescore");
			while(rs.next())
			{
				
			     textArea.append(rs.getString("name")+"                             "
			     		+ "                                   "+rs.getString("score")+"\n");
			
				//step 5
				
			}
		
		  }catch(SQLException se){
		     //Handle errors for JDBC
		     se.printStackTrace();
		  }catch(Exception e){
		     //Handle errors for Class.forName
		     e.printStackTrace();
		  }
		  finally{
			    //finally block used to close resources
			    try{
			       if(stmt!=null)
			          conn.close();
			    }catch(SQLException se){
			    }// do nothing
			    try{
			       if(conn!=null)
			          conn.close();
			    }catch(SQLException se){
			       se.printStackTrace();
			    }//end finally try
			 }//end try
			 

	  }
}
