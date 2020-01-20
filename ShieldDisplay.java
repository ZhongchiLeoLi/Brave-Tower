import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * An indicator for the remaining amount of shields the player has remaining throughout the playthrough
 * of the game. Placed at the top of the stage, acting similarly to a display for the number of lives 
 * of the player which decreases when hit by an enemy.
 * 
 * @author Deston Wong and Justin Huynh
 * @version January 2019
 */
public class ShieldDisplay extends Actor
{
    //Variables
    private int health; //The health for the shield
    private MyWorld world = (MyWorld) getWorld(); //Initialization of the current world
    private GreenfootImage[] shieldDisplay = new GreenfootImage[4]; //Images which change depending on shield health
    private GreenfootSound shieldhit = new GreenfootSound("ShieldHit.mp3"); //Sound to be played when shield receives damage

    /**
     * Main constructor for ShieldDisplay. Scales all images to appropriate size and displays
     * original shield image when player enters world for the first time.
     */
    public ShieldDisplay(){
        //Set initial health of shield to a maximum of 3
        health = 3;
        //Scale all images to appropriate size
        for (int i = 0; i < 4; i++)
        {
            shieldDisplay[i] = new GreenfootImage("Shield"+ (i) +".png");
            shieldDisplay[i].scale(175, 75);
        } 
        //Display the image when first starting
        setImage (shieldDisplay[health]);
    }

    /**
     * Decreases the amount of health displayed by the shield.
     */
    public void decreaseShield(){
        //Decreases health and the shield image accordingly
        health --; 
        setImage (shieldDisplay[health]);
        //Plays sound for shield getting hit
        shieldhit.play(); 
    }

    /**
     * Sets the current health of the shield to a new value. 
     * 
     * @param n     The new value to be assigned to shield health.
     */
    public void setCurrentHealth(int n){
        //Sets health equal to desired value
        health = n;
        //Updates image according to new health value
        setImage (shieldDisplay[health]); 
    }

    /**
     * Returns the current health value of the shield.
     * 
     * @return int  The current durability value of the shield, out of 3.
     */
    public int getLives(){
        return health;
    }
}
