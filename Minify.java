import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Minify {
    private static String minify(String data,String minifiedfilename) throws IOException {
        String returnedstring = "";
        char space = ' ';
        
        new File(minifiedfilename); //Creating the new minified file
    
        FileWriter myWriter = new FileWriter(minifiedfilename);   //Creating the writer of the file

        //reading the original file and writing to the new file in a minified way
        boolean flag = true;
        for (int i = 0; i < data.length(); i++) {
            if (data.charAt(i) == '\n') {
                flag = true;
                continue;
            }
            if (flag) {
                if (data.charAt(i) == space) {
                    continue;
                }
            }
            if (data.charAt(i) != '\n' && data.charAt(i) != space)
                flag = false;
            if (data.charAt(i) == space && data.charAt(i+1) == '\n')
                continue;
            if (data.charAt(i) == space && data.charAt(i+1) == space)
                continue;
            returnedstring = returnedstring + "" + data.charAt(i);

        }
        myWriter.write(returnedstring);
        myWriter.close(); //closing the writer of the file

        return returnedstring;
    }
    public static String minifycaller (String data){
        String x= "";
        try {
            x= minify(data, "minified.xml");
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return x;
    }
}
