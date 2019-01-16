/*BreakerBots Robotics Team 2019*/
package frc.team5104.main;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import frc.team5104.util.TalonFactory;

/**
 * All Devices used in Athena's Code
 */
public class Devices {

	//Main
	public static class Main {
		//public static PowerDistributionPanel pdp = new PowerDistributionPanel();
	}
	
	//Drive
	public static class Drive {
		public static TalonSRX L1 = TalonFactory.getTalon(11);
		public static TalonSRX L2 = TalonFactory.getTalon(12);
		public static TalonSRX R1 = TalonFactory.getTalon(13);
		public static TalonSRX R2 = TalonFactory.getTalon(14);
		
		public static PigeonIMU gyro = new PigeonIMU(L2);
		
		public static DoubleSolenoid shift = new DoubleSolenoid(0, 1);
	}
}
