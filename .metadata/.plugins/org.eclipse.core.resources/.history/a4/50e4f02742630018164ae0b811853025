package travis.halleck;

import java.util.ArrayList;

public class PlayerList {
	@SuppressWarnings("unused")
	private static int pathCount = -1;
	private static int nameCount = -1;
	private static ArrayList<String> playerNameList = new ArrayList<String>();
	private static ArrayList<String> playerPathList = new ArrayList<String>();
	
	private static boolean doesItemExist(String item){
		return playerNameList.contains(item) ? true : false;
	}
		  
    private static int getItemIndex(String item){
    	int index = -1;
		if(doesItemExist(item)){
			index = playerNameList.indexOf(item);
		}
		return index;
	}
		  
    public static void addToNameList(String item){
    	playerNameList.add(item);
    	nameCount++;
	}
    
    public static void addToPathList(String item) {
    	playerPathList.add(item);
    	pathCount++;
    }
    
    
    public static String getPlayerListName() {
    	return playerNameList.get(nameCount);
    }
	
    public static int getListSize() {
    	return playerNameList.size();
    }
    
    public static String getCorrectFullPath(String item) {
    	int index = getItemIndex(item);
    	return playerPathList.get(index);
    }
    
    public static String getFullPathByIndex(int index) {
    	return playerPathList.get(index);
    }
    
<<<<<<< HEAD
    public static String getNamesByIndex(int index) {
    	return playerNameList.get(index);
    }
    
=======
>>>>>>> 0bf3db91d33d43c45bd7a02af0558d1542c1d0ee
    public static void removeItem(String item) {
    	int index = getItemIndex(item);
    	nameCount--;
    	pathCount--;
    	playerPathList.remove(index);
    	playerNameList.remove(index);
    }
    
<<<<<<< HEAD
    public static void emptyAll() {
    	playerPathList.clear();
    	playerNameList.clear();
    }
    
=======
>>>>>>> 0bf3db91d33d43c45bd7a02af0558d1542c1d0ee
}
