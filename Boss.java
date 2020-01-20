import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;
/**
 * The boss enemy can move 1 space in the horizontal/vertical direction.
 * In addition, it is invincible and does a sand attack at the end of its turn.
 * 
 * @author Alexander 
 * @version January 2019
 */
public class Boss extends Enemy
{
    /**
     * Spawns a boss into the level.
     * @param y The Y grid placement.
     * @param x The X grid placement.
     */
    public Boss(int y, int x)
    {
        for (int i = 0; i < 2; i++)
        {
            for (int n = 0;  n < 4; n++) //Idle animations
            {
                image[i][n] = new GreenfootImage("Tomato"+ (i) +"" + n + ".png");
                image[i][n].scale(200,200);
            }
        }
        for (int i = 2; i < 4; i++)
        {
            for (int n = 0;  n < 2; n++) //Moving animations
            {
                image[i][n] = new GreenfootImage("Tomato"+ (i) +"" + n + ".png");
                image[i][n].scale(200,200);
            }
        }
        for (int i = 4; i < 6 ; i++)
        {
            for (int n = 0;  n < 15; n++) //Attacking animations
            {
                image[i][n] = new GreenfootImage("Tomato"+ (i) +"" + n + ".png");
                image[i][n].scale(200,200);
            }
        }
        setImage (image[0][0]);
        placementX = x;
        placementY = y;
        type = 9;
        facingRight = false;
        isIdle = true;
        movement = 2;
        hit = 10;
        frames = 20;
    }
    
    /**
     * Act - do whatever the Slime wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        dead();
        if (attackMove)//If the boss is supposed to be attacking
        {
            //Attack right
            if (facingRight) animate(5,15,5);
            //Attack left
            if (!facingRight) animate(4,15,5);
        }
        else//If the boss is not attacking
        {
            if (move)//If moving
            {
                moveTowardsHero();
            }
            if (isIdle && facingRight)//Idle right animation
            {
                animate (1,2,25);
            }
            if (isIdle && !facingRight)//Idle left animation
            {
                animate (0,2, 25);
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
