package com.example.guil;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Collections;

public class Compress {
    private static Vector<String> vec = new Vector<String>(); //Details of compression will be stored here

    public static String compressinternal (String x) { //Compression Algorithm
        final int compressionlevel = 126;
        String originalstring = x;
        char[][] arry2 = new char[256][256];
        String newstring = new String("");
        int replacedtimes = 0;
        int stringlength = x.length();
        int highestascii = -1;
        int replacingletter = -1;

        //Generation random replacing letters
        ArrayList<Integer> list = new ArrayList<Integer>();
        //Max: i<126 //126 represents ~
        //32 represents the white space
        for (int i = 33; i < compressionlevel; i++) {
            list.add(i);}
        // for (int i=161;i<=255;i++){
        //   list.add(i);}
        Collections.shuffle(list);
        //Max: 94 (Maximum size of random array)

        while (true) {
            //System.out.println(x);
            int[][] arry1 = new int[256][256];
            int first = 300;
            int second = 400;
            int oldfirst;
            int oldsecond;
            int maxfirst = -1;
            int maxsecond = -1;
            int maxoccur = 0;

            for (int i = 0; i < x.length() - 1; i++) {
                oldfirst = first;
                oldsecond = second;
                first = Integer.valueOf(x.charAt(i));
                second = Integer.valueOf(x.charAt(i + 1));
                if (first == 32 || second == 32) //Don't compress the white space
                    continue;
                //Calculation the occurence of each pair
                if (!(oldfirst == oldsecond && oldsecond == second)) {
                    arry1[first][second]++;
                    if (arry1[first][second] > maxoccur) {
                        maxoccur = arry1[first][second];
                        maxfirst = first;
                        maxsecond = second;
                    }
                }

            }

            //Generation Random characters and checking if they're included in the string
            boolean schemeflag = true;
            for (int i=0;i< list.size();i++) {
                if(!x.contains(Character.toString(list.get(i))) && !originalstring.contains(Character.toString(list.get(i)))) {
                    replacingletter = list.get(i);
                    schemeflag = false;
                    list.remove(i);
                    //System.out.println(list.size());
                }
            }


            //No replacement anymore condition
            if (maxoccur <= 1 || schemeflag)
                break;

            //Removing '{' from replacing a letter, as it is a replication operator so it is misunderstood
            if (replacingletter == 123) {
                replacingletter++;
            }

            String replacedstring = Character.toString(maxfirst) + Character.toString(maxsecond);
            vec.add(replacedstring);
            vec.add(Character.toString(replacingletter));
            arry2[maxfirst][maxsecond] = Character.toString(replacingletter).charAt(0);
            newstring = x.replace(replacedstring, Character.toString(replacingletter));
            x = newstring;
            replacedtimes++;
            // System.out.println("No of occurence: " + maxoccur + " Max Occuring pair is: " + Character.toString(maxfirst) + Character.toString(maxsecond) + " Replaced with: " + arry2[maxfirst][maxsecond]);
            //System.out.println("Replacing times: " + replacedtimes);
            //System.out.println(x);
            //System.out.println(Character.toString(highestascii)); */
            // System.out.println("Max Occuring pair is: " + Character.toString(maxfirst) + Character.toString(maxsecond) + " Replaced with: " + arry2[maxfirst][maxsecond]);
            // System.out.println(Character.toString(118)); v
            //  System.out.println(Integer.valueOf('v')); 118
        }
        return x;
    }

    private static String compress(String originalfilename,String compressedfilename) throws IOException {
        String returnedstring = "";

        //Creating the new compressed file
        new File(compressedfilename);

        //Creating the writer of the file
        FileWriter myWriter = new FileWriter(compressedfilename);

        //reading the original file and writing to the new file in a compressed way
        File myObj = new File(originalfilename);
        Scanner myReader = new Scanner(myObj);

        String willcompress = "";
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            willcompress = willcompress + "" + data;
        }
        String compressed = Compress.compressinternal(willcompress);
        returnedstring = returnedstring + "" + compressed;
        myWriter.write(compressed);
        //  System.out.println("Compressed String out of compression function: " + compressed);
        myReader.close();
        myWriter.close();

        //Preparing the compression scheme file
        String x = "";
        int indexofpoint = compressedfilename.lastIndexOf('.');
        x = compressedfilename.substring(0,indexofpoint) +"scheme.xml";
        //Creating the writer of the compressing details file
        FileWriter myWriter2 = new FileWriter(x);
        //Writing the compressing details in a new file
        for(int i=0;i< Compress.vec.size();i++){
            myWriter2.write(Compress.vec.elementAt(i));
        }
        myWriter2.close();

        //  System.out.println("Compression Scheme out of compression function: " + vec);
        return returnedstring;
    }

    public static String compresscaller (String data){
        Minify.minifycaller(data);
        String x = "";
        try {
            x = compress("minified.xml", "compressed.xml");
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return x;
    }


}