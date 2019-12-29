/* BreakerBots Robotics Team (FRC 5104) 2020 */
package frc.team5104.auto.paths;

import frc.team5104.auto.AutoPath;
import frc.team5104.auto.actions.DriveStopAction;
import frc.team5104.auto.actions.DriveTrajectoryAction;

public class ExamplePath extends AutoPath {
	public ExamplePath() {
		add(new DriveTrajectoryAction());
		add(new DriveStopAction());
	}
}
