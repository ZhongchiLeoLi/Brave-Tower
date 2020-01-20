import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;
/**
 * The mirror enemy can move 1 space in the horizontal/vertical direction.
 * It can swap places with the hero if they are facing each other, they are 
 * on the same Y axis and the hero is within 4 blocks of the mirror.
 * 
 * @author Alexander 
 * @version January 2019
 */
public class Mirror extends Enemy
{
    private int transparency = 250;
    /**
     * Spawns a mirror into the level.
     * @param y The Y grid placement.
     * @param x The X grid placement.
     */
    public Mirror(int y, int x)
    {
        for (int i = 0; i < 4; i++)
        {
            for (int n = 0;  n < 2; n++) //Moving and idle animations
            {
                image[i][n] = new GreenfootImage("Mirror"+ (i) +"" + n + ".png");
                image[i][n].scale(100,100);
            }
        }
        
        //Frozen images
        imageFreeze[0] = new GreenfootImage("MirrorFrozen01.png");
        imageFreeze[0].scale(100,100);
        imageFreeze[1] = new GreenfootImage("MirrorFrozen11.png");
        imageFreeze[1].scale(100,100);
        
        //Death sound
//Sound taken from FreeUseSoundEffects at https://www.youtube.com/watch?v=xRD6ByjeNC0
        deathSound = new GreenfootSound("MirrorEnemy.mp3");
        
        setImage (image[0][0]);
        placementX = x;
        placementY = y;
        type = 8;
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
        if (freeze == 0)//If not frozen
        {
            if (move)//Moving
            {
                moveTowardsHero();
            }
            if (isIdle && facingRight)//Idle right animation
            {
                animate (1,2,25);
            }
            if (isIdle && !facingRight)//Idle left animation
            {
                animate (0,2,25);
            }
            if (swap)//If the mirror is swapping places with the hero
            {
                //Lower transparency of both the hero and mirror
                player.setTransparency(transparency);
                getImage().setTransparency(transparency);
                transparency -= 10;
                if (transparency == 0)//Once it has dissapeared
                {
                    swap = false;//Stop swapping process
                    //Set current grid as the hero placement
                    grid[placementY][placementX].setHero();
                    grid[placementY][placementX].setOccupied();    
                    world.setPlayerLocationX (placementX);
                    world.setPlayerLocationY (placementY);
                    player.setLocation (grid[placementY][placementX].getX(), grid[placementY][placementX].getY());
                    player.enterStage (player.getX(), player.getY());
                    placementX = heroX;
                    placementY = heroY;
                    
                    //Set hero grid as the mirror placement
                    setLocation (grid[placementY][placementX].getX(), grid[placementY][placementX].getY());
                    grid[placementY][placementX].setHeroFalse();
                    grid[placementY][placementX].setNotAvailable();
                    
                    //Make player and hero visible
                    player.setTransparency(250);
                    player.imageSet();
                    getImage().setTransparency(250);
                    transparency = 250;
                    resetTransparency();
                    world.enemyTurn();
                }
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
    
    private void resetTransparency() 
    {
        for (int i = 0; i < 4; i++)
        {
            for (int n = 0;  n < 2; n++) 
            {
                image[i][n].setTransparency(250);
            }
        }
    }
}
