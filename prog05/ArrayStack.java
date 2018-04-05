package prog05;

import java.util.EmptyStackException;

/** Implementation of the interface StackInt<E> using
*   an array.
*   @author Koffman & Wolfgang
*/

public class ArrayStack < E >
    implements StackInt < E > {

  // Data Fields
  /** Storage for stack. */
  E[] theData;
  /** Index to top of stack. */
  int size = 0; // Initially empty stack.
  private static final int INITIAL_CAPACITY = 2;

  /** Construct an empty stack with the default
      initial capacity.
   */
  public ArrayStack() {
    theData = (E[])new Object[INITIAL_CAPACITY];
  }

  /** Insert a new item on top of the stack.
      post: The new item is the top item on the stack.
            All other items are one position lower.
      @param obj The item to be inserted
      @return The item that was inserted
   */
  public E push(E obj) {
    if (size == theData.length) {
      reallocate();
    }
    theData[size++] = obj;
    return obj;
  }

  /** Remove and return the top item on the stack.
      pre: The stack is not empty.
      post: The top item on the stack has been
            removed and the stack is one item smaller.
      @return The top item on the stack
      @throws EmptyStackException if the stack is empty
   */
  public E pop() {
    if (empty()) {
      throw new EmptyStackException();
    }
    return theData[--size];
  }

  public E peek() {
	  if (empty()) {
	      throw new EmptyStackException();
	    }
	  return theData[size - 1];
  }
  
  public boolean empty() {
	  return size == 0;
  }
  
  public void reallocate() {

	E[] newData = (E[])new Object[(theData.length) * 2];
	System.arraycopy(theData, 0, newData, 0, theData.length);
	theData = newData;
	 
  }
/**** EXERCISE ****/
}
