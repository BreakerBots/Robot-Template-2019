/*BreakerBots Robotics Team 2019*/
package frc.team5104.auto.util;

/**
 * A waypoint to generate a trajectory from.
 */
public class TrajectoryWaypoint {
	public double x, y, theta;
	
	//Constructors
	/**
	 * Creates a new waypoint from specific parameters.
	 * @param x The x (forward) position of the robot, relative to the start.
	 * @param y The y (sideways) position of the robot, relative to the start.
	 * @param heading The angle of the robot in degrees
	 */
	public TrajectoryWaypoint(double x, double y, double heading) {
		this.x = x;
		this.y = y;
		this.theta = Math.toRadians(heading);
	}
	
	public String toString() {
		return "x: " + x + ", " +
			   "y: " + y + ", " +
			   "heading: " + Math.toDegrees(theta);
	}
}