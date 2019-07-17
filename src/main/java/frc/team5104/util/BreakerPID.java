/*BreakerBots Robotics Team 2019*/
package frc.team5104.util;

/**
 * A closed loop controller that controls the position of a motor. 
 * This controller uses PID (Proportional, Integral, Derivative) to process the input into the desired output.
 * The constant values of P, I, and D are tuning values.
 */
public class BreakerPID {
	public double _kP, _kI, _kD;
	public double err, deriv, integ;
	public double lastPoint, target, tolerance;

	public BreakerPID(double P, double I, double D) { this(P, I, D, 0, .01); }
	public BreakerPID(double P, double I, double D, double tolerance) { this(P, I, D, 0, tolerance); }
	public BreakerPID(double P, double I, double D, double tolerance, double target) {
		_kP = P;
		_kI = I;
		_kD = D;
		this.target = target;
		this.tolerance = tolerance;
		reset();
	}
	
	public void setTarget(double target) { this.target = target; }
	public void setTolerance(double tolerance) { this.tolerance = tolerance; }
	
	public double update(double current) {
		if (lastPoint != Double.NaN) {
			err = target - current;
			deriv = current - lastPoint;
			integ = integ + err;
		}
		
		lastPoint = current;
		return (err * _kP) + (integ * _kI) - (deriv * _kD);
	}
	
	public boolean onTarget() {
		return Math.abs(target - lastPoint) <= tolerance;
	}
	
	public void reset() {
		err = 0;
		deriv = 0;
		integ = 0;
		lastPoint = Double.NaN;
	}
}
