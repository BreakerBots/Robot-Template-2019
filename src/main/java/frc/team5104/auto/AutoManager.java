/* BreakerBots Robotics Team (FRC 5104) 2020 */
package frc.team5104.auto;

import frc.team5104.auto.util.AutoPath;
import frc.team5104.auto.util.Odometry;
import frc.team5104.teleop.CompressorController;
import frc.team5104.util.setup.RobotState;

/** Basically just handles a separate thread for autonomous. The path is updated in "AutoPathScheduler" */
public class AutoManager {
	private static AutoPath targetPath = null;
	private static AutoPathScheduler pathScheduler;
	
	public static void init() {
		//reset odometry
		Odometry.reset();
		
		//choose path
		pathScheduler = new AutoPathScheduler(targetPath);
	}
	
	public static void setTargetPath(AutoPath path) { targetPath = path; }
	
	public static void update() {
		if (RobotState.isSandstorm()) {
			//stop compressor
			CompressorController.stop();
			
			//update odometry
			Odometry.update();
			
			//update path
			pathScheduler.update();
		}
	}
}
