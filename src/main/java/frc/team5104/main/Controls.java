/* BreakerBots Robotics Team (FRC 5104) 2020 */
package frc.team5104.main;

import frc.team5104.util.Controller.Control;
import frc.team5104.util.Controller.ControlList;

/**
 * All the controls for the robot
 */
public class Controls {
	//Drive (used in DriveController)
	public static final Control DRIVE_TURN = new Control(ControlList.LeftJoystickX);
	public static final Control DRIVE_FORWARD = new Control(ControlList.RightTrigger);
	public static final Control DRIVE_REVERSE = new Control(ControlList.LeftTrigger);
	
	//Other
	public static final Control COMPRESSOR_TOGGLE = new Control(ControlList.Menu);
}
