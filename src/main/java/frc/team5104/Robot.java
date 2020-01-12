/*BreakerBots Robotics Team 2019*/
package frc.team5104;

import frc.team5104.util.setup.RobotController;
import edu.wpi.first.wpilinj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetWorkTable;
import edu.wpi.first.networktables.NetWorkTableEntry;
import edu.wpi.first.netowerktables.NetwrokTableInstance;

public class Robot extends RobotController.BreakerRobot {
	public Robot() {
		NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
		NetworkTableEntry tx = table.getEntry("tx");
		NetworkTableEntry ty = table.getEntry("ty");
		NetworkTableEntry ta = table.getEntry("ta");
		
		double x = tx.getDouble(0.0);
		double y = ty.getDouble(0.0);
		double area = ta.getDouble(0.0);
		
		SmartDashboard.putNumber("LimelightX", x);
		SmartDashboard.putNumber("LimelightY", y);
		SmartDashboard.putNumber("LimelightArea", area);
	}

	public void teleopStart() {
		
	}
	public void teleopStop() {
		
	}
	public void teleopLoop() {
		
	}
	
	public void testLoop() {

	}
}
