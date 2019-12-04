/*BreakerBots Robotics Team 2019*/
package frc.team5104;

/** 
 * The Superstructure is a massive state machine that handles the Intake, Wrist, and Elevator
 * The Superstructure only controls the states... its up the subsystems to figure out what to do
 * based on the state of the Superstructure.
 */
public class Superstructure {
	//States and Variables
	public static enum SystemState { DISABLED }
	private static SystemState systemState = SystemState.DISABLED;
	
	//External Functions
	public static SystemState getSystemState() { return systemState; }
	public static void setSystemState(SystemState systemState) { 
		Superstructure.systemState = systemState;
	}

	//Manage States
	static void update() {
		
	}
	static void enabled() { setToDefaultStates(); }
	static void disabled() { setToDefaultStates(); }
	private static void setToDefaultStates() {
		systemState = SystemState.DISABLED;
	}
}