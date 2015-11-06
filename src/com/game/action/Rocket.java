package com.game.action;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * Rocket.
 * Use: create object of this class then initialize it with Initialize method.
 */

public class Rocket {
    
    // Time that must pass before another rocket can be fired.
    public final static long timeBetweenNewRockets = Framework.secInNanosec / 4;
    public static long timeOfLastCreatedRocket = 0;
    
    // Damage that is made to an EnemyHelicopter when it is hit with a rocket.
    public static int damagePower = 100;
    
    // Rocket position
    public int xCoordinate;
    public int yCoordinate;
    
    // Moving speed and also direction. 
    private static int rocketSpeed = 10;
    private double movingXspeed;
    private double movingYspeed;
    
    // Life time of current piece of rocket smoke.
    public long currentSmokeLifeTime;

    // Image of rocket. Image is loaded and set in Game class in LoadContent() method.
    public static BufferedImage rocketImg;
    
    /**
     * Creates new machine gun bullet.
     * 
     * @param xCoordinate From which x coordinate was bullet fired?
     * @param yCoordinate From which y coordinate was bullet fired?
     * @param mousePosition Position of the mouse at the time of the shot.
     */
    public Rocket(int xCoordinate, int yCoordinate, Point mousePosition)
    {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        
        setDirectionAndSpeed(mousePosition);
    
        this.currentSmokeLifeTime = Framework.secInNanosec / 2;
    }
    
    /**
     * Calculate the speed on a x and y coordinate.
     * 
     * @param mousePosition 
     */
    private void setDirectionAndSpeed(Point mousePosition)
    {
        // Unit direction vector of the bullet.
        double directionVx = mousePosition.x - this.xCoordinate;
        double directionVy = mousePosition.y - this.yCoordinate;
        double lengthOfVector = Math.sqrt(directionVx * directionVx + directionVy * directionVy);
        directionVx = directionVx / lengthOfVector; // Unit vector
        directionVy = directionVy / lengthOfVector; // Unit vector
        
        // Set speed.
        this.movingXspeed = rocketSpeed * directionVx;
        this.movingYspeed = rocketSpeed * directionVy;
    }
    
   
   
    /**
     * Checks if the rocket is left the screen.
     * 
     * @return true if the rocket is left the screen, false otherwise.
     */
    public boolean isItLeftScreen()
    {
        if(xCoordinate > 0 && xCoordinate < Framework.frameWidth &&
           yCoordinate > 0 && yCoordinate < Framework.frameHeight)
            return false;
        else
            return true;
    }
    
  /**
     * Moves the rocket.
     */
    public void Update()
    {
        xCoordinate += movingXspeed;
        yCoordinate += movingYspeed;
    }
    
    
    /**
     * Draws the rocket to the screen.
     * 
     * @param g2d Graphics2D
     */
    public void Draw(Graphics2D g2d)
    {
        g2d.drawImage(rocketImg, (int)xCoordinate, (int)yCoordinate, null);
    }
}
