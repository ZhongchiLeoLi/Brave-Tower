import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;
/**
 * This is the Enemy superclass. It includes fundamental properties of all the enemies
 * such as all the booleans that determines their state and the action methods that 
 * determines their behaviour. 
 * 
 * This class passes down important modular methods that help with path-fiding, animations, 
 * moving, attacking, and responding to the changes in the game environment. Allowing
 * all enemies to share fundamental properties while making each of them special in their
 * own ways. 
 * 
 * 
 * @author Alexander, Daniel, Leo
 * @version January 2019
 */
public abstract class Enemy extends Actor
{
    //Hero variables
    protected MyWorld world = (MyWorld) getWorld();
    protected int heroX;
    protected int heroY;
    protected int placementX;
    protected int placementY;
    private int tempPlacementX;
    private int tempPlacementY;
    
    //Movement variables
    protected int type;
    protected ArrayList<Block> blockList;
    protected Block [][] grid;
    protected final int SPEED = 5;
    private int choice = 0;
    protected boolean move = false;
    protected Player player;
    protected boolean facingRight = false;
    protected boolean attack = false;
    private int min;
    
    //EnemyActions
    protected boolean burrow = false;
    protected boolean unBurrow = false;
    protected boolean transform = false;
    protected boolean transformed = false;
    protected int freeze = 0;
    protected boolean kill = false;
    protected boolean swap = false;
    //Image variables
    protected GreenfootImage[] imageFreeze = new GreenfootImage[2];
    protected GreenfootImage[][] image = new GreenfootImage[10][15];
    protected int pic = 0;
    protected GreenfootSound deathSound;
    protected int hit;
    protected boolean attackMove = false;
    protected int movement;
    protected boolean isIdle;
    protected int frames;
    
    /**
     * This method sets off the enemies to find their way to get to the hero
     * under different circumstances such as frozen or transformed for chest
     * monster
     */
    protected void heroFinder()
    {
        //Get location of the hero
        heroX = world.getPlayerLocationX();
        heroY = world.getPlayerLocationY();
        if (freeze == 0 || type == 7 || type == 9) //If frozen, cant move. Iceman and boss not included
        {
            if (type == 1 || type == 5 && transformed || type == 6 || type == 7 || type == 8 || type == 9) 
            {
                //Slime, chest, fireman, iceman and mirror moves one space 
                possibleMoves(1);
            }
            if (type == 2 )
            {
                //Bat can move over walls
                possibleMoves(2);
            }
            if (type == 3)
            {
                //Spider can move diagonally
                possibleMoves(1,1);
            }
            if (type == 4)
            {
                //Worm
                burrow = true;
            }
            if (type == 5 && !transform && !transformed)
            {
                //Chest monster transforms when player is 2 blocks away from it
                if (Math.abs(heroX - placementX) + Math.abs(heroY - placementY) < 3)
                {
                    transform = true;
                }
                else
                {
                    world.enemyTurn(); //runs its turn after transformed
                }
            }
        }
        else 
        {
            if (type == 5) //Transform the chest if it is frozen
            {
                transform = true;
                transformed = true;
            }
            //Frozen for a turn
            freeze --;
            world.enemyTurn();
        }
    }

    /**
     * This method allows the World to be called using the world variable
     */
    public void addedToWorld (World w)
    {
        world = (MyWorld) getWorld();
        grid = world.getGrid();
    }

    /**
     * This method takes an integer that represent the enemy's moving range and determines
     * all possible moves it can take in that turn. The possible blocks are added to an ArrayList
     * for furthur use. (findBestPath())
     * 
     * @param x Distance enemy can travel horizontally or vertically in one turn
     */
    protected void possibleMoves(int x)
    {
        //Retrieve world's grid system
        MyWorld world = (MyWorld) getWorld();
        blockList = new ArrayList<Block>();
        grid = world.getGrid();
        //check all possible movement less or equal to its maximum moving range
        for (int i = x; i > 0; i --)
        {
            //if left is open
            if (placementX - i >= 0)
            {
                //check blocks on left
                if(grid[placementY][placementX -i].getAvailable() || grid[placementY][placementX - i].getOccupied() )
                {
                    blockList.add(grid[placementY][placementX-i]);
                }
            }
            //if right is open
            if (placementX + i < 9)
            {
                //check blocks on right
                if(grid[placementY][placementX + i].getAvailable() || grid[placementY][placementX + i].getOccupied())
                {
                    blockList.add(grid[placementY][placementX + i]);
                }
            }
            //if below is open
            if(placementY + i < 5)
            {
                //check blocks below
                if(grid[placementY + i][placementX].getAvailable() || grid[placementY + i][placementX].getOccupied())
                {
                    blockList.add(grid[placementY + i][placementX]);
                }
            }
            //if above is open
            if (placementY - i >= 0)
            {
                //check blocks above
                if(grid[placementY - i][placementX].getAvailable() || grid[placementY - i][placementX].getOccupied())
                {
                    blockList.add(grid[placementY - i][placementX]);
                }
            }

        }
        findBestPath();
    }

