import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * Writer class used to write lines of text into a text file
 * 
 * @author Mr. Cohen
 * @version November 2018
 */
public class WriteFile {
    private String path;//Name and location of file
    private boolean appendToFile = false; //False means to not append, erase everything on file

    /**
     *Creates new object of WriteFile, with only the file path. This means that if the file already
     *exists, it will BE OVER-WRITTEN. (Be careful)
     *
     *@param file_path Name and location of file
     */
    public WriteFile(String filePath) {
        path = filePath;
    }

    /**
     * Creates new object of WriteFile, with the file path, and user can indicate if they
     * wish to append the file by setting append_value to true.
     * 
     * @param filePath Name and location of file
     * @param appendValue Appends the file when true - meaning adds to existing file, not start a new one
     */
    public WriteFile( String file_path , boolean appendValue ) {
        path = file_path;
        appendToFile = appendValue;
    }

    public void writeToFile( String textLine ) throws IOException {
        FileWriter write = new FileWriter( path , appendToFile);//True (append to the file) or false (don't append)
        PrintWriter printer = new PrintWriter(write);

        //Formats a string of characters and adds a newline at the end. 
        //printer.printf( "%s" + "%n" , textLine);
        printer.println(textLine);
        printer.close();
    }
}

