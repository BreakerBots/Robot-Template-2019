/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.vision;

import frc.team5104.main.BreakerRobotController.RobotMode;
import frc.team5104.subsystem.BreakerSubsystem;

public class VisionManager extends BreakerSubsystem.Manager {
	
	public static enum VisionPipeline {
		line,
		target;
	}
	
	public void enabled(RobotMode mode) {
		VisionActions.changePipeline(VisionPipeline.target);
	}
	
	public void update() {
	}

	public void disabled() {
	}
	
	public VisionManager() {
	}
}
