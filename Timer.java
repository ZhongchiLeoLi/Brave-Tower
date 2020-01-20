import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The timer class is to procedurally increment the display to show how long the player has taken in their game, and also holds the final time if they win the game.
 * 
 * @author Justin and Deston
 * @version January 2019
 */
public class Timer extends Actor
{

    private long lastFrame;//Last frame checked
    private long current;//The current frame
    private long elapsed = 0;//Number of milliseconds passed since the start of the floor
       private int min = 0;//Number of minutes elapsed since start of game
private int sec = 0;//Number of seconds elapsed (residual) since start of game
    private String secText = "";//Number of seconds, in a text format
    private String minText = "";//Number of minutes, in a text format

/**
 * Initializes the starting time for the current floor, and updates the stopwatch to the total time
 */
    public Timer()
    {
        lastFrame = System.nanoTime();

        updateTime();
    }

/**
 * Updates the number of seconds and minutes elapsed into a text format, and displays that in the stopwatch
 */
    private void updateTime()
    {
//Converts seconds over 60 into minutes
        if(sec == 60)
        {
            min++;
            sec -= 60;
        }

//Updates time into a text format       
 if(min < 10)
            minText = "0" + min;
        else
            minText = min + "";

        if(sec < 10)
            secText = "0" + sec;
        else
            secText = sec + "";

//Updates the stopwatch by redrawing the image
        GreenfootImage text = new GreenfootImage(150, 100);
        text.setColor(Color.BLACK);
        text.setFont(new Font("Unispace", false, false, 30));
        text.drawString(minText+":"+secText, 00, 50);
        this.setImage(text);
    }

    /**
     * Repeatedly updates the stopwatch every second
     */
    public void act() 
    {
//If one second has passed since the time was updated, increase the seconds by one and update the display
        current = System.nanoTime();
        elapsed = (current - lastFrame) / 1000000;
        if(elapsed >= 1000)
        {
            lastFrame = current;
            sec++;
            updateTime();
        }
    }

    /**
     * Setter method to specify the total elapsed time
     * @param min The number of minutes taken
     * @param sec The number of seconds taken (residual)
     */
    public void setTime(int min, int sec)
    {
        this.min = min;
        this.sec = sec;
        updateTime();
    }
    
/**
     * Getter method to obtain the number of seconds, in text form
* 
* @return String the number of seconds taken, in text form
     */
    public String getSecText()
    {
        return secText;
    }
    
/**
     * Getter method to obtain the number of minutes, in text form
* 
* @return String the number of minutes taken, in text form
     */
    public String getMinText()
    {
        return minText;
    }

/**
     * Getter method to obtain the number of seconds
* 
* @return int the number of seconds taken
     */
    public int getSec()
    {
        return sec;
    }
    
/**
     * Getter method to obtain the number of minutes
* 
* @return int the number of minutes taken
     */
    public int getMin()
    {
        return min;
    }
}    


