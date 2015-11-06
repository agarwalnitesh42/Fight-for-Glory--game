package com.game.action;





import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

/**
 * Framework that controls the game (Game.java) that created it, update it and draw it on the screen.
 */

public class Framework extends Canvas {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -2489490848859015744L;
	/**
     * Width of the frame.
     */
    public static int frameWidth;
    /**
     * Height of the frame.
     */
    public static int frameHeight;

    /**
     * Time of one second in nanoseconds.
     * 1 second = 1 000 000 000 nanoseconds
     */
    public static final long secInNanosec = 1000000000L;
    
    /**
     * Time of one millisecond in nanoseconds.
     * 1 millisecond = 1 000 000 nanoseconds
     */
    public static final long milisecInNanosec = 1000000L;
    
    /**
     * FPS - Frames per second
     * How many times per second the game should update?
     */
    private final int GAME_FPS = 60;
    /**
     * Pause between updates. It is in nanoseconds.
     */
    private final long GAME_UPDATE_PERIOD = secInNanosec / GAME_FPS;
    
 
    
    /**
     * Possible states of the game
     */
    public static enum GameState{STARTING, VISUALIZING, GAME_CONTENT_LOADING, MAIN_MENU, OPTIONS, PLAYING, GAMEOVER, DESTROYED}
    /**
     * Current state of the game
     */
    public static GameState gameState;
    
    /**
     * Elapsed game time in nanoseconds.
     */
    private long gameTime;
    // It is used for calculating elapsed time.
    private long lastTime;
    
    // The actual game
    private Game game;
    
    
    private Font font,font1,font2;
    
  
    
    // Images for menu.
    private BufferedImage actionMenuImg;
    private BufferedImage fightImg;
    private BufferedImage titleImg1;
    private BufferedImage titleImg2;
    private BufferedImage titleImg3;
   
    // Objects of moving images.
    private MovingBackground fight;
   

    private Hel1 hel1;
    private Hel2 hel2;

    
    public Framework ()
    {
        super();
        
        gameState = GameState.VISUALIZING;
        
        //We start game in new thread.
        Thread gameThread = new Thread() {
            @Override
            public void run(){
                GameLoop();
            }
        };
        gameThread.start();
    }
    
    
   /**
     * Set variables and objects.
     * This method is intended to set the variables and objects for this class, variables and objects for the actual game can be set in Game.java.
     */
    private void Initialize()
    {
    	font = new Font("moonspaced", Font.PLAIN, 25);
    	font1 = new Font("algerian", Font.HANGING_BASELINE, 50);
    	font2 = new Font("algerian", Font.HANGING_BASELINE, 70);
    	
    	
    	hel1 = new Hel1((int) (Framework.frameWidth*0.118), Framework.frameHeight / 4);
    	
        hel2 = new Hel2((int) (Framework.frameWidth*0.67), Framework.frameHeight / 4);
    	 
    	   // Moving images.
        fight = new MovingBackground();
       
    	 helsound();
    	      }
    
