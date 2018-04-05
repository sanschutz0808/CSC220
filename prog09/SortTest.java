package prog09;
import java.util.Random;

import prog02.GUI;
import prog02.UserInterface;
import prog03.Fib;

public class SortTest<E extends Comparable<E>> {
  public void test (Sorter<E> sorter, E[] array) {
    E[] copy = array.clone();
    sorter.sort(copy);
    if (array.length < 100) {
    System.out.println(sorter);
    for (int i = 0; i < copy.length; i++)
      System.out.print(copy[i] + " ");
    System.out.println();
    }
  }
  
  private static UserInterface ui = new GUI();
  
 
  public static void main (String[] args) {
    Random random = new Random(0);
    String[] choices =
		{ "Insertion Sort", "Heap Sort", "Quick Sort", "Merge Sort" };
		int choice = ui.getCommand(choices);
		String stringN = ui.getInfo("Enter N size");
		int n = Integer.parseInt(stringN);
    Integer[] array = new Integer[n];
    for (int i = 0; i < n; i++) {
    	array[i] = random.nextInt(999999999);
    }
    
    SortTest<Integer> tester = new SortTest<Integer>();
    switch (choice) {
    case 0:
    	tester.test(new InsertionSort<Integer>(), array);
    	break;
    case 1:
    	tester.test(new HeapSort<Integer>(), array);
    	break;
    case 2:
    	tester.test(new QuickSort<Integer>(), array);
    	break;
    case 3:
    	tester.test(new MergeSort<Integer>(), array);
    	break;
    }
  
  }
  public double time (Sorter sort, E[] copy, int ncalls) {
		long start = System.currentTimeMillis();
		for (int i = 0; i < ncalls; i++)
			sort = sort.sort(copy);
		long end = System.currentTimeMillis();
		return (double) (end - start) / ncalls;
	}
  
  public static double time (Sorter sort, int n) {
		int ncalls = 1;
		double theTime = time(sort, n, ncalls);

		while (ncalls * theTime <= 1000) {
			ncalls *= 10;
			theTime = time(sort, n, ncalls);
		}

		return theTime;
	}
}

class InsertionSort<E extends Comparable<E>>
implements Sorter<E> {  
  public void sort (E[] array) {
    for (int n = 0; n < array.length; n++) {
      E data = array[n];
      int i = n;
      // move array[i-1] to array[i] as long as array[i-1] > data
      // and decrement i
      while (i> 0 && array[i-1].compareTo(data) > 0) {
    	  array[i] = array [i -1];
    	  i--;
      }
      array[i] = data;
    }
  }
 
}

class HeapSort<E extends Comparable<E>>
  implements Sorter<E> {
  private E[] array;
  
  private void swap (int i, int j) {
    E data = array[i];
    array[i] = array[j];
    array[j] = data;
  }
  
  public void sort (E[] array) {
    this.array = array;
    
    for (int i = parent(array.length - 1); i >= 0; i--)
      swapDown(i, array.length - 1);
    
    for (int n = array.length - 1; n >= 0; n--) {
      swap(0, n);
      swapDown(0, n - 1);
    } 
  }
  
  public void swapDown (int root, int end) {
    // Calculate the left child of root.
    int leftChild = left(root);

    // while the left child is still in the array
    //   calculate the right child
    //   if the right child is in the array and 
    //      it is bigger than than the left child
    //     bigger child is right child
    //   else
    //     bigger child is left child
    //   if the root is not less than the bigger child
    //     return
    //   swap the root with the bigger child
    //   update root and calculate left child
    while (leftChild < end ) {
    	int biggerChild;
    	int rightChild = right(root);
    	if (rightChild < end && array[rightChild].compareTo(array[leftChild]) > 0) {
    		 biggerChild = rightChild;
    	} else {
    		 biggerChild = leftChild;
    	}
    	if (array[root].compareTo(array[biggerChild]) > 0) {
    		return;
    	}
    	swap(root,biggerChild);
    	end--;
        root = parent(root);
    	leftChild = left(root);
        
    }
  }
  
  private int left (int i) { return 2 * i + 1; }
  private int right (int i) { return 2 * i + 2; }
  private int parent (int i) { return (i - 1) / 2; }
 
}

class QuickSort<E extends Comparable<E>>
  implements Sorter<E> {
  private E[] array;
  private void swap (int i, int j) {
    E data = array[i];
    array[i] = array[j];
    array[j] = data;
  }
  
  public void sort (E[] array) {
    this.array = array;
    sort(0, array.length-1);
  }
  
  private void sort(int left, int right) {
    if (left >= right)
      return;
    
    swap(left, (left + right) / 2);
    
    E pivot = array[left];
    int a = left + 1;
    int b = right;
    while (a <= b) {
      // Move a forward if array[a] <= pivot
      // Move b backward if array[b] > pivot
      // Otherwise swap array[a] and array[b]
    	if (array[a].compareTo(pivot) <= 0) {
    		a++;
    	} else if (array[b].compareTo(pivot) > 0) {
    		b--;
    	} else {
    		swap(a,b);
    	}
    }
    
    swap(left, b);
    
    sort(left, b-1);
    sort(b+1, right);
  }
 
}

class MergeSort<E extends Comparable<E>>
  implements Sorter<E> {
  private E[] array, array2;
  
  public void sort (E[] array) {
    this.array = array;
    array2 = array.clone();
    sort(0, array.length-1);
  }
  
  private void sort(int left, int right) {
    if (left >= right)
      return;
    
    int middle = (left + right) / 2;
    sort(left, middle);
    sort(middle+1, right);
    
    int i = left;
    int a = left;
    int b = middle+1;

    while (a <= middle || b <= right) {
    	// If both a <= middle and b <= right
    	if(a<=middle && b<=right) {
    	// copy the smaller of array[a] or array[b] to array2[i]
    		if(array[a].compareTo(array[b])<0){
    			array2[i]=array[a];
    			a++;
    			i++;
    		}
    		if(array[b].compareTo(array[a])<0){
    			array2[i]=array[b];
    			i++;
    			b++;
    		}
    	}
    	else
    	{
    	// Otherwise just copy the remaining elements to array2
    		//System.arraycopy(array, a, array2, i, b-a);
    		System.arraycopy(array, a, array2, i, b-a);
    	}

    
    System.arraycopy(array2, left, array, left, right - left + 1);
  }  
 }
 
}

