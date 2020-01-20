import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
/**
 * The MyWorld class is the foundation of the entire game, holding important variables 
 * that provide all other classes the information needed to perform their tasks. This includes 
 * player and enemy locations and spawning, and terrain generation, as well as the grid that 
 * the game revolves around
 * 
 * This class also handles the switching of floors based on player location, and creating the 
 * gameover and gamewon screens
 * 
 * @author Alexander, Daniel, Deston, Justin, Leo
 * @version January 2019
 */
public class MyWorld extends World
{
    //Initializing variables for grid, enemies and player
    protected Block [][] grid;
    protected ArrayList<Enemy> enemyList;
    protected int heroX;
    protected int heroY;
    protected int enemyTurn;
    protected boolean sandAttack = false;
    protected static boolean hasTreasure = false;

    //Initializing world variables for floor to track progress in game
    protected static int floor = 1;
    protected static int floorNumbers = 1;
    protected Transition screen = new Transition();
    protected Treasure treasure = new Treasure();

    //Initializing variables to run simulation using nanotime
    protected long lastFrame;
    protected double framesPerSecond;
    protected double secondsPerFrame;
    protected int frames = 1;

    //Display variables
    protected Display checkOrder;
    protected ArrayList<Display> displayList;
    protected boolean pressed;
    protected Display floorNumber;

    //Visual variables
    protected Timer timer;
    protected ShieldDisplay shield;

    //Sound variables
    protected GreenfootSound newStage = new GreenfootSound("NewStage.mp3");
    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public MyWorld()
    {    
        // Create a new world with 1169x668 cells with a cell size of 1x1 pixels.
        super(1169, 668, 1);
        if(timer == null) //Creating timer to track players time to complete game
        {
            timer = new Timer();
            addObject(timer,1080, 50);
        }
        if (shield == null) //Displays how many shields are left
        {
            shield = new ShieldDisplay();
            addObject (shield, 584, 35);
        }
    }

    public void act()
    {
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

        orderPress();
        sandDone();
    }

    private void orderPress()
    {
        if (Greenfoot.mouseClicked(checkOrder) && !pressed)//If order button was pressed
        {           
            pressed = true;//Track for next time it is pressed
            int num = 1;
            for (Enemy e: enemyList)//Creating order numbers for each enemy
            {
                Display order = new Display (34,48, "OrderNumber-" + num +".png");
                displayList.add(order);
                addObject (order,e.getX(), e.getY()-50);
                num++;
            }
        }
        else if (Greenfoot.mouseClicked(checkOrder) && pressed)//To delete order numbers
        {
            pressed = false;//Resets order button
            removeObjects(displayList);//Deletes all order numbers
            displayList.clear();//Clears list of ordernumbers
        }
        else
        {
            int num = 0;
            for (Display d: displayList)//Order number tracks enemies while being displayed
            {
                d.setLocation(enemyList.get(num).getX(), enemyList.get(num).getY() - 60);
                num ++;
            }
        }
    }

    /**
     * Creates grid that is used to determine player and enemy locations as well as placing objects without the need to use X and Y coordinates
     */
    protected void createGrid()
    {
        int xBuffer = 131;
        int yBuffer = 112;
        //Creates a grid of block objects in a 2d array
        for (int i = xBuffer; i < (xBuffer + 9); i ++)//For X axis
        {
            for (int j = yBuffer; j < (yBuffer + 5); j ++)//For Y axis
            {
                Block block = new Block(i-xBuffer,j-yBuffer, false, false);
                addObject(block, i + (99 * (i-xBuffer) + 50), j + (99 * (j-yBuffer) + 50));
                grid [j-yBuffer][i-xBuffer] = block;
            }
        }
        setMap();
    }

