import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The corpses of enemies as they die. 
 * 
 * @author Alexander 
 * @version January 2019
 */
public class Death extends Actor
{
    //Initializing variables for animation
    private int pic = 0;
    private MyWorld world;
    private GreenfootImage[] image = new GreenfootImage[3];
    private boolean dead = false;
    
    public Death()
    {
        //Assign images into an array
        for (int i = 0; i < 3; i ++)
        {
            image[i] = new GreenfootImage("Die"+ (i) + ".png");
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
        if (!dead)
        {
            animate();
        }
    }
    
    public void addedToWorld (World w)
    {
        //World can now be called using world variable
        world = (MyWorld) getWorld();
    }
    
    private void animate()
    {
        if (world.getFrames() % 10 == 0)//Every 10 frames, switch to another image
        {
            setImage(image[pic]);
            pic ++;
        }
        if (pic == 3)//Stops animation
        {
            dead = true;
        }
    } 
}
