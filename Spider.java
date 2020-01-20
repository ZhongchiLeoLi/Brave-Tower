import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;
/**
 * The spider enemy can move 1 space diagonally.
 *
 * @author Alexander 
 * @version January 2018
 */
public class Spider extends Enemy
{
    /**
     * Spawns a spider into the level.
     * @param y The Y grid placement.
     * @param x The X grid placement.
     */
    public Spider (int y, int x)
    {
        for (int i = 0; i < 4; i++)
        {
            for (int n = 0;  n < 2; n++) //Moving and idle animations
            {
                image[i][n] = new GreenfootImage("Spider"+ (i) +"" + n + ".png");
                image[i][n].scale(100,100);
            }
        }
        
        //Frozen images
        imageFreeze[0] = new GreenfootImage("SpiderFrozen01.png");
        imageFreeze[0].scale(100,100);
        imageFreeze[1] = new GreenfootImage("SpiderFrozen11.png");
        imageFreeze[1].scale(100,100);
        
        //Death sound
//Sound taken from GroundOfSound at https://www.youtube.com/watch?v=9qawCZgy5No
        deathSound = new GreenfootSound("SpiderEnemy.mp3");
        
        setImage (image[0][0]);
        placementX = x;
        placementY = y;
        type = 3;
        facingRight = false;
        isIdle = true;
        movement = 2;
        frames = 20;
    }
    
    /**
     * Act - do whatever the Slime wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        dead();
        if (freeze == 0)//If frozen
        {
            if (move)//Moving
            {
                moveTowardsHero();
            }
            if (isIdle && facingRight)//Idle right animation
            {
                animate (1,2, 25);
            }
            if (isIdle && !facingRight)//Idle left animation
            {
                animate (0,2, 25);
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