    /**
     * This method takes an integer that represent the enemy's moving range diagonaly and determines
     * all possible moves it can take in that turn. The possible blocks are added to an ArrayList
     * for furthur use. (findBestPath())
     * 
     * @param x Distance enemy can travel horizontally or vertically in one turn
     */
    protected void possibleMoves(int x, int y)
    {
        //Retrieve world's grid system
        MyWorld world = (MyWorld) getWorld();
        grid = world.getGrid();
        blockList = new ArrayList<Block>();
        // for worm:
        if(type == 4)
        {
            //if top right is open
            if(placementY + x < 5 && placementX + y < 9)
            {
                if(grid[placementY + x][placementX + y].getAvailable() || grid[placementY + x][placementX + y].getOccupied())
                {
                    blockList.add(grid[placementY + x][placementX + y]);  
                }
            }
            //if top left is open
            if(placementY - x > -1 && placementX + y < 9)
            {
                if(grid[placementY - x][placementX + y].getAvailable() || grid[placementY - x][placementX + y].getOccupied())
                {
                    blockList.add(grid[placementY - x][placementX + y]);
                }
            }
            //if bottom right is open
            if(placementY + x < 5 && placementX - y > -1)
            {
                if(grid[placementY + x][placementX - y].getAvailable() || grid[placementY + x][placementX - y].getOccupied())
                {
                    blockList.add(grid[placementY + x][placementX - y]);
                }
            }
            //if bottom left is open
            if(placementY - x > -1 && placementX - y > -1)
            {
                if(grid[placementY - x][placementX - y].getAvailable() || grid[placementY - x][placementX - y].getOccupied())
                {
                    blockList.add(grid[placementY - x][placementX - y]);
                }
            }
        }
        // for worm and spider:
        //if top right is open
        if (placementY + y < 5 && placementX + x < 9)
        {
            if(grid[placementY + y][placementX + x].getAvailable()|| grid[placementY + y][placementX + x].getOccupied())
            {
                blockList.add(grid[placementY +y][placementX +x]);
            }
        }       
        //if top left is open
        if (placementY - y > -1 && placementX + x < 9)
        {
            if(grid[placementY - y][placementX + x].getAvailable()|| grid[placementY - y][placementX + x].getOccupied())
            {
                blockList.add(grid[placementY -y][placementX +x]);
            }
        }
        //if bottom right is open
        if (placementY + y < 5 && placementX - x > -1)
        {
            if(grid[placementY +y][placementX -x].getAvailable()|| grid[placementY + y][placementX -x].getOccupied())
            {
                blockList.add(grid[placementY +y][placementX -x]);
            }
        }
        //if bottom left is open
        if (placementY - y > -1 && placementX - x > -1)
        {
            if(grid[placementY -y][placementX -x].getAvailable()|| grid[placementY - y][placementX -x].getOccupied())
            {
                blockList.add(grid[placementY -y][placementX -x]);
            }
        }
        //end its turn if there is no possible place to move
        if (blockList.size() == 0) world.enemyTurn();
        findBestPath(); 
    }

