import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;
/**
 * Subclass of MyWorld that inherits important variables and methods used to create specific 
 * floors depending on the state of the player, as well as placing monsters and setting up 
 * the terrain
 * 
 * @author Daniel
 * @version January 2019
 */
public class LvlWorld extends MyWorld
{
    private Player player = new Player();
    /**
     * Constructor for objects of class LvlWorld.
     * 
     * @param lvlType Type of level to create
     * @param reset   If True resets the certain variables, used for restarting the game
     */
    public LvlWorld(int lvlType, boolean reset)
    {
        if (reset) //If world came from the starting screen
        {
            floor = 1;
            floorNumbers = 1;
            hasTreasure = false;
            newStage.play();
        }

        //Assigning values to needed variables
        grid = new Block [5][9];
        enemyTurn = 0;

        enemyList = new ArrayList<Enemy>();
        displayList = new ArrayList<Display>();

        //Create basic grid of the level
        createGrid();

        //Setting values so that simulation runs at 60 fps
        framesPerSecond = 60;
        secondsPerFrame = 1.0/framesPerSecond;
        lastFrame = System.nanoTime();
        frames = 1;

        //Adding order button into the world
        checkOrder = new Display (50,50, "Order.png");
        addObject (checkOrder,960,35);
        pressed = false;

        //Determines what kind of level it is
        layout(lvlType);

        //Floor number display
        floorNumber = new Display (318,75, "levels-" + floorNumbers + ".png");
        addObject (floorNumber,200,35);

        if (!hasTreasure) //Spawns creatures
        {
            int choice = 0;//Determines number of creates to be spawned
            if (floor < 3) //Earlier levels
            {
                choice = Greenfoot.getRandomNumber(3) + 1;
            }
            if (floor < 6)//Mid levels
            {
                choice = Greenfoot.getRandomNumber (4) + 2;
            }
            if (floor == 5) choice = 2;
            for (int i = 0 ; i < choice; i ++) 
            {
                int random = Greenfoot.getRandomNumber(8) + 1;
                createMonsters (1,random);
            }
        }

        if (hasTreasure)//When coming back down levels get harder
        {
            int choice =  Greenfoot.getRandomNumber(4) + 2;
            for (int i = 0 ; i < choice ; i ++) 
            {
                int random = Greenfoot.getRandomNumber(8) + 1;
                if (random == 3) random = 1;
                createMonsters (1,random);
            }
        }
        //Make sure certain objects are on top of others
        setPaintOrder(Transition.class,Player.class,Boss.class, Enemy.class,Display.class,Torch.class,Skull.class, Block.class, ShieldDisplay.class  );
    }

    private void layout(int lvlType)
    {
        if(lvlType == 1)//Normal levels going up
        {
            //Player spawns in bottom left 
            addObject(player, grid[4][0].getX(), grid[4][0].getY());
            grid[4][0].setHero();
            player.enterStage(grid[4][0].getX(),grid[4][0].getY());

            grid[4][0].setOccupied();

            heroX = 0;
            heroY = 4;
        }
        else if(lvlType == 2)//Boss room
        {
            //Player spawns in bottom left 
            this.setBackground("BossRoom.png");
            addObject(player, grid[4][0].getX(), grid[4][0].getY());
            grid[4][0].setHero();
            player.enterStage(grid[4][0].getX(),grid[4][0].getY());
            //Storing position of the player
            heroX = 0;
            heroY = 4;

            //Spawn treasure
            Treasure treasure = new Treasure();
            addObject (treasure, grid[0][8].getX(), grid[0][8].getY());
            grid[4][0].setOccupied();

            //Spawn boss
            Boss boss = new Boss(2,4);
            enemyList.add(boss);
            addObject (boss, grid[2][4].getX(),grid[2][4].getY());
            grid[2][4].setNotAvailable();
        }
        else if(lvlType == 3)//Descending normal levels
        {
            //Player spawns in top right
            addObject(player, grid[0][8].getX(), grid[0][8].getY());
            grid[0][8].setHero();
            player.enterStage(grid[0][8].getX(),grid[0][8].getY());
            grid[0][8].setOccupied();
            //Storing position of the player
            heroX = 8;
            heroY = 0;
        }
    }
}
