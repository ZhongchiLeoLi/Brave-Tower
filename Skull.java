 import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Skull display that may flip randomly horizontally when spawned into the world
 * 
 * @author Alexander
 * @version January 2019
 */
public class Skull extends Actor
{
    public Skull()
    {
        getImage().scale(100,100);
        if (Greenfoot.getRandomNumber(2) == 1)//Flip horizontally
        {
            getImage().mirrorHorizontally();
        }
    }

}