    private void setMap()
    {
        //Randomly selects a map for the player 
        int num = Greenfoot.getRandomNumber(5);//5 options
        if(num == 0 && floor != 5)//First map
        {
            //Setting up walls
            grid[0][4].setWall(2);
            grid[2][2].setWall(1);
            grid[2][6].setWall(1);
            grid[4][4].setWall(3);

            //Setting spikes up
            grid[1][2].setSpike(Greenfoot.getRandomNumber(2) + 1);
            grid[2][1].setSpike(Greenfoot.getRandomNumber(2) + 1);
            grid[3][2].setSpike(Greenfoot.getRandomNumber(2) + 1);
            grid[1][6].setSpike(Greenfoot.getRandomNumber(2) + 1);
            grid[2][7].setSpike(Greenfoot.getRandomNumber(2) + 1);
            grid[3][6].setSpike(Greenfoot.getRandomNumber(2) + 1);

            //Adding extra items
            Torch torch = new Torch ();
            addObject (torch, grid[0][0].getX(), grid[0][0].getY() - 80);
            torch = new Torch();
            addObject (torch, grid[0][1].getX(), grid[0][1].getY() - 80);
            torch = new Torch();
            addObject (torch, grid[0][7].getX(), grid[0][7].getY() - 80);
            torch = new Torch();
            addObject (torch, grid[0][8].getX(), grid[0][8].getY() - 80);
        }
        if(num == 1 && floor != 5)//Second map
        {
            //Setting up walls
            for(int j = 3; j < 6; j++)
            {
                grid[0][j].setWall(2);
                grid[4][j].setWall(3);
            }
            grid[2][1].setWall(1);
            grid[2][2].setWall(1);
            grid[2][6].setWall(1);
            grid[2][7].setWall(1);

            //Setting up spikes
            for (int i = 1; i < 4; i++ )
            {
                grid[i][3].setSpike(1);
                grid[i][5].setSpike(1);
            }

            //Adding extra items
            Torch torch = new Torch ();
            addObject (torch, grid[0][2].getX(), grid[0][2].getY() - 80);
            torch = new Torch();
            addObject (torch, grid[0][7].getX(), grid[0][7].getY() - 80);
            torch = new Torch();
            addObject (torch, grid[3][6].getX(), grid[3][6].getY() - 80);
            Skull skull = new Skull();
            addObject (skull, grid[0][1].getX(), grid[0][1].getY() - 90);
            skull = new Skull();
            addObject (skull, grid[0][6].getX(), grid[0][6].getY() - 90);
        }
        if(num == 2 && floor != 5)//Third map
        {
            //Setting up walls
            grid[2][1].setWall(3);
            grid[3][1].setWall(1);
            grid[2][4].setWall(1);
            grid[1][7].setWall(3);
            grid[2][7].setWall(1);

            //Setting up spikes
            grid[1][1].setSpike(Greenfoot.getRandomNumber(2) + 1);
            grid[2][2].setSpike(Greenfoot.getRandomNumber(2) + 1);
            grid[2][6].setSpike(Greenfoot.getRandomNumber(2) + 1);
            grid[3][7].setSpike(Greenfoot.getRandomNumber(2) + 1);

            //Add extra items
            Torch torch = new Torch ();
            addObject (torch, grid[0][1].getX(), grid[0][1].getY() - 80);
            torch = new Torch();
            addObject (torch, grid[0][3].getX(), grid[0][3].getY() - 80);
            torch = new Torch();
            addObject (torch, grid[0][6].getX(), grid[0][6].getY() - 80);
            Skull skull = new Skull();
            addObject (skull, grid[0][0].getX(), grid[0][0].getY() - 90);
            skull = new Skull();
            addObject (skull, grid[4][1].getX(), grid[4][1].getY() - 90);
        }
        if(num == 3 && floor != 5)//Fourth map
        {
            //Setting up walls
            grid[1][1].setWall(1);
            grid[3][1].setWall(1);
            grid[0][4].setWall(2);
            grid[4][4].setWall(3);
            grid[1][7].setWall(1);
            grid[3][7].setWall(1);

            //Setting up spikes
            grid[2][1].setSpike(Greenfoot.getRandomNumber(2) + 1);
            grid[2][3].setSpike(Greenfoot.getRandomNumber(2) + 1);
            grid[2][5].setSpike(Greenfoot.getRandomNumber(2) + 1);
            grid[1][4].setSpike(Greenfoot.getRandomNumber(2) + 1);
            grid[3][4].setSpike(Greenfoot.getRandomNumber(2) + 1);
            grid[2][7].setSpike(Greenfoot.getRandomNumber(2) + 1);

            //Adding extra items
            Torch torch = new Torch ();
            addObject (torch, grid[1][4].getX(), grid[1][4].getY() - 80);
            torch = new Torch();
            addObject (torch, grid[0][8].getX(), grid[0][8].getY() - 80);
            torch = new Torch();
            addObject (torch, grid[4][1].getX(), grid[0][6].getY() - 80);
            Skull skull = new Skull();
            addObject (skull, grid[0][5].getX(), grid[0][5].getY() - 90);
            skull = new Skull();
            addObject (skull, grid[4][7].getX(), grid[4][7].getY() - 90);
        }
        if(num == 4 && floor != 5)//Fifth map
        {
            //Setting up walls
            grid[1][1].setWall(1);
            grid[1][2].setWall(1);
            grid[3][6].setWall(1);
            grid[3][7].setWall(1);

            //Setting up spikes
            grid[3][2].setSpike(Greenfoot.getRandomNumber(2) + 1);
            grid[4][2].setSpike(Greenfoot.getRandomNumber(2) + 1);
            grid[2][4].setSpike(Greenfoot.getRandomNumber(2) + 1);
            grid[0][6].setSpike(Greenfoot.getRandomNumber(2) + 1);
            grid[1][6].setSpike(Greenfoot.getRandomNumber(2) + 1);

            //Adding extra items
            Torch torch = new Torch ();
            addObject (torch, grid[0][1].getX(), grid[0][1].getY() - 80);
            torch = new Torch();
            addObject (torch, grid[0][6].getX(), grid[0][6].getY() - 80);
            torch = new Torch();
            addObject (torch, grid[2][1].getX(), grid[2][1].getY() - 80);
            torch = new Torch();
            addObject (torch, grid[2][2].getX(), grid[2][2].getY() - 80);
            Skull skull = new Skull();
            addObject (skull, grid[0][5].getX(), grid[0][5].getY() - 90);
            skull = new Skull();
            addObject (skull, grid[4][7].getX(), grid[4][7].getY() - 90);
        }
        if(floor == 5)//Boss floor
        {
            //Setting up walls
            grid[1][1].setWall(1);
            grid[1][1].getImage().setTransparency(0);
            grid[1][2].setWall(1);
            grid[1][2].getImage().setTransparency(0);
            grid[3][6].setWall(1);
            grid[3][6].getImage().setTransparency(0);
            grid[3][7].setWall(1);
            grid[3][7].getImage().setTransparency(0);

            //Add extra stuff
            Skull skull = new Skull();
            addObject (skull, grid[4][6].getX(), grid[4][6].getY() - 90);
            skull = new Skull();
            addObject (skull, grid[2][1].getX(), grid[2][1].getY() - 90);
        }
    }

