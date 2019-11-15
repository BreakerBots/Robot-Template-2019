/* BreakerBots Robotics Team (FRC 5104) 2020 */
package frc.team5104.util;

import edu.wpi.first.wpilibj.Timer;

/**
 * !!!UNTESTED VERSION!!! (not actually depricated its just prob not working lul)
 * @deprecated
 * A closed loop controller that controls the position of a motor. 
 * This controller uses PIDF (Proportional, Integral, Derivative, Feed-Forward) to process the input into the desired output.
 * The constant values of kP, kI, kD, and kF are tuning values.
 */
public class BreakerPIDF {
	private double 
	kP,
	kI,
	kD,
	kF,
	kMaxOutput = 1.0,
	kMinOutput = -1.0,
	prevError = 0.0,
	totalError = 0.0,
	setpoint = 0.0,
	error = 0.0,
	result = 0.0,
	last_input = Double.NaN,
	last_timestamp = Timer.getFPGATimestamp();

	//Constructors
	public BreakerPIDF(double kP, double kI, double kD, double kF, double setpoint, double minOutput, double maxOutput) {
		setPIDF(kP, kI, kD, kF);
		setSetpoint(setpoint);
		setOutputRange(minOutput, maxOutput);
	}

	//Calculate
	public double calculate(double input) {
		double timestamp = Timer.getFPGATimestamp();
		double dt = timestamp - last_timestamp;
		last_timestamp = timestamp;

		return calculate(input, dt);
	}
	public double calculate(double input, double dt) {
		if (dt < 1E-6) dt = 1E-6;
		last_input = input;
		error = setpoint - input;
		if (BreakerMath.inRange(error * kP, kMinOutput, kMaxOutput))
			totalError += error * dt;
		else
			totalError = 0;
		result = (kP * error + kI * totalError + kD * (error - prevError) / dt + kF * setpoint);
		prevError = error;
		return BreakerMath.clamp(result, kMinOutput, kMaxOutput);
	}

	//Getters/Setters
	public void setPIDF(double kP, double kI, double kD, double kF) {
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
		this.kF = kF;
	}
	public double getP() { return kP; }
	public double getI() { return kI; }
	public double getD() { return kD; }
	public double getF() { return kF; }
	/** @return the latest calculated output */
	public double get() { return result; }
	public void setOutputRange(double minimumOutput, double maximumOutput) {
		if (minimumOutput > maximumOutput) {
			console.error("PIDF Error - Lower bound is greater than upper bound");
		}
		kMinOutput = minimumOutput;
		kMaxOutput = maximumOutput;
	}
	public void setMaxAbsoluteOutput(double maxAbsoluteOutput) { setOutputRange(-maxAbsoluteOutput, maxAbsoluteOutput); }
	public void setSetpoint(double setpoint) { this.setpoint = setpoint; }
	public double getSetpoint() { return setpoint; }
	public double getError() { return error; }
	public boolean onTarget(double tolerance) { return !Double.isNaN(last_input) && Math.abs(last_input - setpoint) < tolerance; }

	//Reset
	public void reset() {
		last_input = Double.NaN;
		prevError = 0;
		totalError = 0;
		result = 0;
		setpoint = 0;
	}
	public void resetIntegrator() {
		totalError = 0;
	}
}