package com.example.guil;

import java.util.ArrayList;

public class graphNode {
	public String name;
	public String value = "";
	public graphNode parent;
	public int depth;
	public ArrayList<graphNode> children = new ArrayList<graphNode>();
	public String closingBracket;
	public boolean visited;

	public graphNode(String name, String value, int depth, graphNode parent)
    {
        this.name = name;
        this.value = value;
        this.depth = depth;
        this.parent = parent;
    }
}
