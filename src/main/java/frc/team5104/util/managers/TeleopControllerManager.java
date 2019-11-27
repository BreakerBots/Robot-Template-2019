/* BreakerBots Robotics Team (FRC 5104) 2020 */
package frc.team5104.util.managers;

import frc.team5104.util.CrashLogger;
import frc.team5104.util.console;
import frc.team5104.util.CrashLogger.Crash;
import frc.team5104.util.console.c;
import frc.team5104.util.console.t;

/** Manages all the calls for all Teleop Controllers given */
public class TeleopControllerManager {
	private static TeleopController[] targetTeleopControllers;
	
	/** Tell the State Machine Manager what State Machines to use */
	public static void useTeleopControllers(TeleopController... teleopControllers) {
		targetTeleopControllers = teleopControllers;
		
		//Print out
		String printOut = "Running Teleop Controllers: ";
		for (TeleopController teleopController : teleopControllers) {
			printOut += teleopController.getName() + " ";
		}
		console.log(c.MAIN, t.INFO, printOut);
	}
	
	/** Call periodically when the robot is enabled (and wants teleoperation) */
	public static void update() {
		for (TeleopController teleopController : targetTeleopControllers) {
			try {
				//Call teleop controller function
				teleopController.update();
			} catch (Exception e) { CrashLogger.logCrash(new Crash("main", e)); }
		}
	}

	/** Call once the robot becomes enabled */
	public static void enabled() {
		for (TeleopController teleopController : targetTeleopControllers) {
			try {
				//Call teleop controller function
				teleopController.enabled();
			} catch (Exception e) { CrashLogger.logCrash(new Crash("main", e)); }
		}
	}
}