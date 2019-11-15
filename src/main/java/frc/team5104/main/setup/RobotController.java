/* BreakerBots Robotics Team (FRC 5104) 2020 */
package frc.team5104.main.setup;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.hal.FRCNetComm.tInstances;
import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import frc.team5104.main.Robot;
import frc.team5104.main.Constants;
import frc.team5104.main.setup.RobotState.RobotMode;
import frc.team5104.util.CrashLogger;
import frc.team5104.util.CrashLogger.Crash;
import frc.team5104.util.console;
import frc.team5104.util.console.c;
import frc.team5104.util.console.t;

public class RobotController extends RobotBase {
	//Modes
	private RobotMode lastMode = RobotMode.Disabled;
	private BreakerRobot robot;
	private RobotState state = RobotState.getInstance();
	private final double loopPeriod = 20; //50hz
	
	//Init Robot
	public void startCompetition() {
		HAL.report(tResourceType.kResourceType_Framework, tInstances.kFramework_Iterative);
		console.logFile.start();
		console.sets.create("RobotInit");
		console.log(c.MAIN, t.INFO, "Initializing " + Constants.ROBOT_NAME + " Code...");
		
		robot = new Robot();
		
		HAL.observeUserProgramStarting();
		
		console.log(c.MAIN, "Devices Created and Seth Proofed");
		console.sets.log(c.MAIN, t.INFO, "RobotInit", "Initialization took ");
		
		//Main Loop
		while (true) {
			double st = Timer.getFPGATimestamp();
			
			//Call main loop function (and crash tracker)
			try {
				loop();
			} catch (Exception e) {
				CrashLogger.logCrash(new Crash("main", e));
			}
			
			//Wait to make loop correct time
			try { Thread.sleep(Math.round(loopPeriod - (Timer.getFPGATimestamp() - st))); } catch (Exception e) { console.error(e); }
			
			state.deltaTime = Timer.getFPGATimestamp() - st;
		}
	}

	//Main Loop
	private void loop() {
		//Default to Disabled
		if (isDisabled()) state.currentMode = RobotMode.Disabled;
		
		//Enabled - Teleop/Test
		else if (isEnabled()) {
			//Forced Through Driver Station
			if (isTest()) state.currentMode = RobotMode.Test;
			
			//Default to Teleop
			else state.currentMode = RobotMode.Teleop;
		}
		
		//Sandstorm
		state.isSandstorm = isAutonomous();
		
		//Handle Modes
		switch(state.currentMode) {
			case Teleop: {
				try {
					//Teleop
					if (lastMode != state.currentMode) {
						console.log(c.MAIN, t.INFO, "Teleop Enabled");
						robot.teleopStart();
					}
					
					robot.teleopLoop();
					HAL.observeUserProgramTeleop();
					
					state.gotDriverStationResponse = DriverStation.getInstance().waitForData(0.2);
				} catch (Exception e) {
					CrashLogger.logCrash(new Crash("main", e));
				}
				break;
			}
			case Test: {
				try {
					//Test
					if (lastMode != state.currentMode) {
						console.log(c.MAIN, t.INFO, "Test Enabled");
						robot.testStart();
					}
					
					robot.testLoop();
					HAL.observeUserProgramTest();
				} catch (Exception e) {
					CrashLogger.logCrash(new Crash("main", e));
				}
				break;
			}
			case Disabled: {
				try {
					//Disabled
					if (lastMode != state.currentMode)
						switch (lastMode) {
							case Teleop: { robot.teleopStop(); console.log(c.MAIN, t.INFO, "Teleop Disabled"); break; }
							case Test: 	 { robot.testStop(); console.log(c.MAIN, t.INFO, "Test Disabled"); break; }
							default: break;
						}
					
					HAL.observeUserProgramDisabled();
				} catch (Exception e) {
					CrashLogger.logCrash(new Crash("main", e));
				}
				break;
			}
			default: break;
		}
		
		//Handle Main Disabling
		try {
			if (lastMode != state.currentMode) {
				if (state.currentMode == RobotMode.Disabled) {
					console.logFile.end();
					robot.mainDisabled();
				}
				else if (lastMode == RobotMode.Disabled) {
					console.logFile.end();
					console.logFile.start();
					robot.mainEnabled();
				}
				LiveWindow.setEnabled(state.currentMode == RobotMode.Disabled);
				lastMode = state.currentMode;
			}
		} catch (Exception e) {
			CrashLogger.logCrash(new Crash("main", e));
		}
		
		//Update Main Robot Loop
		try {
			robot.mainLoop();
		} catch (Exception e) {
			CrashLogger.logCrash(new Crash("main", e));
		}
		
		//Update Live Window
		LiveWindow.updateValues();
	}
	
	//Child Class
	/**
	 * The Main Robot Interface. Called by this, Breaker Robot Controller
	 * <br>Override these methods to run code
	 * <br>Functions Call Order:
	 * <br> - All Enable/Disable Functions are called before the corresponding loop function
	 * <br> - Main Functions are called last (teleop, test, auto are before)
	 */
	public static abstract class BreakerRobot {
		public void mainLoop() { }
		public void mainEnabled() { }
		public void mainDisabled() { }
		public void teleopLoop() { }
		public void teleopStart() { }
		public void teleopStop() { }
		public void testLoop() { }
		public void testStart() { }
		public void testStop() { }
	}
}