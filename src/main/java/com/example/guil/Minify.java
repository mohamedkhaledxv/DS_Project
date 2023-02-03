package com.example.guil;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Minify {
    private static String minify(String data,String minifiedfilename) throws IOException {
        String returnedstring = "";
        char space = ' ';

        //Creating the new minified file
        new File(minifiedfilename);

        //Creating the writer of the file
        FileWriter myWriter = new FileWriter(minifiedfilename);

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

            //  System.out.println(data);
            // if(flag == false)
            //    myWriter.write("\n");
        }
        myWriter.write(returnedstring);
        //closing the writer of the file
        myWriter.close();

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