package com.example.guil;

import java.util.ArrayList;

public class Node {
    private String nameOfTag;
    private String dataOfTag;
    private int depth;
    private ArrayList <ArrayList <Node>> childs= new ArrayList <>();

    public Node(String nameOfTag, String dataOfTag, int depth) {
        this.nameOfTag = nameOfTag;
        this.dataOfTag = dataOfTag;
        this.depth = depth;
    }
    public static  void  parsingtoarraylist(String a,ArrayList<String> string)
    {

        char space=32;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i)=='<'&&a.charAt(i+1)=='!') {
                while(a.charAt(i)!='>') {
                    i++;}
                i++;

            }
            if (a.charAt(i)=='<'&&a.charAt(i+1)=='?') {
                while(a.charAt(i)!='>') {
                    i++;}
                i++;

            }
            if (a.charAt(i)==space||a.charAt(i)=='\r'||a.charAt(i)=='\n')
                continue;


            else if (a.charAt(i)=='<') {

                int j=i;

                while ((a.charAt(i)!='>')) {
                    i++;
                }

                string.add(a.substring(j,i+1));


            }
            else {
                int k=i;
                while((a.charAt(i)!='<')) {

                    if (a.charAt(i)=='\r'||a.charAt(i)=='\n')break;
                    i++;}
                string.add(a.substring(k,i));
                --i;
            }
        }


    }
    public Node() {

    }

    public String getNameOfTag() {
        return nameOfTag;
    }

    public void setNameOfTag(String nameOfTag) {
        this.nameOfTag = nameOfTag;
    }

    public String getDataOfTag() {
        return dataOfTag;
    }

    public void setDataOfTag(String dataOfTag) {
        this.dataOfTag = dataOfTag;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public ArrayList<ArrayList<Node>> getChilds() {
        return childs;
    }

    public void setNewChild(Node child , int i) {
        this.childs.get(i).add(child);
    }
    void addChild(Node child,int i) {

        this.childs.get(i).add(child);

    }
    static void addrepetedChild(Node node,Node child) {
        if (node.getChilds().size()==0) {
            node.getChilds().add(new ArrayList<Node>());
            node.addChild(child, 0);
            return;
        }
        else {
            for (int i=0;i<node.getChilds().size();i++) {
                if (node.getChilds().get(i).get(0).getNameOfTag().equals(child.getNameOfTag()) ) {
                    node.addChild(child, i);
                    return;
                }

            }

            node.getChilds().add(new ArrayList<Node>());
            node.addChild(child, node.getChilds().size()-1);


        }

    }
}
