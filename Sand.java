import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Created whenever the boss attacks
 * Only lasts for one animation
 * 
 * @author Alexander
 * @version January 2019
 */
public class Sand extends Actor
{
    //Initializing variables
    private int pic = 0;
    private MyWorld world;
    private GreenfootImage[] image = new GreenfootImage[3];
    private Block [][] grid;
    public Sand()
    {
        //Assigning images into array
        for (int i = 0; i < 3; i ++)
        {
            image[i] = new GreenfootImage("Sand"+ (i) + ".png");
            image[i].scale(100,100);
        }
        setImage(image[0]);
    }
    
    /**
     * Act - do whatever the Death wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        kill();
        animate();
        
    }
    public void addedToWorld (World w)
    {
        //World can now be called using world variable
        world = (MyWorld) getWorld();
        grid = world.getGrid();
    }
    
    private void kill()
    {
        //Get all players and enemies that are on the same block
        List <Player> user = getWorld().getObjectsAt(getX(),getY(),Player.class);
        List <Enemy>  enemy = getWorld().getObjectsAt(getX(),getY(),Enemy.class);
        if (user.size() > 0)//Remove player
        {
            user.get(0).removePlayer();
        }
        if (enemy.size()> 0)//Remove enemies
        {
            for (Enemy e: enemy)
            {
                if (e.getType() !=9)
                {
                    world.removeEnemy(e);
                    grid[e.getLocationY()][e.getLocationX()].setAvailableTrue();
                }
            }
        }
    }
    
    private void animate()
    {
        if (world.getFrames() % 10 == 0)//Every 10 frames change to next picture
        {
            if (pic == 3)//After animation is done, remove object
            {
                world.removeObject(this);
            }
            else
            {
                setImage(image[pic]);
                pic ++;
            }
        }  
    } 
}
