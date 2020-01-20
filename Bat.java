import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;
/**
 * The bat enemy can move up to 2 spaces in the horizontal/vertical direction.
 * In addition, it can fly over walls
 * 
 * @author Alexander 
 * @version January 2019
 */
public class Bat extends Enemy
{
    /**
     * Spawns a bat into the level.
     * @param y The Y grid placement.
     * @param x The X grid placement.
     */
    public Bat(int y, int x)
    {
        //Storing all animations in a 2d array
        for (int i = 0; i < 4; i++)
        {
            for (int n = 0;  n < 2; n++) //Moving animations
            {
                image[i][n] = new GreenfootImage("Bat"+ (i) +"" + n + ".png");
                image[i][n].scale(100,100);
            }
        }
        
        //Freezing images
        imageFreeze[0] = new GreenfootImage("BatFrozen01.png");
        imageFreeze[0].scale(100,100);
        imageFreeze[1] = new GreenfootImage("BatFrozen11.png");
        imageFreeze[1].scale(100,100);
        
        //Death sound
//Music taken from Gyrf at https://www.youtube.com/watch?v=BgEbGlDoLaw
        deathSound = new GreenfootSound("BatEnemy.mp3");
        
        setImage (image[0][0]);
        placementX = x;
        placementY = y;
        type = 2;
        facingRight = false;
        isIdle = true;
        movement = 2;
        frames = 9;
    }
    /**
     * Act - do whatever the Slime wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        dead();
        if (freeze == 0)//If not frozen
        {
            if (move)//If its supposed to be moving
            {
                moveTowardsHero();
            }
            if (isIdle && facingRight)//Idle animation right
            {
                animate (1,2,11);
            }
            if (isIdle && !facingRight)//Idle animation left
            {
                animate (0,2,11);
            }
        }
        else
        {
            if (facingRight)//Frozen image right
            {
                setImage(imageFreeze[1]);
            }
            if (!facingRight)//Frozen image left
            {
                setImage(imageFreeze[0]);
            }
        }
    } 

    public void addedToWorld ()
    {
        grid = world.getGrid();
    }

    
}