    /**
     * Load files (images).
     * This method is intended to load files for this class, files for the actual game can be loaded in Game.java.
     */
    private void LoadContent()
    {
        try 
        {
            URL actionMenuImgUrl = this.getClass().getResource("/com/game/action/resources/images/intialfront.jpg");
            actionMenuImg = ImageIO.read(actionMenuImgUrl);
          
            URL fightImgUrl = this.getClass().getResource("/com/game/action/resources/images/G4.png");
            fightImg = ImageIO.read(fightImgUrl);
       
            URL titleImg1Url = this.getClass().getResource("/com/game/action/resources/images/title1.png");
            titleImg1 = ImageIO.read(titleImg1Url);
       
            URL titleImg2Url = this.getClass().getResource("/com/game/action/resources/images/title2.png");
            titleImg2 = ImageIO.read(titleImg2Url);
            
            URL titleImg3Url = this.getClass().getResource("/com/game/action/resources/images/title3.png");
            titleImg3 = ImageIO.read(titleImg3Url);
       
            
         } 
        catch (IOException ex) 
        {
            Logger.getLogger(Framework.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Now that we have images we initialize moving images.
       fight.Initialize(fightImg, -1, 30);
       
      
    }
    
    
    /**
     * In specific intervals of time (GAME_UPDATE_PERIOD) the game/logic is updated and then the game is drawn on the screen.
     */
    private void GameLoop()
    {
        // This two variables are used in VISUALIZING state of the game. We used them to wait some time so that we get correct frame/window resolution.
        long visualizingTime = 0, lastVisualizingTime = System.nanoTime();
        
        // This variables are used for calculating the time that defines for how long we should put threat to sleep to meet the GAME_FPS.
        long beginTime, timeTaken, timeLeft;
        
        while(true)
        {
            beginTime = System.nanoTime();
            
            switch (gameState)
            {
                case PLAYING:
                    gameTime += System.nanoTime() - lastTime;
                    
                    game.UpdateGame(gameTime, mousePosition());
                    
                    lastTime = System.nanoTime();
                break;
                case GAMEOVER:
                   //...
                break;
                case MAIN_MENU:
                    //...
                break;
                case OPTIONS:
                    //...
                break;
                case GAME_CONTENT_LOADING:
                    //...
                break;
                case STARTING:
                    // Sets variables and objects.
                    Initialize();
                    // Load files - images, sounds, ...
                    LoadContent();

                    // When all things that are called above finished, we change game status to main menu.
                    gameState = GameState.MAIN_MENU;
                break;
                case VISUALIZING:
                    if(this.getWidth() > 1 && visualizingTime > secInNanosec)
                    {
                        frameWidth = this.getWidth();
                        frameHeight = this.getHeight();

                        // When we get size of frame we change status.
                        gameState = GameState.STARTING;
                    }
                    else
                    {
                        visualizingTime += System.nanoTime() - lastVisualizingTime;
                        lastVisualizingTime = System.nanoTime();
                    }
                break;
            }
            
            // Repaint the screen.
            repaint();
            
            // Here we calculate the time that defines for how long we should put thread to sleep to meet the GAME_FPS.
            timeTaken = System.nanoTime() - beginTime;
            timeLeft = (GAME_UPDATE_PERIOD - timeTaken) / milisecInNanosec; // In milliseconds
            // If the time is less than 10 milliseconds, then we will put thread to sleep for 10 millisecond so that some other thread can do some work.
            if (timeLeft < 10) 
                timeLeft = 10; //set a minimum
            try {
                 //Provides the necessary delay and also yields control so that other thread can do work.
                 Thread.sleep(timeLeft);
            } catch (InterruptedException ex) { }
        }
    }
    
    /**
     * Draw the game to the screen. It is called through repaint() method in GameLoop() method.
     */
    @Override
    public void Draw(Graphics2D g2d)
    {
        switch (gameState)
        {
            case PLAYING:
                game.Draw(g2d, mousePosition(), gameTime);
            break;
            case GAMEOVER:
            	
                g2d.setFont(font);
           	    g2d.drawImage(actionMenuImg, 0, 0, frameWidth, frameHeight, null);
           	    hel1.Draw(g2d);
        	    hel2.Draw(g2d);
        	    g2d.setColor(Color.white);
                g2d.drawString("Press ENTER to restart or ESC to exit.", frameWidth/2 - 113, frameHeight/4 + 30);
                game.DrawStatistic(g2d, gameTime);
                g2d.setFont(font1);
                g2d.drawString("GAME OVER", frameWidth/2 - 90, frameHeight/4);
            
                break;
            case MAIN_MENU:
            	 g2d.drawImage(actionMenuImg, 0, 0, frameWidth, frameHeight, null);
            	 g2d.setFont(font2);
            	 hel1.Draw(g2d);
            	 hel2.Draw(g2d);
            	
            	  // Moving images.
                 fight.Draw(g2d);
                
            	 g2d.setColor(Color.YELLOW);
            	 g2d.drawImage(titleImg1,(int) (frameWidth*0.411), (int) (frameHeight*0.144),null);
            	 g2d.drawImage(titleImg2,(int) (frameWidth*0.508), (int) (frameHeight*0.287),null);
            	 g2d.drawImage(titleImg3,(int) (frameWidth*0.567), (int) (frameHeight*0.417),null);
            	  g2d.setFont(font);
                 g2d.setColor(Color.WHITE);
                g2d.drawString("Press any key to start the game or ESC to exit.",(int)(frameWidth*0.15),(int)(frameHeight*0.70));
            	
            
                break;
            case OPTIONS:
                //...
            break;
            case GAME_CONTENT_LOADING:
            	g2d.setColor(Color.white);
                g2d.setFont(font1);
             
                g2d.drawString("GAME is LOADING", frameWidth / 2 - 140, frameHeight / 2);         
            	break;
        }
    }
    
    private void helsound(){
    	new Music3();
    }
    /**
     * Starts new game.
     */
    private void newGame()
    {
        // We set gameTime to zero and lastTime to current time for later calculations.
        gameTime = 0;
        lastTime = System.nanoTime();
        
        game = new Game();
    }
    
    /**
     *  Restart game - reset game time and call RestartGame() method of game object so that reset some variables.
     */
    private void restartGame()
    {
        // We set gameTime to zero and lastTime to current time for later calculations.
        gameTime = 0;
        lastTime = System.nanoTime();
        
        game.RestartGame();
        
        // We change game status so that the game can start.
        gameState = GameState.PLAYING;
    }
    
    
    /**
     * Returns the position of the mouse pointer in game frame/window.
     * If mouse position is null than this method return 0,0 coordinate.
     * 
     * @return Point of mouse coordinates.
     */
    private Point mousePosition()
    {
        try
        {
            Point mp = this.getMousePosition();
            
            if(mp != null)
                return this.getMousePosition();
            else
                return new Point(0, 0);
        }
        catch (Exception e)
        {
            return new Point(0, 0);
        }
    }
    
    
    /**
     * This method is called when keyboard key is released.
     * 
     * @param e KeyEvent
     */
    @Override
    public void keyReleasedFramework(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
           System.exit(0);
        
        switch(gameState)
        {
            case GAMEOVER:
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                    restartGame();
            break;
            case MAIN_MENU:
                newGame();
            break;
        }
    }
    
    /**
     * This method is called when mouse button is clicked.
     * 
     * @param e MouseEvent
     */
    @Override
    public void mouseClicked(MouseEvent e)
    {
        
    }
    
   
}
