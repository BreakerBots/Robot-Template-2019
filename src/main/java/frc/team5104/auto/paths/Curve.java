/*BreakerBots Robotics Team 2019*/
package frc.team5104.auto.paths;

import frc.team5104.auto.BreakerPath;
import frc.team5104.auto.actions.DriveStopAction;
import frc.team5104.auto.actions.DriveTrajectoryAction;
import frc.team5104.auto.util.TrajectoryWaypoint;

public class Curve extends BreakerPath {
	public Curve() {
		add(new DriveTrajectoryAction(new TrajectoryWaypoint[] {
				new TrajectoryWaypoint(0, 0, 0),
				new TrajectoryWaypoint(5, 0, 0)
		}));
		add(new DriveStopAction());
	}
}
