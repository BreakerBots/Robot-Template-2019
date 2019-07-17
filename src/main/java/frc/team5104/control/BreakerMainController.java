/*BreakerBots Robotics Team 2019*/
package frc.team5104.control;

import frc.team5104.main.RobotState;
import frc.team5104.main.RobotState.RobotMode;
import frc.team5104.util.CrashLogger;
import frc.team5104.util.CrashLogger.Crash;

/**
 * Handles teleoperation control
 */
public class BreakerMainController {
	
	private static DriveController DriveController = new DriveController();
	
	//Enabled
	private static void enabled() {
		//DriveAutoTuneMS.init();
		//DriveAutoTuneDS.init();
	}
	
	//Update
	private static void update() {
		RobotMode currentMode = RobotState.getMode();
		
		//Teleop
		if (currentMode == RobotMode.Teleop) {
			//Drive
			DriveController.handle();
			//DriveCharacterization.update();
			//Drive.set(DriveAutoTune.getNextSignal());
			//Drive.set(DriveAutoTuneDS.getNextSignal());
		}
		
		//Test Mode
		if (currentMode == RobotMode.Test) {
			DriveController.forceIdle();
		}
	}
	
	//Crash Trackers
	public static void handle() {
		try { update(); } catch (Exception e) { CrashLogger.logCrash(new Crash("main", e)); }
	}
	public static void init() {
		try { enabled(); } catch (Exception e) { CrashLogger.logCrash(new Crash("main", e)); }
	}
	static abstract class BreakerController {
		void handle() { try { update(); } catch (Exception e) { CrashLogger.logCrash(new Crash("main", e)); } }
		abstract void update();
		void forceIdle() { try { idle(); } catch (Exception e) { CrashLogger.logCrash(new Crash("main", e)); } }
		void idle() {  }
	}
}
