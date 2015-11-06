package com.game.action;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;



/**
 * The Enemy class.
 */

public class Enemy {
    
    /**
     * How much time must pass in order to create a new enemy?
     */
    public static long timeBetweenenemyp = Framework.secInNanosec*4;
    /**
     * Last time when the enemy was created.
     */
    public static long lastenemypTime = 0;
    
    /**
     * enemy lines.
     * Where is starting location for the enemy?
     * Speed of the enemy?
     * How many points is a enemy worth?
     */
    public static int[][] enemypLines = {
    	 {Framework.frameWidth, (int)(Framework.frameHeight * 0.65), -3, 30}         
    	 };
    /**
     * Indicate which is next enemy line.
     */
    public static int nextenemypLines = 0;
    
    
    /**
     * X coordinate of the enemy.
     */
    public int x;
    /**
     * Y coordinate of the enemy.
     */
    public int y;
    
    /**
     * How fast the enemy should move? And to which direction?
     */
    private int speed;
    
    /**
     * health of the enemy player
     */
    public int healthp;
    
    /**
     * enemy image.
     */
    private BufferedImage enemypImg;
    
    
    /**
     * Creates new enemy.
     * 
     * @param x Starting x coordinate.
     * @param y Starting y coordinate.
     * @param speed The speed of this enemy.
     * @param score How many points this enemy is worth?
     * @param enemyImg Image of the enemy.
     */
    public Enemy(int x, int y, int speed,BufferedImage enemyImg)
    {
    	healthp=40;
        this.x = x;
        this.y = y;
        
        this.speed = speed;
        
    
        
        this.enemypImg = enemyImg;     
 
    }
    
    /**
     * It sets speed and time between enemies to the initial properties.
     */
    public static void restartEnemyp(){
        Enemy.timeBetweenenemyp = Framework.secInNanosec*4;
       Enemy.lastenemypTime = 0;
      
    }
    
    
    
    /**
     * Move the enemy.
     */
    public void Update()
    {
        x += speed;
       
    }
    
    /**
     * Draw the enemy to the screen.
     * @param g2d Graphics2D
     */
    public void Draw(Graphics2D g2d)
    {
        g2d.drawImage(enemypImg, x, y, null);
    }
}
