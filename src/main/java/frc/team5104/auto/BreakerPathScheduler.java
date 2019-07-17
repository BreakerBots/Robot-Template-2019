/*BreakerBots Robotics Team 2019*/
package frc.team5104.auto;

import frc.team5104.util.CrashLogger;
import frc.team5104.util.CrashLogger.Crash;

/**
 * Handles the Execution of BreakerCommands inside the assigned BreakerCommandGroup (Entire Path)
 */
public class BreakerPathScheduler {
	public static BreakerPath path = null;
	public static int pathActionsLength = 0;
	public static int pathIndex = 0;
	public static boolean pathActionInitialized = false;
	
	/**
	 * Set the target command group
	 */
	public static void set(BreakerPath targetPath) {
		//Save the new Command Group
		path = targetPath;
		pathActionsLength = path.pathActionsLength;
		
		//Reset Command Group Filter Index
		pathIndex = 0;
		
		//Say that the first command hasn't been Initiated
		pathActionInitialized = false;
	}
	
	/**
	 * The update function call in Autonomous Periodic
	 */
	public static void handle() {
		try { update(); } catch (Exception e) { CrashLogger.logCrash(new Crash("main", e)); }
	}
	private static void update() {
		//if the command index is less than the commandGroup length
		if (pathIndex < path.pathActionsLength) {
			//If command has not been initialized
			if (!pathActionInitialized) {
				//Call the init function
				path.pathActions[pathIndex].init();
				
				//Dont call it next time
				pathActionInitialized = true;
			}
			
			//Call the update function (then if finished)
			if (path.pathActions[pathIndex].update()) {
				//Call the end init function
				path.pathActions[pathIndex].end();
				
				//Go to the next command
				pathIndex++;
				
				//Say the command hasn't been initialized
				pathActionInitialized = false;
			}
		}
	}
}
