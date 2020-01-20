import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Starting screen for the game
 * Allows the user to click the start button to start playing the game
 * 
 * @author Leo 
 * @version January 2019
 */
public class Start extends World
{
    //Initializing variables to run simulation using nanotime
    protected long lastFrame;
    protected double framesPerSecond;
    protected double secondsPerFrame;
    protected int frames = 1;

    //Inistializing variables for user interface
    private int pic;
    private MyWorld world; //Instance of the world
    private StartButton start;
    private TextButton instructions;
    private GreenfootImage[] image = new GreenfootImage[6];
    //Miscellanous variables
    private Transition screen;
    private GreenfootSound backgroundMusic = new GreenfootSound("Castlevania.mp3");
    /**
     * Constructor for objects of class Start.
     * 
     */
    public Start()
    {
        super(1169, 668, 1);
        start = new StartButton();//Add start button
        addObject(start, 160, 580);
        instructions = new TextButton("  Instructions  ", 40);//Add instructions
        //addObject(instructions, 584, 400);
        screen = new Transition();
        addObject(screen, 1169/2, 668/2);
        backgroundMusic.playLoop();//play background music
        backgroundMusic.setVolume(35);
        for (int n = 0;  n < 6; n++) //Moving animations
        {
            image[n] = new GreenfootImage("StartingScreen"+ (n) + ".png");
            image[n].scale(1169,668 );
        }
        //Setting values so that simulation runs at 60 fpsx
        framesPerSecond = 60;
        secondsPerFrame = 1.0/framesPerSecond;
        lastFrame = System.nanoTime();
        frames = 1;
        screen.setImage (image[0]);
        pic = 0;
        setPaintOrder(StartButton.class, Transition.class);
    }

    public void act()
    {
        animate(20);
        
        if (Greenfoot.mouseClicked(null))
        {
            // If user pressed the start button:
            if (Greenfoot.mouseClicked(start)){
                LvlWorld next = new LvlWorld(1, true);
                fadeOut();//transition screen
                Greenfoot.setWorld(next);//switch worlds
            }

            if (Greenfoot.mouseClicked(instructions)){
                Greenfoot.stop();
            }
        }

        //Checks for current time of the system
        long current  = System.nanoTime();
        //Checks for how long the last frame was from the current frame in milliseconds
        long elapsed = (current = lastFrame) / 1000000;

        //Checks for when a set amount of time has passed in milliseconds
        if (elapsed > secondsPerFrame * 1000)
        {
            //resets the last frame time value
            lastFrame = current;
            frames ++;
            
        }

        //After 60 frames, a second has past
        if (frames > 60)
        {
            //reset frames
            frames = 1;
        }
        
        
    }

    protected void animate (int f)
    {
        if (frames % f == 0)
        {
            screen.setImage (image[pic]);
            pic ++;
            
        }
        if (pic == 6)
        {
            pic = 0;
        }
    }  

    
    protected void fadeOut()
    {
        //Screen will slowly fade into darkness
        for(int i = 0; i < 250; i+=5)
        {
            screen.getImage().setTransparency(i);
            Greenfoot.delay(1);
        }
        removeObject(screen);
    }
}
