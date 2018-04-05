package prog12;

import java.util.*;

public class MyGoogle implements Google {
  /** Map from String url to int id, where ids are assigned
      consecutively as pages are discovered. */
  private Map<String, Integer> urlToID = new TreeMap<String, Integer>();
  
  /** Number of references (links from other pages) to each page,
      indexed by page id. */
  private List<Integer> numRefs = new ArrayList<Integer>();
  
  /** Map from in id to String url. */
  private List<String> idToURL = new ArrayList<String>();
  
  /** Add a new page to the preceeding three structures. */
  private void addPage (String url) {
    urlToID.put(url, urlToID.size());
    numRefs.add(0);
    idToURL.add(url);
  }	
  
  
  /** Map from String word to int id, where ids are assigned
      consecutively as words are discovered. */
  private Map<String, Integer> wordToID = new HashMap<String, Integer>();
  
  /** List of lists, indexed by word id.  Each list is the set of
      page ids of pages which contain the word with that word id.
      The page ids are stored in increasing numerical order. */
  private List<List<Integer>> wordPageIDs = new ArrayList<List<Integer>>();
  
  /** Add a new word to the preceeding two structures. */
  private void addWord (String word) {
    wordToID.put(word, wordToID.size());
    wordPageIDs.add(new ArrayList<Integer>());
  }
  
  /** Visit every page reachable from the pages with urls in
      startingURLs.  If the page has not been seen, record all the
      relevant information in the five data structures.
      @param browser a Browser to view the internet
      @param startingURLs web pages to start from
  */
  public void gather (Browser browser, List<String> startingURLs) {
    Queue<String> urlQueue = new ArrayDeque<String>();
    
    // Put unknown pages into the queue, but don't look at them yet.
    for (String url : startingURLs)
      if (!urlToID.containsKey(url)) {
        addPage(url);
        urlQueue.offer(url);
      }
    
    // Look at each page in the queue.
    int count = 0;
    while (!urlQueue.isEmpty() && count++ < 100) {
      String url = urlQueue.poll();
      System.out.println(url);
      if (browser.loadPage(url)) {
        List<String> urlsOnPage = browser.getURLs();
	Set<Integer> pageIDsOnPage = new HashSet<Integer>();
        for (String urlOnPage : urlsOnPage) {
          // Put links to unknown pages into the queue.
          if (!urlToID.containsKey(urlOnPage)) {
            addPage(urlOnPage);
            urlQueue.offer(urlOnPage);
          }
          // Increment the reference counter for all pages
          // linked to from this page.
          int pageIDOnPage = urlToID.get(urlOnPage);
	  if (!pageIDsOnPage.contains(pageIDOnPage)) {
	      pageIDsOnPage.add(pageIDOnPage);
	      numRefs.set(pageIDOnPage, numRefs.get(pageIDOnPage) + 1);
              System.out.println("new link to " + urlOnPage);
          }
        }
        
        int pageID = urlToID.get(url);
        List<String> words = browser.getWords();
        for (String word : words) {
          // Record unknown words.
          if (!wordToID.containsKey(word))
            addWord(word);
          
          // Add the page id of this page to the end of the
          // list of page ids for this word, but do it only
          // once even if this word appears multiple times
          // on this page.
          int wordID = wordToID.get(word);
          List<Integer> list = wordPageIDs.get(wordID);
          if (list.isEmpty() || list.get(list.size()-1) != pageID)
            list.add(pageID);
        }
      }
    }

    System.out.println("urlToID:");
    System.out.println(urlToID);
    System.out.println("idToURL:");
    System.out.println(idToURL);
    System.out.println("numRefs:");
    System.out.println(numRefs);
    System.out.println("wordToID:");
    System.out.println(wordToID);
    System.out.println("wordPageIDs:");
    System.out.println(wordPageIDs); 
  }
  
  /** Class to hold a page id and the number of reference to that
      page. */
  
  
  private class PageID implements Comparable<PageID> {

