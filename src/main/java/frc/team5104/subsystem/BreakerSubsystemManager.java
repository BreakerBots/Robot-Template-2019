/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem;

import frc.team5104.util.CrashLogger;
import frc.team5104.util.CrashLogger.Crash;

/**
 * Manages the updating and handling of all BreakerSubsystems thrown into it
 */
public class BreakerSubsystemManager {
	private static BreakerSubsystem.Manager[] targets;
	
	/**
	 * NECESSARY: Tell the Subsystem Manager what Subsystems to manage
	 */
	public static void useSubsystems(BreakerSubsystem.Manager... subsystems) {
		targets = subsystems;
	}
	
	/**
	 * CALL when the robot becomes enabled
	 * @param teleop
	 */
	public static void enabled() {
		for (BreakerSubsystem.Manager t : targets) {
			try {
				t.enabled();
			} catch (Exception e) {
				CrashLogger.logCrash(new Crash("main", e));
			}
		}
	}
	
	/**
	 * CALL periodically when the robot is enabled
	 */
	public static void handle() {
		try { update(); } catch (Exception e) { CrashLogger.logCrash(new Crash("main", e)); }
	}
	private static void update() {
		for (BreakerSubsystem.Manager t : targets) {
			try {
				t.update();
			} catch (Exception e) {
				CrashLogger.logCrash(new Crash("main", e));
			}
		}
	}
	
	/**
	 * CALL when the robot becomes disabled
	 */
	public static void disabled() {
		for (BreakerSubsystem.Manager t : targets) {
			try {
				t.disabled();
			} catch (Exception e) {
				CrashLogger.logCrash(new Crash("main", e));
			}
		}
	}
}
