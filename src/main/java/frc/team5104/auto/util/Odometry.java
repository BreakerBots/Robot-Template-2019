package frc.team5104.auto.util;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import frc.team5104.subsystems.Drive;

/**
 * A wrapper class for keeping track of the the robots position.
 * Gets called from AutoManager.java for updating
 * and initializing/resetings from Robot.java
 */
public class Odometry {
	private static DifferentialDriveOdometry m_odometry;
	
	public static void init() {
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
	    m_odometry.resetPosition(new Pose2d(), Rotation2d.fromDegrees(Drive.getGyro()));
	}
}
