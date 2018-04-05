package prog02_Solved;

import java.io.*;

/**
 *
 * @author vjm
 */
public class SortedPD extends ArrayBasedPD {
    /** Add an entry to the directory.
      @param name The name of the new person
      @param number The number of the new person
     */
    protected void add(String name, String number) {
        if (size >= capacity) {
            reallocate();
        }

	// Initialize i to the index of the first available location
	// in the array.
	int index = size;

	// While there is a previous entry before i
	while (index > 0 &&

	       // and the name you want to insert comes before the
	       // name of this previous entry
	       name.compareTo(theDirectory[index-1].getName()) < 0) {

	    // move this entry one forward in the array
	    theDirectory[index] = theDirectory[index-1];

	    // and decrement i.
	    index--;
	}

	// Put the new entry at index i.
        theDirectory[index] = new DirectoryEntry(name, number);

	// Don’t forget to increment size.
        size++;
    }

    /** Find an entry in the directory.
      @param name The name to be found
      @return The index of the entry with the requested name.
              If the name is not in the directory, returns -1
     */
    protected int find(String name) {
	// Initialize first to the index of the first entry and last
	// to the index of the last entry.
	int first = 0, last = size-1;

	// While first is not greater than last
	while (first <= last) {

	    // Set middle equal to the index midway between first and last.
	    int middle = (first + last) / 2;

	    // Compare the name to be inserted to the name of the
	    // entry at index middle and put the result of the comparison
	    // into cmp (which will be < 0, == 0, or > 0).
	    int cmp = name.compareTo(theDirectory[middle].getName());

	    // If cmp equals zero, you’re done. (What do you do? Hint:
	    // read the description of find.)
	    if (cmp == 0)
		return middle;

	    if (cmp < 0)
		// If cmp is negative, set last equal to the index before middle.
		last = middle - 1;

	    else
		// If cmp is positive, set first equal to the index after middle.
		first = middle + 1;
	}

        return -1; // Name not found.
    }
    
    /** Remove an entry from the directory.
      @param name The name of the person to be removed
      @return The current number. If not in directory, null is
              returned
     */
    public String removeEntry(String name) {
	// Starting at i equal to the index of the entry to be removed
        int i = find(name);
        if (i == -1)
            return null;
        DirectoryEntry entry = theDirectory[i];

        for (; i+1 < size; i++)
	    // move the next entry in the array back one
            theDirectory[i] = theDirectory[i+1];
	// and increment i (i++)
	// until you have reached the last entry (i+1 >= size)

	// Or you can make just one call to System.arraycopy!
	/*
	System.arraycopy(theDirectory, i+1, theDirectory, i, size-i-1);
	*/

        size--;
        modified = true;
        return entry.getNumber();
    }
}
