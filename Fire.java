import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Created from a fireman dying.
 * Only lasts for one animation
 * 
 * @author Alexander
 * @version January 2019
 */
public class Fire extends Actor
{
    //Initializing variables
    private int pic = 0;
    private MyWorld world;
    private GreenfootImage[] image = new GreenfootImage[5];
    public Fire()
    {
        //Assigning images into array
        for (int i = 0; i < 5; i ++)
        {
            image[i] = new GreenfootImage("Fire"+ (i) + ".png");
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
        animate();
    }
    
    public void addedToWorld (World w)
    {
        //World can now be called using world variable
        world = (MyWorld) getWorld();
    }
    
    private void animate()
    {
        if (world.getFrames() % 10 == 0)//Every 10 frames, switch to next image
        {
            if (pic == 5)//After animation is done, remove object
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
