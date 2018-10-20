package frc.team5104.main;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.team5104.util.TalonFactory;

/*Breakerbots Robotics Team 2018*/
/**
 * All Devices used in Robot Code
 */
public class Devices {

	//Main
	public static class Main {
	}
	
	//Drive
	static int[] driveTalons = { 11, 12, 13, 14 }; //Update as needed
	static int[] shiftSolenoid = {2, 3};
	
	public static class Drive {
		public static TalonSRX L1 = TalonFactory.getTalon(driveTalons[0]);
		public static TalonSRX L2 = TalonFactory.getTalon(driveTalons[1]);
		public static TalonSRX R1 = TalonFactory.getTalon(driveTalons[2]);
		public static TalonSRX R2 = TalonFactory.getTalon(driveTalons[3]);
		
		public static ADXRS450_Gyro Gyro = new ADXRS450_Gyro();
		//If shifting
		public static DoubleSolenoid shift = new DoubleSolenoid(shiftSolenoid[0], shiftSolenoid[1]);
	}
	
	//Follow the above format for all other subsystems
}
