package prog10;
import java.util.*;

public class BTree<K extends Comparable<K>, V>
  extends AbstractMap<K, V> implements Map<K, V> {
  
  /**
   * At the bottom of the tree, this is an entry in the directory list.
   * It is called a leaf and contains the V value in valueOrList.
   * Inside the tree, this is a node.
   * Its valueOrList points to list the next level down.
   */
  private class Entry<K extends Comparable<K>, V>
    implements Map.Entry<K, V> {
    private K key;
    private Object valueOrList;

    private Entry (K key, V value) {
      this.key = key;
      valueOrList = value;
    }

    private Entry (ArrayList<Entry <K, V>> list) {
      key = list.get(0).key;
      valueOrList = list;
      
    }

    public K getKey () {
      return key;
    }

    private boolean isLeaf () {
      return !(valueOrList instanceof ArrayList &&
               getList().size() > 0 &&
               getList().get(0) instanceof Entry);
    }

    public V getValue () {
      return (V) valueOrList;
    }

    public V setValue (V value) {
      V old = getValue();
      valueOrList = value;
      return old;
    }

    public ArrayList<Entry<K, V>> getList () {
      return (ArrayList<Entry<K, V>>) valueOrList;
    }

    public String toString () {
      return "" + getKey() + " " + getValue();
    }
  }

  // (4) means give it a capacity of 4.
  private ArrayList<Entry<K, V>> list = new ArrayList<Entry<K, V>>(4);

  /**
   * Find the index at which key would be inserted into list.
   * Different for a list at the bottom of the tree:
   * answer might be list.size().
   */
  private int findIndex (K key, ArrayList<Entry<K, V>> list) {
    if (list.size() == 0)
      return 0;

    int i = 0;

    if (list.get(0).isLeaf())
      while (i < list.size() && key.compareTo(list.get(i).key) > 0)
        i++;
    else {
      // EXERCISE
      // Set the value of i for the case that the node is internal
      // instead of at the bottom of the tree.
      while ((i+1) < list.size() && key.compareTo(list.get(i+1).key) >= 0) {
    	  i++;
      }
    }

    return i;
  }

  /**
   * Recursively find the leaf entry for the key in the tree.
   * Return null if not there.
   */
  private Entry<K, V> find (K key, ArrayList<Entry<K, V>> list) {
    // If the list is empty, done.
    if (list.size() == 0)
      return null;

    // Find the index i which should contain the key's entry.
    int i = findIndex(key, list);
   
    // If the list contains entries (not lists)
    if (list.get(0).isLeaf()) {
      // EXERCISE
      // Check if entry i is the one we want.  Be careful!
      // If it is, return it.  If not, return null.
    	if (i <list.size()&& key.compareTo(list.get(i).key) == 0) {
    		return list.get(i);
    	} else {
    		return null;
    	}

    }

    // List contains lists.

    // Recursively find the key in the list at i.
    return find(key, list.get(i).getList());
  }

  public boolean containsKey (K key) {
    return find(key, list) != null;
  }

  public V get (K key) {
    return find(key, list).getValue();
  }

  /**
   * Split off a right hand list for a list containing four elements.
   * Original list contains [0] and [1]
   * Returned list contains what used to be [2] and [3].
   * As always, new list capacity should be 4.
   */
  private ArrayList<Entry<K, V>> split (ArrayList<Entry<K, V>> list) {
    ArrayList<Entry<K, V>> right = new ArrayList<Entry<K, V>>(4);
    right.add(list.get(2));
    right.add(list.get(3));
    list.remove(3);
    list.remove(2);
    return right;
  }

  public V put (K key, V value) {
    Entry<K, V> entry = find(key, list);
    if (entry != null) {
      V old = entry.getValue();
      entry.setValue(value);
      return old;
    }

    insert(key, value, list);

    // Look at this!!
    if (list.size() == 4) {
      ArrayList<Entry<K, V>> left = list;
      ArrayList<Entry<K, V>> right = split(list);
      list = new ArrayList<Entry<K, V>>(4);
      list.add(new Entry<K, V>(left));
      list.add(new Entry<K, V>(right));
    }

    return null;
  }
      
  /**
   * Recursively insert an entry with key and value into list.
   * Precondition:  entry must not be there.
   */
  private void insert (K key, V value, ArrayList<Entry<K, V>> list) {
    int i = findIndex(key, list);

    if (list.size() == 0 || list.get(0).isLeaf()) {
      // EXERCISE
      // Create a new Entry and insert it at index i.
      // Look at the API for List.  You don't need a loop!
    	list.add(i, new Entry<K , V>(key, value));

      return;
    }

    Entry<K, V> node = list.get(i);
    ArrayList<Entry<K, V>> sublist = node.getList();

    insert(key, value, sublist);
    node.key = sublist.get(0).key;

    if (sublist.size() < 4)
      return;

    ArrayList<Entry<K, V>> rightlist = split(sublist);
    // EXERCISE
    // Create a new Entry with rightlist.
    // Insert it into list. What index?  (Hint: to the right of index i!)
  
    rightlist.add((i+1), new Entry<K , V>(rightlist));
  }
      
  public V remove (K key) {
    Entry<K, V> entry = find(key, list);

    if (entry == null)
      return null;

    remove(key, list);

    // EXERCISE
    // If the list has only one element and that element is not a
    // leaf, then throw away the list and that entry and set list
    // equal to that entry's list.
    if (list.size() == 1 && list.get(0).isLeaf() == false) {
    	list.set(0, entry);
    }


    return entry.getValue();
  }

  /**
   * Recursively remove the entry with key from the list.
   * Precondition:  entry must be there.
   */
  private void remove (K key, ArrayList<Entry<K, V>> list) {
    // Use findIndex to find the index to remove from.
      int index = findIndex(key, list);
    // If the Entry at that index is a leaf, then it must be the one
    // we want to remove.  Remove it using the remove method of
    // ArrayList.  Then you are done.
      if (list.get(index).isLeaf() == true) {
    	  remove(key);
      } else {
    	  Entry<K, V> entry = list.get(index);
    	  ArrayList<Entry<K, V>> sublist = entry.getList();
    	  sublist.remove(index);
    	  list.get(index).key = sublist.get(0).key;
    	  if (sublist.size() == 2 || sublist.size() == 3)
    		  return;
    	  if (index == list.size()) {
    		  index--;
    		  
    	  }
    	  
      }
      


    // Otherwise, get the Entry at that index, which is an internal node.

    // And get its list, call it the sublist.


    // Recursively remove the entry with the key from that sublist.


    // Make sure to update the key field of the node because the
    // sublist may have a new key at index 0 now.


    // If the size of the sublist is still 2 or 3, then we are done.

    // Otherwise, we need to merge the sublist with its left or right
    // neighbor in list.  To keep things simple, merge it with the one
    // on the right, with index+1, but if the index is the last index,
    // that's not going to work.  So in that case, decrement the index
    // and make the node and sublist be the ones which belong to that
    // one.

    // Add all the elements of the sublist to the right to the current
    // sublist.  You can just use the addAll method of ArrayList.

    // Remove the node to the right from list.

    // If the size of the sublist is less than 4, then we are done.

    // Otherwise, split the sublist.  Look at insert to see how this
    // is done.

  }

  // Required to implement Map<K, V>.  Ignore.
  public Set<Map.Entry<K,V>> entrySet () { return null; }

  public String toString () {
    return "-------------------------\n" + toString(list, 0);
  }
  
  private String toString (ArrayList<Entry<K, V>> list, int indent) {
    String ret = "";
    for (int i = 0; i < list.size(); i++) {
      for (int j = 0; j < indent; j++)
        ret = ret + "    ";
      ret = ret + list.get(i).key;
      if (list.get(i).isLeaf())
        ret = ret + " " + list.get(i).getValue() + "\n";
      else
        ret = ret + "\n" + toString(list.get(i).getList(), indent+4);
    }
    return ret;
  }

  public static void main (String[] args) {
    BTree<String, Integer> tree = new BTree<String, Integer>();
    
    tree.put("Brad", 46);
    System.out.println(tree);
    tree.put("Hal", 10);
    System.out.println(tree);
    tree.put("Kyle", 6);
    System.out.println(tree);
    tree.put("Lisa", 43);
    System.out.println(tree);
    tree.put("Lynne", 43);
    System.out.println(tree);
    tree.put("Victor", 46);
    System.out.println(tree);
    tree.put("Zoe", 6);
    System.out.println(tree);
    tree.put("Zoran", 76);
    System.out.println(tree);

    tree.remove("Zoe");
    System.out.println(tree);
    tree.remove("Kyle");
    System.out.println(tree);
    tree.remove("Brad");
    System.out.println(tree);
    tree.remove("Zoran");
    System.out.println(tree);
    tree.remove("Lisa");
    System.out.println(tree);
  }
}