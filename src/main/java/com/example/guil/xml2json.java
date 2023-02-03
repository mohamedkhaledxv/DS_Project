package com.example.guil;

public class xml2json {
    public static boolean dataisNumeric(String str)
    {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
    static void printdata(Node leaf ,   StringBuffer str ) {
        if (dataisNumeric(leaf.getDataOfTag()))
        {
            str.append("\""+ leaf.getNameOfTag().substring(1, leaf.getNameOfTag().length()-1) +"\": "+ (leaf.getDataOfTag())+",\n");
        }
        else {
            str.append("\""+leaf.getNameOfTag().substring(1, leaf.getNameOfTag().length()-1)+"\": "+ "\""+(leaf.getDataOfTag())+"\""+",");
        }
    }
    static void printValueofrep(Node leaf , StringBuffer str )
    {
        if (dataisNumeric(leaf.getDataOfTag()))
        {
            str.append((leaf.getDataOfTag())+",\n"+"\n");
        }
        else
        {
            str.append(  "\""+(leaf.getDataOfTag())+"\""+","+"\n");
        }

    }

    public static void  convert(Node parent , StringBuffer str)
    {
        if (parent.getDataOfTag()==null) {
            if (parent.getDepth()==0)
            {
                str.append("{"+"\n");
                str.append("\""+parent.getNameOfTag().substring(1,parent.getNameOfTag().length()-1)+"\": {"+"\n");
            }
            int i=0;
            while (i<parent.getChilds().size()){

                if(parent.getChilds().get(i).size()==1) {
                    if(parent.getChilds().get(i).get(0).getDataOfTag()==null) {
                        str.append(""+"\n");
                        str.append("\""+parent.getChilds().get(i).get(0).getNameOfTag().substring(1,parent.getChilds().get(i).get(0).getNameOfTag().length()-1) +"\": {"+"\n");
                    }
                    convert(parent.getChilds().get(i).get(0) , str );

                }
                else {
                    for(int j=0;j<parent.getChilds().get(i).size();j++) {
                        if (j==0) {
                            str.append("\""+parent.getChilds().get(i).get(0).getNameOfTag().substring(1,parent.getChilds().get(i).get(0).getNameOfTag().length()-1)	+"\": ["+"\n");
                        }

                        if (parent.getChilds().get(i).get(j).getDataOfTag()!=null) {
                            str.append(""+"\n");
                            printValueofrep(parent.getChilds().get(i).get(j), str);
                            if(j==parent.getChilds().get(i).size()-1)
                            {
                                str.append(""+"\n");
                                str.append("]"+"\n");
                            }
                        }
                        else
                        {
                            str.append("{"+"\n");
                            convert(parent.getChilds().get(i).get(j),str);
                            str.append("},"+"\n");
                            if(j==parent.getChilds().get(i).size()-1)str.append("]");
                        }

                    }

                }
                i++;
            }
            str.append("\n"+"}"+"\n");
        }

        else {
            printdata(parent,str);
            return;
        }

        if (parent.getDepth()==0)
        {
            str.append("}"+"\n");
        }

    }

}

