package prog05;

import java.util.EmptyStackException;
import java.util.List;
import java.util.ArrayList;

/** Implementation of the interface StackInt<E> using
*   a List.
*   @author vjm
*/

public class ListStack < E >
    implements StackInt < E > {

  // Data Fields
  /** Storage for stack. */
  List<E> theData;
  int index = 0;
  /** Construct an empty stack with the default
      initial capacity.
   */
  public ListStack() {
    theData = new ArrayList<E>();
  }

  /** Insert a new item on top of the stack.
      post: The new item is the top item on the stack.
            All other items are one position lower.
      @param obj The item to be inserted
      @return The item that was inserted
   */
  public E push(E obj) {
    theData.add(obj);
    index++;
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
    /**** EXERCISE ****/
    return theData.remove(--index);
  }
  
  public E peek() {
	  return theData.get(index-1);
  }

  public boolean empty() {
	  return theData.isEmpty();
  }
/**** EXERCISE ****/
}
