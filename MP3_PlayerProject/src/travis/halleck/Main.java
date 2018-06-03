package travis.halleck;
import java.io.IOException;
/**
 * @author Travis Halleck
 * Starting point for the program to run.  
 */

import javax.swing.SwingUtilities;

public class Main {
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() { 
			public void run() {
				MainGUI baseApp;
				try {
					baseApp = new MainGUI();
					baseApp.setVisible(true);
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}		
		});
	}
}

