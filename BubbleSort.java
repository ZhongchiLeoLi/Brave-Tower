import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.lang.NumberFormatException;

import java.io.File;
/**
 * Helper class used to take a score and add it to the file, sort all the files, then add the top five scores back to the file
 * 
 * @author Justin and Deston
 * @version January 2019
* 
 */
public class BubbleSort  
{
    /**
     * Main method where all the processes (reading, writing, sorting) are executed
* @param fileName  File name where scores to be sorted are stored
* @param min Number of minutes the player used in their game
* @param sec Number of seconds (residual) the player used in their game
     */
    public static void sortAndRewrite(String fileName, int min, int sec)
    {
        //Scanner used for file reading
        Scanner input = new Scanner (System.in);
        //File name where scores to be sorted are stored
        String file = fileName;
        //Holds the number of lines in the file
        int numLines = 0;
        //True when operation is complete, can exit while loops
        boolean done;

//At the bottom of the score file, write the user’s score
        WriteFile addScore = new WriteFile(fileName, true);
        try {
            addScore.writeToFile (""+(sec+(min*60)));
        }
        catch (Exception e)
        {
            System.out.println ("An error occurred. Please try again");
        }

//Initialize scanner to read the score file
        try{
            input = new Scanner(new File(file));
        }
        catch(FileNotFoundException e){
            System.out.println("File was not found");
        }

        //Loop to count the number of lines in the file
        do{
            try{
                //Reads the next line and increases count of the number of lines
                int num = Integer.parseInt(input.nextLine());
                numLines++;
                done = false;
            }
            catch (NoSuchElementException e){
                //If there are no more lines to read, loop will exit
                done = true;
            }
        }while(!done);
        
        

        //An array to hold each value in the file
        int[] nums = new int[numLines+1];
        try{
            //Scanner is reinitialized to read file from the beginning
            input = new Scanner(new File(fileName));
            //Stores each line as a number in the array
            for(int i=0; i<numLines; i++)
            {
                nums[i] = Integer.parseInt(input.nextLine());
            }
        }
        catch(FileNotFoundException e){

        }
        
//Sort the scores from least to greatest
        bubbleSort(nums);

        //Write the best score onto the file, deleting all other entries
        WriteFile rewriteScore= new WriteFile(fileName, false);
        try {
            rewriteScore.writeToFile (nums[0] + "");
        }
        catch (Exception e)
        {
            System.out.println ("An error occurred. Please try again");
        }

//Write the next best scores to the end of the file, not including the worst score
        for(int i=1; i<5; i++)
        {
            try {
                addScore.writeToFile (nums[i] + "");
            }
            catch (Exception e)
            {
                System.out.println ("An error occurred. Please try again");
            }
        }
//Close the scanner
input.close();
    }

/**
     * Bubble sort algorithm that procedurally swaps values in the array, exiting if values are already sorted
     * 
     * @param nums[] Array to be sorted
     * 
     * @author Jorden Cohen
     */
    private static void bubbleSort(int[] nums) {
        boolean done = false;

        for(int i = 0; i < nums.length && !done; i++) {
            done = true;
            
            for(int x = 1; x < nums.length - i-1; x++) {
                if(nums[x - 1] > nums[x]) {
                    int temp = nums[x - 1];
                    nums[x - 1] = nums[x];
                    nums[x] = temp;
                    done = false;
                    
                }
                
            }
        }
    }
    
}


