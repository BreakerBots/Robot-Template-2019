/* BreakerBots Robotics Team (FRC 5104) 2020 */
package frc.team5104.util.managers;

/** A twix rapper for all the requirements in a state machine */ 
public abstract class StateMachine {
	/** Return the name of this state machine (for prints) */
	protected abstract String getName();
	/** Called whenever the robot becomes enabled */
	protected abstract void enabled();
	/** Called periodically from the robot loop */
	protected abstract void update();
	/** Called whenever the robot becomes disabled */
	protected abstract void disabled();
}