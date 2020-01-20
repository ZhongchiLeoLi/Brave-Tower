import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
/**
 * The game over screen when the player reaches the exit with the treasure
 * 
 * @author Justin
 * @version January 2019
 */
public class GameWon extends MyWorld
{
    private Retry yes;
    private Exit no;
    private Transition screen;
    private GreenfootImage text = new GreenfootImage("Success.png");
    /**
     * Constructor for objects of class GameOver.
     * 
     */
    public GameWon()
    {    
        yes = new Retry();
        addObject(yes, 440, 230);
        no = new Exit();
        addObject(no, 750, 230);
        screen = new Transition();
        addObject(screen, 1169/2, 668/2);
        //Remove unnecessary objects
        removeObject(timer);
        removeObject(shield);

        text.scale(1169,608);

        setPaintOrder(Retry.class, Exit.class, Transition.class);

        //Sound taken from Koben at https://www.youtube.com/watch?v=YUSRyEmelrQ
        GreenfootSound gameWon = new GreenfootSound("GameWon.mp3");
        gameWon.play();

    }

    /**
     * Setter method to obtain the player’s total time from when they started the game
     * @param minText The total number of minutes passed since the start of the game
     * @param secText The total number of seconds passed since the start of the game (residual)
     */
    public void setTime(String minText, String secText)
    {
        //Draws a game over message and the user’s score onto the background
        boolean done = false;
        text.setColor(Color.WHITE);
        text.setFont(new Font("Unispace", false, false, 40));
        text.drawString("Your time was: " + minText+":"+secText,400, 280);
        text.drawString("Highscores:",470, 325);

        //Obtain the number of minutes and seconds the user took to finish the game
        int min = Integer.parseInt(minText);
        int sec = Integer.parseInt(secText);

        //Uses BubbleSort method to add the user’s score, and update the score text file with the top five scores
        BubbleSort sort = new BubbleSort();
        sort.sortAndRewrite("scores.txt", min,sec);

        //Scanner used for user input
        Scanner input = new Scanner (System.in);
        try{
            input = new Scanner(new File("scores.txt"));
        }
        catch(FileNotFoundException e){
            System.out.println("File was not found");
        }
        //Close scanner
        int y = 330;//Initial y value of the text
        for(int i=1; i<6; i++)
        {
            try{
                //Reads the shortest times, from smallest to greatest
                int time = Integer.parseInt(input.nextLine());
                min = time/60;//Number of minutes user took
                sec = time%60;//Number of seconds user took (residual)

                //Converts time into text format
                if(min < 10)
                    minText = "0" + min;
                else
                    minText = min + "";

                if(sec < 10)
                    secText = "0" + sec;
                else
                    secText = sec + "";

                y += 50;
                //Draws the user’s score onto the background
                text.drawString(i + ". " + minText + ":" + secText, 504, y);
                done = false;
            }
            catch (Exception e){
                //If there are no more lines to read, loop will exit
                done = true;
            }
        }
        screen.setImage(text);

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

