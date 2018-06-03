package travis.halleck;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * 
 * @author Travis Halleck
 * All of the special buttons, such as play, next song, previous song, stop, etc.
 * Each of the special buttons has an icon associated with it's function, for example
 * the stop button has the stop icon.  Icon images are found in the getResource.
 *
 */

public class SpecialButtons extends JButton{
	private static final long serialVersionUID = 1L;

	public SpecialButtons(String buttonFunction, int width, int height) {
		if(buttonFunction == "play") {
			this.setIcon(scaleImage(width, height, new ImageIcon(MainGUI.class.getResource("/images/png/009-arrows-1.png"))));
		}else if(buttonFunction == "stop") {
			this.setIcon(scaleImage(width, height, new ImageIcon(MainGUI.class.getResource("/images/png/005-square.png"))));
		}else if(buttonFunction == "rewind") {
			this.setIcon(scaleImage(width, height, new ImageIcon(MainGUI.class.getResource("/images/png/003-arrows-4.png"))));
		}else if(buttonFunction == "forward") {
			this.setIcon(scaleImage(width, height, new ImageIcon(MainGUI.class.getResource("/images/png/007-arrows-2.png"))));
		}else if(buttonFunction == "loop") {
			this.setIcon(scaleImage(width, height, new ImageIcon(MainGUI.class.getResource("/images/png/001-arrows-6.png"))));
		}else if(buttonFunction == "pause") {
			this.setIcon(scaleImage(width, height, new ImageIcon(MainGUI.class.getResource("/images/png/016-signs-1.png"))));
		}else {
			this.setText(super.getText());
		}
	}

	//helper method for resizing icon image to ensure programmer controls the size rendered
	private ImageIcon scaleImage(int w, int h, ImageIcon icon) {
		 Image img = icon.getImage();
		 Image newImg = img.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
		 icon = new ImageIcon(newImg);
		 return icon;
	}
}