    /**
     * This method take the ArrayList of blocks collected by possibleMoves and finds the block
     * with the minimum distance to the hero.
     */
    protected void findBestPath()
    {
        min = 100000; // initialize min value
        ArrayList<Block> removeList = new ArrayList<Block>(); //a list that collects all the blocks furthur than the minimum distance
        for (Block b: blockList)
        {
            //calculate distance
            int distance = Math.abs(heroX-b.getLocationX()) + Math.abs(heroY - b.getLocationY());
            if (distance < min)
            {
                min = distance; // update min value
            }
        } 
        for (Block b: blockList)
        {
            int distance = Math.abs(heroX-b.getLocationX()) + Math.abs(heroY - b.getLocationY());
            if (distance > min)
            {
                removeList.add(b); //collect blocks furthur than min value
            }
        } 
        blockList.removeAll(removeList); // remove all blocks furthur than min value 
        player = getWorld().getObjects(Player.class).get(0);
        // if the block is the hero's block
        if (min == 0)
        {
            attack(); // attack the hero
        }
        else
        {
            moveEnemy(); // move the enemy
        }
    }

    /**
     * This method moves the enemy based on their best route/routes
     */
    protected void moveEnemy()
    {
        if (blockList.size() != 0)
        {
            grid[placementY][placementX].setAvailableTrue();//setting original block location available
            //if multiple possible location with same distance to hero:
            if (blockList.size() > 1)
            {
                choice = Greenfoot.getRandomNumber(blockList.size()); //make a random move between the choices
            }
            else
            {
                choice = 0;
            }
            blockList.get(choice).setNotAvailable(); // setting new location unavaible
            placementX = blockList.get(choice).getLocationX();
            placementY = blockList.get(choice).getLocationY();
            move = true; //move enemy
        }
    }

    /**
     * This method conducts the attack animations based on different enemies and direction
     * of the attack, the hero would be able to block the attack with his shield under
     * certain conditions
     */
    protected void attack()
    {           
        //spider
        if (type == 3)
        {
            
            if (player.getFacingRight() && blockList.get(0).getLocationX() < placementX || !(player.getFacingRight()) && blockList.get(0).getLocationX() > placementX) //Check if player is facing spider
            {
                player.reduceLives(); //Reduce number of shields
                world.shield.decreaseShield();
            }
            else //If player doesnt block they die
            {
                kill = true; 
            }
        }
        else if (blockList.get(0).getLocationY() == placementY && type!= 4 && type!= 8)//Check if enemies are on the same Y axis and if the enemy is not a worm or mirror
        {
            if (player.getFacingRight() && blockList.get(0).getLocationX() < placementX  || !(player.getFacingRight()) && blockList.get(0).getLocationX() > placementX)//Check if player is facing towards enemy
            {
                player.reduceLives();//Reduce number of shields
                world.shield.decreaseShield();
            }
            else
            {
                kill = true;//Player will die
            }
        }
        else//If worm or mirror player will always die
        {
            kill = true;
        }
        if (type!= 4 && !kill)attack = true; //If player does not die then it plays the attack movement
        move = true;
        //Setting up temp positions so that enemy can return back to its original position
        tempPlacementX = placementX;
        tempPlacementY = placementY;
        //Set x and y values for the new block that enemy will travel to
        placementX = blockList.get(0).getLocationX();
        placementY = blockList.get(0).getLocationY();

    }
    
