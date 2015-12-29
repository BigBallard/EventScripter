package com.champtc.champ.eventscripter;

import java.util.Scanner;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;


/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication {

	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
	 */
	public Object start(IApplicationContext context) throws Exception {
		
//		ScripterUI SUI = new ScripterUI();
		
//		ScriptController controller = new ScriptController();
//		controller.setSourceFolder(); 
//		controller.setDestinationFolder();
//		controller.setTimerPreferences();
//		System.out.println("Press ENTER to run...");
//		
//		String choice = new Scanner(System.in).nextLine();
//		
//		if(choice.equals("\n")){
//			controller.run();
//		}
		   return IApplication.EXIT_OK;
  }

	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#stop()
	 */
	public void stop() {
		// nothing to do
	}
}
