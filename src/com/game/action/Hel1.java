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


public class Hel1 {
    
    
    // Position of the helicopter on the screen.
    public int xCoordinate;
    public int yCoordinate;
    
    // Images of helicopter and its propellers.
    public BufferedImage helicopterBodyImg;
    private BufferedImage helicopterFrontPropellerAnimImg;
    private BufferedImage helicopterRearPropellerAnimImg;
    
    // Animation of the helicopter propeller.
    private Animation helicopterFrontPropellerAnim;
    private Animation helicopterRearPropellerAnim;
    // Offset for the propeler. We add offset to the position of the position of helicopter.
    private int offsetXFrontPropeller;
    private int offsetYFrontPropeller;
    private int offsetXRearPropeller;
    private int offsetYRearPropeller;
    
    
    
    /**
     * Creates object of player.
     * 
     * @param xCoordinate Starting x coordinate of helicopter.
     * @param yCoordinate Starting y coordinate of helicopter.
     */
    public Hel1(int xCoordinate, int yCoordinate)
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
   
        this.offsetXFrontPropeller = 70;
        this.offsetYFrontPropeller = -23;        
        this.offsetXRearPropeller = -6;
        this.offsetYRearPropeller = -21;
        
     }
    
    /**
     * Load files for this class.
     */
    private void LoadContent()
    {
        try 
        {
            URL helicopterBodyImgUrl = this.getClass().getResource("/com/game/action/resources/images/heli1.png");
            helicopterBodyImg = ImageIO.read(helicopterBodyImgUrl);
            
            URL helicopterFrontPropellerAnimImgUrl = this.getClass().getResource("/com/game/action/resources/images/1_front_propeller_anim.png");
            helicopterFrontPropellerAnimImg = ImageIO.read(helicopterFrontPropellerAnimImgUrl);
            
            URL helicopterRearPropellerAnimImgUrl = this.getClass().getResource("/com/game/action/resources/images/1_rear_propeller_anim.png");
            helicopterRearPropellerAnimImg = ImageIO.read(helicopterRearPropellerAnimImgUrl);
        } 
        catch (IOException ex) {
            Logger.getLogger(Hel1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Now that we have images of propeller animation we initialize animation object.
        helicopterFrontPropellerAnim = new Animation(helicopterFrontPropellerAnimImg, 204, 34, 3, 20, true, (int)(Framework.frameWidth*0.191),(int)(Framework.frameHeight*0.221), 0);
        helicopterRearPropellerAnim = new Animation(helicopterRearPropellerAnimImg, 54, 54, 4, 20, true, (int)(Framework.frameWidth*0.103),(int)(Framework.frameHeight*0.229) , 0);
    }
    
    
    
    /**
     * Draws helicopter to the screen.
     * 
     * @param g2d Graphics2D
     */
    public void Draw(Graphics2D g2d)
    {
  
        g2d.drawImage(helicopterBodyImg, xCoordinate, yCoordinate, null);
        helicopterFrontPropellerAnim.Draw(g2d);
        helicopterRearPropellerAnim.Draw(g2d);
    }
    
}