    /**
     * Enemies will move towards the hero and attack if it is within range.
     */
    protected void moveTowardsHero()
    {
        isIdle = false; //Makes sure that the idle animation is not played
        if (grid[placementY][placementX].getX() != getX() || grid[placementY][placementX].getY() != getY()) //Checks to see if the the enemy has reached the desired location
        {
            if(grid[placementY][placementX].getX() < this.getX()) //Move left
            {
                setLocation(this.getX() - SPEED, this.getY());  
                facingRight = false;
            }
            if(grid[placementY][placementX].getX() > this.getX()) //Move right
            {
                setLocation(this.getX() + SPEED, this.getY());
                facingRight = true;
            }
            if(grid[placementY][placementX].getY() < this.getY()) //Move Up
            {
                setLocation(this.getX(), this.getY() - SPEED);
            }
            if(grid[placementY][placementX].getY() > this.getY()) //Move Down
            {
                setLocation(this.getX(), this.getY() + SPEED);
            }
            if (type != 4) //Animation will occur depending on if the enemy is facing left or right
            {
                if (!facingRight) animate(2,movement, frames);
                if (facingRight) animate(3,movement, frames);
            }
        }
        else if (!(attack))//If the enemy is not attacking 
        {
            isIdle = true;//Return back to idle position
            move = false;
            //Face towards the enemy
            if (player.getX() - getX() > 0) facingRight = true;
            if (player.getX() - getX() < 0) facingRight = false;
            burrow = false;
            unBurrow = true;
            if (type != 4)//If it is not the worm
            {
                if (kill)//Remove player and proceed to end screen
                {
                    player.removePlayer();
                }
                else if (type == 8 && Math.abs(placementX - heroX) < 5 && placementY == heroY) //mirror checks if it can swap with hero
                {
                    swap = true;
                    for (int i = Math.abs(placementX - heroX); i > 0; i --) //If there are things in between the mirror, it will not swap with the hero
                    {
                        if (facingRight)
                        {
                            if (player.getFacingRight() || grid[placementY][placementX + i].getObstacle() || !(grid[placementY][placementX + i].getAvailable()) &&  !(grid[placementY][placementX + i].getOccupied()))
                            {
                                swap = false;
                            }
                        }
                        else
                        {
                            if (!player.getFacingRight() || grid[placementY][placementX - i].getObstacle() || !(grid[placementY][placementX - i].getAvailable()) && !(grid[placementY][placementX - i].getOccupied()))
                            {
                                swap = false;
                            }
                        }
                    }
                    if (!swap)
                    {
                        world.enemyTurn();      
                    }
                }
                else
                {
                    if (type == 9) //Boss will play attack animation
                    {
                        attackMove = true;
                    }
                    else //Proceed to next enemy
                    {
                        world.enemyTurn();
                    }
                }
            }
        }
        else //If attacking, return back to original position
        {
            placementX = tempPlacementX;
            placementY = tempPlacementY;
            attack = false;
        }
    }
    
    /**
     * Changes the image of the enemy for animations.
     * @param type The type of animation to be played.
     * @param length How many images there are in the animation.
     * @param frames How many frames per image.
     */
    protected void animate (int type, int length, int frames)
    {
        if (world.getFrames() % frames == 0) //Every "frames" frames, switch to the next image
        {
            setImage (image[type][pic]); //Set image 
            pic ++;//Next image will be played the next time the statement is ran through
        }
        if (pic == length) //When animation has reached the last image
        {
            pic = 0;//Reset pics
            if (this.type == 4)//If it is the worm
            {
                if (burrow)//Worm is now underground
                {
                    burrow = false;
                    getImage().setTransparency(0);//Worm is invisible while underground
                    possibleMoves(2,1);
                }
                else 
                {
                    if (unBurrow && min != 0) world.enemyTurn();//If the worm is not on the player
                    unBurrow = false;
                    isIdle = true;//Return back to idle animation
                    if (min == 0 && kill)player.removePlayer();//Kill player        
                }
            }      
            if (this.type == 5)//If it is the chest
            {
                if (transform) possibleMoves(1);//If the chest has transformed already
                transform = false;
                transformed = true;
            }
            if (this.type == 9 && attackMove)//If it is the boss and it just played the attack animation
            {
                attackMove = false;
                sandAttack();//Spawn sand

            }
        }
    }  
    
    /**
     * Checks if the player has killed the enemy
     */
    protected void dead()
    {
        List <Player> user = world.getObjects(Player.class);
        if (Greenfoot.mouseClicked(this) && grid[placementY][placementX].getMovable() && user.get(0).getCanClick() && type!= 9) //If player has clicked on the enemy and is within range
        {
            user.get(0).setCannotClick();//Stop player from moving
            //Create a list of the player in the world
            //Move the player to current block coordinates
            user.get(0).resetBlock();
            user.get(0).moveToBlock(grid[placementY][placementX].getX(),  grid[placementY][placementX].getY()); 
            move = false;
            if (type == 6) explodeFire();//Explosion for fireman
            if (type == 7) explodeIce();//Freezing effect for iceman
            world.death(getX(),getY());//Playing death animation
            world.removeEnemy(this);//Remove itself
            deathSound.play();//Play death sound
        }
    }
    
