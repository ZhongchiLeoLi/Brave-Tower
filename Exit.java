import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Exit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Exit extends Actor
{
    private GreenfootImage myImage;
    public Exit()
    {
        myImage = new GreenfootImage("Exit.png");
        myImage.scale(175,81);
        setImage(myImage);
    }
}
