import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The game over screen when the player dies before reaching the exit
 * 
 * @author Justin and Deston
 * @version January 2019
 */
public class GameLost extends MyWorld
{
    private Retry yes;
    private Exit no;
    private Transition screen;
    private GreenfootImage myImage;
    /**
     * Constructor for objects of class GameLost.
     * 
     */
    public GameLost()
    {   
        //Creting buttons to replay game
        yes = new Retry();
        addObject(yes, 440, 400);
        no = new Exit();
        addObject(no, 750, 400);
        screen = new Transition();
        addObject(screen, 1169/2, 668/2);
        //Remove unnecessary objecys
        removeObject(timer);
        removeObject(shield);
        myImage = new GreenfootImage("YouDied.png");
        myImage.scale(1169,668 );

        screen.setImage(myImage);
        setPaintOrder(Retry.class, Exit.class, Transition.class);

        //Sound taken from LT Sound Effects at https://www.youtube.com/watch?v=TpAMArdUMcw
        GreenfootSound gameLose = new GreenfootSound("GameLose.mp3");
        gameLose.play();
    }

    /**
     * Setter method to obtain the player’s total time from when they started the game
     * @param minText The total number of minutes passed since the start of the game
     * @param secText The total number of seconds passed since the start of the game (residual)
     */
    public void setTime(String minText, String secText)
    {
        //Draws a game over message and the user’s score onto the background
        GreenfootImage text = getBackground();
        text.setColor(Color.WHITE);
        text.setFont(new Font("Unispace", false, false, 60));
        text.drawString("Your time was: " + minText +":"+secText, 310, 180);

        setBackground(text);
    }

    /**
     * Act method, checking user’s input
     */

    public void act()
    {
        //If user clicked something:
        if (Greenfoot.mouseClicked(null))
        {
            // If user presses yes, game will restart
            if (Greenfoot.mouseClicked(yes)){
                LvlWorld next = new LvlWorld(1, true);
                Greenfoot.setWorld(next);
            }
            // If user presses no, game will restart
            if (Greenfoot.mouseClicked(no)){
                Greenfoot.stop();
            }
        }
    }
}
