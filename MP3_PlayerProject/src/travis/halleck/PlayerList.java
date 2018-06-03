package travis.halleck;

import java.util.ArrayList;

/**
 * 
 * @author Travis Halleck
 * Two array list are used to store song name and song paths
 * of where the song is stored on user's computer.  The decision was
 * made to make this a static because of it's frequent use throughout
 * the application and for ease of maintenance.
 *
 */

public class PlayerList {
	@SuppressWarnings("unused")
	private static int pathCount = -1;
	private static int nameCount = -1;
	private static ArrayList<String> playerNameList = new ArrayList<String>();
	private static ArrayList<String> playerPathList = new ArrayList<String>();
	
	
	/**
	 * If song exist in array list
	 * @param item
	 * @return true if song exist in playerNameList ArrayList, false otherwise.
	 */
	
	private static boolean doesItemExist(String item){
		return playerNameList.contains(item) ? true : false;
	}
		  
	/**
	 * Private helper method that searches for the item in the list
	 * and gives me it's corresponding index;
	 * @param item takes a string and searches for it in the list
	 * @return indexof the song
	 */
	
    private static int getItemIndex(String item){
    	int index = -1;
		if(doesItemExist(item)){
			index = playerNameList.indexOf(item);
		}
		return index;
	}
    
    /**
     * Adds the song name to the playerNameList arraylist
     * @param item is the song name
     */
		  
    public static void addToNameList(String item){
    	playerNameList.add(item);
    	nameCount++;
	}
    
    /**
     * Adds the song path to the  playerPathList
     * @param item is the song path
     */
    
    public static void addToPathList(String item) {
    	playerPathList.add(item);
    	pathCount++;
    }
    
    
    /**
     * Used to get song name without needing an index passed.
     * @return String song name 
     */
    
    public static String getPlayerListName() {
    	return playerNameList.get(nameCount);
    }
    
    /**
     * Overloaded method to return song name if you need an index
     * @param index  
     * @return String song name
     */
    public static String getPlayerListName(int index) {
    	return playerNameList.get(index);
    }
	
    /**
     * Both ArrayList are correlated every time so it doesn't matter which
     * length I return, could of just as well been playerPathList ArrayList.
     * @return int the playerNameList number of elements, ie length
     */
    public static int getListSize() {
    	return playerNameList.size();
    }
    
    /**
     * 
     * @param item is the song being searched
     * @return full path of where the mp3 is being stored on the user's computer
     */
    public static String getCorrectFullPath(String item) {
    	int index = getItemIndex(item);
    	return playerPathList.get(index);
    }
    
    /**
     * Same as the getCorrectFullPath except it accepts an index if that is what you
     * need instead
     * @param index of the song stored in the playerPathList ArrayList
     * @return String full path of where the mp3 is being stored on the user's computer
     */
    
    public static String getFullPathByIndex(int index) {
    	return playerPathList.get(index);
    }

    /**
     * Removes song from player list
     * @param item
     */
    
    public static String getNamesByIndex(int index) {
    	return playerNameList.get(index);
    }

    public static void removeItem(String item) {
    	int index = getItemIndex(item);
    	nameCount--;
    	pathCount--;
    	playerPathList.remove(index);
    	playerNameList.remove(index);
    }
}
