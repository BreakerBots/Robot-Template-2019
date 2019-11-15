/* BreakerBots Robotics Team (FRC 5104) 2020 */
package frc.team5104.subsystems.drive;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.team5104.subsystems.drive.DriveObjects.DriveSignal;
import frc.team5104.subsystems.drive.DriveObjects.DriveUnits;
import frc.team5104.util.managers.Subsystem;

class DriveLooper extends Subsystem.Looper {
	private DriveSignal currentDriveSignal = new DriveSignal();
	
	//Loop
	protected void update() { 
		//shifting
		Drive._interface.shift(currentDriveSignal.isHighGear);
		
		//motor speed
		switch (currentDriveSignal.unit) {
			case percentOutput: {
				Drive._interface.set(
						currentDriveSignal.leftSpeed, 
						currentDriveSignal.rightSpeed, 
						ControlMode.PercentOutput
					);
				break;
			}
			case feetPerSecond: {
				Drive._interface.set(
						DriveUnits.feetPerSecondToTalonVel(currentDriveSignal.leftSpeed), 
						DriveUnits.feetPerSecondToTalonVel(currentDriveSignal.rightSpeed), 
						ControlMode.Velocity
					);
				break;
			}
			case voltage: {
				Drive._interface.set(
						currentDriveSignal.leftSpeed / Drive._interface.getLeftGearboxVoltage(),
						currentDriveSignal.rightSpeed / Drive._interface.getRightGearboxVoltage(),
						ControlMode.PercentOutput
					);
				break;
			}
			case stop:
				Drive._interface.stop();
				break;
		}
		
		currentDriveSignal = new DriveSignal();
	}

	//Set Current Drive Signal
	void setDriveSignal(DriveSignal signal) {
		this.currentDriveSignal = signal;
	}
	
	//Stop Everything
	protected void disabled() { 
		Drive.stop();
		currentDriveSignal = new DriveSignal();
	}
	protected void enabled() { 
		currentDriveSignal = new DriveSignal();
	}
}
