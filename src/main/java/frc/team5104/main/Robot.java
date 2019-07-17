/*BreakerBots Robotics Team 2019*/
package frc.team5104.main;

import edu.wpi.first.cameraserver.CameraServer;
import frc.team5104.control.BreakerMainController;
import frc.team5104.subsystem.BreakerSubsystemManager;
import frc.team5104.subsystem.drive.DriveManager;
import frc.team5104.subsystem.drive.Odometry;
import frc.team5104.util.console;
import frc.team5104.util.CSV;
import frc.team5104.util.Controller;
import frc.team5104.webapp.Tuner;
import frc.team5104.webapp.Webapp;

/**
 * Fallthrough from <strong>Breaker Robot Controller</strong>
 */
public class Robot extends RobotController.BreakerRobot {
	public Robot() {
		BreakerSubsystemManager.useSubsystems(
			 new DriveManager()
		);
		Webapp.init();
		Tuner.init();
		CameraServer.getInstance().startAutomaticCapture();
		Odometry.run();
	}
	
	//Main
	public void mainEnabled() {
		console.logFile.start();
		console.log("Robot Enabled");
		BreakerSubsystemManager.enabled();
		BreakerMainController.init();
		Odometry.reset();
		CSV.init(null);
	}
	public void mainDisabled() {
		console.log("Robot Disabled");
		BreakerSubsystemManager.disabled();
		console.logFile.end();
		CSV.writeFile("temp");
	}
	
	public void mainLoop() {
		if (RobotState.isEnabled()) {
			BreakerSubsystemManager.handle();
			Controller.handle();
		}
		BreakerMainController.handle();
		CSV.handle();
	}

	//Auto
	public void autoStart() { }
	public void autoLoop() { }
	public void autoStop() { }
	
	//Teleop
	public void teleopStart() { }
	public void teleopLoop() { }
	public void teleopStop() { }
}
