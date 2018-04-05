package prog05;

import java.util.Stack;
import prog02.UserInterface;
import prog02.GUI;

public class Tower {
  static UserInterface ui = new GUI();

  static public void main (String[] args) {
    int n = getInt("How many disks?");
    if (n <= 0)
      return;
    Tower tower = new Tower(n);

    String[] commands = { "Human plays.", "Computer plays." };
    int c = ui.getCommand(commands);
    if (c == 0)
      tower.play();
    else
      tower.solve();
  }

  /** Get an integer from the user using prompt as the request.
   *  Return 0 if user cancels or enters an empty string.  */
  static int getInt (String prompt) {
    while (true) {
      String number = ui.getInfo(prompt);
      if (number == null || number.length() == 0)
        return 0;
      try {
        return Integer.parseInt(number);
      } catch (Exception e) {
        ui.sendMessage(number + " is not a number.  Try again.");
      }
    }
  }

  int nDisks;
  StackInt<Integer>[] pegs = (StackInt<Integer>[]) new ArrayStack[3];

  Tower (int nDisks) {
    this.nDisks = nDisks;
    for (int i = 0; i < pegs.length; i++)
      pegs[i] = new ArrayStack<Integer>();
     
    while (nDisks != 0) {
    pegs[0].push(nDisks);
      --nDisks;
    }
    // EXERCISE: Initialize game with pile of nDisks disks on peg 'a'
    // (pegs[0]).
  }

  void play () {
    while (!pegs[0].empty() || !pegs[1].empty()) {
      displayPegs();
      String move = getMove();
      int from = move.charAt(0) - 'a';
      int to = move.charAt(1) - 'a';
      move(from, to);
      
    	  
    }
	  ui.sendMessage("You win!");
  }

   static String stackToString (StackInt peg) {
    StackInt helper = new ArrayStack();

    // String to append items to.
    String s = "";

    // EXERCISE:  append the items in peg to s from bottom to top.
     
    while (!peg.empty()) {
    	  helper.push(peg.pop());
      }
    
    while (!helper.empty()){
    	s= s + peg.push(helper.pop());
    }
    return s;
  }

  void displayPegs () {
    String s = "";
    for (int i = 0; i < pegs.length; i++) {
      char abc = (char) ('a' + i);
      s = s + abc + stackToString(pegs[i]);
      if (i < pegs.length-1)
        s = s + "\n";
    }
    ui.sendMessage(s);
  }

  String getMove () {
    String[] moves = { "ab", "ac", "ba", "bc", "ca", "cb" };
    return moves[ui.getCommand(moves)];
  }

  void move (int from, int to) {
    // EXERCISE:  move one disk form pegs[from] to pegs[to].
    // Don't do illegal moves.  Send a warning message instead, like:
    // Cannot place 2 on top of 1.
	  if (pegs[to].empty()) {
		  pegs[to].push(pegs[from].pop());
	  } else if (pegs[to].peek() < pegs[from].peek()) {
		  ui.sendMessage("Cannot place a larger number on top of a smaller number " );
	  } else {
		  pegs[to].push(pegs[from].pop());
	  }
  }
  
  boolean checkMove (int from, int to) {
	  if (pegs[to].empty()) {
		  return true; 
	  } else if (pegs[from].empty()) {
		  return false;
	  } else if (pegs[to].peek() < pegs[from].peek()) {
		  return false;
	  } else {
		  return true;
	  }
  }

  
  // EXERCISE:  create Goal class.

  void solve () {
    // EXERCISE
	  if (nDisks % 2 == 0 ) {
	      while (!pegs[0].empty() || !pegs[1].empty()) {
	          displayPegs();
	          if (checkMove(0, 1) == false) {
              move(1, 0);
	          } else {
	    	      move(0, 1);
	          }
	      
	          displayPegs();
	          if (checkMove(0, 2) == false) {
              move(2, 0);
	          } else {
	    	      move(0, 2);
	          }
	      
	          displayPegs();
	          if (checkMove(1, 2) == false) {
              move(2, 1);
	          } else {
	    	      move(1, 2);
	          }
	         
	      }
	      ui.sendMessage("You win!");
	  } else {
		  while (!pegs[1].empty() || !pegs[0].empty()) {
	          displayPegs();
	          if (checkMove(0, 2) == false) {
              move(2, 0);
	          } else {
	    	      move(0, 2);
	          }
	          
	          displayPegs();
	          if(pegs[0].empty() && pegs[1].empty()) {
	        	  ui.sendMessage("you win!");
	        	  return;
	          }
	          if (checkMove(0, 1) == false) {
              move(1, 0);
	          } else {
	    	      move(0, 1);
	          }
	      
	          
	          displayPegs();
	          if (checkMove(1, 2) == false) {
              move(2, 1);
	          } else {
	    	      move(1, 2);
	          }
	  }
	   ui.sendMessage("You win!");   
	  }
  }
}