import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Decorative torch
 * 
 * @author Alexander, Daniel
 * @version January 2019
 */
public class Torch extends Actor
{
    private int pic = 0;
    private MyWorld world;
    private GreenfootImage[] image = new GreenfootImage[9];
    public Torch()
    {
        //Assign images into an array
        for (int i = 0; i < 9; i ++)
        {
            image[i] = new GreenfootImage("Torch_0"+ (i) + ".png");
            image[i].scale((int)(image[i].getWidth()* 6.25), (int)(image[i].getHeight() * 6.25));
        }
        setImage(image[0]);
        pic = Greenfoot.getRandomNumber(9);//Tortch animation starts on a random image
    }
    
    /**
     * Act - do whatever the Fire wants to do. This method is called whenever
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
    
    public void animate()
    {
        if (world.getFrames() % 5 == 0) //Every 5 frames, change image
        {
            setImage(image[pic]);
            pic ++;
        }
        if (pic == 9)//Resets animation
        {
            pic = 0;
        }
    }
}
