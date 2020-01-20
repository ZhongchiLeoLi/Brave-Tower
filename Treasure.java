import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * The treasure chest to be created on the boss level at a specific coordinate on the stage. 
 * The player must pick up the treasure before progressing through the remainder of the game in 
 * order to win.
 * 
 * @author Deston Wong and Justin Huynh
 * @version January 2019
 */
public class Treasure extends Actor
{
    //Variables
    private MyWorld world; //Instance of the current world
    private GreenfootImage treasureImage = getImage(); //Image of the treasure box to be manipulated 
    private Block [][] grid; //Instance of 2DArray of stage
    
    /**
     * Main constructor for treasure chest to be created on the boss level.
     */
    public Treasure(){
        treasureImage.scale(90, 90); //scales the image to desired dimensions
    }
    
    /**
     * Allows for the world to be accessed using the world variable. Also initializes the grid used 
     * as the stage.
     * 
     * @param World     The world to be referenced.
     */
    public void addedToWorld (World w)
    {
        //World can now be called using world variable
        world = (MyWorld) getWorld();
        //2DArray from the world can now be called on using grid variable
        grid = world.getGrid(); 
    }
    
    /**
     * Act - do whatever the Treasure wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        //Gets list of player in the world
        List <Player> user = getWorld().getObjects(Player.class);
        //If the treasure is able to be clicked
        if (Greenfoot.mouseClicked(this) &&  user.get(0).getCanClick() && grid[0][8].getMovable()) 
        {
            //Stops user from clicking multiple times between animation
            user.get(0).setCannotClick();
            //Move the player to current block coordinates
            user.get(0).resetBlock();
            user.get(0).moveToBlock(this.getX(), this.getY()); 
        }
    }    
}
