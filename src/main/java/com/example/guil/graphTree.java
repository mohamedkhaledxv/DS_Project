package com.example.guil;

import java.io.IOException;

public class graphTree {
	private graphNode root;
	
    public graphTree(graphNode root)
    {
        this.root = root;
    }
    
    public graphNode parseXML(String stringToBeParsed, int n, graphNode node) throws IOException {
    	char c;
    	
        while (n <stringToBeParsed.length() )
        {
        	
            c = (char)stringToBeParsed.charAt(n++);
            
            //checking if we reached the end of the file each time n is incremented
            if (n > stringToBeParsed.length() ) break;
            

            if (c == '<' && stringToBeParsed.charAt(n) != '/' ) 
            {
                String name = "";
                String value = "";
                graphNode child = new graphNode(name,value, node.depth + 1, node);
                node.children.add(child);
                c = (char) stringToBeParsed.charAt(n++);
                if (n > stringToBeParsed.length() ) break;
                
                while (c != '>')
                {
                	//skip white space
                    if (c == '\r' || c == '\t' || c == '\n')
                    {
                        c = (char)stringToBeParsed.charAt(n++);
                        if (n > stringToBeParsed.length() ) break;
                        continue;
                    }
                    
                    //append characters to tag name
                    name += c;
                    c = (char)stringToBeParsed.charAt(n++);
                    if (n > stringToBeParsed.length() ) break;
                }

                child.name=name;
                
                //skip white space between opening tag and text of tag
                while (stringToBeParsed.charAt(n) == '\n' || stringToBeParsed.charAt(n) == '\t' || stringToBeParsed.charAt(n) == '\r' || stringToBeParsed.charAt(n) == ' ')
                {
                    c = (char)stringToBeParsed.charAt(n++);
                    if (n > stringToBeParsed.length() ) break;
                }
                
                //if reach another tag recursively parse it
                if (stringToBeParsed.charAt(n) == '<')
                {
                    parseXML(stringToBeParsed,n ,child);
                    break;
                }
                else
                {
                    c = (char)stringToBeParsed.charAt(n++);
                    if (n > stringToBeParsed.length() ) break;
                    
                    while (c != '<')
                    {
                    	
                        if (c == '\r' || c == '\t' ||c == '\n' ||
                        		(c == ' ' && stringToBeParsed.charAt(n) == ' ') ||
                        		(c == ' ' && stringToBeParsed.charAt(n) == '<'))
                        {
                            c = (char)stringToBeParsed.charAt(n++);
                            if (n > stringToBeParsed.length() ) break;
                            continue;
                        }
                        
                        //append to the tag value
                        value += c;
                        c = (char)stringToBeParsed.charAt(n++);
                        if (n > stringToBeParsed.length() ) break;
                    }
                    
                    child.value=value;
                }

            }
            
            else if (c == '<' && stringToBeParsed.charAt(n) == '/')
            {
                //go back to parent and recursively parse it starting after the closing tag
                parseXML(stringToBeParsed, n,node.parent);
                break;
            }
        }
    return root;
    }
}
