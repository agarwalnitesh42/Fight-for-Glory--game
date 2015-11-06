package com.game.action;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;




/**
 * Actual game.
 */

public class Game {
	
	
	// JDBC driver name and database URL
	  static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	  static final String DB_URL = "jdbc:mysql://localhost:3307/game";

	  //  Database credentials
	  static final String USER = "root";
	  static final String PASS = "root";
	 
    
    // Use this to generate a random number.
    private Random random;
    
    // We will use this for setting mouse position.
    private Robot robot;
    
    // Player that is managed by user.
    private Player player;
    
    // EnemyHelicopters.
    private ArrayList<EnemyHelicopter> enemyHelicopterList = new ArrayList<EnemyHelicopter>();
    
    // enemy tank
    private ArrayList<Enemytank> enemies;
    
    // enemy player
    private ArrayList<Enemy> terrorist;
    
    // Explosions
    private ArrayList<Animation> explosionsList;
    private BufferedImage explosionAnimImg;
    
    // List of all the machine gun bullets.
    private ArrayList<Bullet> bulletsList;
    
    // List of all the rockets.
    private ArrayList<Rocket> rocketsList;
    // List of all the rockets smoke.
    private ArrayList<RocketSmoke> rocketSmokeList;
    
    // Image for the sky color.
    private BufferedImage skyColorImg;
    
    // Images for white spot on the sky.
    private BufferedImage cloudLayer1Img;
    private BufferedImage cloudLayer2Img;
    // Images for mountains and ground.
    private BufferedImage mountainsImg;
    private BufferedImage groundImg;
    
    // Objects of moving images.
    private MovingBackground cloudLayer1Moving;
    private MovingBackground cloudLayer2Moving;
    private MovingBackground mountainsMoving;
    private MovingBackground groundMoving;
    
    // Image of mouse cursor.
    private BufferedImage mouseCursorImg;
    // image of tank
    private BufferedImage enemyImg;
  
    // image of terrorist
    private BufferedImage enemypImg;
    
 // images of fire
    public BufferedImage bulletFireImg;
  
    // Font that we will use to write statistic to the screen.
    private Font font;
    
    // Statistics (destroyed enemies, run away enemies)
    private int runAwayEnemies;
    private int destroyedEnemies;
    private int killedtanks;
    private int killedenemy;
    private int score;
    

    public Game()
    {
        Framework.gameState = Framework.GameState.GAME_CONTENT_LOADING;
        
        Music3.clip.stop();
        
        Thread threadForInitGame = new Thread() {
            @Override
            public void run(){
                // Sets variables and objects for the game.
                Initialize();
                // Load game files (images, sounds, ...)
                LoadContent();
                
                Framework.gameState = Framework.GameState.PLAYING;
                
            
            }
        };
        threadForInitGame.start();
    }
    
    
   /**
     * Set variables and objects for the game.
     */
    private void Initialize()
    {
        random = new Random();
        
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        player = new Player(Framework.frameWidth / 80, (int) (Framework.frameHeight*0.6));
        
        enemyHelicopterList = new ArrayList<EnemyHelicopter>();
        
        enemies = new ArrayList<Enemytank>();
       
        terrorist = new ArrayList<Enemy>();
          
        explosionsList = new ArrayList<Animation>();
        
        bulletsList = new ArrayList<Bullet>();
        
        rocketsList = new ArrayList<Rocket>();
        rocketSmokeList = new ArrayList<RocketSmoke>();
        
        // Moving images.
        cloudLayer1Moving = new MovingBackground();
        cloudLayer2Moving = new MovingBackground();
        mountainsMoving = new MovingBackground();
        groundMoving = new MovingBackground();
        
        font = new Font("monospaced", Font.BOLD, 18);
        
        runAwayEnemies = 0;
        destroyedEnemies = 0;
        killedtanks = 0;
        killedenemy=0;
        score=0;  
        
    }
    
