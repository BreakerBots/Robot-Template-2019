/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.drive;

import frc.team5104.main.Devices;
import frc.team5104.subsystem.BreakerSubsystem;

public class DriveManager extends BreakerSubsystem.Manager {
	public DriveManager() {
		DriveSystems.setup();
	}
	
	public void enabled() {
		DriveSystems.shifters.set(true);
	}
	public void update() { 
		Devices.Drive.L1.config_kP(0, _DriveConstants._kP, 0);
		Devices.Drive.L1.config_kI(0, _DriveConstants._kI, 0);
        Devices.Drive.L1.config_kD(0, _DriveConstants._kD, 0);
        Devices.Drive.R1.config_kP(0, _DriveConstants._kP, 0);
        Devices.Drive.R1.config_kI(0, _DriveConstants._kI, 0);
        Devices.Drive.R1.config_kD(0, _DriveConstants._kD, 0);
	}
	public void disabled() { }
}