    /**
     * Adds certain monsters to the map when the world is generated
     * 
     * @param monsters Number of monster
     * @param type     Type of monster
     */
    protected void createMonsters(int monsters, int type)
    {
        //Creates as many monsters as the variable "monsters"
        for (int i = 0; i < monsters; i ++)
        {
            //Chooses a random location in the grid
            int locationX = (Greenfoot.getRandomNumber(5)+3);
            int locationY = Greenfoot.getRandomNumber(5);
            if(hasTreasure)//So that monster doenst spawn on chest
            {
                locationX = Greenfoot.getRandomNumber(5);
                locationY = (Greenfoot.getRandomNumber(3)+2);
            }
            //If the grid spot is available
            if (grid[locationY][locationX].getAvailable() == true && grid[locationY][locationX].getObstacle() == false && grid[locationY][locationX].getSpike() == 0)
            {
                grid[locationY][locationX].setNotAvailable();//Set grid spot to not available
            }
            else
            {       
                //keep switching grid placements until a location that is available comes up
                while (grid[locationY][locationX].getAvailable() == false || grid[locationY][locationX].getOccupied() || grid[locationY][locationX].getObstacle() || grid[locationY][locationX].getSpike() == 0)
                {
                    locationX = (Greenfoot.getRandomNumber(5)+3);
                    locationY = Greenfoot.getRandomNumber(5);
                    if(hasTreasure)
                    {
                        locationX = Greenfoot.getRandomNumber(5);
                        locationY = (Greenfoot.getRandomNumber(3)+2);
                    }
                }
                grid[locationY][locationX].setNotAvailable();//Set grid spot to not available
            }
            if (type == 1)//Spawns a slime at the desired grid location
            {
                Slime slime = new Slime(locationY, locationX);
                enemyList.add(slime);
                addObject (slime, grid[locationY][locationX].getX(),grid[locationY][locationX].getY());
            }
            if (type == 2)//Spawns a bat at the desired grid location
            {
                Bat bat = new Bat(locationY, locationX);
                enemyList.add(bat);
                addObject (bat, grid[locationY][locationX].getX(),grid[locationY][locationX].getY());
            }
            if (type == 3)//Spawns a spider at the desired grid location
            {
                Spider spider = new Spider(locationY, locationX);
                enemyList.add(spider);
                addObject (spider, grid[locationY][locationX].getX(),grid[locationY][locationX].getY());
            }
            if (type == 4)//Spawns a worm at the desired grid location
            {
                Worm worm = new Worm(locationY, locationX);
                enemyList.add(worm);
                addObject (worm, grid[locationY][locationX].getX(),grid[locationY][locationX].getY());
            }
            if (type == 5)//Spawns a chest at the desired grid location
            {
                Chest chest = new Chest(locationY, locationX);
                enemyList.add(chest);
                addObject (chest, grid[locationY][locationX].getX(),grid[locationY][locationX].getY());
            }
            if (type == 6)//Spawns a fireman at the desired grid location
            {
                Fireman fire = new Fireman(locationY, locationX);
                enemyList.add(fire);
                addObject (fire, grid[locationY][locationX].getX(),grid[locationY][locationX].getY());
            }
            if (type == 7)//Spawns an iceman at the desired grid location
            {
                Iceman ice = new Iceman(locationY, locationX);
                enemyList.add(ice);
                addObject (ice, grid[locationY][locationX].getX(),grid[locationY][locationX].getY());
            }
            if (type == 8)//Spawns a mirror at the desired grid location
            {
                Mirror mirror = new Mirror(locationY, locationX);
                enemyList.add(mirror);
                addObject (mirror, grid[locationY][locationX].getX(),grid[locationY][locationX].getY());
            }
        } 
    }

