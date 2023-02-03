package com.example.guil;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class Decompress {
    private static Vector<String> vec = new Vector<String>(); //Details of compression will be stored here

    public static String decompressinternal (String x){
        //    System.out.println("Compression Scheme entering decompression function: "+vec);
        //    System.out.println("Compressed String entering decompression function: " + x);
        String decompressedstring = "";


        for (int j=0;j<x.length();j++) {
            for (int i = vec.size()-1; i >= 0; i = i - 2) {
                if (Character.toString(x.charAt(j)).equals(vec.elementAt(i))){
                    decompressedstring =  x.replace(Character.toString(x.charAt(j)),vec.elementAt(i-1));
                    x = decompressedstring;
                }
            }
        }
        //   System.out.println("Decompressed String: " + decompressedstring);
        return decompressedstring;
    }


    private static String decompress(String compressedfilename,String decompressedfilename) throws IOException {

        //Creating the decompressed file
        new File(decompressedfilename);

        //Creating the writer of the file
        FileWriter myWriter = new FileWriter(decompressedfilename);

        //reading the compressed file
        File myObj = new File(compressedfilename);
        Scanner myReader = new Scanner(myObj);
        String willdecompress = "";
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            willdecompress = willdecompress + "" + data;
        }
        //   System.out.println(data);
        myReader.close();


        //reading the compressed file scheme
        String x = "";
        int indexofpoint = compressedfilename.lastIndexOf('.');
        x = compressedfilename.substring(0,indexofpoint) +"scheme.xml";
        File myObj2 = new File(x);
        Scanner myReader2 = new Scanner(myObj2);
        String compressingdetails = "";
        Vector<String> vec = new Vector<String>();
        while (myReader2.hasNextLine()) {
            compressingdetails = compressingdetails + ""+myReader2.nextLine();
        }
        //  System.out.println(compressingdetails);
        myReader2.close();

        int i =0;

        while(i!= compressingdetails.length()-1){
            if ((i+1)%3 == 0){
                vec.add(Character.toString(compressingdetails.charAt(i)));
                i = i+1;
            }
            else {
                String duo = compressingdetails.charAt(i) +""+ compressingdetails.charAt(i+1);
                vec.add(duo);
                i = i+2;
            }
        }
        vec.add(Character.toString(compressingdetails.charAt(compressingdetails.length()-1)));
        Decompress.vec = vec;
        //  System.out.println(vec);
        String decompressed = Decompress.decompressinternal(willdecompress);
        myWriter.write(decompressed);
        //  System.out.println(decompressed);
        myWriter.close();
        return decompressed;
    }

    public static String decompresscaller (){
        String x = "";
        try {
            x=   decompress("compressed.xml", "decompressed.xml");
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return x;
    }
}