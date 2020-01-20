import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;
/**
 * The slime enemy can move 1 space in the horizontal/vertical direction.
 * 
 * @author Alexander 
 * @version January 2019
 */
public class Slime extends Enemy
{
    /**
     * Spawns a mirror into the level.
     * @param y The Y grid placement.
     * @param x The X grid placement.
     */
    public Slime(int y, int x)
    {
        for (int i = 0; i < 4; i++)
        {
            for (int n = 0;  n < 2; n++) //Moving and idle animations
            {
                image[i][n] = new GreenfootImage("Slime"+ (i) +"" + n + ".png");
                image[i][n].scale(100,100);
            }
        }
        
        //Frozen images
        imageFreeze[0] = new GreenfootImage("SlimeFrozen01.png");
        imageFreeze[0].scale(100,100);
        imageFreeze[1] = new GreenfootImage("SlimeFrozen11.png");
        imageFreeze[1].scale(100,100);
        
        //Death sounds
//Sound taken from HonorHunter at https://freesound.org/people/HonorHunter/sounds/271666/

        deathSound = new GreenfootSound("SlimeEnemy.wav");
        setImage (image[0][0]);
        placementX = x;
        placementY = y;
        type = 1;
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
        if (freeze == 0)
        {
            if (move)
            {
                moveTowardsHero();
            }
            if (isIdle && facingRight)
            {
                animate (1,2,25);
            }
            if (isIdle && !facingRight)
            {
                animate (0,2, 25);
            }
        }
        else
        {
            if (facingRight)
            {
                setImage(imageFreeze[1]);
            }
            if (!facingRight)
            {
                setImage(imageFreeze[0]);
            }
        }
    } 

    public void addedToWorld (World w)
    {
        //World can now be called using world variable
        world = (MyWorld) getWorld();
        grid = world.getGrid();
    }
    
}
