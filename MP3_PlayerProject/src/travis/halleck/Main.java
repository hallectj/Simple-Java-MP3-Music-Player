package travis.halleck;
import java.io.IOException;

import javax.swing.SwingUtilities;

public class Main {
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() { 
<<<<<<< HEAD
			public void run() {
				MainGUI baseApp;  
=======
			public void run() {
				MainGUI baseApp;
>>>>>>> 5f499da9b033047aa7705665cecbe8e2d393eca8
				try {
					baseApp = new MainGUI();
					baseApp.setVisible(true);
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}		
<<<<<<< HEAD
		});
	}
=======
		});
	} 
>>>>>>> 5f499da9b033047aa7705665cecbe8e2d393eca8
}

