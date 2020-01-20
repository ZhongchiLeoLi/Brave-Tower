import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;
/**
 * The worm enemy can move in an "L" shape like the horse in chess.
 *
 * @author Alexander 
 * @version January 2018
 */
public class Worm extends Enemy
{
    /**
     * Spawns a spider into the level.
     * @param y The Y grid placement.
     * @param x The X grid placement.
     */
    public Worm (int y, int x)
    {
        for (int i = 0; i < 2; i++)
        {
            for (int n = 0;  n < 2; n++) //Idle animations
            {
                image[i][n] = new GreenfootImage("Worm"+ (i) +"" + n + ".png");
                image[i][n].scale(100,100);
            }
        }
        for (int i = 2; i < 6; i++)
        {
            for (int n = 0;  n < 9; n++) //Moving animations
            {
                image[i][n] = new GreenfootImage("Worm"+ (i) +"" + n + ".png");
                image[i][n].scale(100,100);
            }
        }
        
        //Frozen images
        imageFreeze[0] = new GreenfootImage("WormFrozen01.png");
        imageFreeze[0].scale(100,100);
        imageFreeze[1] = new GreenfootImage("WormFrozen11.png");
        imageFreeze[1].scale(100,100);
        
        //Death sound
//Sound taken from Needed Sound Effects at https://www.youtube.com/watch?v=QOCmPsH5xTY
        deathSound = new GreenfootSound("WormEnemy.wav");
        setImage (image[0][0]);
        placementX = x;
        placementY = y;
        type = 4;
        facingRight = false;
        isIdle = true;
        movement = 4;
        frames = 5;
    }
    
    /**
     * Act - do whatever the Slime wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        dead();
        if (freeze == 0 )//Not frozen
        {
            if (move)//Moving
            {
                moveTowardsHero();
            }

            if (isIdle)//Idle
            {
                resetTransparency();
                //Facing right
                if (facingRight) animate (1,2, 25);
                else//Facing left
                {
                    animate (0,2, 25);
                }
            }

            if (burrow)//Burrow animation
            {
                isIdle = false;    
                if (facingRight) animate (3,9, 2);
                else animate (2,9, 2);
            }

            if (unBurrow)//Unburrow animation
            {
                if (isIdle)
                {
                    resetTransparency();
                }
                isIdle = false;  
                if (facingRight) animate (5,9, 2);
                else
                {
                    animate (4,9, 2);
                }
            }
        }
        else
        {
            if (facingRight)//Frozen right
            {
                setImage(imageFreeze[1]);
            }
            if (!facingRight)//Frozen left
            {
                setImage(imageFreeze[0]);
            }
        }
    } 

    public void addedToWorld ()
    {
        grid = world.getGrid();
    }
    
    private void resetTransparency()
    {
         for (int i = 0; i < 2; i++)
        {
            for (int n = 0;  n < 2; n++)//Idle animations
            {
                image[i][n].setTransparency(250);
            }
        }
        for (int i = 2; i < 6; i++)//Moving animations
        {
            for (int n = 0;  n < 9; n++) 
            {
                image[i][n].setTransparency(250);
            }
        }
    }

}
