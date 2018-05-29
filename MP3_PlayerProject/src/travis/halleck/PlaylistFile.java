package travis.halleck;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.DefaultComboBoxModel;

public class PlaylistFile {
	private DefaultComboBoxModel<String> cb;
	private BufferedReader brNames, brPaths;
	private BufferedWriter bwNames, bwPaths;
	private final String NamesOnList = "music_names.txt";
	private final String PathsOnList = "music_paths.txt";
	private int lineCount;
	
	public PlaylistFile(DefaultComboBoxModel<String> cb) throws IOException {
		this.cb = cb;
	}
	
	public void readFromFiles() throws IOException { 
			
			try {
				brNames = new BufferedReader(new FileReader(NamesOnList));
				brPaths = new BufferedReader(new FileReader(PathsOnList));
				
				while(true) {
					String names = brNames.readLine();
					String paths = brPaths.readLine();
					
					if(names == null || paths == null) {
						break;
					}
					
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
	
	private void populateCB() {
		File fileNames = new File(NamesOnList);  
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(fileNames));
			String st;
			
			while ((st = br.readLine()) != null) {
			    cb.addElement(st);
			}
		} catch (IOException e) {
			e.printStackTrace();
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
