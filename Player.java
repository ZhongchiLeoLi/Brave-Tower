import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * The Player class holds all the information required for the user to take action within the game. 
 * Allows the user to utilize dynamic controls, be limited to a certain number of lives, and controls 
 * the speed of animations used within the game to present it coherently and smoothly. 
 *
 * @author Deston Wong and Justin Huynh
 * @version January 2019
 */
public class Player extends Actor
{
    //Variables
    private int currentX, currentY; //The current location of the player in the world
    private boolean move = false; 
    private int counter = 0;
    private int lives = 3; //The lives of the player
    private final int SPEED = 10; //Speed of the player to be used in animations
    private boolean facingRight = true; //True if facing right, false if facing left
    private boolean canClick = true; 
    private MyWorld world; //Instance of the world
    private GreenfootImage[][] image = new GreenfootImage[4][2];
    private int pic;
    private boolean isIdle;
    
    /**
     * The constructor for the Player class
     */

    public Player()
    {
        for (int i = 0; i < 4; i++)
        {
            for (int n = 0;  n < 2; n++) //Moving animations
            {
                image[i][n] = new GreenfootImage("Hero"+ (i) +"" + n + ".png");
                image[i][n].scale(100,100);
            }
        }
        setImage (image[0][0]);
        pic = 0;
        isIdle = true;
        facingRight = true;
    }

    protected void animate (int type, int length, int frames)
    {
        if (world.getFrames() % frames == 0)
        {
            setImage (image[type][pic]);
            pic ++;
        }
        if (pic == length)
        {
            pic = 0;
        }
    }  

    /**
     * Allows for movement of the player and updates coordinates accordingly.
     * 
     * @param x     The x coordinate to be moved to.
     * @param y     The y coordinate to be moved to.
     */
    public void moveToBlock(int x, int y)
    {
        if(!move)
        {
            //Check the location of x and y coordinates provided
            //If the location provided is the same as player's last move
            if (currentX == x && currentY == y){
                if (facingRight)
                {
                    facingRight = false;
                }
                else
                {
                    facingRight = true;
                }
            }
            else
            {
                move = true;
                //Update new coordinates of the current position
                currentX = x;
                currentY = y;

                //Updates blocks and variables accordingly in the 2DArray
                List <Block> block = getWorld().getObjectsAt(x, y,Block.class);
                block.get(0).setHero();
                block.get(0).setOccupied();
            }

        }
    }

    /**
     * Updates coordinates of the player when entering a new stage.
     * 
     * @param x     The x coordinate to be moved to.
     * @param y     The y coordinate to be moved to.
     */
    public void enterStage(int x, int y){
        currentX = x;
        currentY = y;
    }

    /**
     * Allows for the world to be accessed using the world variable. 
     * 
     * @param World     The world to be referenced.
     */
    public void addedToWorld (World w)
    {
        //World can now be called using world variable
        world = (MyWorld) getWorld();
    }

    /**
     * Act - do whatever the Player wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        //If the player is clicked on by the user
        if (Greenfoot.mouseClicked(this) && canClick) 
        {
            canClick = false;
            List <Block> block = getWorld().getObjectsAt(getX(), getY(),Block.class); //Creates a list of block the player is currently on 
            moveToBlock(currentX, currentY); //Updates coordinates and turns the player horizontally
            
            MyWorld world = (MyWorld) getWorld();
            world.nextTurn();
            world.playerLocation();
        }

        if(move)
        {
            counter++;
            isIdle = false;
            if (currentX != getX() || currentY != getY())
            {
                if(currentX < this.getX()) //Move left
                {
                    setLocation(this.getX() - SPEED, this.getY());  
                    facingRight = false;
                }
                if(currentX > this.getX()) //Move right
                {
                    setLocation(this.getX() + SPEED, this.getY());
                    facingRight = true;
                }
                if(currentY < this.getY()) //Move Up
                {
                    setLocation(this.getX(), this.getY() - SPEED);
                }
                if(currentY > this.getY()) //Move Down
                {
                    setLocation(this.getX(), this.getY() + SPEED);
                }
                if (!facingRight) 
                {
                    animate(3,2, 10);
                }

                if (facingRight) animate(2,2, 10);
            }
            else 
            {
                move = false;
                isIdle = true;
                counter = 0;
                MyWorld world = (MyWorld) getWorld();
                world.nextTurn();
                world.playerLocation();
            }

        }

        if (isIdle && facingRight)
        {
            animate (0,2,20);
        }
        if (isIdle && !facingRight)
        {
            animate (1,2,20);
        }
    }

    /**
     * Sets the block the user is currently on to not be occupied by the player.
     */
    public void resetBlock()
    {
        List <Block> block = getWorld().getObjectsAt(getX(), getY(),Block.class); //Gets block user is currently on
        block.get(0).setHeroFalse(); //Sets block to not be occupied by the player
    }

    /**
     * Reduces the number of lives the player holds. Removes player from world when lives are depleted.
     */
    public void reduceLives(){
        lives --; //Decrease the number of player lives
        //If lives are depleted, remove the player 
        if (lives == 0){
            removePlayer();
        }
    }

    /**
     * Returns the number of lives the player currently holds.
     * 
     * @return int     The number of lives the player currently holds
     */
    public int getLives(){
        return lives;
    }

    /**
     * Returns if the player is facing right.
     * 
     * @return boolean  Returns true if the player is facing right.
     */
    public boolean getFacingRight(){
        return facingRight;
    }

    public void setCanClick()
    {
        canClick = true;
    }

    public void setCannotClick()
    {
        canClick = false;
    }

    public boolean getCanClick()
    {
        return canClick;
    }

    /**
     * Sets the transparency of the player to a desired integer value, from 0 to 255.
     * 
     * @param num   The desired transparency of the player, from 0 to 255.
     */
    public void setTransparency (int num)
    {
        getImage().setTransparency (num);
    }

    public void imageSet()
    {
        for (int i = 0; i < 4; i++)
        {
            for (int n = 0;  n < 2; n++) 
            {
                image[i][n].setTransparency(250);
            }
        }
    }

    /**
     * Removes the player from the world. Transfers timer data to the following stage.
     */
    public void removePlayer()
    {
        world.floorReset (1);
        GameLost next = new GameLost();
        Timer timer = (Timer)getWorld().getObjects(Timer.class).get(0);
        next.setTime(timer.getMinText(), timer.getSecText());
        Greenfoot.setWorld(next);
    }
}
