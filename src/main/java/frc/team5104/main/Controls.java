/* BreakerBots Robotics Team (FRC 5104) 2020 */
package frc.team5104.main;

import frc.team5104.util.BezierCurve;
import frc.team5104.util.XboxController;
import frc.team5104.util.XboxController.Axis;
import frc.team5104.util.XboxController.Button;
import frc.team5104.util.Deadband;

/** All the controls for the robot */
public class Controls {
	public static XboxController driver = XboxController.create(0);
	
	//Drive (used in DriveController)
	public static final Axis DRIVE_TURN = driver.getAxis(Axis.LEFT_JOYSTICK_X, new Deadband(0.08), new BezierCurve(0.15, 0.7, 0.8, 0.225));
	public static final Axis DRIVE_FORWARD = driver.getAxis(Axis.RIGHT_TRIGGER, new Deadband(0.01));
	public static final Axis DRIVE_REVERSE = driver.getAxis(Axis.LEFT_TRIGGER, new Deadband(0.01));
	
	//Other
	public static final Button COMPRESSOR_TOGGLE = driver.getButton(Button.MENU);
}
