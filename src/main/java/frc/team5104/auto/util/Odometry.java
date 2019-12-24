package frc.team5104.auto.util;

import frc.team5104.subsystems.Drive;
import wpi.geometry.Pose2d;
import wpi.geometry.Rotation2d;
import wpi.kinematics.DifferentialDriveOdometry;
import wpi.kinematics.DifferentialDriveWheelSpeeds;

/**
 * A wrapper class for keeping track of the the robots position.
 * Gets called from AutoManager.java for updating
 * and initializing/resetings from Robot.java
 */
public class Odometry {
	private static DifferentialDriveOdometry m_odometry;
	
	public static void init() {
		Drive.resetEncoders();
		Drive.resetGyro();
		m_odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(Drive.getGyro()));
	}
	
	public static void update() {
		m_odometry.update(Rotation2d.fromDegrees(Drive.getGyro()), Drive.getLeftEncoderPositionMeters(),
		        Drive.getRightEncoderPositionMeters());
	}
	
	public static Pose2d getPose() {
	    return m_odometry.getPoseMeters();
	}
	
	public static DifferentialDriveWheelSpeeds getWheelSpeeds() {
	    return new DifferentialDriveWheelSpeeds(Drive.getLeftEncoderPositionMeters(),
		        Drive.getRightEncoderPositionMeters());
	}
	
	public static void resetOdometry() {
	    Drive.resetEncoders();
	    Drive.resetGyro();
	    m_odometry.resetPosition(new Pose2d(), Rotation2d.fromDegrees(Drive.getGyro()));
	}
}
