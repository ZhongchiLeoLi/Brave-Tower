import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Displays images that can vary in size
 * 
 * @author Alexander
 * @version January 2019
 */
public class Display extends Actor
{
    private GreenfootImage myImage; 
    /**
     * Controls the size and what image to display.
     * @param length The X size of the image.
     * @param width The Y size of the image.
     */
    public Display (int length, int width, String image)
    {
        //scale the images
        myImage = new GreenfootImage (image);
        myImage.scale(length,width);
        //set Image
        setImage (myImage);
    } 
}

