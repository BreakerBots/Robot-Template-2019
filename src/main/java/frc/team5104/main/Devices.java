/*BreakerBots Robotics Team 2019*/
package frc.team5104.main;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.team5104.subsystem.drive._DriveConstants;
import frc.team5104.util.TalonFactory;
import frc.team5104.util.TalonFactory.TalonSettings;

/**
 * All Devices used in Code
 */
public class Devices {
	//Drive
	public static class Drive {
		public static TalonSRX L1 = TalonFactory.getTalon(11, new TalonSettings(NeutralMode.Brake, true, _DriveConstants._currentLimit, true));
		public static TalonSRX L2 = TalonFactory.getTalon(12, new TalonSettings(NeutralMode.Brake, true, _DriveConstants._currentLimit, true));
		public static TalonSRX R1 = TalonFactory.getTalon(13, new TalonSettings(NeutralMode.Brake, false, _DriveConstants._currentLimit, true));
		public static TalonSRX R2 = TalonFactory.getTalon(14, new TalonSettings(NeutralMode.Brake, false, _DriveConstants._currentLimit, true));
	}
}