	  private int pageID;
	  private int refNum;
	  PageID(int pageID, int refNum) {
		  this.pageID = pageID;
		  this.refNum = refNum;
	  }
		  
	  
	@Override
	public int compareTo(PageID that) {
		// comparing number of references of the current object with the number
		// of references of the selected object
		
		return this.refNum - that.refNum;
	}
	  
  }
    
    /** The natural order for this class is by increasing number of
        references. */

  
  /** Return an array of up to numResults urls of pages containing
      all words in keyWords.  Array is ordered by decreasing number
      of references.
      @param keyWords list of words to look for
      @param numResults maximum number of pages to return
      @return list of URLs of pages containing all words in keyWords
  */
  public String[] search (List<String> keyWords, int numResults) {
    // Iterator into list page ids for each key word.
    Iterator<Integer>[] pageIDiterators =
      (Iterator<Integer>[]) new Iterator[keyWords.size()];
    
    // Current page id in each list, just ``behind'' the iterator.
    int[] currentPageIDs = new int[keyWords.size()];
    
    // LEAST popular page is at top of heap so if heap has more
    // than numResults elements, the least popular page can be
    // thrown away.
    PriorityQueue<PageID> bestPageIDs = new PriorityQueue<PageID>();
   
    for(int i = 0; i < pageIDiterators.length; i++) {
    	//check if the urls list for the word is empty, if so return empty string
    	String word = keyWords.get(i);
    	pageIDiterators[i] = wordPageIDs.get(wordToID.get(word)).iterator();
    	if (!pageIDiterators[i].hasNext()) {
    		
    	}
    }
    
    while (updateSmallest(currentPageIDs, pageIDiterators)) {
    	if (allEqual(currentPageIDs)) {
    		//define priority according to number of references
    		
    		bestPageIDs.offer(new PageID(currentPageIDs[0], numRefs.get(0)));
    		
    		//if size of bestPageIDs is over numResults, poll out the first element in  bestPageIDs
    		if (bestPageIDs.size() > numResults) {
    			bestPageIDs.poll();
    		}
    	}
    }
    int ID;
    String[] results = new String[bestPageIDs.size()];
    for (int index = bestPageIDs.size() -1; index > -1; index--) {
    	//put the pages in the array of results
    	ID = bestPageIDs.poll().pageID;
    	results[index] = idToURL.get(ID);
    }

    return results;
  }
  
  /** Look through currentPageIDs for all the smallest elements.  For
      each smallest element currentPageIDs[i], load the next element
      from pageIDiterators[i].  If pageIDiterators[i] does not have a
      next element, return false.  Otherwise, return true.
      @param currentPageIDs array of current page ids
      @param pageIDiterators array of iterators with next page ids
      @return true if all minimum page ids updates, false otherwise
  */
  private boolean updateSmallest
    (int[] currentPageIDs, Iterator<Integer>[] pageIDiterators) {
	  //for loop to find smallest element
	  //for loop to update smallest element. set the current equal to the next iteration 
	  //if there are no more elements, return false. if there are still elements return true.
	  int holder = currentPageIDs[0];
	  int smallestIndex = 0;
	  for(int i=1; i<currentPageIDs.length;i++) {
	      if(currentPageIDs[i] < holder) {
	          holder = currentPageIDs[i];
	           smallestIndex=i;
	      }

	  }
	  if(pageIDiterators[smallestIndex].hasNext()) {
	  pageIDiterators[smallestIndex].next();
	  smallestIndex++;
	  return true;
	  }
	  return false;
  }

  /** Check if all elements in an array are equal.
      @param array an array of numbers
      @return true if all are equal, false otherwise
  */
  private boolean allEqual (int[] array) {
	  
	  // go over all elements in the currentpageIDs array and make sure they're all equal.
	  //if equal, return true, if not, return false.
	  
	  for(int i = 0; i < array.length; i++) {
	      if (array[0]!=array[i]) {
	          return false;
	      }
	  }
	  return true;
	  }
	  

}