    /**
     * Load game files (images).
     */
    private void LoadContent()
    {
        try 
        {
            // Images of environment
            URL skyColorImgUrl = this.getClass().getResource("/com/game/action/resources/images/sky_color.jpg");
            skyColorImg = ImageIO.read(skyColorImgUrl);
            URL cloudLayer1ImgUrl = this.getClass().getResource("/com/game/action/resources/images/cloud_layer_1.png");
            cloudLayer1Img = ImageIO.read(cloudLayer1ImgUrl);
            URL cloudLayer2ImgUrl = this.getClass().getResource("/com/game/action/resources/images/cloud_layer_2.png");
            cloudLayer2Img = ImageIO.read(cloudLayer2ImgUrl);
            URL mountainsImgUrl = this.getClass().getResource("/com/game/action/resources/images/mountains.png");
            mountainsImg = ImageIO.read(mountainsImgUrl);
            URL groundImgUrl = this.getClass().getResource("/com/game/action/resources/images/ground.png");
            groundImg = ImageIO.read(groundImgUrl);
            
            // Load images for EnemyHelicopter
            URL helicopterBodyImgUrl = this.getClass().getResource("/com/game/action/resources/images/2_helicopter_body.png");
            EnemyHelicopter.helicopterBodyImg = ImageIO.read(helicopterBodyImgUrl);
            URL helicopterFrontPropellerAnimImgUrl = this.getClass().getResource("/com/game/action/resources/images/2_front_propeller_anim.png");
            EnemyHelicopter.helicopterFrontPropellerAnimImg = ImageIO.read(helicopterFrontPropellerAnimImgUrl);
            URL helicopterRearPropellerAnimImgUrl = this.getClass().getResource("/com/game/action/resources/images/2_rear_propeller_anim.png");
            EnemyHelicopter.helicopterRearPropellerAnimImg = ImageIO.read(helicopterRearPropellerAnimImgUrl);
            
            // load images for tank
            
            URL enemyImgUrl = this.getClass().getResource("/com/game/action/resources/images/tank.gif");
            enemyImg = ImageIO.read(enemyImgUrl);
           
           // load images for terrorist
            
            URL enemypImgUrl = this.getClass().getResource("/com/game/action/resources/images/enemy.gif");
            enemypImg = ImageIO.read(enemypImgUrl);
           
            
            // Images of rocket and its smoke.
            URL rocketImgUrl = this.getClass().getResource("/com/game/action/resources/images/rocket.png");
            Rocket.rocketImg = ImageIO.read(rocketImgUrl);
            URL rocketSmokeImgUrl = this.getClass().getResource("/com/game/action/resources/images/rocket_smoke.png");
            RocketSmoke.smokeImg = ImageIO.read(rocketSmokeImgUrl);
            
            // Images of explosion animation.
            URL explosionAnimImgUrl = this.getClass().getResource("/com/game/action/resources/images/explosion_anim.png");
            explosionAnimImg = ImageIO.read(explosionAnimImgUrl);
            
            // Image of mouse cursor.
            URL mouseCursorImgUrl = this.getClass().getResource("/com/game/action/resources/images/mouse_cursor.png");
            mouseCursorImg = ImageIO.read(mouseCursorImgUrl);
            
            // player machine gun bullet.
            URL bulletImgUrl = this.getClass().getResource("/com/game/action/resources/images/bullet.png");
            Bullet.bulletImg = ImageIO.read(bulletImgUrl);
           // images of fire
           
            URL bulletFireImgUrl = this.getClass().getResource("/com/game/action/resources/images/fire.png");
            bulletFireImg = ImageIO.read(bulletFireImgUrl);
       
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Now that we have images we initialize moving images.
        cloudLayer1Moving.Initialize(cloudLayer1Img, -3, 0);
        cloudLayer2Moving.Initialize(cloudLayer2Img, -5, 0);
        mountainsMoving.Initialize(mountainsImg, -3, Framework.frameHeight - groundImg.getHeight() - mountainsImg.getHeight() + 40);
        groundMoving.Initialize(groundImg, -1, Framework.frameHeight - groundImg.getHeight());
    }
    
    
    /**
     * Restart game - reset some variables.
     */
    public void RestartGame()
    {
        player.Reset(Framework.frameWidth / 80, (int) (Framework.frameHeight*0.6));
        
        EnemyHelicopter.restartEnemy();
       
      
        Enemytank.restartEnemyt();
      
        Enemy.restartEnemyp();
        
        Bullet.timeOfLastCreatedBullet = 0;
        Rocket.timeOfLastCreatedRocket = 0;
        
        // Empty all the lists.
        enemies.clear();
        terrorist.clear();
        enemyHelicopterList.clear();
        bulletsList.clear();
        rocketsList.clear();
        rocketSmokeList.clear();
        explosionsList.clear();
        
        // Statistics
        runAwayEnemies = 0;
        destroyedEnemies = 0;
        killedtanks = 0;
        killedenemy=0;
        score=0;     
   
    
    }
    
    
    /**
     * Update game logic.
     * 
     * @param gameTime The elapsed game time in nanoseconds.
     * @param mousePosition current mouse position.
     */
    public void UpdateGame(long gameTime, Point mousePosition)
    {
        /* Player */
        // When player is destroyed and all explosions are finished showing we change game status.
        if( !isPlayerAlive() && explosionsList.isEmpty() ){
            Framework.gameState = Framework.GameState.GAMEOVER;
            return; // If player is destroyed, we don't need to do thing below.
        }
        // When a player is out of rockets and machine gun bullets, and all lists 
        // of bullets, rockets and explosions are empty(end showing) we finish the game.
        if(player.numberOfAmmo <= 0 && 
           player.numberOfRockets <= 0 && 
           bulletsList.isEmpty() && 
           rocketsList.isEmpty() && 
           explosionsList.isEmpty())
        {
            Framework.gameState = Framework.GameState.GAMEOVER;
            return;
        }
        // If player is alive we update him.
        if(isPlayerAlive()){
            isPlayerShooting(gameTime, mousePosition);
            didPlayerFiredRocket(gameTime,mousePosition);
            player.isMoving();
            player.Update();
        }
        
        

        
        
        /* Mouse */
        limitMousePosition(mousePosition);
        
        /* Bullets */
        updateBullets();
        
        /* Rockets */
        updateRockets(gameTime); // It also checks for collisions (if any of the rockets hit any of the enemy).
        updateRocketSmoke(gameTime);
        
        /* EnemyHelicopter */
        createEnemyHelicopter(gameTime);
        updateEnemies();
        
        /*Enemy tank   */
        createEnemyTank(gameTime);
        updateEnemies1();
               
        /* terrorist   */
        createEnemy(gameTime);
        updateEnemies2();
        
        /* Explosions */
        updateExplosions();
    }
    
   



	/**
     * Draw the game to the screen.
     * 
     * @param g2d Graphics2D
     * @param mousePosition current mouse position.
     */
    public void Draw(Graphics2D g2d, Point mousePosition, long gameTime)
    {
        // Image for background sky color.
        g2d.drawImage(skyColorImg, 0, 0, Framework.frameWidth, Framework.frameHeight, null);
        
        // Moving images.
        mountainsMoving.Draw(g2d);
        groundMoving.Draw(g2d);
        cloudLayer2Moving.Draw(g2d);
        
        if(isPlayerAlive())
            player.Draw(g2d);
        
        // Draws all the enemyHelicopter.
        for(int i = 0; i < enemyHelicopterList.size(); i++)
        {
            enemyHelicopterList.get(i).Draw(g2d);
        }
        
     // Here we draw all the tanks.
        for(int i = 0; i < enemies.size(); i++)
        {
            enemies.get(i).Draw(g2d);
        }
        
        // Here we draw all the terrorist.
        for(int i = 0; i < terrorist.size(); i++)
        {
            terrorist.get(i).Draw(g2d);
        }
           
        // Draws all the bullets. 
        for(int i = 0; i < bulletsList.size(); i++)
        {
            bulletsList.get(i).Draw(g2d);
        }
        
        // Draws all the rockets. 
        for(int i = 0; i < rocketsList.size(); i++)
        {
            rocketsList.get(i).Draw(g2d);
        }
        // Draws smoke of all the rockets.
        for(int i = 0; i < rocketSmokeList.size(); i++)
        {
            rocketSmokeList.get(i).Draw(g2d);
        }
        
        // Draw all explosions.
        for(int i = 0; i < explosionsList.size(); i++)
        {
            explosionsList.get(i).Draw(g2d);
            
        }
        
        // Draw statistics
        g2d.setFont(font);
        g2d.setColor(Color.black);
        
        g2d.drawString(formatTime(gameTime), Framework.frameWidth/2 - 45, 21);
        g2d.drawString("HELICOPTER DESTROYED: " + destroyedEnemies, 10, 21);
        g2d.drawString("HELICOPTER RUNAWAY: "   + runAwayEnemies,   10, 41);
        g2d.drawString("ROCKETS: "   + player.numberOfRockets, 10, 61);
        g2d.drawString("AMMO: "      + player.numberOfAmmo, 10, 81);
        g2d.drawString("TANK DESTROYED: " + killedtanks, 10, 101);
        g2d.drawString("ENEMY KILLED: " + killedenemy, 10, 121);
        g2d.drawString("SCORE: " + score, 10, 141);
        
        // Moving images. We draw this cloud in front of the EnemyHelicopter.
        cloudLayer1Moving.Draw(g2d);
        
        // Mouse cursor
        if(isPlayerAlive())
            drawRotatedMouseCursor(g2d, mousePosition);
        
        
     // When 3 EnemyHelicopters runaway, the game ends.
        if(runAwayEnemies >= 20)
        	Framework.gameState = Framework.GameState.GAMEOVER;
        

    }
    
    /**
     * Draws some game statistics when game is over.
     * 
     * @param g2d Graphics2D
     * @param gameTime Elapsed game time.
     */
    public void DrawStatistic(Graphics2D g2d, long gameTime){
       
    	execute();
    	g2d.setColor(Color.WHITE);
    	g2d.drawString("Time: " + formatTime(gameTime),                Framework.frameWidth/2 - 50, Framework.frameHeight/3 + 80);
        g2d.drawString("Rockets left: "      + player.numberOfRockets, Framework.frameWidth/2 - 55, Framework.frameHeight/3 + 105);
        g2d.drawString("Ammo left: "         + player.numberOfAmmo,    Framework.frameWidth/2 - 55, Framework.frameHeight/3 + 125);
        g2d.drawString("Destroyed Helicopters: " + destroyedEnemies,       Framework.frameWidth/2 - 65, Framework.frameHeight/3 + 150);
        g2d.drawString("Destroyed Tanks: " + killedtanks,       Framework.frameWidth/2 - 65, Framework.frameHeight/3 + 170);
        g2d.drawString("Enemy Killed: " + killedenemy,       Framework.frameWidth/2 - 65, Framework.frameHeight/3 + 190);
        g2d.drawString("Runaway Helicopters: "   + runAwayEnemies,         Framework.frameWidth/2 - 65, Framework.frameHeight/3 + 210);
        g2d.drawString("Score: "   + score,         Framework.frameWidth/2 - 65, Framework.frameHeight/3 + 230);
        g2d.drawString("Statistics: ",                                 Framework.frameWidth/2 - 75, Framework.frameHeight/3 + 60);
   
       
    }
    /**
     * sql call
     */
    public void execute(){
    	
    	 Connection conn = null;
		  Statement stmt = null;
		  try{
		     //STEP 2: Register JDBC driver
		     Class.forName("com.mysql.jdbc.Driver");

		     //STEP 3: Open a connection
		     System.out.println("Connecting to a selected database...");
		     conn = DriverManager.getConnection(DB_URL, USER, PASS);
		     System.out.println("Connected database successfully...");
		     
		     //STEP 4: Execute a query
		     System.out.println("Inserting records into the table...");
		     stmt = conn.createStatement();
		     String sql = "INSERT INTO gamescore VALUES ('"+Profile.put+"',"+score+")";
		     stmt.executeUpdate(sql);
		     System.out.println("Inserted records into the table...");

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
  
    
    /**
     * Draws rotated mouse cursor.
     * It rotates the cursor image on the basis of the player machine gun.
     * 
     * @param g2d Graphics2D
     * @param mousePosition Position of the mouse.
     */
    private void drawRotatedMouseCursor(Graphics2D g2d, Point mousePosition)
    {
        double RIGHT_ANGLE_RADIANS = Math.PI / 2;
        
        // Position of the player  machine gun.
        int pivotX = player.machineGunXcoordinate;
        int pivotY = player.machineGunYcoordinate;
        int a = pivotX - mousePosition.x;
        int b = pivotY - mousePosition.y;
        double ab = (double)a / (double)b;
        double alfaAngleRadians = Math.atan(ab);

        if(mousePosition.y < pivotY) // Above the player.
            alfaAngleRadians = RIGHT_ANGLE_RADIANS - alfaAngleRadians - RIGHT_ANGLE_RADIANS*2;
        else if (mousePosition.y > pivotY) // Under the player.
            alfaAngleRadians = RIGHT_ANGLE_RADIANS - alfaAngleRadians;
        else
            alfaAngleRadians = 0;

        AffineTransform origXform = g2d.getTransform();
        AffineTransform newXform = (AffineTransform)(origXform.clone());

        newXform.rotate(alfaAngleRadians, mousePosition.x, mousePosition.y);
        g2d.setTransform(newXform);
        
        g2d.drawImage(mouseCursorImg, mousePosition.x, mousePosition.y - mouseCursorImg.getHeight()/2, null); // We subtract half of the cursor image so that will be drawn in center of the y mouse coordinate.
        

        if(Canvas.mouseButtonState(MouseEvent.BUTTON1))
        { g2d.drawImage(bulletFireImg, mousePosition.x, mousePosition.y-18, null);
        
        new Music2();
        }
        
        if(Canvas.mouseButtonState(MouseEvent.BUTTON3))
        { 
        new Music1();
        }
        
        g2d.setTransform(origXform);
    }
    
    /**
     * Format given time into 00:00 format.
     * 
     * @param time Time that is in nanoseconds.
     * @return Time in 00:00 format.
     */
    private static String formatTime(long time){
            // Given time in seconds.
            int sec = (int)(time / Framework.milisecInNanosec / 1000);

            // Given time in minutes and seconds.
            int min = sec / 60;
            sec = sec - (min * 60);

            String minString, secString;

            if(min <= 9)
                minString = "0" + Integer.toString(min);
            else
                minString = "" + Integer.toString(min);

            if(sec <= 9)
                secString = "0" + Integer.toString(sec);
            else
                secString = "" + Integer.toString(sec);

            return minString + ":" + secString;
    }
    
    
    
    
    
    /*
     * 
     * Methods for updating the game. 
     * 
     */
    
     
    /**
     * Check if player is alive. If not, set game over status.
     * 
     * @return True if player is alive, false otherwise.
     */
    private boolean isPlayerAlive()
    {
        if(player.health <= 0)
            return false;
        
        return true;
    }
    
    /**
     * Checks if the player is shooting with the machine gun and creates bullets if he shooting.
     * 
     * @param gameTime Game time.
     */
    private void isPlayerShooting(long gameTime, Point mousePosition)
    {
    	
    	
        if(player.isShooting(gameTime))
        {
        	
            Bullet.timeOfLastCreatedBullet = gameTime;
            player.numberOfAmmo--;
       
            Bullet b = new Bullet(player.machineGunXcoordinate, player.machineGunYcoordinate, mousePosition);
            bulletsList.add(b);

          
        }
    }
    
    /**
     * Checks if the player is fired the rocket and creates it if he did.
     * It also checks if player can fire the rocket.
     * 
     * @param gameTime Game time.
     */
    private void didPlayerFiredRocket(long gameTime, Point mousePosition)
    {
        if(player.isFiredRocket(gameTime))
        {
            Rocket.timeOfLastCreatedRocket = gameTime;
            player.numberOfRockets--;
           
   
            Rocket r = new Rocket(player.rocketHolderXcoordinate, player.rocketHolderYcoordinate, mousePosition);
           rocketsList.add(r);
   
        }
    }
    
    /**
     * Creates a new enemy if it's time.
     * 
     * @param gameTime Game time.
     */
    private void createEnemyHelicopter(long gameTime)
    {
        if(gameTime - EnemyHelicopter.timeOfLastCreatedEnemy >= EnemyHelicopter.timeBetweenNewEnemies)
        {
            EnemyHelicopter eh = new EnemyHelicopter();
            int xCoordinate = Framework.frameWidth;
            int yCoordinate = random.nextInt(Framework.frameHeight-500);
            
            eh.Initialize(xCoordinate, yCoordinate);
            // Add created enemy to the list of enemies.
            enemyHelicopterList.add(eh);
            
            // Speed up enemy speed and appearance.
            EnemyHelicopter.speedUp();
            
            // Sets new time for last created enemy.
            EnemyHelicopter.timeOfLastCreatedEnemy = gameTime;
        }
    }
    
    
	private void createEnemyTank(long gameTime) {
		 // Creates a new tank, if it's the time, and add it to the array list.
        if(gameTime - Enemytank.lastenemyTime >= Enemytank.timeBetweenenemy)
        {
            // Here we create new tank and add it to the array list.
            enemies.add(new Enemytank(Enemytank.enemyLines[Enemytank.nextenemyLines][0] + random.nextInt(200), Enemytank.enemyLines[Enemytank.nextenemyLines][1], Enemytank.enemyLines[Enemytank.nextenemyLines][2], enemyImg));
            
            // Here we increase nextenemyLines so that next tank will be created in next line.
            Enemytank.nextenemyLines++;
            if(Enemytank.nextenemyLines >= Enemytank.enemyLines.length)
                Enemytank.nextenemyLines = 0;
            
            Enemytank.lastenemyTime = gameTime;
        }
        
		
	}

	private void createEnemy(long gameTime) {
		 // Creates a new terrorist, if it's the time, and add it to the array list.
       if(gameTime - Enemy.lastenemypTime >= Enemy.timeBetweenenemyp)
       {
           // Here we create new terrorist and add it to the array list.
           terrorist.add(new Enemy(Enemy.enemypLines[Enemy.nextenemypLines][0] , Enemy.enemypLines[Enemy.nextenemypLines][1], Enemy.enemypLines[Enemy.nextenemypLines][2], enemypImg));
           
           // Here we increase nextenemyLines so that next terrorist will be created in next line.
           Enemy.nextenemypLines++;
           if(Enemy.nextenemypLines >= Enemy.enemypLines.length)
               Enemy.nextenemypLines = 0;
           
           Enemy.lastenemypTime = gameTime;
       }
       
		
	}

	
    /**
     * Updates all enemies.
     * Move the EnemyHelicopter and checks if he left the screen.
     * Updates EnemyHelicopter animations.
     * Checks if enemy was destroyed.
     * Checks if any enemy collision with player.
     */
    private void updateEnemies()
    {
        for(int i = 0; i < enemyHelicopterList.size(); i++)
        {
            EnemyHelicopter eh = enemyHelicopterList.get(i);
            
            eh.Update();
            
            // Check health.
            if(eh.health <= 0){
                // Add explosion of EnmeyHelicopter.
                Animation expAnim = new Animation(explosionAnimImg, 134, 134, 12, 45, false, eh.xCoordinate, eh.yCoordinate - explosionAnimImg.getHeight()/3, 0); // Substring 1/3 explosion image height (explosionAnimImg.getHeight()/3) so that explosion is drawn more at the center of the EnemyHelicopter.
                explosionsList.add(expAnim);
                new Music();
              
                score=score+5;
                // Increase the destroyed enemies counter.
                destroyedEnemies++;
                
                // Remove EnemyHelicopter from the list.
                enemyHelicopterList.remove(i);
                
                // EnemyHelicopter was destroyed so we can move to next EnemyHelicopter.
                continue;
            }
            
            // If the current enemy is left the screen we remove him from the list and update the runAwayEnemies variable.
            if(eh.isLeftScreen())
            {
                enemyHelicopterList.remove(i);
                runAwayEnemies++;
            }
        }
    }
    // for tanks
    private void updateEnemies1() {
    	 // Update all of the tanks.
        for(int i = 0; i < enemies.size(); i++)
        {   
        	Enemytank ehh=enemies.get(i);
        	
            // Move the tank.
            ehh.Update();
            
            // Check health.
            if(ehh.healtht <= 0){
                // Add explosion of tank.
                Animation expAnim = new Animation(explosionAnimImg, 134, 134, 12, 45, false, ehh.x, ehh.y - explosionAnimImg.getHeight()/3, 0); // Substring 1/3 explosion image height (explosionAnimImg.getHeight()/3) so that explosion is drawn more at the center of the EnemyHelicopter.
                explosionsList.add(expAnim);
            
                new Music();
               score=score+3;
                // Increase the destroyed enemies counter.
                killedtanks++;
                
                // Remove tank from the list.
                enemies.remove(i);
                
                // tank was destroyed so we can move to next tank.
                continue;
            }

            
            
            // Is crashed with player?
         Rectangle playerRectangel = new Rectangle(player.xCoordinate, player.yCoordinate, player.playerBodyImg.getWidth(), player.playerBodyImg.getHeight());
         Rectangle enemyRectangel = new Rectangle(ehh.x, ehh.y,enemyImg.getWidth(), enemyImg.getHeight());
         if(playerRectangel.intersects(enemyRectangel)){
             score=score+3;
        	 player.health = 0;
             
             // Remove tank from the list.
             enemies.remove(i);
             
             // Add explosion of player .
             for(int exNum = 0; exNum < 3; exNum++){
                 Animation expAnim = new Animation(explosionAnimImg, 134, 134, 12, 45, false, player.xCoordinate, player.yCoordinate, exNum * 200 +random.nextInt(100));
                 explosionsList.add(expAnim);
             }
             // Add explosion of tank.
             for(int exNum = 0; exNum < 3; exNum++){
                 Animation expAnim = new Animation(explosionAnimImg, 134, 134, 12, 45, false, ehh.x, ehh.y, exNum * 200 +random.nextInt(100));
                 explosionsList.add(expAnim);
             }
             
             // Because player crashed with enemy the game will be over so we don't need to check other enemies.
             break;
         }
       
            
        }
		
    
	}


    // for terrorist
    private void updateEnemies2() {
    	 // Update all of the terrorist.
        for(int i = 0; i < terrorist.size(); i++)
        {   
        	Enemy ehhh=terrorist.get(i);
        	
            // Move the terrorist.
            ehhh.Update();
            
            // Check health.
            if(ehhh.healthp <= 0){
                // Add explosion of terrorist.
                Animation expAnim = new Animation(explosionAnimImg, 134, 134, 12, 45, false, ehhh.x, ehhh.y - explosionAnimImg.getHeight()/3, 0); // Substring 1/3 explosion image height (explosionAnimImg.getHeight()/3) so that explosion is drawn more at the center of the EnemyHelicopter.
                explosionsList.add(expAnim);
                new Music();
               score=score+2;
                // Increase the destroyed enemies counter.
                killedenemy++;
                
                // Remove terrorist from the list.
                terrorist.remove(i);
                
                // terrorist was destroyed so we can move to next terrorist.
                continue;
            }

            
            
            // Is crashed with player?
         Rectangle playerRectangel = new Rectangle(player.xCoordinate, player.yCoordinate, player.playerBodyImg.getWidth(), player.playerBodyImg.getHeight());
         Rectangle enemyRectangel = new Rectangle(ehhh.x, ehhh.y,enemypImg.getWidth(), enemypImg.getHeight());
         if(playerRectangel.intersects(enemyRectangel)){
             score=score+2;
        	 player.health = 0;
             
             // Remove terrorist from the list.
             terrorist.remove(i);
             
             // Add explosion of player .
             for(int exNum = 0; exNum < 3; exNum++){
                 Animation expAnim = new Animation(explosionAnimImg, 134, 134, 12, 45, false, player.xCoordinate, player.yCoordinate, exNum * 200 +random.nextInt(100));
                 explosionsList.add(expAnim);
             }
             // Add explosion of terrorist.
             for(int exNum = 0; exNum < 3; exNum++){
                 Animation expAnim = new Animation(explosionAnimImg, 134, 134, 12, 45, false, ehhh.x, ehhh.y, exNum * 200 +random.nextInt(100));
                 explosionsList.add(expAnim);
             }
             
             // Because player crashed with enemy the game will be over so we don't need to check other enemies.
             break;
         }
       
        }	
        
    }

   
    
    /**
     * Update bullets. 
     * It moves bullets.
     * Checks if the bullet is left the screen.
     * Checks if any bullets is hit any enemy.
     */
    private void updateBullets()
    {
        for(int i = 0; i < bulletsList.size(); i++)
        {
            Bullet bullet = bulletsList.get(i);
            
            // Move the bullet.
            bullet.Update();
            
            // Is left the screen?
            if(bullet.isItLeftScreen()){
                bulletsList.remove(i);
                // Bullet have left the screen so we removed it from the list and now we can continue to the next bullet.
                continue;
            }
            
            // Did hit any EnemyHelicopter?
            // Rectangle of the bullet image.
            Rectangle bulletRectangle = new Rectangle((int)bullet.xCoordinate, (int)bullet.yCoordinate, Bullet.bulletImg.getWidth(), Bullet.bulletImg.getHeight());
            // Go trough all enemies.
            for(int j = 0; j < enemyHelicopterList.size(); j++)
            {
                EnemyHelicopter eh = enemyHelicopterList.get(j);

                // Current enemy rectangle.
                Rectangle enemyRectangel = new Rectangle(eh.xCoordinate, eh.yCoordinate, EnemyHelicopter.helicopterBodyImg.getWidth(), EnemyHelicopter.helicopterBodyImg.getHeight());

                // Is current bullet over current enemy?
                if(bulletRectangle.intersects(enemyRectangel))
                {
                    // Bullet hit the enemy so we reduce his health.
                    eh.health -= Bullet.damagePower;
                    
                    // Bullet was also destroyed so we remove it.
                    bulletsList.remove(i);
                    
                    // That bullet hit enemy so we don't need to check other enemies.
                    break;
                }
            }
            
            
         // Did hit any enemy tank?
            // Go trough all enemies.
            for(int t = 0; t < enemies.size(); t++)
            {
                Enemytank ehh = enemies.get(t);

                // Current enemy rectangle.
                Rectangle enemyRectangell = new Rectangle(ehh.x, ehh.y, enemyImg.getWidth(), enemyImg.getHeight());

                // Is current bullet over current enemy?
                if(bulletRectangle.intersects(enemyRectangell))
                {
                    // Bullet hit the enemy so we reduce his health.
                    ehh.healtht -= Bullet.damagePower;
                    
                    // Bullet was also destroyed so we remove it.
                    bulletsList.remove(i);
                    
                    // That bullet hit enemy so we don't need to check other enemies.
                    break;
                }
            }

            
            
            // Did hit any enemy player?
               // Go trough all enemies.
               for(int p = 0; p < terrorist.size(); p++)
               {
                   Enemy ehhh = terrorist.get(p);

                   // Current enemy rectangle.
                   Rectangle enemyRectangelll = new Rectangle(ehhh.x, ehhh.y, enemypImg.getWidth(), enemypImg.getHeight());

                   // Is current bullet over current enemy?
                   if(bulletRectangle.intersects(enemyRectangelll))
                   {
                       // Bullet hit the enemy so we reduce his health.
                       ehhh.healthp -= Bullet.damagePower;
                       
                       // Bullet was also destroyed so we remove it.
                       bulletsList.remove(i);
                       
                       // That bullet hit enemy so we don't need to check other enemies.
                       break;
                   }
               }

            
        }
    }

    /**
     * Update rockets. 
     * It moves rocket and add smoke behind it.
     * Checks if the rocket is left the screen.
     * Checks if any rocket is hit any enemy.
     * 
     * @param gameTime Game time.
     */
    private void updateRockets(long gameTime)
    {
        for(int i = 0; i < rocketsList.size(); i++)
        {
        	 Rocket rocket = rocketsList.get(i);
            
            // Move the rocket.
            rocket.Update();
            
            // Is left the screen?
            if(rocket.isItLeftScreen()){
                rocketsList.remove(i);
                // rocket have left the screen so we removed it from the list and now we can continue to the next bullet.
                continue;
            }
            // Creates a rocket smoke.
            RocketSmoke rs = new RocketSmoke();
            int xCoordinate = rocket.xCoordinate - RocketSmoke.smokeImg.getWidth(); // Subtract the size of the rocket smoke image (rocketSmokeImg.getWidth()) so that smoke isn't drawn under/behind the image of rocket.
            int yCoordinte = rocket.yCoordinate - 5 + random.nextInt(6); // Subtract 5 so that smoke will be at the middle of the rocket on y coordinate. We rendomly add a number between 0 and 6 so that the smoke line isn't straight line.
            rs.Initialize(xCoordinate, yCoordinte, gameTime, rocket.currentSmokeLifeTime);
            rocketSmokeList.add(rs);
            
            // Because the rocket is fast we get empty space between smokes so we need to add more smoke. 
            // The higher is the speed of rockets, the bigger are empty spaces.
            int smokePositionX = 5 + random.nextInt(8); // We will draw this smoke a little bit ahead of the one we draw before.
            rs = new RocketSmoke();
            xCoordinate = rocket.xCoordinate - RocketSmoke.smokeImg.getWidth() + smokePositionX; // Here we need to add so that the smoke will not be on the same x coordinate as previous smoke. First we need to add 5 because we add random number from 0 to 8 and if the random number is 0 it would be on the same coordinate as smoke before.
            yCoordinte = rocket.yCoordinate - 5 + random.nextInt(6); // Subtract 5 so that smoke will be at the middle of the rocket on y coordinate. We rendomly add a number between 0 and 6 so that the smoke line isn't straight line.
            rs.Initialize(xCoordinate, yCoordinte, gameTime, rocket.currentSmokeLifeTime);
            rocketSmokeList.add(rs);
            
            // Increase the life time for the next piece of rocket smoke.
            rocket.currentSmokeLifeTime *= 1.02;
            
            // Checks if current rocket hit any enemy.
            if( checkIfRocketHitEnemy(rocket) ||checkIfRocketHitEnemyt( rocket)|| checkIfRocketHitEnemyp(rocket))
                // Rocket was also destroyed so we remove it.
                rocketsList.remove(i);
     
            
        }
    }
 
    /**
     * Checks if the given rocket is hit any of enemyHelicopters.
     * 
     * @param rocket Rocket to check.
     * @return True if it hit any of enemyHelicopters, false otherwise.
     */
    private boolean checkIfRocketHitEnemy(Rocket rocket)
    {
        boolean didItHitEnemy = false;
        
         // Current rocket rectangle. // I inserted number 2 instead of rocketImg.getWidth() because I wanted that rocket 
        // is over EnemyHelicopter when collision is detected, because actual image of EnemyHelicopter isn't a rectangle shape. (We could calculate/make 3 areas where helicopter can be hit and checks these areas, but this is easier.)
        Rectangle rocketRectangle = new Rectangle(rocket.xCoordinate, rocket.yCoordinate, 2, Rocket.rocketImg.getHeight());
        
        // Go trough all enemies.
        for(int j = 0; j < enemyHelicopterList.size(); j++)
        {
            EnemyHelicopter eh = enemyHelicopterList.get(j);

            // Current enemy rectangle.
            Rectangle enemyRectangel = new Rectangle(eh.xCoordinate, eh.yCoordinate, EnemyHelicopter.helicopterBodyImg.getWidth(), EnemyHelicopter.helicopterBodyImg.getHeight());

            // Is current rocket over current enemy?
            if(rocketRectangle.intersects(enemyRectangel))
            {
                didItHitEnemy = true;
                
                // Rocket hit the enemy so we reduce his health.
                eh.health -= Rocket.damagePower;
                
                // Rocket hit enemy so we don't need to check other enemies.
                break;
            }
        }
        
        return didItHitEnemy;
    }

    
    /**
     * Checks if the given rocket is hit any of enemy tank.
     * 
     * @param rocket Rocket to check.
     * @return True if it hit any of enemy tank, false otherwise.
     */
    private boolean checkIfRocketHitEnemyt(Rocket rocket)
    {
        boolean didItHitEnemyt = false;
        
         // Current rocket rectangle. // I inserted number 2 instead of rocketImg.getWidth() because I wanted that rocket 
        // is over Enemy tank when collision is detected, because actual image of Enemy tank isn't a rectangle shape. (We could calculate/make 3 areas where helicopter can be hit and checks these areas, but this is easier.)
        Rectangle rocketRectangle = new Rectangle(rocket.xCoordinate, rocket.yCoordinate, 2, Rocket.rocketImg.getHeight());
        
        // Go trough all enemy tank.
        for(int j = 0; j < enemies.size(); j++)
        {
            Enemytank ehh = enemies.get(j);

            // Current enemy rectangle.
            Rectangle enemyRectangel = new Rectangle(ehh.x, ehh.y, enemyImg.getWidth(), enemyImg.getHeight());

            // Is current rocket over current enemy?
            if(rocketRectangle.intersects(enemyRectangel))
            {
                didItHitEnemyt = true;
                
                // Rocket hit the enemy so we reduce his health.
                ehh.healtht -= Rocket.damagePower;
                
                // Rocket hit enemy so we don't need to check other enemies.
                break;
            }
        }
        
        return didItHitEnemyt;
    }
    


    /**
     * Checks if the given rocket is hit any of enemy 
     * 
     * @param rocket Rocket to check.
     * @return True if it hit any of enemy, false otherwise.
     */
    private boolean checkIfRocketHitEnemyp(Rocket rocket)
    {
        boolean didItHitEnemyp = false;
        
         // Current rocket rectangle. // I inserted number 2 instead of rocketImg.getWidth() because I wanted that rocket 
        // is over enemy when collision is detected, because actual image of Enemy isn't a rectangle shape. (We could calculate/make 3 areas where helicopter can be hit and checks these areas, but this is easier.)
        Rectangle rocketRectangle = new Rectangle(rocket.xCoordinate, rocket.yCoordinate, 2, Rocket.rocketImg.getHeight());
        
        // Go trough all terrorist.
        for(int j = 0; j < terrorist.size(); j++)
        {
            Enemy ehhh = terrorist.get(j);

            // Current enemy rectangle.
            Rectangle enemyRectangel = new Rectangle(ehhh.x, ehhh.y, enemypImg.getWidth(), enemypImg.getHeight());

            // Is current rocket over current enemy?
            if(rocketRectangle.intersects(enemyRectangel))
            {
                didItHitEnemyp = true;
                
                // Rocket hit the enemy so we reduce his health.
                ehhh.healthp -= Rocket.damagePower;
                
                // Rocket hit enemy so we don't need to check other enemies.
                break;
            }
        }
        
        return didItHitEnemyp;
    }
    


    
    /**
     * Updates smoke of all the rockets.
     * If the life time of the smoke is over then we delete it from list.
     * It also changes a transparency of a smoke image, so that smoke slowly disappear.
     * 
     * @param gameTime Game time.
     */
    private void updateRocketSmoke(long gameTime)
    {
        for(int i = 0; i < rocketSmokeList.size(); i++)
        {
            RocketSmoke rs = rocketSmokeList.get(i);
            
            // Is it time to remove the smoke.
            if(rs.didSmokeDisapper(gameTime))
                rocketSmokeList.remove(i);
            
            // Set new transparency of rocket smoke image.
            rs.updateTransparency(gameTime);
        }
    }
    
    /**
     * Updates all the animations of an explosion and remove the animation when is over.
     */
    private void updateExplosions()
    {
        for(int i = 0; i < explosionsList.size(); i++)
        {
            // If the animation is over we remove it from the list.
            if(!explosionsList.get(i).active)
                explosionsList.remove(i);
        }
    }
    
    /**
     * It limits the distance of the mouse from the player.
     * 
     * @param mousePosition Position of the mouse.
     */
    private void limitMousePosition(Point mousePosition)
    {
        // Max distance from the player on y coordinate above player .
        int maxYcoordinateDistanceFromPlayer_top = 20;
        // Max distance from the player on y coordinate under player .
        int maxYcoordinateDistanceFromPlayer_bottom =20;
        
        // Mouse cursor will always be the same distance from the player  machine gun on the x coordinate.
        int mouseXcoordinate = player.machineGunXcoordinate + 10;
        
        // Here we will limit the distance of mouse cursor on the y coordinate.
        int mouseYcoordinate = mousePosition.y;
        if(mousePosition.y < player.machineGunYcoordinate){ // Above the player machine gun.
            if(mousePosition.y < player.machineGunYcoordinate - maxYcoordinateDistanceFromPlayer_top)
                mouseYcoordinate = player.machineGunYcoordinate - maxYcoordinateDistanceFromPlayer_top;
        } else { // Under the player.
            if(mousePosition.y > player.machineGunYcoordinate + maxYcoordinateDistanceFromPlayer_bottom)
                mouseYcoordinate = player.machineGunYcoordinate + maxYcoordinateDistanceFromPlayer_bottom;
        }
        
        // We move mouse on y coordinate with player. That makes shooting easier.
        mouseYcoordinate += player.movingXspeed;
        
        // Move the mouse.
        robot.mouseMove(mouseXcoordinate, mouseYcoordinate);
    }
  
   
}
