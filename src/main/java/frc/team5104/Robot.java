/*BreakerBots Robotics Team 2019*/
package frc.team5104;

import frc.team5104.util.setup.RobotController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Robot extends RobotController.BreakerRobot {
	NetworkTable table;
	NetworkTableEntry tx;
	NetworkTableEntry ty;
	NetworkTableEntry ta;
	
	public Robot() {
		
	}

	public void teleopStart() {
		table = NetworkTableInstance.getDefault().getTable("limelight");
		tx = table.getEntry("tx");
		ty = table.getEntry("ty");
		ta = table.getEntry("ta");
	}
	public void teleopStop() {
		
	}
	public void teleopLoop() {
		
		
		double x = tx.getDouble(0.0);
		double y = ty.getDouble(0.0);
		double area = ta.getDouble(0.0);
		
		System.out.println("LimelightX: " + x);
		System.out.println("LimelightY: " + y);
		System.out.println("LimelightArea: " + area);
	}
	
	public void testLoop() {

	}
}
