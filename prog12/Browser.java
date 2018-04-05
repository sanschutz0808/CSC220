package prog12;

import java.util.List;

public interface Browser {
    /**
     * 
     * @param url
     * @return true if page loads properly and false if it does not
     */
	boolean loadPage (String url);
    
	
	/**
     * gets the list of words */
	List<String> getWords ();
    
	
	/**
     * gets the list of urls */
	List<String> getURLs ();
}

