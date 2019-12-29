/*BreakerBots Robotics Team 2019*/
package frc.team5104;

import frc.team5104.Superstructure.SystemState;
import frc.team5104.auto.AutoManager;
import frc.team5104.auto.paths.ExamplePath;
import frc.team5104.auto.util.Odometry;
import frc.team5104.subsystems.Drive;
import frc.team5104.teleop.CompressorController;
import frc.team5104.teleop.DriveController;
import frc.team5104.teleop.SuperstructureController;
import frc.team5104.util.XboxController;
import frc.team5104.util.console;
import frc.team5104.util.managers.SubsystemManager;
import frc.team5104.util.managers.TeleopControllerManager;
import frc.team5104.util.setup.RobotController;
import frc.team5104.util.setup.RobotState;
import frc.team5104.vision.Limelight;
import frc.team5104.util.Plotter;
import frc.team5104.util.Plotter.PlotterPoint.Color;
import frc.team5104.util.Webapp;

public class Robot extends RobotController.BreakerRobot {
	public Robot() {
		//Managers
		SubsystemManager.useSubsystems(
			new Drive()
		);
		TeleopControllerManager.useTeleopControllers(
			new DriveController(),
			new SuperstructureController(),
			new CompressorController()
		);
		
		//Other Initialization
		Webapp.run();
		Plotter.reset();
		Odometry.init();
		Limelight.init();
		CompressorController.stop();
		AutoManager.setTargetPath(new ExamplePath());
	}
	
	//Teleop (includes sandstorm)
	public void teleopStart() {
		Odometry.reset(); // delete me
		
		console.logFile.start();
		if (RobotState.isSandstorm()) { AutoManager.init(); }
		else { TeleopControllerManager.enabled(); }
		TeleopControllerManager.enabled();
		Superstructure.reset();
		SubsystemManager.enabled();
	}
	public void teleopStop() {
		if (!RobotState.isSandstorm()) { TeleopControllerManager.disabled(); }
		Superstructure.reset();
		SubsystemManager.disabled();
		console.logFile.end();
	}
	public void teleopLoop() {
		if (RobotState.isSandstorm()) { AutoManager.update(); }
		else { TeleopControllerManager.update(); }
		Superstructure.update();
		SubsystemManager.update();
		Odometry.update(); //delete me
		Plotter.plot( //delete me
				Odometry.getPose().getTranslation().getX(), 
				Odometry.getPose().getTranslation().getY(),
				Color.ORANGE
			);
	}
	
	//Test
	public void testLoop() {
		Superstructure.setSystemState(SystemState.DISABLED);
		Drive.stop();
		SubsystemManager.update();
		CompressorController.start(); 
	}
	
	//Main
	public void mainLoop() { XboxController.update(); }
}
