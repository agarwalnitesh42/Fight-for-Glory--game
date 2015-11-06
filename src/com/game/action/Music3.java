package com.game.action;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;
   
// To play sound using Clip, the process need to be alive.
// Hence, we use a Swing application.
public class Music3 extends JFrame {
   
	static Clip clip;
	
   // Constructor
   public  Music3() {
	
      try {
         // Open an audio input stream.
    	  URL url1 = this.getClass().getResource("/com/game/action/resources/sounds/militaryheli.wav");
         AudioInputStream audioIn = AudioSystem.getAudioInputStream(url1);
         // Get a sound clip resource.
          clip = AudioSystem.getClip();
         // Open audio clip and load samples from the audio input stream.
         clip.open(audioIn);
         clip.loop(20);
         
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }
   }
 
}