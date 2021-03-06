package travis.halleck;

import java.io.File;
import java.util.prefs.Preferences;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
/**
 * 
 * @author Travis Halleck
 * Simple Java MP3 Music Player
 * FileSongChooser class only accepts MP3 files by way of a filter and goes
 * to the user directory where the user will import MP3 files.  FileSongChooser
 * uses JFileChooser
 *
 */

public class FileSongChooser {
	private JFileChooser fileChooser;
	private String name;
	private String fullPath;
	private File selectedFile;
	
	private void filterSettings() {
		fileChooser.setFileFilter(new FileNameExtensionFilter("MP3 files", "mp3"));
		fileChooser.setAcceptAllFileFilterUsed(false);
	}
	
	/**
	 * FileSongChooser allows user to open directory to find mp3 to import in the program
	 * The default directory is set to home directory of the user
	 */
	
	public FileSongChooser() {
		File userDirectory = FileSystemView.getFileSystemView().getHomeDirectory();
		fileChooser = new JFileChooser(userDirectory);	
		
		filterSettings();
		int retVal = fileChooser.showOpenDialog(null);
		
		if(retVal == JFileChooser.APPROVE_OPTION) {
		    selectedFile = fileChooser.getSelectedFile();
			fileChooser.setCurrentDirectory(selectedFile);
			fullPath = selectedFile.getAbsolutePath();	
			name = selectedFile.getName();
		}
	}
	
	/**
	 * Returns the name on the song derived from the file name
	 * @return String the name of the song 
	 */
	
	public String getPlaylistName() {
		return name;
	}
	
	/**
	 * @return String the full path of the file so the Player class can locate 
	 * the song, again path is dependent upon user's location of files
	 *
	 */
	
	public String getFullPlaylistPath() {
		return fullPath;
	}
}
