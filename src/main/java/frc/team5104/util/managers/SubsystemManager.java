/* BreakerBots Robotics Team (FRC 5104) 2020 */
package frc.team5104.util.managers;

import frc.team5104.util.CrashLogger;
import frc.team5104.util.CrashLogger.Crash;
import frc.team5104.util.console;
import frc.team5104.util.console.c;
import frc.team5104.util.console.t;

/** Manages the calls for all Subsystems given */
public class SubsystemManager {
	private static Subsystem.Actions[] targetSubsystems;
	
	/** Tell the Subsystem Manager what Subsystems to manage */
	public static void useSubsystems(Subsystem.Actions... availableSubsystems) {
		//Save all subsystems
		targetSubsystems = availableSubsystems;

		//Initialize Subsystem's Interface & Print out target subsystems
		String message = "Running Subsystems: ";
		for (Subsystem.Actions subsystem : targetSubsystems) {
			try {
				subsystem.getInterface().init();
				message += subsystem.getName() + ", ";
			} catch (Exception e) { CrashLogger.logCrash(new Crash("main", e)); }
		}
		console.log(c.MAIN, t.INFO, message.substring(0, message.length()-2));
	}
	
	/** Call when the robot becomes enabled */
	public static void enabled() {
		for (Subsystem.Actions subsystem : targetSubsystems) {
			try {
				//Call subsystem looper function (if available)
				if (subsystem.getLooper() != null)
					subsystem.getLooper().enabled();
			} catch (Exception e) { CrashLogger.logCrash(new Crash("main", e)); }
		}
	}
	
	/** Call when the robot becomes disabled */
	public static void disabled() {
		for (Subsystem.Actions subsystem : targetSubsystems) {
			try {
				//Call subsystem looper function (if available)
				if (subsystem.getLooper() != null)
					subsystem.getLooper().disabled();
			} catch (Exception e) { CrashLogger.logCrash(new Crash("main", e)); }
		}
	}
	
	/** Call periodically when the robot is enabled */
	public static void update() {
		for (Subsystem.Actions subsystem : targetSubsystems) {
			try {
				//Call subsystem looper function (if available)
				if (subsystem.getLooper() != null) {
					subsystem.getLooper().update();
				}
			} catch (Exception e) { CrashLogger.logCrash(new Crash("main", e)); }
		}
	}
}
