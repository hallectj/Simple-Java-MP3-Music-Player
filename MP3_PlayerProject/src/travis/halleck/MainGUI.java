package travis.halleck;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.border.EtchedBorder;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.SystemColor;

/**
 * 
 * @author Travis Halleck
 *
 */

public class MainGUI extends GenericFrame {
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel, leftPanel, centerPanel, innerPanel, bottomPanel;
	private SpringLayout mainPanelLayout, leftPanelLayout, centerPanelLayout;
	private static DefaultComboBoxModel<String> defaultPlaylistComboBox;
	private JComboBox<String> playListComboBox;
	private JLabel label1, label2;
	private JButton newPlayListButton, deleteButton, saveListButton;
	private SpecialButtons playButton, loopButton, rewindButton, forwardButton, pauseButton, stopButton;
	private PlaylistFile plf;
	
	//loopClicks is needed so I can determine even and odd numbers to know if the loop
	//button is "on" or "off"
	
	private static int loopClicks = 0;
	private MusicPlayer mp;
	
	public MainGUI() throws IOException {
		this.setVisible(true);
		this.setTitle("Simple Java Music Player");
		initUI();
		mp = new MusicPlayer("");
	}
	
	@SuppressWarnings("rawtypes")
	public static DefaultComboBoxModel getComboBox() {
		return defaultPlaylistComboBox;
	}
	
