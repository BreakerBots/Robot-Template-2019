/* BreakerBots Robotics Team (FRC 5104) 2020 */
package frc.team5104.util.managers;

/** 
 * A snickers rapper of all the requirements of a subsystem. 
 * 
 * Example Setups:
 *   1) Subsystem has Actions and Interface
 *   2) Subsystem has Actions, Looper, and Interface
 */
public class Subsystem {
	/** (Optional) [PROTECTED-NOT_STATIC] Handle state machines or other autonomous features */
	public static abstract class Looper {
		/** Called whenever the robot becomes enabled */
		protected abstract void enabled();
		/** Called periodically from the robot loop */
		protected abstract void update();
		/** Called whenever the robot becomes disabled; stop all devices here */
		protected abstract void disabled();
	}
	
	/** (Required) [PROTECTED-NOT_STATIC] Interfaces with a subsystem's devices */
	public static abstract class Interface {
		/** Called when robots boots up; initialize devices here */
		protected abstract void init();
	}
	
	/** (Optional) [PUBLIC-STATIC]  */
	public static abstract class Actions {
		/** Return the name of this subsystem (for prints) */
		protected abstract String getName();
		/** Return the interface for this subsystem (for calls) */
		protected abstract Subsystem.Interface getInterface();
		/** Return the looper for this subsystem (for calls) */
		protected abstract Subsystem.Looper getLooper();
		/** If the subsystem should be debugging (spamming out values to console) */
		public boolean isDebugging = false;
	}
}