    /**
     * Checks whether or not the player has reached the end of a floor when meeting certain requirements, as well as setting the players current location
     */
    protected void playerLocation()
    {
        if(grid[0][8].getOccupied() && !hasTreasure && floor < 4)//If user has reached the stairs
        {
            floor++;
            floorNumbers++;
            newStage.play();//play transitioning music
            fadeOut();
            LvlWorld next = new LvlWorld(1, false);//Switch back to basic stage

            Timer timerNext = (Timer)next.getObjects(Timer.class).get(0);//Set next timer
            timerNext.setTime(timer.getMin(),timer.getSec());
            ShieldDisplay shieldNext = next.getObjects(ShieldDisplay.class).get(0);//Set next shield bar
            shieldNext.setCurrentHealth(shield.getLives());

            Greenfoot.setWorld(next);//Switch worlds
        }
        else if(grid[0][8].getOccupied() && !hasTreasure && floor == 4)//Going into the boss room
        {
            floor++;
            floorNumbers++;
            newStage.play();//play transitioning music
            fadeOut();
            LvlWorld next = new LvlWorld(2, false);//Switch to boss room

            Timer timerNext = (Timer)next.getObjects(Timer.class).get(0);//Set next timer
            timerNext.setTime(timer.getMin(),timer.getSec());
            ShieldDisplay shieldNext = next.getObjects(ShieldDisplay.class).get(0);//Set next shield display
            shieldNext.setCurrentHealth(shield.getLives());

            next.addObject (treasure, 981, 162);//Add treasure box
            Greenfoot.setWorld(next);//Switch worlds
        }
        else if(grid[0][8].getOccupied() && !hasTreasure && floor == 5)//Taking the treasure
        {
            hasTreasure = true;
            removeObjects(getObjects(Treasure.class));//Remove treasure
        }
        else if(grid[4][0].getOccupied() && floor == 1 && hasTreasure)//If reached all the way down
        {
            newStage.play();//Play transitioning music
            fadeOut();
            GameWon next = new GameWon();
            Timer timer = (Timer)getObjects(Timer.class).get(0);//Sets next timer
            next.setTime(timer.getMinText(), timer.getSecText());
            Greenfoot.setWorld(next);//Switch worlds to winning screen
        }
        else if(grid[4][0].getOccupied() && hasTreasure)//If going back down
        {
            floor--;
            floorNumbers++;
            fadeOut();
            newStage.play();
            LvlWorld next = new LvlWorld(3, false);
            Timer timerNext = (Timer)next.getObjects(Timer.class).get(0);//Set next timer
            timerNext.setTime(timer.getMin(),timer.getSec());
            ShieldDisplay shieldNext = next.getObjects(ShieldDisplay.class).get(0);//Set next shield display
            shieldNext.setCurrentHealth(shield.getLives());
            Greenfoot.setWorld(next);//Switch worlds
        }
        //Finding the position of the hero
        for (int i = 0; i < grid.length; i ++)
        {
            for (int j = 0; j < grid[0].length; j++)
            {
                if (grid [i][j].getOccupied())
                {
                    heroX = j;
                    heroY = i;
                    break;
                }
            }
        }
        if (enemyList.size()>0)
        {
            enemyList.get(0).heroFinder();//First enemies turn to go
        }
        else
        {
            getObjects(Player.class).get(0).setCanClick(); //Player can click again         
        }
    }

