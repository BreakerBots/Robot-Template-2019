/* BreakerBots Robotics Team (FRC 5104) 2020 */
package frc.team5104.auto.paths;

import frc.team5104.auto.actions.DriveTrajectoryAction;
import frc.team5104.auto.util.AutoPath;
import frc.team5104.auto.util.TrajectoryWaypoint;

public class ExamplePath extends AutoPath {
	public ExamplePath() {
		add(new DriveTrajectoryAction(new TrajectoryWaypoint[] {
				new TrajectoryWaypoint(0, 0, 0),
				new TrajectoryWaypoint(5, 5, 90)
		}));
	}
}
