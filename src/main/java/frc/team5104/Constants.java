/*BreakerBots Robotics Team 2019*/
package frc.team5104;

public class Constants {
	//Main
	public static final boolean OVERWRITE_NON_MATCH_LOGS = true;
	public static final boolean OVERWRITE_MATCH_LOGS = false;
	public static final int MAIN_LOOP_SPEED = 50;
	public static final String ROBOT_NAME = "Example-Robot";
	
	//Drive
	public static final double DRIVE_WHEEL_DIAMETER = 6.0/12.0; //ft
	public static final double DRIVE_TICKS_PER_REVOLUTION = 4096.0 * 3.0 * (54.0/30.0);
	public static final double DRIVE_WHEEL_BASE_WIDTH = 25.5 / 12.0; //ft
	public static final double DRIVE_KP = 0;
	public static final double DRIVE_KD = 0;
	public static final double DRIVE_KS = 1.48;
	public static final double DRIVE_KA = 0.773;
	public static final double DRIVE_KV = 0.431;
	public static final double AUTO_MAX_VELOCITY = 3; //ft/s
	public static final double AUTO_MAX_ACCEL = 3;
	public static final double AUTO_CORRECTION_FACTOR = 2.0; //>0
	public static final double AUTO_DAMPENING_FACTOR  = 0.7; //0-1
}