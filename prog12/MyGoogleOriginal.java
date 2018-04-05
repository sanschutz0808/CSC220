package prog12;

import java.net.URL;
import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

public class MyGoogleOriginal implements Google {
     private Map<String, Integer>  urltoID = new TreeMap<String,Integer>();
     private List<Integer> numRef = new ArrayList<Integer>();
     private List<String> idToURL = new ArrayList<String>();
	 private Map<String, Integer> wordToID = new HashMap<String, Integer>();
	 private List<List<Integer>> wordToURL = new ArrayList<List<Integer>>();
	 private Queue<String> urlQueue = new LinkedList<String>();
	 


    private void addPage(String url) {
    	urltoID.put(url, urltoID.size());
    	numRef.add(0);
    	idToURL.add(url);
    }
    
    private void addWord(String word) {
    	wordToID.put(word, wordToID.size());
    	wordToURL.get(wordToID.get(word)).add(urltoID.get(word));
    }

@Override
	
	public void gather(Browser browser, List<String> startingURLs) {
		// TODO Auto-generated method stub
	for(String url :startingURLs) {
		if (!urltoID.containsKey(url)) {
			addPage(url);
			urlQueue.offer(url);
		}
	}
	
	int count = 0;
	  while (!urlQueue.isEmpty() && count++ < 100) {
	  	String url = urlQueue.poll();
		System.out.println(url);
	  }


	}

	@Override
	public String[] search(List<String> keyWords, int numResults) {
		// TODO Auto-generated method stub
		return new String[0];
	}

}
