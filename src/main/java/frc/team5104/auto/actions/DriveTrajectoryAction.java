/* BreakerBots Robotics Team (FRC 5104) 2020 */
package frc.team5104.auto.actions;

import java.util.Arrays;

import controller.PIDController;
import controller.RamseteController;
import controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.Timer;
import frc.team5104.Constants;
import frc.team5104.auto.util.AutoPathAction;
import frc.team5104.auto.util.Odometry;
import frc.team5104.subsystems.Drive;
import frc.team5104.util.Units;
import frc.team5104.util.console;
import frc.team5104.util.DriveSignal;
import frc.team5104.util.DriveSignal.DriveUnit;
import frc.team5104.util.console.c;
import geometry.Pose2d;
import geometry.Rotation2d;
import geometry.Translation2d;
import kinematics.ChassisSpeeds;
import kinematics.DifferentialDriveKinematics;
import kinematics.DifferentialDriveWheelSpeeds;
import trajectory.Trajectory;
import trajectory.TrajectoryConfig;
import trajectory.TrajectoryGenerator;
import trajectory.constraint.DifferentialDriveVoltageConstraint;

/**
 * Follow a trajectory using the Breaker Trajectory Follower (Ramses Follower)
 */
public class DriveTrajectoryAction extends AutoPathAction {

	private final Timer m_timer = new Timer();
	private final Trajectory m_trajectory;
	private final RamseteController m_follower;
	private final SimpleMotorFeedforward m_feedforward;
	private final DifferentialDriveKinematics m_kinematics;
	private final PIDController m_leftController;
	private final PIDController m_rightController;
	private DifferentialDriveWheelSpeeds m_prevSpeeds;
	private double m_prevTime;

	public DriveTrajectoryAction() {
		m_feedforward = new SimpleMotorFeedforward(Constants.DRIVE_KS, Constants.DRIVE_KV, Constants.DRIVE_KA);

		m_kinematics = new DifferentialDriveKinematics(Units.feetToMeters(Constants.DRIVE_WHEEL_BASE_WIDTH));

		// Create a voltage constraint to ensure we don't accelerate too fast
		DifferentialDriveVoltageConstraint autoVoltageConstraint = new DifferentialDriveVoltageConstraint(m_feedforward,
				m_kinematics, 10);

		// Create config for trajectory
		TrajectoryConfig config = new TrajectoryConfig(Constants.AUTO_MAX_VELOCITY, Constants.AUTO_MAX_ACCEL)
				// Add kinematics to ensure max speed is actually obeyed
				.setKinematics(m_kinematics)
				// Apply the voltage constraint
				.addConstraint(autoVoltageConstraint);

		// An example trajectory to follow. All units in meters.
		m_trajectory = TrajectoryGenerator.generateTrajectory(
				// Start at the origin facing the +X direction
				new Pose2d(0, 0, new Rotation2d(0)),
				// Pass through these two interior waypoints, making an 's' curve path
				Arrays.asList(new Translation2d(1, 1), new Translation2d(2, -1)),
				// End 3 meters straight ahead of where we started, facing forward
				new Pose2d(3, 0, new Rotation2d(0)),
				// Pass config
				config);

		m_follower = new RamseteController(Constants.AUTO_CORRECTION_FACTOR, Constants.AUTO_DAMPENING_FACTOR);
		m_leftController = new PIDController(Constants.DRIVE_KP, 0, Constants.DRIVE_KD);
		m_rightController = new PIDController(Constants.DRIVE_KP, 0, Constants.DRIVE_KD);
	}

	public void init() {
		console.sets.create("RunTrajectoryTime");
		console.log(c.AUTO, "Running Trajectory");
		m_prevTime = 0;
		Trajectory.State initialState = m_trajectory.sample(0);
		m_prevSpeeds = m_kinematics.toWheelSpeeds(new ChassisSpeeds(initialState.velocityMetersPerSecond, 0,
				initialState.curvatureRadPerMeter * initialState.velocityMetersPerSecond));
		m_timer.reset();
		m_timer.start();
		m_leftController.reset();
		m_rightController.reset();
	}

	public boolean update() {
		double curTime = m_timer.get();
		double dt = curTime - m_prevTime;

		DifferentialDriveWheelSpeeds targetWheelSpeeds = m_kinematics
				.toWheelSpeeds(m_follower.calculate(Odometry.getPose(), m_trajectory.sample(curTime)));

		double leftSpeedSetpoint = targetWheelSpeeds.leftMetersPerSecond;
		double rightSpeedSetpoint = targetWheelSpeeds.rightMetersPerSecond;

		double leftOutput;
		double rightOutput;

		double leftFeedforward = m_feedforward.calculate(leftSpeedSetpoint,
				(leftSpeedSetpoint - m_prevSpeeds.leftMetersPerSecond) / dt);

		double rightFeedforward = m_feedforward.calculate(rightSpeedSetpoint,
				(rightSpeedSetpoint - m_prevSpeeds.rightMetersPerSecond) / dt);

		leftOutput = leftFeedforward
				+ m_leftController.calculate(Odometry.getWheelSpeeds().leftMetersPerSecond, leftSpeedSetpoint);

		rightOutput = rightFeedforward
				+ m_rightController.calculate(Odometry.getWheelSpeeds().rightMetersPerSecond, rightSpeedSetpoint);

		m_prevTime = curTime;
		m_prevSpeeds = targetWheelSpeeds;

		Drive.set(new DriveSignal(leftOutput, rightOutput, DriveUnit.VOLTAGE));

		return m_timer.hasPeriodPassed(m_trajectory.getTotalTimeSeconds());
	}

	public void end() {
		m_timer.stop();
		Drive.stop();
		console.log(c.AUTO, "Trajectory Finished in " + console.sets.getTime("RunTrajectoryTime") + "s");
	}
}
