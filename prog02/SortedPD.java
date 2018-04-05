package prog02;

import java.io.*;

/**
 *
 * @author vjm
 */
public class SortedPD extends ArrayBasedPD {

	 public String removeEntry(String name) {
	        int i = find(name);
	        if (i == -1)
	            return null;
	        DirectoryEntry entry = theDirectory[i];
		theDirectory[i] = theDirectory[--size];
	        modified = true;
	        return entry.getNumber();
	    }
}
