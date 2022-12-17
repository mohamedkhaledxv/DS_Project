package com.example.guil;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
public class correction {
    public static String listtostring(ArrayList<String> lis)
    {
        StringBuffer a = new StringBuffer() ;
        for (int i =0  ; i< lis.size() ; i++)
        {
            a.append(lis.get(i)+"\n") ;
        }
        return a.toString() ;
    }
    public static int find_char(String line, char x)
    {
        return line.indexOf(x) ; // returns the index of found char or -1 if not found
    }
    public static String print(ArrayList<String> fixError)
    {    StringBuffer sb=new StringBuffer();
        for (int i = 0; i < fixError.size(); i ++)
        {
            sb.append( fixError.get(i));
        }
        return sb.toString() ;
    }
    public static String check_error(ArrayList<String> line, ArrayList<Integer> lineOfError,ArrayList<String> fixError)
    {
        StringBuffer sb=new StringBuffer();
        Stack <String> s = new Stack<>() ;
        int openBracket, endBracket ;

        String currentLine ;
        String correct ;
        String value_Stack ;
        for(int i = 0; i < line.size(); i ++) // filling the fixerror list with the original code
        {
            fixError.add(line.get(i));
            lineOfError.add(0);
        }
        for (int i = 0; i < line.size(); i ++)
        {
            currentLine = line.get(i) ;
            while(currentLine.length() != 0 && (find_char(currentLine, '<') != -1) )
            {
                openBracket = find_char(currentLine, '<') ;
                if( openBracket != -1 ) // if = -1 , line full of words without opening or closing tags
                {
                    if(currentLine.indexOf('/') == (openBracket + 1)) // Closing Tag
                    {
                        endBracket = find_char(currentLine , '>') ;
                        if (endBracket != -1)
                        {
                            value_Stack = currentLine.substring((openBracket + 2),(endBracket )) ;
                            boolean no_error = value_Stack.equals(s.peek());

                            if(!no_error)
                            {
                                lineOfError.add(i,1);
                                while(!value_Stack.equals(s.peek()))
                                {
                                    correct =   "</" + s.peek() + ">" ;
                                    fixError.add(i , correct) ;
                                    s.pop() ;
                                }
                            }
                            s.pop();
                        }
                        else
                        {
                            lineOfError.add(i + 1,1);
                            int x = find_char(currentLine, '<') ;
                            currentLine = currentLine.substring(0, x) + "</" + s.peek() + ">";
                            fixError.add(i + 1, currentLine) ;
                            correct = fixError.get(i) ;
                            correct = correct.substring(0, find_char(correct , '>') + 1) ;
                            fixError.set(i , correct) ;
                            s.pop() ;
                        }

                    }
                    else
                    {
                        endBracket = find_char(currentLine , '>') ;
                        value_Stack = currentLine.substring(openBracket + 1, endBracket) ;
                        if (value_Stack.length() != 0)
                        {
                            s.push(value_Stack);
                            //
                        }
                    }
                    endBracket = find_char(currentLine , '>') ;
                    currentLine = currentLine.substring(endBracket +1) ;

                }
            }
        }
        int end  = line.size() - 1 ;
        while(!s.empty())
        {
            lineOfError.add(end,1);
            correct = fixError.get(end ) +  "</" + s.peek() + ">" ;
            fixError.add(end , correct) ;
            s.pop() ;
        }
        boolean flag = false ;
        for (int i = 0; i < fixError.size(); i ++) // finding the line that contaion the error
        {

            if (lineOfError.get(i) == 1)
            {
                sb.append("You have Error in line "+" " + i  +"\n") ;
                sb.append("Please Fix it or Press the Correct Button Below" ) ;
                flag = true ;

            }
        }
        if (flag == false ) // if the code is error free
        {
            sb.append("Your Code is Error free "+ "\n"+"Nice Coding =)"+"\n"+"press format then any button" ) ;
        }
        return sb.toString();

    }


}
