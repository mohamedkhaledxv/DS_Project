package com.example.guil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import java.util.Scanner ;

public class GUIController {
    @FXML
    private Label welcometext;
    @FXML
    static String mystring;
    static String st, formateed;
    StringBuffer str = new StringBuffer();
    ArrayList<String> fixError2 = new ArrayList<String>();
    ArrayList<Integer> lineOfError2 = new ArrayList<Integer>();





    @FXML
    private TextArea txt1;
    @FXML
    private TextArea txt2;

    @FXML
    protected void correctbutton() {
        Boolean flag = false;
        txt2.clear();
        for (int i = 0; i < lineOfError2.size(); i++) {
            if (lineOfError2.get(i) == 1) {
                flag = true;
                break;
            }
        }
        if (flag) {
            txt2.appendText(Consistency.print(fixError2));
            txt2.appendText("\n" + "Press Prettify then ");

        } else {
            txt2.appendText("No need to be corrected");
        }


    }

    @FXML
    protected void checkbutton() {
        txt2.clear();
        File file = new File(Reader.filepath);
        Scanner scan = null;
        try {
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<String> line = new ArrayList<String>();
        ArrayList<Integer> lineOfError = new ArrayList<Integer>();
        ArrayList<String> fixError = new ArrayList<String>();

        while (scan.hasNextLine()) {
            line.add(scan.nextLine());
        }
        try {
            String a;
            a = Consistency.check_error(line, lineOfError, fixError);
            fixError2 = fixError;
            lineOfError2 = lineOfError;
            txt2.appendText(a);
        } catch (Exception EmptyStackException) {
            txt2.appendText("Wrong XML file , You have a Closing Tag without Opening it");
            txt2.appendText("Please Fix it, then Try again");

        }


    }


    @FXML
    protected void browsebutton() {
        String f;
        f = Reader.Read();
        txt1.appendText(f);


    }

    @FXML
    protected void Compressbutton() {
        txt2.clear();
        String a;
        a = Compress.compresscaller(formateed);
        txt2.appendText(a);


    }

    @FXML
    protected void Decompressbutton() {
        txt2.clear();
        String a;
        a = Decompress.decompresscaller();
        txt2.appendText(a);

    }

    @FXML
    protected void Minfiybutton() {
        txt2.clear();
        String b;
        b = Minify.minifycaller(formateed);
        txt2.appendText(b);
    }

    @FXML
    protected void Prettifybutton() {
        txt2.clear();
        st = Consistency.listtostring(fixError2);
        int a[] = new int[st.length() + 1];
        ArrayList<String> output = new ArrayList<String>();
        Prettify myformat = new Prettify();
        myformat.parsing_xml_with_array(st, output, a);
        myformat.Check_ParsingArray(a, output);
        StringBuilder output2 = new StringBuilder();
        myformat.pretiffy(a, output, output2);
        String output3 = output2.toString();
        output3 = output3.substring(output3.indexOf('\n')+1);
        formateed = output3;
        txt2.appendText(String.valueOf(output3));
        new File("Formatted.xml");
        FileWriter myWriter = null;
        try {
            myWriter = new FileWriter("Formatted.xml");
            myWriter.write((String.valueOf(output3)));
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        try {
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    @FXML
    protected void convertbutton()
    {
        txt2.clear();
        ArrayList<String> xmllist = new ArrayList<>();
        Node root = new Node();
        String temp ;
        temp = Minify.minifycaller(st);
        Node.parsingtoarraylist(temp, xmllist);
        root.setNameOfTag(xmllist.get(0));
        count i=new count(1);
        tree.filltree(root, xmllist, i);
        xml2json.convert(root,str);
        txt2.appendText(String.valueOf(str));
        new File("ToJSON.json");
        FileWriter myWriter = null;
        try{
            myWriter = new FileWriter("ToJSON.json");
            myWriter.write((String.valueOf(str)));}
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        try{
            myWriter.close();}
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}




