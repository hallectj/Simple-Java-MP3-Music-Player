package travis.halleck;

import java.io.File;
import java.util.prefs.Preferences;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class FileSongChooser {
	private JFileChooser fileChooser;
	private String name;
	private String fullPath;
	private File selectedFile;
	
	private void filterSettings() {
		fileChooser.setFileFilter(new FileNameExtensionFilter("MP3 files", "mp3"));
		fileChooser.setAcceptAllFileFilterUsed(false);
	}
	
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
	
	public String getPlaylistName() {
		return name;
	}
	
	public String getFullPlaylistPath() {
		return fullPath;
	}
}
