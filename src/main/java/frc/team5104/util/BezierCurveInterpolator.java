/*BreakerBots Robotics Team 2019*/
package frc.team5104.util;

/**
 * <h1>Interpolating Curve</h1>
 * Iterates to a changing setpoint in a certain amount of time.
 */
public class BezierCurveInterpolator {
	private double setPoint;  //Setpoint
	private double currentPoint;  //Current Point
	
	private double startPoint; //The point started from when trying to reach setpoint
	
	public double deltaTime; //The time in terms of how many ticks to get from 0-1 to change the current point by
	
	BezierCurve curve;
	
	/**
	 * <h1>Interpolating Curve</h1>
	 * Iterates to a changing setpoint in a certain amount of time.
	 * @param deltaTime The time to reach each setpoint (time to go from min to max in loops)
	 * @param min The min value being input for the Setpoint
	 * @param max The max value being input for the Setpoint
	 */
	public BezierCurveInterpolator(double deltaTime, BezierCurve curve) {
		this.deltaTime = deltaTime;
		this.curve = curve;
		setSetpoint(0, true);
	}
	
	/**
	 * Call this function periodically
	 * @return the current value
	 */
	public double update() {
		//Get Progress %
		double p = (currentPoint - startPoint) / (setPoint - startPoint);
		
		//Add Step
		if ((Math.abs(startPoint - currentPoint) + deltaTime) < Math.abs(startPoint - setPoint))
			currentPoint += (deltaTime * (startPoint < setPoint ? 1 : -1));
		else
			currentPoint = setPoint;
		
		//Return Value at Progress
		return curve.getPoint(p) * (setPoint - startPoint) + (startPoint);
	}
	
	/**
	 * Sets the target/setpoint
	 * @param setPoint The setpoint to set to
	 */
	public void setSetpoint(double setPoint) {
		setSetpoint(setPoint, false);
	}
	
	/**
	 * Sets the target/setpoint
	 * @param setPoint The setpoint to set to
	 * @param instant If the current position should instantly move to this point [def false]
	 */
	public void setSetpoint(double setPoint, boolean instant) {
		if (currentPoint != setPoint) {
			//Setpoints
			this.setPoint = setPoint;
			if (instant)
				currentPoint = setPoint;
			
			//Get init to calculate progress
			startPoint = currentPoint;
		}
	}
}





