package frc.team5104.teleop;

import frc.team5104.util.Curve;
import frc.team5104.util.controller.Control;

/*Breakerbots Robotics Team 2018*/
/**
 * All Controls used in Athena's Code
 */
public class HMI {
	//Drive
	public static class Drive {
		public static final Control _turn = Control.LX;
		public static final Control _forward = Control.LT;
		public static final Control _reverse = Control.RT;
		
		public static final Control _shift = Control.LJ;
		public static final Curve.BezierCurve _driveCurve = new Curve.BezierCurve(.2, 0, .2, 1);
		public static final double _driveCurveChange = 0.08;
	}
}