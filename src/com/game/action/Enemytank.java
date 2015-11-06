package com.game.action;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * The Enemy class.
 */

public class Enemytank {
    
    /**
     * How much time must pass in order to create a new enemy?
     */
    public static long timeBetweenenemy = Framework.secInNanosec*10 ;
    /**
     * Last time when the enemy was created.
     */
    public static long lastenemyTime = 0;
    
    /**
     * enemy lines.
     * Where is starting location for the enemy?
     * Speed of the enemy?
     * How many points is a enemy worth?
     */
    public static int[][] enemyLines = {
                                    {Framework.frameWidth, (int)(Framework.frameHeight * 0.78), -2, 50}
                                      };
    /**
     * Indicate which is next enemy line.
     */
    public static int nextenemyLines = 0;
    
    
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
     * health of the tank
     */
    public int healtht;
    
    /**
     * enemy image.
     */
    private BufferedImage enemyImg;
    
    
    /**
     * Creates new enemy.
     * 
     * @param x Starting x coordinate.
     * @param y Starting y coordinate.
     * @param speed The speed of this enemy.
     * @param score How many points this enemy is worth?
     * @param enemyImg Image of the enemy.
     */
    public Enemytank(int x, int y, int speed,BufferedImage enemyImg)
    {
    	healtht=60;
        this.x = x;
        this.y = y;
        
        this.speed = speed;
        
    
        
        this.enemyImg = enemyImg;     
 
    }
    
    /**
     * It sets speed and time between enemies to the initial properties.
     */
    public static void restartEnemyt(){
        Enemytank.timeBetweenenemy = Framework.secInNanosec*10;
       Enemytank.lastenemyTime = 0;
      
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
        g2d.drawImage(enemyImg, x, y, null);
    }
}
