import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;
/**
 * The fireman enemy can move 1 space in the horizontal/vertical direction.
 * When it dies, fire will spawn around it, killing nearby enemies.
 * It will die from the ice explosion caused by the iceman.
 * 
 * @author Alexander 
 * @version January 2019
 */
public class Fireman extends Enemy
{
    /**
     * Spawns a fireman into the level.
     * @param y The Y grid placement.
     * @param x The X grid placement.
     */
    public Fireman(int y, int x)
    {
        for (int i = 0; i < 4; i++)
        {
            for (int n = 0;  n < 2; n++) //Moving and idle animations
            {
                image[i][n] = new GreenfootImage("Fireman"+ (i) +"" + n + ".png");
                image[i][n].scale(100,100);
            }
        }
        //Death sound
//Sound taken from HiseVideoCollection at https://www.youtube.com/watch?v=bKjrpxXbJ3s
        deathSound = new GreenfootSound("FiremanEnemy.wav");
        
        setImage (image[0][0]);
        placementX = x;
        placementY = y;
        type = 6;
        facingRight = false;
        isIdle = true;
        movement = 2;
        frames = 10;
    }

    /**
     * Act - do whatever the Slime wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        dead();
        if (move)//If moving
        {
            moveTowardsHero();
        }
        if (isIdle && facingRight)//Idle right animation
        {
            animate (1,2,13);
        }
        if (isIdle && !facingRight)//Idle left animation
        {
            animate (0,2,13);
        }
        frozen();
    } 

    public void addedToWorld ()
    {
        grid = world.getGrid();
    }
    
    public void frozen()
    {
        if (freeze != 0)//Die if frozen
        {
            world.death(getX(),getY());
            world.removeEnemy(this);
        }
    }
    
    
    
}
