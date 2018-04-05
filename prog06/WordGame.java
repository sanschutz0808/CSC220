package prog06;
import java.io.*;
import java.util.Scanner;

import prog02.GUI;
public class WordGame {
	
	 static GUI ui = new GUI();
	
	 public static void main(String[] args) {
	  
	    WordGame game = new WordGame();

	    String start = ui.getInfo("Enter starting word:");
	   
	    String target = ui.getInfo("Enter target word:");

	    String[] commands = { "Computer plays.", "Human plays." };
	   
	    int choice = ui.getCommand(commands);

	    if (choice == 1) {
	      game.play(start, target);
	    } else {
	      game.solve(start, target);
	    }
	     
	 }

	 void play(String start, String target) {
		   while (true) {
		      ui.sendMessage("Current word: " + start + "\n" + "Target word: " + target);
		      
		      String nextWord = ui.getInfo("What is your next word?");
		      
		      if (nextWord.equals(target)) {
		          ui.sendMessage("You win!");
		          return;
		        }
		   }

}
}
