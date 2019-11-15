/* BreakerBots Robotics Team (FRC 5104) 2020 */
package frc.team5104.subsystems.drive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.team5104.main.Constants;
import frc.team5104.subsystems.drive.DriveObjects.DriveEncoders;
import frc.team5104.subsystems.drive.DriveObjects.DriveUnits;
import frc.team5104.util.managers.Subsystem;

class DriveInterface extends Subsystem.Interface {

	//Devices
	private TalonSRX talonL1 = new TalonSRX(11);
	private TalonSRX talonL2 = new TalonSRX(12);
	private TalonSRX talonR1 = new TalonSRX(13);
	private TalonSRX talonR2 = new TalonSRX(14);
	//private DoubleSolenoid shifter = new DoubleSolenoid(Ports.DRIVE_SHIFTER_FORWARD, Ports.DRIVE_SHIFTER_REVERSE);
	
	//Functions
	void set(double leftSpeed, double rightSpeed, ControlMode controlMode) {
		talonL1.set(controlMode, leftSpeed);
		talonR1.set(controlMode, rightSpeed);
	}
	void stop() {
		talonL1.set(ControlMode.Disabled, 0);
		talonR1.set(ControlMode.Disabled, 0);
	}
	
	double getLeftGearboxVoltage() { return talonL1.getBusVoltage(); }
	double getRightGearboxVoltage() { return talonR1.getBusVoltage(); }
	double getLeftGearboxOutputVoltage() { return talonL1.getMotorOutputVoltage(); }
	double getRightGearboxOutputVoltage() { return talonR1.getMotorOutputVoltage(); }

	void resetGyro() { /*?*/ }
	double getGyro() { return -1; }

	void resetEncoders() {
		talonL1.setSelectedSensorPosition(0);
		talonR1.setSelectedSensorPosition(0);
	}
	DriveEncoders getEncoders() {
		return new DriveEncoders(
				talonL1.getSelectedSensorPosition(),
				talonR1.getSelectedSensorPosition(),
				DriveUnits.ticksToWheelRevolutions(talonL1.getSelectedSensorPosition()),
				DriveUnits.ticksToWheelRevolutions(talonR1.getSelectedSensorPosition()),
				talonL1.getSelectedSensorVelocity(),
				talonR1.getSelectedSensorVelocity(),
				DriveUnits.ticksToWheelRevolutions(talonL1.getSelectedSensorVelocity()),
				DriveUnits.ticksToWheelRevolutions(talonR1.getSelectedSensorVelocity())
			);
	}
	
	void shift(boolean toHigh) { /*shifter.set(toHigh ? Value.kForward : Value.kReverse);*/ }
	boolean getShift() { return true;/*shifter.get() == Value.kForward;*/ }
	
	//Config
	protected void init() {
		talonL1.configFactoryDefault();
		talonL2.configFactoryDefault();
		talonL1.configAllowableClosedloopError(0, 0, 10);
		talonL1.config_kP(0, Constants.DRIVE_KP, 10);
		talonL1.config_kI(0, Constants.DRIVE_KI, 10);
		talonL1.config_kD(0, Constants.DRIVE_KD, 10);
		talonL1.config_kF(0, Constants.DRIVE_KF, 10);
		talonL2.set(ControlMode.Follower, talonL1.getDeviceID());
		talonL1.setInverted(true);
		talonL2.setInverted(true);
		
		talonR1.configFactoryDefault();
		talonR2.configFactoryDefault();
		talonR1.configAllowableClosedloopError(0, 0, 10);
		talonR1.config_kP(0, Constants.DRIVE_KP, 10);
		talonR1.config_kI(0, Constants.DRIVE_KI, 10);
		talonR1.config_kD(0, Constants.DRIVE_KD, 10);
		talonR1.config_kF(0, Constants.DRIVE_KF, 10);
		talonR2.set(ControlMode.Follower, talonR1.getDeviceID());
        
		stop();
		resetGyro();
		resetEncoders();
	}
}
