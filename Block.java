import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * The block class holds all the information in each grid of our world, 
 * whether it is information about where the player can move, where
 * the spikes are, where the walls are, etc.
 * 
 * @author Deston, Justin, Daniel
 * @version January 2019
 */
public class Block extends Actor
{
    //Variables for when something is on the block
    private boolean available = true;
    private boolean occupied;
    private boolean movable;
    private boolean obstacle;

    //Variables for spikes
    private boolean animating = false;
    private boolean extending = true;
    private int spikeLevel = 3;
    private int spike;

    //Variables for position
    private int placementX;
    private int placementY;
    private GreenfootImage image;

    //Variables for images
    private GreenfootImage[] spikeImage = new GreenfootImage[5];
    private GreenfootImage[] spikeImageMovable = new GreenfootImage[5];

    //Sound taken from Flint Luke at https://www.youtube.com/watch?v=O5ZWNkXI3KU
    private GreenfootSound spikeSound = new GreenfootSound("Spike.wav");

    /**
     * The constructor for Block, setting the initial booleans for different properties
     * 
     * @author (your name) 
     * @version (a version number or a date)
     * @param x The x coordinate of the block
     * @param y The y coordinate of the block
     * @param occupied True if player is on this block
     * @param obstacle True if there is a wall at this block
     */    
    public Block (int x, int y, boolean occupied, boolean obstacle)
    {

        //Assinging values to necessary variables
        placementX = x;
        placementY = y;
        this.occupied = occupied;
        this.obstacle = obstacle;
        //Hide blocks that are not movable/spikes
        getImage().setTransparency(10);
        movable = false;

        //Assign spike images to the array
        for(int i=0; i<5; i++)
        {
            spikeImage[i] = new GreenfootImage("spike" + (i+1) + ".png");
            spikeImageMovable[i] = new GreenfootImage("spike_movable" + (i+1) + ".png");
        }
    }

    /**
     * The act method of the Block class
     */ 
    public void act() 
    {
        //Create a list of the player in the world
        List <Player> user = getWorld().getObjects(Player.class);
        //If the block is clicked
        if (Greenfoot.mouseClicked(this) && getMovable() && user.get(0).getCanClick()) 
        {
            user.get(0).setCannotClick();
            //Move the player to current block coordinates
            user.get(0).resetBlock();
            user.get(0).moveToBlock(this.getX(), this.getY()); 
        }
        //If spike is at stage 3 and is going through the animation:
        if(spike == 3 && animating)
        {
            //After every 5 frames:
            MyWorld world = (MyWorld)getWorld();
            if (world.getFrames() % 5 == 0)
            {
                //If it is extending, increase the spikeLevel (spike extends)                
                if(extending)
                    spikeLevel++;
                else
                //Otherwise, decrease the spikeLevel (spike retracts)
                    spikeLevel--;
                //Sets the appropriate image whether player is able to move to this block or not
                if(movable)
                    setImage(spikeImageMovable[spikeLevel]);
                else
                    setImage(spikeImage[spikeLevel]);

                //If the spike is at its max extension, start retracting
                if(spikeLevel == 4) extending = false;
                //If the spike is at its peak, and there is a player at this location, kill them
                if(spikeLevel == 3)
                {
                    spikeSound.setVolume(80);
                    spikeSound.play();

                    List<Player> player = getWorld().getObjectsAt(getX(), getY(),Player.class);
                    if(player.size() == 1)
                        player.get(0).removePlayer();
                }
                //If the spike is fully retracted, stop animating
                if(spikeLevel == 0)
                {
                    animating = false;
                }

            }
        }
    }

    /**
     * Displays the area that the player can move, at a maximum of two blocks horizontally or vertically
     */
    public void setOccupied()
    {
        //Sets appropriate boolean
        occupied = true; 

        //List of all blocks in the grid
        List<Block> allBlocks = getWorld().getObjects(Block.class);
        //List of blocks 1 away
        List<Block> oneRadius = getNeighbours(100, false, Block.class);
        //List of blocks 2 away
        List<Block> twoRadius = getNeighbours(200, false, Block.class);

        //For every block, set them unmovable (player cannot move there)
        for(Block s: allBlocks)
        {
            if(s.getMovable())
                s.setNotMovable();
        }
        //For each block beside the player:
        for(Block s: oneRadius)
        {
            int xOffset = 0;
            int yOffset = 0;
            //If it is an obstacle:
            if(s.getObstacle())
            {
                yOffset = s.getY() - getY();
                xOffset = s.getX() - getX();
                //Remove that block and the block beside it from the larger list
                twoRadius.remove(s.getOneObjectAtOffset(xOffset, yOffset, Block.class));
                twoRadius.remove(s);
            }
        }
        //For each block in the larger list:
        for(Block s: twoRadius)
        {
            //If it is not an obstacle and is either horizontal or vertical to the player, that block in in the player’s range
            if(!s.getObstacle() && (s.getX() == getX() || s.getY() == getY()))
            {
                s.setMovable();
            }
        }
        //Set the current block inside the player’s range
        setMovable();
    }

