/*BreakerBots Robotics Team 2019*/
package frc.team5104.control;

import frc.team5104.util.Controller.Control;
import frc.team5104.util.Controller.ControlList;
import frc.team5104.util.Controller.Rumble;

/**
 * All the controls for the robot
 */
public class _Controls {
	//Main 
	static class Main {
		static final Control _toggleVision = new Control(ControlList.DirectionPadUp);
		static final Control _toggleAuto = new Control(ControlList.DirectionPadDown);
		static final Control _toggleCamera = new Control(ControlList.DirectionPadDown);
		
		static final Control _idle = new Control(ControlList.List);
		
		static boolean _manualCompressor = true;
		static final Control _toggleCompressor = new Control(ControlList.DirectionPadLeft);
	}
	
	//Drive
	public static class Drive {
		static final Control _turn = new Control(ControlList.LeftJoystickX);
		static final Control _forward = new Control(ControlList.RightTrigger);
		static final Control _reverse = new Control(ControlList.LeftTrigger);
		
		static final Control _shift = new Control(ControlList.LeftJoystickPress);
		public static final Rumble _shiftRumbleLow = new Rumble(0.75, true, false, 200);
		public static final Rumble _shiftRumbleHigh = new Rumble(0.75, false, false, 200);
	}
	
	//Hatch
	public static class Hatch {
		static final Control _intake = new Control(ControlList.LeftBumper);
		static final Control _eject = new Control(ControlList.RightBumper);
		
		public static final Rumble _holdRumble = new Rumble(1.0, false, false, 200);
		static final Rumble _ejectRumble = new Rumble(1.0, true, false, 150);
	}
	
	//Cargo
	public static class Cargo {
		static final Control _intake = new Control(ControlList.X);
		static final Control _eject = new Control(ControlList.B);
		static final Control _trapdoorUp = new Control(ControlList.Y);
		static final Control _trapdoorDown = new Control(ControlList.A);
		
		public static final Rumble _intakeRumble = new Rumble(1.0, false, true, 300);
		public static final Rumble _storedRumble = new Rumble(1.0, true, true, 300);
		static final Rumble _ejectRumble = new Rumble(0.25, true, false, 300);
		
		static final Rumble _trapdoorUpRumble = new Rumble(0.75, true, false, 200);
		static final Rumble _trapdoorDownRumble = new Rumble(0.75, false, false, 200);
		
		public static boolean _manualArm = false;
		static final Control _armManual = new Control(ControlList.RightJoystickY);
	}
	
	//Climb
	static class Climb {
		static final Control _climb = new Control(ControlList.DirectionPadRight);
	}
}
