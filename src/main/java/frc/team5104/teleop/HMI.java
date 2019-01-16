/*BreakerBots Robotics Team 2019*/
package frc.team5104.teleop;

import frc.team5104.util.Curve;
import frc.team5104.util.controller.Control;

/**
 * The controls for the robot.
 */
public class HMI {
	//Drive
	public static class Drive {
		public static final Control _turn = Control.LX;
		public static final Control _forward = Control.RT;
		public static final Control _reverse = Control.LT;
		
		public static final Control _shift = Control.LJ;
		public static final Curve.BezierCurve _driveCurve = new Curve.BezierCurve(.2, 0, .2, 1);
		public static final double _driveCurveChange = 0.2;
	}
}