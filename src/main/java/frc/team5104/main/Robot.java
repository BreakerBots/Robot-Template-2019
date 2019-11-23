/* BreakerBots Robotics Team (FRC 5104) 2020 */
package frc.team5104.main;

import frc.team5104.auto.AutoManager;
import frc.team5104.auto.paths.ExamplePath;
import frc.team5104.auto.util.Odometry;
import frc.team5104.main.setup.RobotController;
import frc.team5104.main.setup.RobotState;
import frc.team5104.statemachines.ExampleStatemachine;
import frc.team5104.subsystems.drive.Drive;
import frc.team5104.teleop.CompressorController;
import frc.team5104.teleop.DriveController;
import frc.team5104.util.BreakerCompressor;
import frc.team5104.util.BreakerController;
import frc.team5104.util.WebappTuner;
import frc.team5104.util.managers.StateMachineManager;
import frc.team5104.util.managers.SubsystemManager;
import frc.team5104.util.managers.TeleopControllerManager;
import frc.team5104.util.Webapp;

public class Robot extends RobotController.BreakerRobot {
	public Robot() {
		//Managers
		SubsystemManager.useSubsystems(
			new Drive()
		);
		StateMachineManager.useStateMachines(
			new ExampleStatemachine()
		);
		TeleopControllerManager.useTeleopControllers(
			new DriveController(),
			new CompressorController()
		);
		
		//Other Initialization
		Webapp.run();
		Odometry.run();
		AutoManager.setTargetPath(new ExamplePath());
		BreakerCompressor.stop();
		//Limelight.init();
		//Cameras.init();
		
		//Debug Subsystems
		WebappTuner.init();
	}
	
	//Teleop (includes sandstorm)
	public void teleopStart() {
		if (RobotState.isSandstorm()) { Odometry.reset(); AutoManager.run(); }
		else { TeleopControllerManager.enabled(); }
		StateMachineManager.enabled();
		SubsystemManager.enabled();
		BreakerCompressor.stop();
	}
	public void teleopStop() {
		StateMachineManager.enabled();
		SubsystemManager.disabled();
	}
	public void teleopLoop() {
		if (RobotState.isSandstorm()) { BreakerCompressor.stop(); }
		else { TeleopControllerManager.update(); }
		StateMachineManager.update();
		SubsystemManager.update();
	}
	
	//Test
	public void testLoop() { BreakerCompressor.run(); }
	
	//Main
	public void mainLoop() { BreakerController.update(); }
}
