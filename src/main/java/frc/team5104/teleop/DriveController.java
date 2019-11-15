/* BreakerBots Robotics Team (FRC 5104) 2020 */
package frc.team5104.teleop;

import frc.team5104.main.Controls;
import frc.team5104.subsystems.drive.Drive;
import frc.team5104.subsystems.drive.DriveHelper;
import frc.team5104.util.managers.TeleopController;

public class DriveController extends TeleopController {
	protected String getName() { return "Drive Controller"; }

	protected void update() {
		double turn = Controls.DRIVE_TURN.getAxis();
		double forward = Controls.DRIVE_FORWARD.getAxis() - Controls.DRIVE_REVERSE.getAxis();
		Drive.set(DriveHelper.get(turn, forward, true));
	}
}