    /**
     * Create balls of flame around the fireman when it dies, killing enemies around it.
     */
    protected void explodeFire()
    {
        List <Enemy> enemyList = (getObjectsInRange(100, Enemy.class));//Remove all enemies near the fireman
        for (Enemy e : enemyList) {
            if (e.type != 9)
            {
                world.removeEnemy(e);
            }
        }
        if (placementX - 1 >= 0)//Spawn fire to the left of fireman
        {
            if(!grid[placementY][placementX -1].getObstacle() )
            {
                world.fire(grid[placementY][placementX-1].getX(), grid[placementY][placementX-1].getY());
            }
        }
        if (placementX + 1 < 9)//Spawn fire to the right of fireman
        {
            if(!grid[placementY][placementX +1].getObstacle() )
            {
                world.fire(grid[placementY][placementX+1].getX(), grid[placementY][placementX+1].getY());
            }
        }
        if(placementY + 1 < 5)//Spawn fire below fireman
        {
            if(!grid[placementY + 1][placementX].getObstacle())
            {
                world.fire(grid[placementY + 1][placementX].getX(), grid[placementY + 1][placementX].getY());
            }
        }
        if (placementY - 1 >= 0)//Spawn fire above fireman
        {
            if(!grid[placementY - 1][placementX].getObstacle())
            {
                world.fire(grid[placementY - 1][placementX].getX(), grid[placementY - 1][placementX].getY());
            }
        }
    }
    
    /**
     * Creates sand in front of the boss during an attack.
     */
    protected void sandAttack()
    {
        boolean attack = false;
        if (facingRight)//If facing right
        {
            if (placementX + 1 < 9)
            {
                if (placementY + 1 < 5)//If its within the grid, spawn sand
                {
                    if(!grid[placementY + 1][placementX + 1].getObstacle())
                    {
                        world.sand(grid[placementY + 1][placementX +1].getX(), grid[placementY + 1][placementX + 1].getY());
                        attack = true;
                    }
                }
                if (placementY - 1 > -1)//If its within the grid, spawn sand
                {
                    if(!grid[placementY - 1][placementX + 1].getObstacle())
                    {
                        world.sand(grid[placementY - 1][placementX +1].getX(), grid[placementY - 1][placementX + 1].getY());
                        attack = true;
                    }
                }
                if(!grid[placementY][placementX + 1].getObstacle())//If its within the grid, spawn sand
                {
                    world.sand(grid[placementY][placementX +1].getX(), grid[placementY][placementX + 1].getY());
                    attack = true;
                }
            }
        }
        if (!facingRight)//If facing left
        {
            if (placementX - 1 > -1)
            {
                if (placementY + 1 < 5)//If its within the grid, spawn sand
                {
                    if(!grid[placementY + 1][placementX - 1].getObstacle())
                    {
                        world.sand(grid[placementY + 1][placementX -1].getX(), grid[placementY + 1][placementX - 1].getY());
                        attack = true;
                    }
                }
                if (placementY - 1 > -1)//If its within the grid, spawn sand
                {
                    if(!grid[placementY - 1][placementX - 1].getObstacle())
                    {
                        world.sand(grid[placementY - 1][placementX -1].getX(), grid[placementY - 1][placementX - 1].getY());
                        attack = true;
                    }
                }
                if(!grid[placementY][placementX - 1].getObstacle())//If its within the grid, spawn sand
                {
                    world.sand(grid[placementY][placementX -1].getX(), grid[placementY][placementX - 1].getY());
                    attack = true;
                }
            }
        }
        if (!attack) world.enemyTurn();
    }
    
    /**
     * Freezes all enemies in the world.
     */
    protected void explodeIce()
    {
        List <Enemy> enemyList = world.getObjects(Enemy.class);
        for (Enemy e : enemyList) {
            e.setFreeze(2);//Frozen for 2 turns
        }
    }
    
    /**
     * Freeze enemy for a set number of turns.
     * @return num The number of turns the enemy will be frozen for.
     */
    public void setFreeze (int num)
    {
        freeze = num;
    }
    
    /**
     * Get X grid placement of enemy.
     * @return int The X grid placement of enmy.
     */
    public int getLocationX()
    {
        return placementX;
    }
    
    /**
     * Get Y grid placement of enemy.
     * @return int The Y grid placement of enmy.
     */
    public int getLocationY()
    {
        return placementY;
    }
    
    /**
     * Get the type of enemy.
     * @return int The type of enemy.
     */
    public int getType()
    {
        return type;
    }
}