	private void initUI()  {
		//initialize layouts
		mainPanelLayout = new SpringLayout();
		leftPanelLayout = new SpringLayout();
		centerPanelLayout = new SpringLayout();
		
		//initialize panels needed
		mainPanel = new JPanel();
		leftPanel = new JPanel();
		innerPanel = new JPanel();
		centerPanel = new JPanel();
		bottomPanel = new JPanel();
		
		//initialize labels needed
		label1 = new JLabel("JAVA MUSIC PLAYER");
		label2 = new JLabel("Playlist");
		
		//initialize buttons needed
		newPlayListButton = new JButton("New Playlist");
		deleteButton = new JButton("Delete Song");	
		saveListButton = new JButton("Save Playlist");
		saveListButton.setBackground(SystemColor.inactiveCaption);
		saveListButton.setOpaque(true);
		
		//playlist combobox
		defaultPlaylistComboBox = new DefaultComboBoxModel<String>();
		playListComboBox = new JComboBox<String>(defaultPlaylistComboBox);
		playListComboBox.setPreferredSize(new Dimension(80, 25));
		
		mainPanel.setBackground(new Color( 136, 165, 124 ));
		centerPanel.setBackground(new Color( 148, 191, 163 ));
		leftPanel.setBackground(new Color( 148, 191, 163 ));
		bottomPanel.setBackground(new Color( 148, 191, 163 ));
		
		//Populate the comboBox and ArrayLists if music_names.txt and music_paths.txt 
		//exist.  The two ArrayList are located in the PlayerList class
		try {
			plf = new PlaylistFile(defaultPlaylistComboBox);
			if(plf.doesFileExist()) {
				plf.readFromFiles();
			}	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setupPanels();
		setupLayout();
		initEnterFrame();
		specialButtonEvents();
		
		//If no song exist, disable all the special buttons (ie stop, play, next song, etc)
		//Also disables the delete button since there is nothing to delete.  However if mp3's
		//are read from the text files or user adds them via the addPlayList button on the next
		//frame then all the buttons will be enabled.
		if(PlayerList.getListSize() == 0) {
			enableSpecialButtons(false);
			deleteButton.setEnabled(false);
		}else {
			enableSpecialButtons(true);
			deleteButton.setEnabled(true);
		}	
	}
	
	//If the component is a JButton and is an instance of SpecialButtons then
	//setEnable to true or false.
	
	private void enableSpecialButtons(boolean enable) {
		Component[] component = bottomPanel.getComponents();
		for(int i = 0; i<bottomPanel.getComponentCount(); i++) {
			if(component[i] instanceof SpecialButtons) {
				if(enable == true) {
					component[i].setEnabled(true);
				}else {
					component[i].setEnabled(false);
				}
			}
		}
	}
	
	//Windows look and feel
	private void lookAndFeel() {
        try {
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	private void setupLayout() {
        lookAndFeel();
        
		//layout managers
		mainPanel.setLayout(mainPanelLayout);
		centerPanel.setLayout(centerPanelLayout);
		leftPanel.setLayout(leftPanelLayout);
		
		//mainPanel Layout
		mainPanelLayout.putConstraint(SpringLayout.WEST, leftPanel, 10, SpringLayout.WEST, mainPanel);
		mainPanelLayout.putConstraint(SpringLayout.SOUTH, leftPanel, 225, SpringLayout.NORTH, mainPanel);
		mainPanelLayout.putConstraint(SpringLayout.EAST, leftPanel, 144, SpringLayout.WEST, mainPanel);
		mainPanelLayout.putConstraint(SpringLayout.NORTH, leftPanel, 10, SpringLayout.NORTH, mainPanel);
		mainPanelLayout.putConstraint(SpringLayout.NORTH, centerPanel, 0, SpringLayout.NORTH, leftPanel);
		mainPanelLayout.putConstraint(SpringLayout.WEST, centerPanel, 6, SpringLayout.EAST, leftPanel);
		mainPanelLayout.putConstraint(SpringLayout.SOUTH, centerPanel, 0, SpringLayout.SOUTH, leftPanel);
		mainPanelLayout.putConstraint(SpringLayout.EAST, centerPanel, -6, SpringLayout.EAST, mainPanel);
		mainPanelLayout.putConstraint(SpringLayout.NORTH, bottomPanel, 6, SpringLayout.SOUTH, leftPanel);
		mainPanelLayout.putConstraint(SpringLayout.WEST, bottomPanel, 10, SpringLayout.WEST, mainPanel);
		mainPanelLayout.putConstraint(SpringLayout.SOUTH, bottomPanel, 86, SpringLayout.SOUTH, leftPanel);
		mainPanelLayout.putConstraint(SpringLayout.EAST, bottomPanel, 0, SpringLayout.EAST, centerPanel);
		
		//leftPanel Layout
		leftPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		leftPanelLayout.putConstraint(SpringLayout.NORTH, label2, 10, SpringLayout.NORTH, leftPanel);
		leftPanelLayout.putConstraint(SpringLayout.WEST, label2, 45, SpringLayout.WEST, leftPanel);
		leftPanelLayout.putConstraint(SpringLayout.NORTH, playListComboBox, 6, SpringLayout.SOUTH, label2);
		leftPanelLayout.putConstraint(SpringLayout.WEST, playListComboBox, 22, SpringLayout.WEST, leftPanel);
		leftPanelLayout.putConstraint(SpringLayout.WEST, saveListButton, 10, SpringLayout.WEST, leftPanel);
		leftPanelLayout.putConstraint(SpringLayout.SOUTH, saveListButton, -10, SpringLayout.SOUTH, leftPanel);
		

		//centerPanel Layout
		centerPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		centerPanelLayout.putConstraint(SpringLayout.NORTH, newPlayListButton, 15, SpringLayout.SOUTH, label1);
		centerPanelLayout.putConstraint(SpringLayout.NORTH, label1, 10, SpringLayout.NORTH, centerPanel);
		centerPanelLayout.putConstraint(SpringLayout.WEST, label1, 112, SpringLayout.WEST, centerPanel);
		centerPanelLayout.putConstraint(SpringLayout.NORTH, deleteButton, 0, SpringLayout.NORTH, newPlayListButton);
		centerPanelLayout.putConstraint(SpringLayout.WEST, deleteButton, 6, SpringLayout.EAST, newPlayListButton);
		centerPanelLayout.putConstraint(SpringLayout.EAST, deleteButton, -62, SpringLayout.EAST, centerPanel);
		centerPanelLayout.putConstraint(SpringLayout.EAST, newPlayListButton, 156, SpringLayout.WEST, centerPanel);
		centerPanelLayout.putConstraint(SpringLayout.WEST, newPlayListButton, 21, SpringLayout.WEST, centerPanel);
		centerPanelLayout.putConstraint(SpringLayout.NORTH, innerPanel, 14, SpringLayout.SOUTH, newPlayListButton);
		centerPanelLayout.putConstraint(SpringLayout.WEST, innerPanel, 0, SpringLayout.WEST, newPlayListButton);
		centerPanelLayout.putConstraint(SpringLayout.SOUTH, innerPanel, -16, SpringLayout.SOUTH, centerPanel);
		centerPanelLayout.putConstraint(SpringLayout.EAST, innerPanel, 0, SpringLayout.EAST, deleteButton);
		
		//innerPanel Layout
		innerPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.GRAY));
		innerPanel.setLayout(new GridLayout(1, 0, 0, 0));
		JLabel picLabel = new JLabel();
		picLabel.setHorizontalAlignment(SwingConstants.CENTER);
		picLabel.setIcon(scaleImage(250, 100, new ImageIcon(MainGUI.class.getResource("/images/music_pic.jpg"))));
		innerPanel.add(picLabel);
		
		//Gridbag layout for specialbuttons to consider
		loopButton = new SpecialButtons("loop", 25, 25);
		GridBagConstraints gbc_loopButton = new GridBagConstraints();
		gbc_loopButton.anchor = GridBagConstraints.WEST;
		gbc_loopButton.fill = GridBagConstraints.VERTICAL;
		gbc_loopButton.insets = new Insets(10, 10, 0, 5);
		gbc_loopButton.gridx = 1;
		gbc_loopButton.gridy = 0;
		bottomPanel.add(loopButton, gbc_loopButton);
		
		
		rewindButton = new SpecialButtons("rewind", 25, 25);
		GridBagConstraints gbc_rewindButton = new GridBagConstraints();
		gbc_rewindButton.anchor = GridBagConstraints.WEST;
		gbc_rewindButton.fill = GridBagConstraints.VERTICAL;
		gbc_rewindButton.insets = new Insets(10, 10, 0, 5);
		gbc_rewindButton.gridx = 2;
		gbc_rewindButton.gridy = 0;
		bottomPanel.add(rewindButton, gbc_rewindButton);
		
		
		forwardButton = new SpecialButtons("forward", 25, 25);
		GridBagConstraints gbc_forwardButton = new GridBagConstraints();
		gbc_forwardButton.anchor = GridBagConstraints.WEST;
		gbc_forwardButton.fill = GridBagConstraints.VERTICAL;
		gbc_forwardButton.insets = new Insets(10, 10, 0, 5);
		gbc_forwardButton.gridx = 3;
		gbc_forwardButton.gridy = 0;
		bottomPanel.add(forwardButton, gbc_forwardButton);
		
		
		playButton = new SpecialButtons("play", 25, 25);
		GridBagConstraints gbc_playButton = new GridBagConstraints();
		gbc_playButton.anchor = GridBagConstraints.WEST;
		gbc_playButton.fill = GridBagConstraints.VERTICAL;
		gbc_playButton.insets = new Insets(10, 10, 0, 5);
		gbc_playButton.gridx = 4;
		gbc_playButton.gridy = 0;
		bottomPanel.add(playButton, gbc_playButton);
		
		
		pauseButton = new SpecialButtons("pause", 25, 25);
		GridBagConstraints gbc_pauseButton = new GridBagConstraints();
		gbc_pauseButton.anchor = GridBagConstraints.WEST;
		gbc_pauseButton.fill = GridBagConstraints.VERTICAL;
		gbc_pauseButton.insets = new Insets(10, 10, 0, 5);
		gbc_pauseButton.gridx = 5;
		gbc_pauseButton.gridy = 0;
		bottomPanel.add(pauseButton, gbc_pauseButton);
		
		
		stopButton = new SpecialButtons("stop", 25, 25);
		GridBagConstraints gbc_stopButton = new GridBagConstraints();
		gbc_stopButton.insets = new Insets(10, 10, 0, 5);
		gbc_stopButton.anchor = GridBagConstraints.WEST;
		gbc_stopButton.fill = GridBagConstraints.VERTICAL;
		gbc_stopButton.gridx = 6;
		gbc_stopButton.gridy = 0;
		bottomPanel.add(stopButton, gbc_stopButton);
		
		//bottomPanel Layout
		bottomPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
	}

	private void setupPanels() {
		this.getContentPane().add(mainPanel);
		
		//add to main panel
		mainPanel.add(leftPanel);
		mainPanel.add(centerPanel);
		mainPanel.add(bottomPanel);
		
		//add to left panel
		leftPanel.add(label2);
		leftPanel.add(playListComboBox);
		leftPanel.add(saveListButton);
		
		//General gridbag layout for specialButtons
		GridBagLayout gbl_bottomPanel = new GridBagLayout();
		gbl_bottomPanel.columnWidths = new int[]{47, 33, 33, 33, 33, 33, 90, 33, 0};
		gbl_bottomPanel.rowHeights = new int[]{35, 0};
		gbl_bottomPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_bottomPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		bottomPanel.setLayout(gbl_bottomPanel);
		
				
		//add to center panel
		centerPanel.add(innerPanel);
		centerPanel.add(label1);
		centerPanel.add(newPlayListButton);
		centerPanel.add(deleteButton);
		
		//deleteButton.setEnabled(false);	    
	}
	
	@SuppressWarnings("static-access")
	private void initEnterFrame() {
		newPlayListButton.addActionListener((ActionEvent frameEvent) -> {
			NewPlayerlistGUI playListGUI = NewPlayerlistGUI.getInstance();
			playListGUI.SetVisiblity();
			saveListButton.setBackground(SystemColor.activeCaption);
		});
		
		saveListButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				saveListButton.setBackground(SystemColor.LIGHT_GRAY);
				try {
					plf = new PlaylistFile(defaultPlaylistComboBox);
					plf.writeToFiles();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		
		deleteButton.addActionListener((ActionEvent evt) -> {
			String highlight = defaultPlaylistComboBox.getSelectedItem().toString();
			
			int lastSlash = mp.getPath().lastIndexOf("\\");
			if(mp.getPath().substring(lastSlash+1, mp.getPath().length()).equals(highlight)) {
				mp.stop();
			}	
			
			
			defaultPlaylistComboBox.removeElement(highlight);
			PlayerList.removeItem(highlight);
			
			if(defaultPlaylistComboBox.getSize() == 0) {
				enableSpecialButtons(false);
				deleteButton.setEnabled(false);
			}else {
				enableSpecialButtons(true);
				deleteButton.setEnabled(true);
			}
		});
		
		//checks selected item, since there is an item to begin with, the combobox
		//effectively has items and fullfills what I need this for, which is to 
		//check for items and enable buttons if so.
		playListComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
                if(event.getStateChange() == ItemEvent.SELECTED) {
                	enableSpecialButtons(true);
                	deleteButton.setEnabled(true);
                	
                	//If the user had renamed the file or moved it, then rewrite the
                	//songs to the text file.  Note, this only works if there is more 
                	//than one user error.  This is a known bug that needs to be addressed
                	
                	if(mp.didFileErrorOut()) {
                		plf.writeToFiles();
                	}
                }	
			}
        });
		
	}
	
	private ImageIcon scaleImage(int w, int h, ImageIcon icon) {
		 Image img = icon.getImage();
		 Image newImg = img.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
		 icon = new ImageIcon(newImg);
		 return icon;
	}
	
	private void specialButtonEvents() {
		ClickLisetner cl = new ClickLisetner();
		playButton.addActionListener(cl);
		pauseButton.addActionListener(cl);
		forwardButton.addActionListener(cl);
		rewindButton.addActionListener(cl);
		loopButton.addActionListener(cl);
		stopButton.addActionListener(cl);
	}
	
	private class ClickLisetner implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			SpecialButtons clicked = (SpecialButtons)e.getSource();
			
			String comboBoxHighlight = (String) playListComboBox.getSelectedItem();
			String absPath = PlayerList.getCorrectFullPath(comboBoxHighlight);
			mp.setPath(absPath);
			
			
			//TODO  There is a bug where if the user plays a song, presses pause,
			//then selects a different song, presses play and then pause again.  What
			//happens is, is the song plays and the pause will act as the stop button.
			//if the stop is pressed everything is back to normal, so somewhere I need
			//to check for that behavior and add an additional mp.stop().
			
			if((clicked == playButton)) {
				//The mp.setComboBoxListener only runs if the user has moved
				//or renamed a file, it is otherwise skipped.
				
				mp.setComboBoxListener(new ComboBoxListener() {
					@Override
					public void removeItem(String item)  {
						defaultPlaylistComboBox.removeElement(item);
					}
				});				
					
				if(mp.isSongPaused() && !mp.isSongPlaying()){
					mp.resume();
				}else if(mp.getSongLength() == 0) {
					mp.play();
				}
			}else if(clicked == stopButton) {
				mp.stop();
			}else if(clicked == pauseButton) {
				if(mp.getSongLength() == 0) {
					mp.stop();
				}else {
					//This prevents the user from crashing the program if the user
					//keeps pressing pause.
					if(mp.isSongPaused()) {
						return;
					}else {
						mp.pause();
						System.out.println("just pause");
					}
				}
			}else if(clicked == forwardButton) {
				mp.stop();
				if(defaultPlaylistComboBox.getIndexOf(comboBoxHighlight)+1 != defaultPlaylistComboBox.getSize()) {
					playListComboBox.setSelectedIndex(defaultPlaylistComboBox.getIndexOf(comboBoxHighlight)+1);
				}
			}else if(clicked == rewindButton) {
				mp.stop();
				if(defaultPlaylistComboBox.getIndexOf(comboBoxHighlight)-1 >= 0) {
					playListComboBox.setSelectedIndex(defaultPlaylistComboBox.getIndexOf(comboBoxHighlight)-1);
				}
			}else if(clicked == loopButton) {
				loopClicks++;
				if(loopClicks == 10) {
					loopClicks = 0;
				}
						
				//If odd number (on) then loop button is toggled with darken image
				//and the repeat is set to true.
				if(loopClicks % 2 != 0) {
					mp.setRepeat(true);
					loopButton.setIcon(scaleImage(25, 25, new ImageIcon(MainGUI.class.getResource("/images/png/002-arrows-5.png"))));
				}else {
					loopButton.setIcon(scaleImage(25, 25, new ImageIcon(MainGUI.class.getResource("/images/png/001-arrows-6.png"))));
					mp.setRepeat(false);
				}
			}	
		}
	}

}
	
	

