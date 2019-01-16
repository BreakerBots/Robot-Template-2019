package frc.team5104.teleop;

import frc.team5104.subsystem.drive.DriveActions;
import frc.team5104.subsystem.drive.DriveSystems;
import frc.team5104.subsystem.drive.RobotDriveSignal;
import frc.team5104.subsystem.drive.RobotDriveSignal.DriveUnit;
import frc.team5104.util.Curve;
import frc.team5104.util.CurveInterpolator;
import frc.team5104.util.Deadband;
import frc.team5104.util.console;
import frc.team5104.util.controller;

public class BreakerTeleopController {
	public static void update() {
		//Drive
		drive();
			
		//Updates
		controller.update();
	}
	
	//Drive
	public static final CurveInterpolator vTeleopLeftSpeed  = new CurveInterpolator(HMI.Drive._driveCurveChange, HMI.Drive._driveCurve);
	public static final CurveInterpolator vTeleopRightSpeed = new CurveInterpolator(HMI.Drive._driveCurveChange, HMI.Drive._driveCurve);
	public static void drive() {
		//Get inputs
		double turn = HMI.Drive._turn.getAxis();
		double forward = HMI.Drive._forward.getAxis() - HMI.Drive._reverse.getAxis();
		
		//Apply controller deadbands
		turn = -Deadband.get(turn,  0.1);
		forward = Deadband.get(forward, 0.01);
		
		//Apply bezier curve
		double x1 = (1 - Math.abs(forward)) * (1 - 0.3) + 0.3;
		turn = Curve.getBezierCurve(turn, x1, 0.4, 1, 0.2);
		
		//Apply inertia affect
		vTeleopLeftSpeed.setSetpoint(forward - turn);
		vTeleopRightSpeed.setSetpoint(forward + turn);
		RobotDriveSignal signal = new RobotDriveSignal(
			//vTeleopLeftSpeed.update(), 
			//vTeleopRightSpeed.update(), 
				forward - turn,
				forward + turn,
			DriveUnit.percentOutput
		);
		
		//Apply motor affects
		signal = DriveActions.applyDriveStraight(signal);
		//signal = DriveActions.applyMotorMinSpeed(signal);
		
		//signal.leftSpeed = Deadband.getClipping(signal.leftSpeed, 0.1);
		//signal.rightSpeed = Deadband.getClipping(signal.rightSpeed, 0.1);
		
		console.log(signal);
		
		//Set talon speeds
		DriveActions.set(signal);
		
		//Shifting
		if (HMI.Drive._shift.getPressed())
			DriveSystems.shifters.toggle();
	}
}
