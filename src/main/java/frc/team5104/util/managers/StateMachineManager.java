/* BreakerBots Robotics Team (FRC 5104) 2020 */
package frc.team5104.util.managers;

import frc.team5104.util.CrashLogger;
import frc.team5104.util.CrashLogger.Crash;
import frc.team5104.util.console;
import frc.team5104.util.console.c;
import frc.team5104.util.console.t;

public class StateMachineManager {
private static StateMachine[] targetStateMachines;
	/** Tell the State Machine Manager what State Machines to use */
	public static void useStateMachines(StateMachine... availableStateMachines) {
		//Save target state machines
		targetStateMachines = availableStateMachines;
		
		//Print out
		String printOut = "Running State Machines: ";
		for (StateMachine state_machine : targetStateMachines) {
			printOut += state_machine.getName() + " ";
		}
		console.log(c.MAIN, t.INFO, printOut);
	}

	/** Call when the robot becomes enabled */
	public static void enabled() {
		for (StateMachine state_machine : targetStateMachines) {
			try {
				//Call state machine function
				state_machine.enabled();
			} catch (Exception e) { CrashLogger.logCrash(new Crash("main", e)); }
		}
	}

	/** Call when the robot becomes disabled */
	public static void disabled() {
		for (StateMachine state_machine : targetStateMachines) {
			try {
				//Call state machine function
				state_machine.disabled();
			} catch (Exception e) { CrashLogger.logCrash(new Crash("main", e)); }
		}
	}

	/** Call periodically when the robot is enabled */
	public static void update() {
		for (StateMachine state_machine : targetStateMachines) {
			try {
				//Call state machine function
				state_machine.update();
			} catch (Exception e) { CrashLogger.logCrash(new Crash("main", e)); }
		}
	}
}
