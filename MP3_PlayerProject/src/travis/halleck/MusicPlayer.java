package travis.halleck;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class MusicPlayer {
	private String songPath;
	private FileInputStream fis;
	private BufferedInputStream bis;
	private boolean isPaused, playerCompleted, isPlaying, loopSong, stopClicked;
	private Player player;
	private long pausePosInBits, songLenInBits, currentTime;
	
	public MusicPlayer(String songPath) {
		this.songPath = songPath;
		isPaused = false;
		playerCompleted = false;
		isPlaying = false;	
		stopClicked = false;
	}
	
	public void play() {
		try {
			fis = new FileInputStream(songPath);
			bis = new BufferedInputStream(fis);
			songLenInBits = bis.available();
			player = new Player(bis);
		} catch (JavaLayerException | IOException e) {
			e.printStackTrace();
		}
		
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
						
						if(loopSong) {
							loop();
						}else {
							stop();
						}
					}
					
					playerCompleted = player.isComplete();
				} catch (JavaLayerException e) {
					e.printStackTrace();
				}
			}
		});
		
		thread.start();
		
	}
	
	public void stop() {
		isPaused = false;
		stopClicked = true;
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
						
						if(loopSong) {
							loop();
						}
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
	

	public boolean isSongPaused() {
		return isPaused;
	}
	
	public void setPath(String absPath) {
		songPath = absPath;
	}
	
	public String getPath() {
		return songPath;
	}
	
	public long getPausePos() {
		return pausePosInBits;
	}
	
	public long getSongLength() {
		return songLenInBits;
	}
	
	public boolean isPlayerFinished() {
		return playerCompleted;
	}
	
	public boolean isSongPlaying() {
		return isPlaying;
	}
	
	public boolean isRepeating() {
		return loopSong;
	}
	
	public void setRepeat(boolean repeat) {
		loopSong = repeat;
	}
	
	public long getCurrentTime() {
		return currentTime;
	}
	
	private void showStats() {
		System.out.println();
		System.out.println("pausePos: " + pausePosInBits + " songLen: " + songLenInBits + " currentTime: " + currentTime);
		System.out.println("isPaused: " + isPaused + " isPlaying: " + isPlaying + " loopSong " + loopSong);
		System.out.println();
	}

	public boolean stopIsClicked() {
		return stopClicked;
	}
}
