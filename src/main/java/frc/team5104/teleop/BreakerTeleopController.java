package frc.team5104.teleop;

import frc.team5104.subsystem.drive.DriveActions;
import frc.team5104.subsystem.drive.DriveSystems;
import frc.team5104.subsystem.drive.RobotDriveSignal;
import frc.team5104.subsystem.drive.RobotDriveSignal.DriveUnit;
import frc.team5104.util.Curve;
import frc.team5104.util.CurveInterpolator;
import frc.team5104.util.Deadband;
import frc.team5104.util.controller;

public class BreakerTeleopController {
	public static final CurveInterpolator vTeleopLeftSpeed  = new CurveInterpolator(HMI.Drive._driveCurveChange, HMI.Drive._driveCurve);
	public static final CurveInterpolator vTeleopRightSpeed = new CurveInterpolator(HMI.Drive._driveCurveChange, HMI.Drive._driveCurve);
	
	public static void update() {
		//Driving
		double turn = Deadband.get(HMI.Drive._turn.getAxis(), -0.2);
		double forward = Deadband.get(HMI.Drive._forward.getAxis() - HMI.Drive._reverse.getAxis(), 0.1);
		double x1 = (1 - Math.abs(forward)) * (1 - 0.3) + 0.3;
		turn = Curve.getBezierCurve(turn, x1, 0.4, 1, 0.2);
		vTeleopLeftSpeed.setSetpoint(forward - turn);
		vTeleopRightSpeed.setSetpoint(forward + turn);
		DriveActions.set(
				new RobotDriveSignal(vTeleopLeftSpeed.update(), vTeleopRightSpeed.update(), 
						DriveUnit.percentOutput), true
				);
		if (HMI.Drive._shift.getPressed())
			DriveSystems.shifters.toggle();
			
		//Updates
		controller.update();
	}
}
