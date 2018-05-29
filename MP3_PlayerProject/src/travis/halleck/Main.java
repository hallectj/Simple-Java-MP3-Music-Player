package travis.halleck;
import java.io.IOException;

import javax.swing.SwingUtilities;

public class Main {
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() { 
			public void run() {
<<<<<<< HEAD
				MainGUI baseApp;  
=======
				MainGUI baseApp;
>>>>>>> 0bf3db91d33d43c45bd7a02af0558d1542c1d0ee
				try {
					baseApp = new MainGUI();
					baseApp.setVisible(true);
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}		
		});
<<<<<<< HEAD
	} 
=======
	}
>>>>>>> 0bf3db91d33d43c45bd7a02af0558d1542c1d0ee
}
