package com.game.action;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;



/**
 * Player which is managed by user.
 */

public class Player {
    
    // Health of the player.
    private final int healthInit = 100;
    public int health;
    
    // Position of the player on the screen.
    public int xCoordinate;
    public int yCoordinate;
    
    // Moving speed and also direction.
    public double movingXspeed;
   
    private double acceleratingXspeed;
   private double stoppingXspeed;

    
    // player rockets.
    private final int numberOfRocketsInit = 120;
    public int numberOfRockets;
    
    // player machine gun ammo.
    private final int numberOfAmmoInit = 1000;
    public int numberOfAmmo;
    
    // Images of player.
    public BufferedImage playerBodyImg;
    
   
    
    
    
    // Offset of the player rocket holder.
    private int offsetXRocketHolder;
    private int offsetYRocketHolder;
    // Position on the frame/window of the player rocket holder.
    public int rocketHolderXcoordinate;
    public int rocketHolderYcoordinate;
    
    // Offset of the player machine gun. We add offset to the position of the position of player.
    private int offsetXMachineGun;
    private int offsetYMachineGun;
    // Position on the frame/window of the player machine gun.
    public int machineGunXcoordinate;
    public int machineGunYcoordinate;
    
    
    /**
     * Creates object of player.
     * 
     * @param xCoordinate Starting x coordinate of player.
     * @param yCoordinate Starting y coordinate of player.
     */
    public Player(int xCoordinate, int yCoordinate)
    {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        
        LoadContent();
        Initialize();
    }
    
    
    /**
     * Set variables and objects for this class.
     */
    private void Initialize()
    {
        this.health = healthInit;
        
        this.numberOfRockets = numberOfRocketsInit;
        this.numberOfAmmo = numberOfAmmoInit;
        
        this.movingXspeed = 0;
        
        this.acceleratingXspeed = 0.2;
        this.stoppingXspeed = 0.1;


        
        this.offsetXRocketHolder =playerBodyImg.getWidth()-70;
        this.offsetYRocketHolder =playerBodyImg.getHeight()-150;
        this.rocketHolderXcoordinate = this.xCoordinate + this.offsetXRocketHolder;
        this.rocketHolderYcoordinate = this.yCoordinate + this.offsetYRocketHolder;
        
        this.offsetXMachineGun = playerBodyImg.getWidth()-70;
        this.offsetYMachineGun = playerBodyImg.getHeight()-150;
        
        this.machineGunXcoordinate = this.xCoordinate + this.offsetXMachineGun;
        this.machineGunYcoordinate = this.yCoordinate + this.offsetYMachineGun;
    }
    
    /**
     * Load files for this class.
     */
    private void LoadContent()
    {
        try 
        {
            URL playerBodyImgUrl = this.getClass().getResource("/com/game/action/resources/images/player.gif");
            playerBodyImg = ImageIO.read(playerBodyImgUrl);
            
        } 
        catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    /**
     * Resets the player.
     * 
     * @param xCoordinate Starting x coordinate of player.
     * @param yCoordinate Starting y coordinate of player.
     */
    public void Reset(int xCoordinate, int yCoordinate)
    {
        this.health = healthInit;
        
        this.numberOfRockets = numberOfRocketsInit;
        this.numberOfAmmo = numberOfAmmoInit;
        
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        
        this.machineGunXcoordinate = this.xCoordinate + this.offsetXMachineGun;
        this.machineGunYcoordinate = this.yCoordinate + this.offsetYMachineGun;
        
        this.movingXspeed = 0;
       
    }
    
    
    /**
     * Checks if player is shooting. It also checks if player can 
     * shoot (time between bullets, does a player have any bullet left).
     * 
     * @param gameTime The current elapsed game time in nanoseconds.
     * @return true if player is shooting.
     */
    public boolean isShooting(long gameTime)
    {
        // Checks if left mouse button is down && if it is the time for a new bullet.
        if( Canvas.mouseButtonState(MouseEvent.BUTTON1) && 
            ((gameTime - Bullet.timeOfLastCreatedBullet) >= Bullet.timeBetweenNewBullets) &&
            this.numberOfAmmo > 0) 
        {
            return true;
        } else
            return false;
    }
    
    
    /**
     * Checks if player is fired a rocket. It also checks if player can 
     * fire a rocket (time between rockets, does a player have any rocket left).
     * 
     * @param gameTime The current elapsed game time in nanoseconds.
     * @return true if player is fired a rocket.
     */
    public boolean isFiredRocket(long gameTime)
    {
        // Checks if right mouse button is down && if it is the time for new rocket && if he has any rocket left.
        if( Canvas.mouseButtonState(MouseEvent.BUTTON3) && 
            ((gameTime - Rocket.timeOfLastCreatedRocket) >= Rocket.timeBetweenNewRockets) && 
            this.numberOfRockets > 0 ) 
        {
            return true;
        } else
            return false;
    }
    
    
    /**
     * Checks if player moving and sets its moving speed if player is moving.
     */
    public void isMoving()
    {
        // Moving on the x coordinate.
        if(Canvas.keyboardKeyState(KeyEvent.VK_D) || Canvas.keyboardKeyState(KeyEvent.VK_RIGHT))
            movingXspeed += acceleratingXspeed;
        else if(Canvas.keyboardKeyState(KeyEvent.VK_A) || Canvas.keyboardKeyState(KeyEvent.VK_LEFT))
            movingXspeed -= acceleratingXspeed;
        else    // Stopping
            if(movingXspeed < 0)
                movingXspeed += stoppingXspeed;
            else if(movingXspeed > 0)
                movingXspeed -= stoppingXspeed;
        
    }
    
    
    /**
     * Updates position of player, animations.
     */
    public void Update()
    {
        // Move player.
        xCoordinate += movingXspeed;
      
        
        // Change position of the rocket holder.
        this.rocketHolderXcoordinate = this.xCoordinate + this.offsetXRocketHolder;
        this.rocketHolderYcoordinate = this.yCoordinate + this.offsetYRocketHolder;
        
        // Move the machine gun with player.
        this.machineGunXcoordinate = this.xCoordinate + this.offsetXMachineGun;
        this.machineGunYcoordinate = this.yCoordinate + this.offsetYMachineGun;
    }
    
    
    /**
     * Draws player to the screen.
     * 
     * @param g2d Graphics2D
     */
    public void Draw(Graphics2D g2d)
    {
       g2d.drawImage(playerBodyImg, xCoordinate, yCoordinate,null);
    }
    
}
