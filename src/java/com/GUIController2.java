package com.example.guil;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class GUIController2 {
	private Stage stage;
	@FXML
	private TextFlow tfOut = new TextFlow();
	@FXML
	private TextFlow tfIn = new TextFlow();
	
	@FXML TextField wordSearch;
	@FXML TextField topicSearch;
	@FXML TextField firstUser;
	@FXML TextField secondUser;
	private boolean fileExist = false;
	StringBuilder str_in = new StringBuilder();  
  StringBuilder latestString = new StringBuilder();
  StringBuilder jsonString = new StringBuilder();
  StringBuilder errorString = new StringBuilder();
  StringBuilder latestStringCopy = new StringBuilder();
  StringBuilder searchStringXML = new StringBuilder();
  graphNode root = null;
  Graph graph = null;
	
	public void init(Stage s) {stage=s;}

	public void chooseXMLFile(ActionEvent actionevent){
		FileChooser filechooser = new FileChooser();
		File file = filechooser.showOpenDialog(stage); 		//file path

		str_in = new StringBuilder();					//instantiate again to reset the string when we choose another file
		latestString = new StringBuilder();				//instantiate again to reset the string when we choose another file
		latestStringCopy = new StringBuilder(); 		//instantiate again to reset the string when we choose another file
		tfOut.getChildren().clear();
		tfIn.getChildren().clear();
		tfOut.setStyle(" -fx-border-color: Yellow;");
		tfIn.setStyle(" -fx-border-color: Yellow;");

		if(file != null){

			fileExist = true;
			String filename = file.getName();

			boolean checkxmlfile = filename.contains(".xml")||filename.contains(".txt");
			if(checkxmlfile){

				//try catch block is needed for FileReader to work.
				try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

					String line;

					//read each line in the file and stores it in str_in.
					//then removes all tabs and empty lines and store it in latestString to be used later by other functions.
					while ((line = reader.readLine()) != null) {
						str_in.append(line).append("\n");
						if(line.isEmpty())
							continue;
						line = line.replaceAll("  ","");
						latestString.append(line).append("\n");
					}


					try {
						String stringToBeParsed =  str_in.toString();
						graphNode parent = new graphNode(null,null,-1,null);
						graphTree xmlTree = new graphTree(parent);
						root=xmlTree.parseXML(stringToBeParsed,0,parent);
						root.closingBracket = "}";
					}
					catch(Exception e) {
						tfOut.getChildren().clear();
						tfOut.setStyle(" -fx-border-color: Yellow;");
						Text t = new Text("This file is too corrupt please click \"Check Errors\" to fix it first \n"
								+ "then you can perform operations on it");
						tfOut.getChildren().add(t);
					}
				}

				catch (IOException e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("ERROR");
					String s = e.getMessage();
					alert.setContentText(s);
					alert.show();
				}

				//Creating a Text text to be displayed in the text flow output.
				Text text = new Text(str_in.toString());
				tfIn.getChildren().add(text);

				latestStringCopy =latestStringCopy.append(latestString);
				searchStringXML = searchStringXML.append(latestString);
				graph = populateGraph();
			}

			//throw an error if the file is not .xml or .txt
			else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR");
				String s ="Please Enter XML File";
				alert.setContentText(s);
				alert.show();
			}
		}
	}
	
	private boolean hasSiblingsWithSameName(graphNode node) {
		for(graphNode sibling : node.parent.children) {
			if(sibling != node && node.name.compareTo(sibling.name) == 0) {
				return true;
			}
		}
		return false;
	}
	
	private void preorderTraverse(graphNode node) {
		if(node==null) {
			return;
		}
		if(node.children.size()==0 && !hasSiblingsWithSameName(node)) {
			for(int i=0 ;i< node.depth+1;i++) {
				jsonString.append("\t");
			}
			if(node.parent.children.indexOf(node) == node.parent.children.size()-1)
				jsonString.append("\""+node.name+"\": \"" + node.value+"\"\n");
			else
				jsonString.append("\""+node.name+"\": \"" + node.value+"\",\n");
			
		}
		else if(node.children.size()==0 && hasSiblingsWithSameName(node) && !node.visited) {
			for(int i=0 ;i< node.depth+1;i++) {
				jsonString.append("\t");
			}
			jsonString.append("\""+node.name+"\": [ \"" + node.value+"\",\n");
			for(int i=0;i<node.parent.children.size();i++) {
				graphNode sibling = node.parent.children.get(i);
				if(sibling != node && node.name.compareTo(sibling.name) == 0) {
					for(int j=0 ;j< node.depth+1;j++) {
						jsonString.append("\t");
					}
					if(i==(node.parent.children.size()-1))
						jsonString.append("\""+sibling.value+"\"\n");
					else
						jsonString.append("\""+sibling.value+"\",\n");
					sibling.visited=true;
				}
			}
			for(int i=0 ;i< node.depth+1;i++) {
				jsonString.append("\t");
			}
			jsonString.append("]\n");
		}
		else if(node.children.size()>0 && !hasSiblingsWithSameName(node)) {
			for(int i=0 ;i< node.depth+1;i++) {
				jsonString.append("\t");
			}
			jsonString.append("\""+node.name+"\": ");
			jsonString.append("{\n");
			if(node.parent.children.indexOf(node) == node.parent.children.size()-1)
				node.closingBracket="}";
			else
				node.closingBracket="},";
		}
		else if(node.children.size()>0 && hasSiblingsWithSameName(node)){

			if(!node.visited) {
				for(int i=0 ;i< node.depth+1;i++) {
					jsonString.append("\t");
				}
				jsonString.append("\""+node.name+"\": [\n");
			}
			for(graphNode sibling : node.parent.children) {
				if(sibling != node && node.name.compareTo(sibling.name) == 0) {
					sibling.visited=true;
				}
			}
			for(int i=0 ;i< node.depth+2;i++) {
				jsonString.append("\t");
			}
			jsonString.append("{\n");
			for(graphNode sibling : node.children) {
				if(sibling != node && node.name.compareTo(sibling.name) == 0) {
					for(int i=0 ;i< node.depth+1;i++) {
						jsonString.append("\t");
					}
					preorderTraverse(sibling);
					sibling.visited=true;
				}
			}
			if(node.parent.children.indexOf(node) == node.parent.children.size()-1)
				node.closingBracket="}";
			else
				node.closingBracket="},";
			if(node.parent.parent.children.indexOf(node.parent) == node.parent.parent.children.size()-1)
				node.parent.closingBracket="]";
			else
				node.parent.closingBracket="],";
		}
		
		for(graphNode child : node.children) {
			preorderTraverse(child);
		}
		if(node.parent.children.indexOf(node) == node.parent.children.size()-1){
			for(int i=0 ;i< node.depth+1;i++) {
				jsonString.append("\t");
			}
			jsonString.append(node.parent.closingBracket + "\n");
		}
	}
	 

	 
	public void mostInfluencer(ActionEvent action){
		//Check if a file has been chosen then clears the text flow output box.
		if(fileExist){
					
			tfOut.getChildren().clear();
			tfOut.setStyle(" -fx-border-color: Yellow;");	
					
					
			SNA analysis = new SNA(graph);
			String displayedString = analysis.mostInfluencerUser();
					
			//Creating a Text t to be displayed in the text flow output.
			Text t = new Text(displayedString);
			tfOut.getChildren().add(t);
		}
				
		//Give an error when there is no file path chosen.
		else{
					
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			String s ="Please Enter XML File First";
			alert.setContentText(s);
			alert.show();

		}
	}
	
	public void mostActive(ActionEvent action){
		//Check if a file has been chosen then clears the text flow output box.
		if(fileExist){
							
			tfOut.getChildren().clear();
			tfOut.setStyle(" -fx-border-color: Yellow;");	
							
							
			SNA analysis = new SNA(graph);
			String displayedString = analysis.mostActiveUser();
							
			//Creating a Text t to be displayed in the text flow output.
			Text t = new Text(displayedString);
			tfOut.getChildren().add(t);
		}
						
			//Give an error when there is no file path chosen.
		else{
							
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			String s ="Please Enter XML File First";
			alert.setContentText(s);
			alert.show();

		}
	}
	
	public void suggestFollowers(ActionEvent action){
		//Check if a file has been chosen then clears the text flow output box.
		if(fileExist){
									
			tfOut.getChildren().clear();
			tfOut.setStyle(" -fx-border-color: Yellow;");			
									
									
			SNA analysis = new SNA(graph);
			String displayedString = analysis.suggestFollowers();
									
			//Creating a Text t to be displayed in the text flow output.
			Text t = new Text(displayedString);
			tfOut.getChildren().add(t);
		}
								
		//Give an error when there is no file path chosen.
		else{
									
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			String s ="Please Enter XML File First";
			alert.setContentText(s);
			alert.show();

		}
	}
	
	public void searchByWord(ActionEvent a) {
		if(fileExist){
			//System.out.println(wordSearch.getText());
			tfOut.getChildren().clear();
			tfOut.setStyle(" -fx-border-color: Yellow;");	
			if(wordSearch.getText().length()==0) {
				return;
			}
			Post pf = new Post(searchStringXML.toString());
			LinkedList<LinkedList<String>> posts = pf.searchByWord(wordSearch.getText());
			StringBuilder postsData = new StringBuilder();
			if(posts.size()==0) {
				postsData.append("No posts found");
			}
			for(LinkedList<String> post : posts) {
				postsData.append("Author: "+post.get(0)+"\n");
				postsData.append("topics:");
				for(int i=2;i<post.size();i++) {
					postsData.append(" "+post.get(i)+",");
				}
				postsData.deleteCharAt(postsData.length()-1);
				postsData.append("\nbody:\n");
				postsData.append(post.get(1));
				postsData.append("\n-------------------------------------\n");

			}
			
			
			//Creating a Text t to be displayed in the text flow output.
			Text t = new Text(postsData.toString());
			tfOut.getChildren().add(t);
		}
		
		//Give an error when there is no file path chosen.
		else{
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			String s ="Please Enter XML File First";
			alert.setContentText(s);
			alert.show();

		}
	}
	
	public void searchByTopic(ActionEvent a) {
		
		if(fileExist){	
			tfOut.getChildren().clear();
			tfOut.setStyle(" -fx-border-color: Yellow;");		
			if(topicSearch.getText().length()==0) {
				return;
			}
			Post pf = new Post(searchStringXML.toString());
			LinkedList<LinkedList<String>> posts = pf.searchByTopic(topicSearch.getText());
			StringBuilder postsData = new StringBuilder();
			if(posts.size()==0) {
				postsData.append("No posts found");
			}
			for(LinkedList<String> post : posts) {
				postsData.append("Author : "+post.get(0)+"\n");
				postsData.append("topics:");
				for(int i=2;i<post.size();i++) {
					postsData.append(" "+post.get(i)+",");
				}
				postsData.deleteCharAt(postsData.length()-1);
				postsData.append("\nbody:\n");
				postsData.append(post.get(1));
				postsData.append("\n-------------------------------------\n");

			}
			
			
			//Creating a Text t to be displayed in the text flow output.
			Text t = new Text(postsData.toString());
			tfOut.getChildren().add(t);
		}
		
		//Give an error when there is no file path chosen.
		else{
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			String s ="Please Enter XML File First";
			alert.setContentText(s);
			alert.show();

		}
	}


		public void drawGraph(ActionEvent a) {
			Map<Integer,ArrayList<Integer>> map = graph.getGraph();
			StringBuilder digraph=new StringBuilder();
			for(Integer key: map.keySet()) {
				ArrayList<Integer> values = map.get(key);
				for(Integer value : values) {
					digraph.append(graph.getName(value).replaceAll(" ","_")+"_"+value+"->"+graph.getName(key).replaceAll(" ","_")+"_"+key+";");			}
			}
			Graphviz.createDotGraph(digraph.toString(), "DotGraph");
			try
			{
				//constructor of file class having file as argument
				File file = new File("./output.png");
				if(!Desktop.isDesktopSupported())  //check if Desktop is supported by Platform
				{
					System.out.println("not supported");
					return;
				}
				Desktop desktop = Desktop.getDesktop();
				if(file.exists())         //checks file exists or not
					desktop.open(file);     //opens the specified file
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	
	public void findMutualFollowers(ActionEvent a) {
		if(fileExist){	
			tfOut.getChildren().clear();
			tfOut.setStyle(" -fx-border-color: Yellow;");		
			
			if(firstUser.getText().length()==0 || secondUser.getText().length()==0) {
				return;
			}
			
			//get names of first and second user.
			String name1 = firstUser.getText();
			String name2 = secondUser.getText();
			
			SNA analysis = new SNA(graph);
			String displayedString = analysis.mutualFollowers(name1, name2);
			
			//Creating a Text t to be displayed in the text flow output.
			Text t = new Text(displayedString.toString());
			tfOut.getChildren().add(t);
		}
		
		//Give an error when there is no file path chosen.
		else{
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			String s ="Please Enter XML File First";
			alert.setContentText(s);
			alert.show();

		}
	}

	Graph populateGraph() {
		Graph graph = new Graph();
		jsonString.append("{\n");
		preorderTraverse(root.children.get(0));
		jsonString.append("}\n");
		graph.parseToGraph(jsonString.toString());
		jsonString.setLength(0);		
		System.out.println(graph.getGraph().toString());
		return graph;
	}
	
}
