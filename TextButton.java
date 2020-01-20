import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A Generic Button to display text that is clickable. Owned by a World, which controls click
 * capturing.
 * 
 * @author Jordan Cohen
 * @version v0.1.5
 */
public class TextButton extends Actor
{
    // Declare private variables
    private GreenfootImage myImage;
    private String buttonText;
    private int textSize;

    /**
     * Construct a TextButton with a given String at the default size
     * 
     * @param text  String value to display
     * 
     */
    public TextButton (String text)
    {
        this(text, 20);
    }

    /**
     * Construct a TextButton with a given String and a specified size
     * 
     * @param text  String value to display
     * @param textSize  size of text, as an integer
     * 
     */
    public TextButton (String text, int textSize)
    {
        buttonText = text;
        GreenfootImage tempTextImage = new GreenfootImage (text, textSize, Color.WHITE, Color.BLACK);
        myImage = new GreenfootImage (tempTextImage.getWidth() + 8, tempTextImage.getHeight() + 8);
        myImage.setColor (Color.BLACK);
        myImage.fill();
        myImage.drawImage (tempTextImage, 4, 4);

        myImage.setColor (Color.WHITE);
        myImage.drawRect (0,0,tempTextImage.getWidth() + 7, tempTextImage.getHeight() + 7);
        setImage(myImage);
    }

    /**
     * Change the text displayed on this Button
     * 
     * @param   text    String to display
     * 
     */
    public void update (String text)
    {
        buttonText = text;
        GreenfootImage tempTextImage = new GreenfootImage (text, 20, Color.WHITE, Color.BLACK);
        myImage = new GreenfootImage (tempTextImage.getWidth() + 8, tempTextImage.getHeight() + 8);
        myImage.setColor (Color.BLACK);
        myImage.fill();
        myImage.drawImage (tempTextImage, 4, 4);

        myImage.setColor (Color.WHITE);
        myImage.drawRect (0,0,tempTextImage.getWidth() + 7, tempTextImage.getHeight() + 7);
        setImage(myImage);
    }

}
