package frc.team5104.main;

import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.FitMethod;

/*Breakerbots Robotics Team 2018*/
/**
 * Constants used in Robot Code
 * Update as needed
 * Units: ()
 *    - Feet
 *    - Degrees
 *    - Encoder Tick
 *    - Talon Current (TalCurrent)
 *    - Talon Percent Speed (TalSpeed)
 *    - Seconds
 *    - Milliseconds
 *    - Hertz (hz)
 *    - Other/None
 *    
 * Marks: []
 *    - Tune
 *    - Choose
 *    - Measure
 *    - Other/None
 */
public class Constants {
	
	//Robot Varibles (In Feet) - Update as needed!
	public static final double _robotLength = Units.inchesToFeet(32.0 + 4.0);	//(Input - Inches; Output - Feet) [Measure] Length of Robot
	public static final double _robotWidth = Units.inchesToFeet(28.0 + 4.0);	//(Input - Inches; Output - Feet) [Measure] Width of Robot
	public static final double _wheelDiameter = 0.5; 				  			//(Feet) [Measure] The diameter of the wheels
	public static final double _ticksPerRevolution = 4600; 						//(Encoder Tick) [Measure] Encoder Ticks Per Wheel Revolution
	public static final double _wheelBaseWidth = 2.179;			  				//(Feet) [Measure] The Distance from the Left and Right Wheels
	
	//Drive
	public static final class Drive {
		public static final double _rightAccount = 1; 		//(TalSpeed) [Measure] Multiply the right motor by (For Driving Straight)
		public static final double _leftAccount  = 0.94; 	//(TalSpeed) [Measure] Multiply the left  motor by (For Driving Straight)
		
		public static final double _gyroAngle = 65;   		//(Degrees) [Measure] Yaw Angle of Gyro
		
		public static final double _rampSeconds			= 0.0; //(Seconds) [Tune/Choose]
		public static final int _currentLimitPeak		= 80;  //(Current) [Tune/Choose]
		public static final int _currentLimitPeakTime	= 10;  //(Milliseconds) [Tune/Choose]
		public static final int _currentLimitSustained	= 36;  //(Current) [Tune/Choose]
		
		public static final int _highPidId = 0;
		public static final double highDrivePidF = 1.00;
		public static final double highDrivePidP = 0.00;
		public static final double highDrivePidI = 0.00;
		public static final double highDrivePidD = 0.00;
		
		public static final int _lowPidId = 1;
		public static final double lowDrivePidF = 1.00;
		public static final double lowDrivePidP = 0.00;
		public static final double lowDrivePidI = 0.00;
		public static final double lowDrivePidD = 0.00;
	}
	
	// -- AutonomousWP (! Deprication Notice)
	public static final class AutonomousWP {
		public static final double _PIDA[] = { 1.0, 0.0, 0.0, 0 };//(None) [Tune] Speed
		public static final double _maxVelocity = 8.0; 			  //(Feet) [Tune] in ft/s
		public static final double _maxAcceleration = 4.0; 		  //(Feet) [Tune] in ft/s/s
		public static final double _maxJerk = 50; 				  //(Feet) [Tune] in ft/s/s/s
		public static final double _angleMult = 0.8;			  //(None) [None] Multiply the gyro angle value by this, keep from 0.6 - 0.8
		public static final double _xAngleMult = 4.0;			  //(None) [Tune] Multiple the x cordinate of this in each trajectory
	}
	
	// -- Autonomous
	public static final class Autonomous {
		//Trajectory Generation
		public static final double _maxVelocity = 5.0; 			  //(Feet) [Tune] in ft/s
		public static final double _maxAcceleration = 4.0; 		  //(Feet) [Tune] in ft/s/s
		public static final double _maxJerk = 100; 				  //(Feet) [Tune] in ft/s/s/s
		public static final FitMethod _fitMethod = Trajectory.FitMethod.HERMITE_CUBIC;
													//(Other) [Choose] What curve to Gen trajectory in (use Hermite Cubic)
		public static final int 	  _samples	 = Trajectory.Config.SAMPLES_HIGH; 
													//(Other) [Choose] Affects generation speed and quality of Trajectory Generation
		public static final double 	  _dt 		 = Constants.Loops._robotHz; 
													//(hz) delta time of Trajectory (time between each point)
		
		//Trajectory Folowing
		public static final double _tfB    = 0.5; //(None) [Tune/Choose] (Range: Great Than Zero) Increases/Decreases Correction
		public static final double _tfZeta = 0.4; //(None) [Tune/Choose] (Range: Zero to One) Increases/Decreases Dampening
	}
	
	// -- Logging
	public static final class Logging {
		public static final boolean _SaveNonMatchLogs = false;
		public static final boolean _SaveMatchLogs = true;
	}
	
	// -- Looper
	public static final class Loops {
		public static final double _odometryHz = 100; //(hz) [Choose]
		public static final double _robotHz    = 50;  //(hz) [Choose]
	}
	
	//Follow the above format for all other subsystems
}
