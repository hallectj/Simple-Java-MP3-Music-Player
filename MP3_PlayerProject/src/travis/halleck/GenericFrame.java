package travis.halleck;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * 
 * @author Travis Halleck
 * Sets up basic attributes I want for frames, such as size and
 * setting the frame to the middle of the computer screen.
 *
 */
public class GenericFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private int winWidth = 525;
	private int winHeight = winWidth - (winWidth / 3);
	
	public GenericFrame() {
		setUpFrame();
	}
	
	private void setUpFrame() {
		this.setSize(winWidth, winHeight);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		int xPos = (dim.width/2) - (this.getWidth()/2);
		int yPos = (dim.height/2) - (this.getHeight()/2);
		
		this.setLocation(xPos, yPos);
		this.setResizable(false);
		
		if(this instanceof MainGUI) {
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}else {
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
	}
}
