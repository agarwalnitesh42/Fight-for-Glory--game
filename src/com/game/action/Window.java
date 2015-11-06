package com.game.action;


import javax.swing.JFrame;



public class Window extends JFrame{
        

	public Window()
    {
        // Sets the title for this frame.
        this.setTitle("FIGHT FOR GLORY");
        
        // Sets size of the frame.
        if(true) // Full screen mode
        {
            // Disables decorations for this frame.
            this.setUndecorated(true);
            // Puts the frame to full screen.
            this.setExtendedState(this.MAXIMIZED_BOTH);
        }
        
        
        // Exit the application when user close frame.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
		
        this.setContentPane(new Framework());
      
        this.setVisible(true);
        
    
   
    }

}
