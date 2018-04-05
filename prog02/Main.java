package prog02;

/**
 *
 * @author vjm
 */
public class Main {
    
    /** Processes user's commands on a phone directory.
      @param fn The file containing the phone directory.
      @param ui The UserInterface object to use
             to talk to the user.
      @param pd The PhoneDirectory object to use
             to process the phone directory.
     */
    public static void processCommands(String fn, UserInterface ui, PhoneDirectory pd) {
	pd.loadData(fn);

	String[] commands = {
	    "Add/Change Entry",
	    "Look Up Entry",
	    "Remove Entry",
	    "Save Directory",
	    "Exit"};

	String name, number, oldNumber;

	while (true) {
	    int c = ui.getCommand(commands);
	    switch (c) {
	    case 0: //add/change
		    name = ui.getInfo("Enter Name");
		    if(name == null || name.length() == 0)
		    	break;
		    number = ui.getInfo("Enter Number");
		    oldNumber = pd.addOrChangeEntry(name, number);
		    if(oldNumber == null) {
		    	ui.sendMessage(name + " was added to the directory \n new number: " + number);
		    	break;
		    }
		    ui.sendMessage("the number for " + name + " has been changed to \n old number: " + oldNumber + 
		    		"\n new number: " + number);
		break;
	    case 1: //look up entry
	    	name = ui.getInfo("Enter Name");
	    	if(name == null || name.length() == 0)
	    		break;
	    	number = pd.lookupEntry(name);
	    	if(number == null)
	    		ui.sendMessage(name + " is not in the list");
	    	else
	    		ui.sendMessage("the number for " + name + " is " + number);
		break;
	    case 2: //remove entry 
		name = ui.getInfo("Enter Name");
		if(name == null || name.length() == 0)
			break;
		number = pd.removeEntry(name);
		if (number == null) {
			ui.sendMessage(name + " was not listed in the directory");
			break;
		} else {
			ui.sendMessage(name + " with number " + number + " was removed from the directory");
		}
		break;
	    case 3: // save directory
		pd.save();
		break;
	    case 4: //exit
		// implement
		return;
	    }
	}
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	String fn = "csc220.txt";
	PhoneDirectory pd = new SortedPD();
	UserInterface ui = new GUI();
	processCommands(fn, ui, pd);
    }
}
