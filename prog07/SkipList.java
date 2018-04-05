package prog07;
import java.util.*;

public class SkipList <K extends Comparable<K>, V>
  extends AbstractMap<K, V> {

  private static class Node <K extends Comparable<K>, V>
    implements Map.Entry<K, V> {

    K key;
    V value;
    Node<K, V>[] next;
    
    Node () {
      next = (Node<K,V>[]) new Node[1];
    }

    Node (K key, V value) {
      this.key = key;
      this.value = value;
      int levels = 1;

      // EXERCISE:
      // Start flipping a coin and increment levels as many times as
      // you get ``heads'' in a row.  Stop when you get ``tails''.


      next = (Node<K,V>[]) new Node[levels];
    }
    
    public K getKey () { return key; }
    public V getValue () { return value; }
    public V setValue (V newValue) {
      V oldValue = value;
      value = newValue;
      return oldValue;
    }

    /**
     * Increase the length of the next array.
     * Copy values from the old array to the new one.
     * @param newLength The desired length of the new next array.
     */
    private void reallocate (int newLength) {
      // EXERCISE:

    }
  }
  
  private Node<K, V> dummy = new Node();
  private int size;
  
  /**
   * Find the node with the given key.
   * @param key The key to be found.
   * @return The node with that key.
   */
  private Node<K, V> find (K key) {
    Node<K, V> previous = dummy;
    Node<K, V> select = new Node();

    int level = dummy.next.length - 1; // What is the largest level?

    while (level >= 0) {
      // Get the next node after previous on this level.  (Warning: it
      // might be null.)
    	if(previous.next[level] != null){
    	select = previous.next[level];
    	} else {
    		level--;
    	}
    	if (select.equals(key)) {
    		return select;
    	} 
    	
    	if (select.getKey().compareTo(key) < 0) {
    		select = select.next[level];
    	} else {
    		level--;
    	}
    		

      // If next is the node we are looking for, return it.

      // If next comes before the node we are looking for, move
      // forward.  (Set previous equal to next!!)

      // Else go down a level.  (Decrease level by 1.)
    }

    return null;
  }    
  
  public V remove (Object keyAsObject) {
    K key = (K) keyAsObject;
    Node next = new Node();
    if (containsKey(key) == false) {
    	return null;
    } else {
    	 Node<K, V> previous = dummy;
    	 int level = dummy.next.length - 1;
    	 next = previous.next[level];
    	 
    	 while (level >= 0) {
    		 if (previous.next[level].equals(key)) {
    			 Node remove = previous.next[level];
    			 next = next.next[level];
    			 previous.next[level] = next;
    			 size --;
    			 return (V) remove;
    		 } else if (next.getKey().compareTo(key) < 0) {
    			 previous = next;
    			 next = previous.next[level];
    			 
    		 } else {
    			 level--;
    		 }
    	 }
    	 
    	return null;
    }
    // EXERCISE:

    //size--;
    //return null; // ??
  }      

  public boolean containsKey (Object key) {
    return find((K) key) != null;
  }
  
  public V get (Object key) {
    Node<K, V> node = find((K) key);
    if (node != null)
      return node.value;
    return null;
  }
  
  public boolean isEmpty () { return size == 0; }
  
  public V put (K key, V value) {
    // EXERCISE:
    
	  
    size++;
    return null;
  }      
  
  private static class Iter<K extends Comparable<K>, V> implements Iterator<Map.Entry<K, V>> {
    Node<K, V> next;
    
    Iter (Node<K, V> dummy) {
      next = dummy.next[0];
    }
    
    public boolean hasNext () { return next != null; }
    
    public Map.Entry<K, V> next () {
      Map.Entry<K, V> ret = next;
      next = next.next[0];
      return ret;
    }
    
    public void remove () {
      throw new UnsupportedOperationException();
    }
  }
  
  private class Setter extends AbstractSet<Map.Entry<K, V>> {
    public Iterator<Map.Entry<K, V>> iterator () {
      return new Iter(dummy);
    }
    
    public int size () { return size; }
  }
  
  public Set<Map.Entry<K, V>> entrySet () { return new Setter(); }
  
  public static void main (String[] args) {
    Map<String, Integer> map = new SkipList<String, Integer>();
    
    map.put("Victor", 50);
    map.put("Irina", 45);
    map.put("Lisa", 47);
    
    for (Map.Entry<String, Integer> pair : map.entrySet())
      System.out.println(pair.getKey() + " " + pair.getValue());
    
    System.out.println(map.get("Irina"));
    map.remove("Irina");
    
    for (Map.Entry<String, Integer> pair : map.entrySet())
      System.out.println(pair.getKey() + " " + pair.getValue());
    
    System.out.println(map.get("Irina"));
  }
}
