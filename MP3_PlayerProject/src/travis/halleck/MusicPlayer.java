package travis.halleck;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 * 
 * @author Travis Halleck
 * All of the basic functions you would expect in an mp3 player
 * 
 *
 */

public class MusicPlayer {
	private String songPath;
	private FileInputStream fis;
	private BufferedInputStream bis;
	private boolean isPaused, playerCompleted, isPlaying, loopSong;
	private Player player;
	private long pausePosInBits, songLenInBits, currentTime;
	
	private boolean fileErroredOut;  //error occurs when user renames file or moves file.
	private ComboBoxListener cbListener;
	
	/**
	 * 
	 * @param songPath which is ultimately retrieved via the PlayerList class
	 */
	
	public MusicPlayer(String songPath) {
		this.songPath = songPath;
		isPaused = false;
		playerCompleted = false;
		isPlaying = false;	
		fileErroredOut = false;
	}
	
	/**
	 * Instead of creating a comboBox component, I decided to pass in the 
	 * interface in case later I decide to use some other component to display
	 * playerlist other than a combobox.  This decision was made to decouple.
	 * @param comboBoxListener
	 */
	public void setComboBoxListener(ComboBoxListener comboBoxListener) {
		this.cbListener = comboBoxListener;
	}
	
	public void play() {
		try {
			fis = new FileInputStream(songPath);
			bis = new BufferedInputStream(fis);
			songLenInBits = bis.available();
			player = new Player(bis);
		} catch (JavaLayerException | IOException e) {
			//This exception handles the known error of a user moving or renaming a file.
			
			//Display JOptionPane message notifying user the file can not be located  
			//Remove path and name from text files
			//Remove from comboBox playlist
			//Remove from PlayerList name and path
			//After removal from combobox re-populate combobox
			fileErroredOut = true;
			
			handleMoveAndRenameFileError();
			
		    if(fileErroredOut) {
		    	this.stop();
		    	fileErroredOut = false;
		    	return;
		    }
			
		    //The expected error is when the user changes the name of the file or
		    //moves the destination of the file.  That error is handled in the catch
		    //clause.  The e.printStackTrace() is there for any other error I'm not 
		    //aware of, although there shouldn't be any.
		    e.printStackTrace();
		}
		/**
		 * currentTime is the song in seconds played, so if you were
		 * to pause the song after 5 seconds, then current time would be
		 * 5 seconds.  Every play, pause and resume sets flags so MainGUI knows 
		 * what to do.
		 */
		Thread thread = new Thread(new Runnable() {
			public void run() {
				try {
					isPlaying = true;
					long startTime = System.nanoTime();
					
					player.play();
					long endTime = System.nanoTime();
					currentTime = (endTime - startTime) / 1000000000;
					
					if(player.isComplete()) {
						isPlaying = false;
						pausePosInBits = 0;
						songLenInBits = 0;
						currentTime = 0;
						
						if(loopSong) { loop(); }
					}
					
					playerCompleted = player.isComplete();
				} catch (JavaLayerException e) {
					e.printStackTrace();
				}
			}
		});
		
		thread.start();
	}
	
	/**
	 * resets all the flags and stops the mp3
	 */
	public void stop() {
		isPaused = false;
		if(player != null) {
			player.close();
			pausePosInBits = 0;
			songLenInBits = 0;
			isPlaying = false;
			currentTime = 0;
		}
	}
	
	public void pause() {
		isPaused = true;
		isPlaying = false;
		if(player != null) {
			try {
				pausePosInBits = bis.available();
				player.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void resume() {
		try {
			isPaused = false;
			fis = new FileInputStream(songPath);
			bis = new BufferedInputStream(fis);
			
			bis.skip(songLenInBits - pausePosInBits);
			
			player = new Player(bis);
			
			
		} catch (JavaLayerException | IOException e) {
			
			fileErroredOut = true;
			
			handleMoveAndRenameFileError();
			
		    if(fileErroredOut) {
		    	this.stop();
		    	fileErroredOut = false;
		    	return;
		    }
			
			e.printStackTrace();
		}
		
		new Thread() {
			public void run() {
				try {
					long startTime = System.nanoTime();
					player.play();
					long endTime = System.nanoTime();
					currentTime += (endTime - startTime) / 1000000000;
					
					if(player.isComplete()) {
						isPlaying = false;
						pausePosInBits = 0;
						songLenInBits = 0;
						currentTime = 0;
						
						if(loopSong) { loop(); }
					}
					
					
					
				
				} catch (JavaLayerException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	
	public void loop() {
		if(loopSong) {
			if(currentTime == 0) {
				play();
			}
		}
	}
	
	/**
	 * If user has an mp3 that is moved or renamed in current playlist that
	 * doesn't jive with path from PlayerList ArrayList.
	 * @return boolean true if user error found.
	 */
	public boolean didFileErrorOut() {
		return 	fileErroredOut;
	}

	/**
	 * 
	 * @return boolean true if song is paused
	 */
	public boolean isSongPaused() {
		return isPaused;
	}
	
	/**
	 * Sets the song path, done in MainGUI.
	 * @param absPath
	 */
	public void setPath(String absPath) {
		songPath = absPath;
	}
	
	/**
	 * 
	 * @return String song path, path of course is the location on user's computer of song.
	 */
	public String getPath() {
		return songPath;
	}
	
	/**
	 * This is the pause position according to the BufferedInputStream available bytes.
	 * @return long position where user paused the mp3
	 */
	public long getPausePos() {
		return pausePosInBits;
	}
	
	/**
	 * Song length
	 * @return long song length according to BufferedInputStream
	 */
	public long getSongLength() {
		return songLenInBits;
	}
	
	/**
	 * Is player finished playing mp3 song.
	 * @return boolean
	 */
	public boolean isPlayerFinished() {
		return playerCompleted;
	}
	
	public boolean isSongPlaying() {
		return isPlaying;
	}
	
	/**
	 * If user presses loop button
	 * @return boolean
	 */
	public boolean isRepeating() {
		return loopSong;
	}
	
	
	public void setRepeat(boolean repeat) {
		loopSong = repeat;
	}
	
	/**
	 * Current time is the song in seconds.  If user clicks pause after 
	 * 5 seconds, then currentTime returns long 5.
	 * @return long current time of song in seconds.
	 */
	
	public long getCurrentTime() {
		return currentTime;
	}
	
	/**
	 * Gets the song name that is playing
	 * @return String
	 */
	public String getSongName() {
		return songPath.substring(songPath.lastIndexOf("\\")+1, songPath.length());
	}
	
	/**
	 * Handles the error if user moves the mp3 file or changes the name of the file
	 */
	private void handleMoveAndRenameFileError() {
		int lastSlasth = songPath.lastIndexOf("\\") + 1;
		String name = songPath.substring(lastSlasth, songPath.length());	
		
		cbListener.removeItem(name);
		PlayerList.removeItem(name);
	    JOptionPane.showMessageDialog(null,
	            "Problem locating file: " + songPath + "\nsong will be removed from playlist.  Import the song and try again.",
	            "File Location Error",
	            JOptionPane.INFORMATION_MESSAGE);

	}
}
