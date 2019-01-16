/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.vision;

import frc.team5104.subsystem.BreakerSubsystem;
import frc.team5104.util.console;

public class VisionActions extends BreakerSubsystem.Actions {
	// Return what to add to the right wheels based on offset
	public static double getRightTurn() {
		double offSet = VisionSystems.limelight.getX() < -_VisionConstants._minXOffset ? VisionSystems.limelight.getX() : 0;
		double p = _VisionConstants._p * _VisionConstants._maxXOffset;
		return -offSet/p;
	}
	
	// Return what to add to the left wheels based on offset
	public static double getLeftTurn() {
		double offSet = VisionSystems.limelight.getX() > _VisionConstants._minXOffset ? VisionSystems.limelight.getX() : 0;
		double p = _VisionConstants._p * _VisionConstants._maxXOffset;
		return offSet/p;
	}
	
	// Change the pipeline you are using
	public static void changePipeline(VisionManager.VisionPipeline p) {
		console.log(VisionSystems.pipeline.change());
	}
}
