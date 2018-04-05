package prog04;

public class SortedDLLPD extends DLLBasedPD {

	protected void add(String name, String number) {
		 DLLNode entry = new DLLNode(name, number);
		 DLLNode next = head;
		 
		 if (tail == null) {
		    	tail = entry;
		    	head = tail;
		 } else if (entry.getKey().compareTo(tail.getKey()) > 0) {
			 tail.setNext(entry);
			    entry.setPrevious(tail);
			    tail = entry; 
		 } else if (entry.getKey().compareTo(head.getKey()) < 0) {
			 head.setPrevious(entry);
			 entry.setNext(head);
			 head = entry;
		 } else {
		 while (entry.getKey().compareTo(next.getKey()) > 0 ) {
			 next = next.getNext();
		 }	 
		 DLLNode previous = next.getPrevious();
		 entry.setNext(next);
		 entry.setPrevious(previous);
		 previous.setNext(entry);
		 next.setPrevious(entry);
		 }
	}
}