    /**
     * For each spike in the world, iterate the spike stage by one, and check if there are enemies on them
     */
    public void nextSpike() 
    {
        //If spike is at the first stage, go to the next
        if(spike == 1)
        {
            spike++;
        }
        //If spike is at the second stage, go to the third (where it is fully extended)
        else if(spike == 2){
            spike++;
            //Resets appropriate variables for the animation
            spikeLevel = 3;
            extending = true;
            animating = true;

            //If there is an enemy on this block, kill them
            List<Enemy> Enemies = getWorld().getObjectsAt(getX(), getY(),Enemy.class);
            if(Enemies.size() > 0)
            {
                MyWorld world = (MyWorld) getWorld();
                if (Enemies.get(0).getType() == 6)
                {
                    Enemies.get(0).explodeFire();//Explosion for iceman
                }
                if (Enemies.get(0).getType() == 7)
                {
                    Enemies.get(0).explodeIce();//Explosion for iceman
                }
                world.removeEnemy(Enemies.get(0));
                world.death(getX(),getY());
                available = true;
            }
        }
        //If the spike has already extended, move to the first stage
        else if(spike == 3)
        {
            spike = 1;
        }

        //Whether the player can move to this spike or not, sets the appropriate image
        if(movable)
        {
            setImage(spikeImageMovable[spike-1]);
        }
        else
        {
            setImage(spikeImage[spike-1]);
        }
    }    

    /**
     * Changes the block to an alternate image, indicating that the player can move to this location
     */
    public void setMovable()
    {
        movable = true;
        //If there is no spike, set appropriate image
        if(spike == 0)
        {
            setImage("tile_movable.png");
            getImage().setTransparency(90);
        }
        else
        {
            //If there is a spike, set the appropriate image
            if(spike == 3)
                setImage(spikeImageMovable[0]);
            else
                setImage(spikeImageMovable[spike-1]);
        }
    }

    /**
     * Changes the block to an alternate image, indicating the player cannot move to this location
     */
    public void setNotMovable()
    {
        movable = false;
        //If there is no spike, set the appropriate image
        if(spike == 0)
        {
            setImage("tile.png");
            getImage().setTransparency(15);
        }
        //If there is a spike, set the appropriate image
        else
        {
            if(spike == 3)
                setImage(spikeImage[0]);
            else
                setImage(spikeImage[spike-1]);
        }
    }

    public void setNotAvailable()
    {
        available = false;
    }

    public void setAvailableTrue()
    {
        available = true;
    }

    public boolean getAvailable()
    {
        if (available && !occupied && !obstacle)
        {
            return true;
        }
        return false;
    }

    public void setHero()
    {
        available = false;
        occupied = true;
    }

    public void setHeroFalse()
    {
        available = true;
        occupied = false;
    }

    /** Getter method to obtain whether or not the player is on this block
     * 
     * @return boolean True if player is at this location
     */
    public boolean getOccupied()
    {
        return occupied;
    }

    public int getLocationX()
    {
        return placementX;
    }

    public int getLocationY()
    {
        return placementY;
    }

    /**
     * sets the desired Block to a wall, making that location unavailable to move towards
     *
     * @param wallType Type of wall depending on the location of the wall on the map
     */
    public void setWall(int wallType) 
    {
        if(wallType == 1)
        {
            this.setImage("Wall" + Greenfoot.getRandomNumber(4) + ".png");
            GreenfootImage image = getImage();
            image.scale(100, 100);
            setImage(image);
            obstacle = true;
        }
        else if(wallType == 2)
        {
            this.setImage("Wall" + (Greenfoot.getRandomNumber(4)+4) + ".png");
            GreenfootImage image = getImage();
            setImage(image);
            this.setLocation(getX(), getY());
            obstacle = true;
        }
        if(wallType == 3)
        {
            this.setImage("Wall8.png");
            GreenfootImage image = getImage();
            image.scale(100, 101);
            setImage(image);
            obstacle = true;
        }
    }     

    /**
     * Getter method to know if the spike is extending/retracting
     * 
     * @param spike The stage of the spike
     */
    public void setSpike(int spike) 
    {
        this.setImage(spikeImageMovable[spike-1]);
        this.spike = spike;
        nextSpike();
    }   

    /**
     * Getter method to know if the spike is extending/retracting
     * 
     * @return boolean True if spike is currently animating
     */
    public boolean getAnimating()
    {
        return animating;
    }

    /**
     * Getter method to obtain the stage of the spike
     * 
     * @return int The stage of the spike (0 - no spike, 1 - hidden, 2 - pointing out, 3 - extended)
     */
    public int getSpike()
    {
        return spike;
    }

    /**
     * Getter method to obtain if the player can move to this block
     * 
     * @return boolean True if player can move to this block
     */
    public boolean getMovable()
    {
        return movable;
    }

    /**
     * Getter method to obtain if there is an obstacle at this location
     * 
     * @return boolean True there is a wall at this block
     */
    public boolean getObstacle()
    {
        return obstacle;
    }

}
