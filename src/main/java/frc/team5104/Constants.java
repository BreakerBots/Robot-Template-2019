/*BreakerBots Robotics Team 2019*/
package frc.team5104;

import com.ctre.phoenix.motorcontrol.NeutralMode;

public class Constants {
	//Main
	public static final boolean OVERWRITE_NON_MATCH_LOGS = true;
	public static final boolean OVERWRITE_MATCH_LOGS = false;
	public static final int MAIN_LOOP_SPEED = 50;
	public static final int AUTO_LOOP_SPEED = 100;
	public static final String ROBOT_NAME = "High-Tide";
	
	//Drive
	public static final double DRIVE_WHEEL_DIAMETER = 6.0/12.0; //ft
	public static final double DRIVE_TICKS_PER_REVOLUTION = 4096.0 * 3.0 * (54.0/30.0);
	public static final double DRIVE_WHEEL_BASE_WIDTH = 25.5 / 12.0; //ft
	public static final int DRIVE_CURRENT_LIMIT = 40; //amps
	public static final double DRIVE_KP = 0.5;
	public static final double DRIVE_KI = 0;
	public static final double DRIVE_KD = 0;
	public static final double DRIVE_KS = 1.48;
	public static final double DRIVE_KA = 0.773;
	public static final double DRIVE_KV = 0.431;
	public static final double AUTO_MAX_VELOCITY = 5; //ft/s
	public static final double AUTO_MAX_ACCEL = 5;
	public static final double AUTO_MAX_JERK = 20;
	public static final double AUTO_CORRECTION_FACTOR = 0.5; //>0
	public static final double AUTO_DAMPENING_FACTOR  = 0.5; //0-1
	
	//Elevator
	public static final double ELEVATOR_SPOOL_CIRC = 1.25 * Math.PI;
	public static final double ELEVATOR_CALIBRATE_SPEED = 0.25;
	public static final int ELEVATOR_CURRENT_LIMIT = 20;
	public static final NeutralMode ELEVATOR_NEUTRAL_MODE = NeutralMode.Brake;
	public static final double ELEVATOR_MOTION_KP = 0.5;
	public static final double ELEVATOR_MOTION_KI = 0;
	public static final double ELEVATOR_MOTION_KD = 6;
	public static final int ELEVATOR_MOTION_ACCEL = 20000;
	public static final int ELEVATOR_MOTION_CRUISE_VELOCITY = 20000;
	public static final double ELEVATOR_HEIGHT_TOL = 6;
	public static final double ELEVATOR_HEIGHT_TOL_ROUGH = 12;
	public static final double ELEVATOR_HEIGHT_CARGO_SHIP = 28;
	public static final double ELEVATOR_HEIGHT_CARGO_WALL = 20;
	public static final double ELEVATOR_HEIGHT_CARGO_L1 = 4;
	public static final double ELEVATOR_HEIGHT_CARGO_L2 = 28;
	public static final double ELEVATOR_HEIGHT_CARGO_L3 = 54;
	public static final double ELEVATOR_HEIGHT_HATCH_L2 = 24;
	public static final double ELEVATOR_HEIGHT_HATCH_L3 = 50;
	
	//Wrist
	public static final double WRIST_CALIBRATE_SPEED = 0.25;
	public static final int WRIST_CURRENT_LIMIT = 20;
	public static final NeutralMode WRIST_NEUTRAL_MODE = NeutralMode.Brake;
	public static final double WRIST_MOTION_KP = 20;
	public static final double WRIST_MOTION_KI = 0;
	public static final double WRIST_MOTION_KD = 200;
	public static final int WRIST_MOTION_ACCEL = 1000;
	public static final int WRIST_MOTION_CRUISE_VELOCITY = 2000;
	public static final double WRIST_LIMP_MODE_MAX_SPEED = 0.1;
	public static final int WRIST_LIMP_MODE_TIME_START = 1500;
	public static final double WRIST_ANGLE_TOL = 10;
	public static final double WRIST_ANGLE_HATCH_INTAKE = 72;
	public static final double WRIST_ANGLE_HATCH_EJECT = 75; 
	public static final double WRIST_ANGLE_CARGO_EJECT_ROCKET = 40; 
	public static final double WRIST_ANGLE_CARGO_EJECT_SHIP = 120;
	public static final double WRIST_ANGLE_CARGO_INTAKE_WALL = 50; 
	public static final double WRIST_ANGLE_CARGO_INTAKE_GROUND = 125;
	
	//Intake
	public static final double INTAKE_INTAKE_SPEED_HATCH = 1;
	public static final double INTAKE_EJECT_SPEED_HATCH = 1;
	public static final double INTAKE_HOLD_SPEED_HATCH = 0.05;
	public static final double INTAKE_INTAKE_SPEED_CARGO = 1;
	public static final double INTAKE_EJECT_SPEED_CARGO = 1;
	public static final double INTAKE_HOLD_SPEED_CARGO = 0.05;
	public static final int INTAKE_CURRENT_LIMIT = 10;
	public static final NeutralMode INTAKE_NEUTRAL_MODE = NeutralMode.Coast;
	public static final int INTAKE_EJECT_TIME = 250;
}