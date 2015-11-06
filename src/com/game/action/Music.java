package com.game.action;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;
   
// To play sound using Clip, the process need to be alive.
// Hence, we use a Swing application.
public class Music extends JFrame {
   
   // Constructor
   public Music() {
	
      try {
         // Open an audio input stream.
    	  URL url1 = this.getClass().getResource("/com/game/action/resources/sounds/explode.wav");
         AudioInputStream audioIn = AudioSystem.getAudioInputStream(url1);
         // Get a sound clip resource.
         Clip clip = AudioSystem.getClip();
         // Open audio clip and load samples from the audio input stream.
         clip.open(audioIn);
         clip.start();
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }
   }
   
}