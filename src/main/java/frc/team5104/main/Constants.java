/* BreakerBots Robotics Team (FRC 5104) 2020 */
package frc.team5104.main;

public class Constants {
	//Other
	public static final String ROBOT_NAME = "Example-Robot";
	public static final boolean OVERWRITE_NON_MATCH_LOGS = true;
	public static final boolean OVERWRITE_MATCH_LOGS = false;
	
	//Drive (teleop tuning variables in DriveHelper)
	public static final double DRIVE_WHEEL_DIAMETER = 6.0/12.0; //ft
	public static final double DRIVE_TICKS_PER_REVOLUTION = 4096.0 * 3.0 * (54.0/30.0);
	public static final double DRIVE_WHEEL_BASE_WIDTH = 24.25 / 12.0; //ft
	public static final int DRIVE_CURRENT_LIMIT = 40; //amps
	public static final double DRIVE_KP = 0.5;
	public static final double DRIVE_KI = 0;
	public static final double DRIVE_KD = 0;
	public static final double DRIVE_KS = 1.48;
	public static final double DRIVE_KA = 0.773;
	public static final double DRIVE_KV = 0.431;
	
	//Autonomous
	public static final double AUTO_MAX_VELOCITY = 8; //ft/s
	public static final double AUTO_MAX_ACCEL = 4;
	public static final double AUTO_MAX_JERK = 20;
	public static final double AUTO_CORRECTION_FACTOR = 1; //>0
	public static final double AUTO_DAMPENING_FACTOR  = 0.8; //0-1
	public static final int AUTO_LOOP_SPEED = 100; //100hz, 10ms dt
}