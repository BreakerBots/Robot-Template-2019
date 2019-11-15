/* BreakerBots Robotics Team (FRC 5104) 2020 */
package frc.team5104.main.setup;

import edu.wpi.first.wpilibj.RobotBase;

public final class Main {
	private Main() { }
	public static void main(String... args) { RobotBase.startRobot(RobotController::new); }
}