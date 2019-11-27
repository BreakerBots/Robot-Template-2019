/* BreakerBots Robotics Team (FRC 5104) 2020 */
package frc.team5104.auto.actions;

import frc.team5104.auto.BreakerTrajectoryFollower;
import frc.team5104.auto.BreakerTrajectoryGenerator;
import frc.team5104.auto.util.AutoPathAction;
import frc.team5104.auto.util.Odometry;
import frc.team5104.auto.util.TrajectoryWaypoint;
import frc.team5104.subsystems.drive.Drive;
import frc.team5104.subsystems.drive.DriveObjects.DriveSignal;
import frc.team5104.util.console;
import frc.team5104.util.console.c;

/**
 * Follow a trajectory using the Breaker Trajectory Follower (Ramses Follower)
 */
public class DriveTrajectoryAction extends AutoPathAction {
	private BreakerTrajectoryFollower follower;
	private TrajectoryWaypoint[] waypoints;
		
    public DriveTrajectoryAction(TrajectoryWaypoint[] points) {
    	this.waypoints = points;
    }

    public void init() {
    	console.sets.create("RunTrajectoryTime");
    	console.log(c.AUTO, "Running Trajectory");
    	
    	//Reset Odometry and Get Path (Reset it twice to make sure it all good)
    	follower = new BreakerTrajectoryFollower(BreakerTrajectoryGenerator.getTrajectory(waypoints));
		Odometry.reset();
		
		//Wait 100ms for Device Catchup
		try { Thread.sleep(100); }  catch (Exception e) { console.error(e); e.printStackTrace(); }
    }

    public boolean update() {
    	DriveSignal nextSignal = follower.getNextDriveSignal(Odometry.getPosition());
		//nextSignal = DriveHelper.applyDriveStraight(nextSignal);
    	Drive.set(nextSignal);
    	
		return follower.isFinished();
    }

    public void end() {
    	Drive.stop();
    	console.log(c.AUTO, "Trajectory Finished in " + console.sets.getTime("RunTrajectoryTime") + "s at position " + Odometry.getPosition().toString());
    }
}
