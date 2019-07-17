/*BreakerBots Robotics Team 2019*/
package frc.team5104.auto;

/**
 * A Collection of BreakerCommands (The Entire Path)
 * Ran through the BreakerCommandScheduler
 */
public abstract class BreakerPath {
	
	/**
	 * The Actions for the path
	 */
	public BreakerPathAction[] pathActions = new BreakerPathAction[10];
	
	/**
	 * The number of Actions in the path
	 */
	public int pathActionsLength = 0;
	
	/**
	 * Add an action to the Path
	 */
	public void add(BreakerPathAction action) {
		pathActions[pathActionsLength] = action;
		pathActionsLength++;
	}
}
