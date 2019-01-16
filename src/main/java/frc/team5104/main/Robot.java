/*BreakerBots Robotics Team 2019*/
package frc.team5104.main;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.CameraServer;
import frc.team5104.auto.AutoSelector;
import frc.team5104.auto.BreakerPathScheduler;
import frc.team5104.subsystem.BreakerSubsystemManager;
import frc.team5104.subsystem.drive.DriveManager;
import frc.team5104.subsystem.drive.DriveSystems;
import frc.team5104.subsystem.drive.Odometry;
import frc.team5104.teleop.BreakerTeleopController;
import frc.team5104.util.console;
import frc.team5104.util.controller.Control;

/**
 * Fallthrough from <strong>Breaker Robot Controller</strong>
 */
public class Robot extends BreakerRobotController.BreakerRobot {
	public Robot() {
		BreakerSubsystemManager.throwSubsystems(
			new DriveManager()
			// new VisionManager()
		);
		
//		CameraServer.getInstance().startAutomaticCapture();
	}
	
	//Main
	public void mainEnabled() {
		BreakerSubsystemManager.enabled(mode);
		console.logFile.start();
		Odometry.reset();
	}
	
	public void mainDisabled() {
		BreakerSubsystemManager.disabled();
		console.logFile.end();
	}
	
	public void mainLoop() {
		if (enabled) {
			BreakerSubsystemManager.update();
		}
	}

	//Auto
	public void autoEnabled() {
//		BreakerPathScheduler.set(
//			AutoSelector.getAuto()
// 			AutoSelector.Paths.Curve.getPath()
//		);
	}
	
	public void autoLoop() {
		BreakerPathScheduler.update();
	}
	
	//Teleop
	public void teleopLoop() {
		BreakerTeleopController.update();
	}
}
