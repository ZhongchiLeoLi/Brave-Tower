import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;
/**
 * The Iceman enemy can move 1 space in the horizontal/vertical direction.
 * When it dies, it will freeze all enemies except for other icemen and the boss for 2 turns.
 * It will die from the ice explosion caused by the iceman.
 * 
 * @author Alexander 
 * @version January 2019
 */
public class Iceman extends Enemy
{
    /**
     * Spawns an iceman into the level.
     * @param y The Y grid placement.
     * @param x The X grid placement.
     */
    public Iceman(int y, int x)
    {
        for (int i = 0; i < 4; i++)
        {
            for (int n = 0;  n < 2; n++) //Moving animations
            {
                image[i][n] = new GreenfootImage("Iceman"+ (i) +"" + n + ".png");
                image[i][n].scale(100,100);
            }
        }
//Sound taken from SoundJay at https://www.soundjay.com/ice-sound-effect.html
        deathSound = new GreenfootSound("IcemanEnemy.mp3");
        
        setImage (image[0][0]);
        placementX = x;
        placementY = y;
        type = 7;
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
        if (move)
        {
            moveTowardsHero();
        }
        if (isIdle && facingRight)
        {
            animate (1,2,13);
        }
        if (isIdle && !facingRight)
        {
            animate (0,2,13);
        }
    } 

    public void addedToWorld ()
    {
        grid = world.getGrid();
    }

    
    
}
