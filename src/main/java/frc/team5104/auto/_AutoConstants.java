/*BreakerBots Robotics Team 2019*/
package frc.team5104.auto;

public class _AutoConstants {
	
	//Trajectory Generation
	public static final double _maxVelocity = 10;//16.455; 			  //(Feet) [Tune] in ft/s
	public static final double _maxAcceleration = 10;//21.442; 		  //(Feet) [Tune] in ft/s/s
	public static final double _maxJerk = 20;//0.297; 				  //(Feet) [Tune] in ft/s/s/s
	
	//Trajectory Folowing
	public static final double _tfCorrection = 0.2; //(None) [Tune/Choose] (Range: Great Than Zero) Increases/Decreases Correction
	public static final double _tfDampening  = 0.5; //(None) [Tune/Choose] (Range: Zero to One) Increases/Decreases Dampening
}
