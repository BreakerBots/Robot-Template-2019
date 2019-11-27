/* BreakerBots Robotics Team (FRC 5104) 2020 */
package frc.team5104.subsystems.drive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

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
	private PigeonIMU gyro = new PigeonIMU(talonR2);
	
	//Functions
	void set(double leftSpeed, double rightSpeed, ControlMode controlMode, double feedForward) {
		talonL1.set(controlMode, leftSpeed, DemandType.ArbitraryFeedForward, feedForward);
		talonR1.set(controlMode, rightSpeed, DemandType.ArbitraryFeedForward, feedForward);
	}
	void stop() {
		talonL1.set(ControlMode.Disabled, 0);
		talonR1.set(ControlMode.Disabled, 0);
	}
	
	double getLeftGearboxVoltage() { return talonL1.getBusVoltage(); }
	double getRightGearboxVoltage() { return talonR1.getBusVoltage(); }
	double getLeftGearboxOutputVoltage() { return talonL1.getMotorOutputVoltage(); }
	double getRightGearboxOutputVoltage() { return talonR1.getMotorOutputVoltage(); }

	void resetGyro() { gyro.addYaw(getGyro()); }
	double getGyro() {
		double[] ypr = new double[3];
		gyro.getYawPitchRoll(ypr); 
		return -ypr[0];
	}

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
		talonL1.config_kP(0, Constants.DRIVE_KP, 0);
		talonL1.config_kI(0, Constants.DRIVE_KI, 0);
		talonL1.config_kD(0, Constants.DRIVE_KD, 0);
		talonL1.config_kF(0, 0, 0);
		talonL2.set(ControlMode.Follower, talonL1.getDeviceID());
		talonL1.setInverted(true);
		talonL2.setInverted(true);
		
		talonR1.configFactoryDefault();
		talonR2.configFactoryDefault();
		talonR1.config_kP(0, Constants.DRIVE_KP, 0);
		talonR1.config_kI(0, Constants.DRIVE_KI, 0);
		talonR1.config_kD(0, Constants.DRIVE_KD, 0);
		talonR1.config_kF(0, 0, 0);
		talonR2.set(ControlMode.Follower, talonR1.getDeviceID());
		
		stop();
		resetGyro();
		resetEncoders();
	}
}
