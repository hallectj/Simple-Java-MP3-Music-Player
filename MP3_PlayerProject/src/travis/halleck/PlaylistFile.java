package travis.halleck;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;

public class PlaylistFile {
	private DefaultComboBoxModel<String> cbBox;
	private BufferedReader brNames, brPaths;
	private BufferedWriter bwNames, bwPaths;
	private final String NamesOnList = "music_names.txt";
	private final String PathsOnList = "music_paths.txt";
	private int lineCount;
	private ArrayList<String> pathNames, songNames;
	
	
	public PlaylistFile(DefaultComboBoxModel<String> cbBox) throws IOException {
		this.cbBox = cbBox;
	}
	
	public void readFromFiles() throws IOException { 
			try {
				songNames = new ArrayList<String>();
				pathNames = new ArrayList<String>();
				brNames = new BufferedReader(new FileReader(NamesOnList));
				brPaths = new BufferedReader(new FileReader(PathsOnList));
				
				while(true) {
					String names = brNames.readLine();
					String paths = brPaths.readLine();
					
					if(names == null || paths == null) {
						break;
					}
					
					songNames.add(names);
					pathNames.add(paths);
					
					lineCount++;
					PlayerList.addToNameList(names);
					PlayerList.addToPathList(paths);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			brNames.close();
			brPaths.close();
			populateCB();
	}
	
	public void writeToFiles() throws IOException  {
		try {
			bwNames = new BufferedWriter(new FileWriter(NamesOnList));
			bwPaths = new BufferedWriter(new FileWriter(PathsOnList));
			
			for(int i = 0; i<PlayerList.getListSize(); i++) {
				bwNames.write(PlayerList.getNamesByIndex(i));
				bwNames.newLine();
				bwPaths.write(PlayerList.getFullPathByIndex(i));
				bwPaths.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
			e.getMessage();
		}
		bwNames.close();
		bwPaths.close();
	}
	
	private void populateCB(){
		for(int i = 0; i<pathNames.size(); i++) {
			cbBox.addElement(songNames.get(i));
		}
	}
	
	public boolean doesFileExist() {
		Path path = Paths.get(NamesOnList);
		boolean fileExist = Files.exists(path);
		if(fileExist) {
			return true;
		}
		return false;
		
	}
	
	public int getLength() {
		return lineCount;
	}
	
}