    /**
     * Removes enemies from the list of enemies
     * 
     * @param e List of Enemies
     */
    protected void removeEnemy(Enemy e)
    {
        if (displayList.size() > 0) //Remove associated order display
        {
            removeObject (displayList.get(enemyList.indexOf(e)));
            displayList.remove(enemyList.indexOf(e));
        }
        removeObject(e); //remove enemy from world
        enemyList.remove(e); //remove enemy from the list

    }

    /**
     * Runs the enemies turn
     */
    public void enemyTurn()
    {
        enemyTurn ++;
        if (enemyTurn < enemyList.size())
        {
            enemyList.get(enemyTurn).heroFinder();
        }
        else
        {
            getObjects(Player.class).get(0).setCanClick();
            enemyTurn = 0;
        }
    }

    /**
     * Fades the screen to black when reaching the end of a floor
     */
    private void fadeOut()
    {
        addObject(screen, 1169/2, 668/2);
        //Gradually darken the screen
        for(int i = 0; i < 250; i+=5) 
        {
            screen.getImage().setTransparency(i);
            Greenfoot.delay(1);
        }
        removeObject(screen);
    }

    protected void nextTurn()
    {
        for (int i = 0; i < 9; i ++)
        {
            for (int j = 0; j < 5; j ++)
            {
                if(grid[j][i].getSpike() != 0)//increaments each spike 
                    grid[j][i].nextSpike();
            }
        }
        List <Block> block = getObjectsAt(heroX, heroY,Block.class);//Sets block that player is on to occupied
        if(block.size() > 0)
            block.get(0).setOccupied();

    }

    /**
     * Resets floor values
     */
    protected void floorReset(int n)
    {
        floor = n;
        floorNumbers = n;
    }

    /**
     * Creates death object
     */
    protected void death(int x, int y)
    {
        Death death = new Death ();
        addObject (death, x, y);
    }

    /**
     * Creates fire object
     */
    protected void fire(int x, int y)
    {
        Fire fire = new Fire();
        addObject (fire,x, y);
    }

    /**
     * Creates sand object
     */
    protected void sand(int x, int y)
    {
        sandAttack = true; //Stores when a boss attack goes off
        Sand sand = new Sand();
        addObject (sand,x, y);
    }

    protected void sandDone()
    {
        //Enemy's turn after boss finishes sand attack
        if (getObjects(Sand.class).size() == 0 && sandAttack)
        {
            sandAttack = false;
            enemyTurn();
        }
    }

    protected int getPlayerLocationX()
    {
        return heroX;
    }

    protected int getPlayerLocationY()
    {
        return heroY;
    }

    protected void setPlayerLocationX(int x)
    {
        heroX = x;
    }

    protected void setPlayerLocationY(int y)
    {
        heroY = y;
    }

    protected Block[][] getGrid()
    {
        return grid;
    }

    protected int getFrames()
    {
        return frames;
    }

}