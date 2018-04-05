package prog06;

import java.util.Queue;
import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implements the Queue interface using a single-linked list.
 * @author Koffman & Wolfgang
 **/
public class ListQueue<E> extends AbstractQueue<E>
        implements Queue<E> {

    // Data Fields
    /** Reference to front of queue. */
    private Node<E> front;
    /** Reference to rear of queue. */
    private Node<E> rear;
    /** Size of queue. */
    private int size;

    /** A Node is the building block for a single-linked list. */
    private static class Node<E> {
        // Data Fields

        /** The reference to the data. */
        private E data;
        /** The reference to the next node. */
        private Node<E> next;

        // Constructors
        /**
         * Creates a new node with a null next field.
         * @param dataItem The data stored
         */
        private Node(E dataItem) {
            data = dataItem;
            next = null;
        }

        /**
         * Creates a new node that references another node.
         * @param dataItem The data stored
         * @param nodeRef The node referenced by new node
         */
        // USE THE OTHER CONSTRUCTOR
        /*
        private Node(E dataItem, Node<E> nodeRef) {
            data = dataItem;
            next = nodeRef;
        }
        */
    } //end class Node

    // Methods
    /**
     * Insert an item at the rear of the queue.
     * @post item is added to the rear of the queue.
     * @param item The element to add
     * @return true (always successful)
     */
    @Override
    public boolean offer(E item) {
        // Check for empty queue.
        if (front == null) {
            rear = new Node<E>(item);
            front = rear;
        } else {
            // Allocate a new node at end, store item in it, and
            // link it to old end of queue.
            rear.next = new Node<E>(item);
            rear = rear.next;
        }
        size++;
        return true;
    }

    /**
     * Remove the entry at the front of the queue and return it
     * if the queue is not empty.
     * @post front references item that was second in the queue.
     * @return The item removed if successful, or null if not
     */
    @Override
    public E poll() {
      Node<E> select = front;
    	if (size == 0) {
    		return null;
    	} else {
    		 front = front.next;
    		 return select.data;
    	}
    }

    /**
     * Return the item at the front of the queue without removing it.
     * @return The item at the front of the queue if successful;
     * return null if the queue is empty
     */
    @Override
    public E peek() {
        if (size == 0) {
            return null;
        } else {
            return front.data;
        }
    }

    /**
     * Returns the size of the queue
     * @return the size of the queue
     */
    @Override
      public int size() {
      return size;
    }
    /**
     * Returns an iterator to the contents of this queue
     * @return an iterator to the contents of this queue
     */
    public Iterator<E> iterator() {
      return new Iter();
    }

    // EXERCISE
    private class Iter implements Iterator<E> {
        // Data Fields
        // Index of next element */

        
        // Count of elements accessed so far */
        private int count = 0;
        Node<E> select = front;

        // Methods
        
       

        /**
         * Returns true if there are more elements in the queue to access.
         * @return true if there are more elements in the queue to access.
         */
        @Override
        public boolean hasNext() {
            return count < size;
        	//return !(select.next == null);
        }

        /**
         * Returns the next element in the queue.
         * @pre index references the next element to access.
         * @post index and count are incremented.
         * @return The element with subscript index
         */
        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E returnValue = (E) select;
            select = select.next;
            count++;
            return returnValue;
        }

        /**
         * Remove the item accessed by the Iter object -- not implemented.
         * @throws UnsupportedOperationException when called
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
