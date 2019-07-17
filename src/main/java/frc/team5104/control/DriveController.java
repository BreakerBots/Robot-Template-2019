/*BreakerBots Robotics Team 2019*/
package frc.team5104.control;

import frc.team5104.control.BreakerMainController.BreakerController;
import frc.team5104.subsystem.drive.Drive;
import frc.team5104.subsystem.drive.DriveSystems;
import frc.team5104.subsystem.drive.DriveSignal;
import frc.team5104.subsystem.drive.DriveSignal.DriveUnit;
import frc.team5104.util.BezierCurve;
import frc.team5104.util.BezierCurveInterpolator;
import frc.team5104.util.Deadband;
import frc.team5104.util.Deadband.deadbandType;

/**
 * Handles drive control (included all augmentation from the driver to the robot)
 */
public class DriveController extends BreakerController {
	//Variables
	private static final BezierCurve _driveCurve = new BezierCurve(.2, 0, .2, 1);
	private static final double _driveCurveChange = 1.0;
	private static final BezierCurveInterpolator vTeleopLeftSpeed  = new BezierCurveInterpolator(_driveCurveChange, _driveCurve);
	private static final BezierCurveInterpolator vTeleopRightSpeed = new BezierCurveInterpolator(_driveCurveChange, _driveCurve);
	
	private static BezierCurve turnCurve = new BezierCurve(0.15, 0.7, 0.8, 0.225);//0, 0.4, 0.8, 0.4//0.15, 0.7, 0.8, 0.225
	private static double _turnCurveSpeedAdjust = 0.2;
	
	//Main Handle Function
	void update() {
		//Get inputs
		double turn = _Controls.Drive._turn.getAxis();
		double forward = _Controls.Drive._forward.getAxis() - _Controls.Drive._reverse.getAxis();

		//Apply controller deadbands
		turn = -Deadband.get(turn,  0.08, deadbandType.slopeAdjustment);
		forward = Deadband.get(forward, 0.01, deadbandType.slopeAdjustment);
		
		//Apply bezier curve
		turnCurve.x1 = (1 - Math.abs(forward)) * (1 - _turnCurveSpeedAdjust) + _turnCurveSpeedAdjust;
		turn = turnCurve.getPoint(turn);
		
		//Apply inertia affect
		vTeleopLeftSpeed.setSetpoint(forward - turn);
		vTeleopRightSpeed.setSetpoint(forward + turn);
		DriveSignal signal = new DriveSignal(
			vTeleopLeftSpeed.update() * 12, 
			vTeleopRightSpeed.update() * 12, 
			DriveUnit.voltage
		);
		
		//Apply drive straight effects
		signal = Drive.applyDriveStraight(signal);
		
		//Apply min speed
		signal = Drive.applyMotorMinSpeed(signal);
		
		//Set talon speeds
		Drive.set(signal);
		
		//Shifting
		if (_Controls.Drive._shift.getPressed())
			DriveSystems.shifters.toggle();
	}

	//Stop The Subsystem
	void idle() {
		Drive.stop();
		DriveSystems.shifters.set(true);
	}
}
