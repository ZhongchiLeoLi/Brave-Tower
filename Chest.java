import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;
/**
 * The chest enemy can move 1 space in the horizontal/vertical direction.
 * It will only move after its transformation or after it is frozen.
 * Transformation occurs when the player is within 2 blocks of the chest.
 * 
 * @author Alexander 
 * @version January 2019
 */
public class Chest extends Enemy
{
    /**
     * Spawns a chestmonster into the level.
     * @param y The Y grid placement.
     * @param x The X grid placement.
     */
    public Chest(int y, int x)
    {
        for (int i = 0; i < 4; i++)
        {
            for (int n = 0;  n < 2; n++) //Moving and animations
            {
                image[i][n] = new GreenfootImage("Chestmon"+ (i) +"" + n + ".png");
                image[i][n].scale(100,100);
            }
        }
        for (int i = 4; i < 6; i++)
        {
            for (int n = 0;  n < 6; n++) //Tranformation animations
            {
                image[i][n] = new GreenfootImage("Chestmon"+ (i) +"" + n + ".png");
                image[i][n].scale(100,100);
            }
        }
        
        //Frozen images
        imageFreeze[0] = new GreenfootImage("ChestmonFrozen01.png");
        imageFreeze[0].scale(100,100);
        imageFreeze[1] = new GreenfootImage("ChestmonFrozen11.png");
        imageFreeze[1].scale(100,100);
        
        //Death sound
//Sound taken from SyentifikFilms at https://www.youtube.com/watch?v=MPKq0R2JKW4
        deathSound = new GreenfootSound("ChestEnemy.wav");
        
        setImage (image[4][0]);
        placementX = x;
        placementY = y;
        type = 5;
        facingRight = false;
        isIdle = false;
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
        if (freeze == 0)//If not frozen
        {
            if (move)//If moving
            {
                moveTowardsHero();
            }
            if (transform && heroX <= placementX)//Transform to the left
            {
                animate (4,6,5);
            }
            if (transform && heroX > placementX)//Transform to the right
            {
                animate (5,6,5);
            }
            if (isIdle && facingRight)//Idle right animation
            {
                animate (1,2,13);
            }
            if (isIdle && !facingRight)//Idle left animation
            {
                animate (0,2, 13);
            }
        }
        else//If frozen
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