package frc.team5104.subsystem.drive;

public class DriveUnits {
	// Ticks and Wheel Revs
	public static double ticksToWheelRevolutions(double ticks) {
		return ticks / _DriveConstants._ticksPerRevolution;
	}
	public static double wheelRevolutionsToTicks(double revs) {
		return revs * _DriveConstants._ticksPerRevolution;
	}
	
	// Feet and Wheel Revs
	public static double feetToWheelRevolutions(double feet) {
		return feet / (_DriveConstants._wheelDiameter * Math.PI);
	}
	public static double wheelRevolutionsToFeet(double revs) {
		return revs * (_DriveConstants._wheelDiameter * Math.PI);
	}
	
	// Feet and Ticks
	public static double ticksToFeet(double ticks) {
		double r = ticksToWheelRevolutions(ticks);
			   r = wheelRevolutionsToFeet(r);
		return r;
	}
	public static double feetToTicks(double feet) {
		double r = feetToWheelRevolutions(feet);
			   r = wheelRevolutionsToTicks(r);
		return r;
	}
	
	// feet/second and talon velocity (ticks/100ms)
	public static double talonVelToFeetPerSecond(double talonVel) {
		return ticksToFeet(talonVel) * 10.0;
	}
	public static double feetPerSecondToTalonVel(double feetPerSecond) {
		return feetToTicks(feetPerSecond) / 10.0;
	}
}
