/* BreakerBots Robotics Team (FRC 5104) 2020 */
package frc.team5104.subsystems.drive;

import frc.team5104.subsystems.drive.DriveObjects.DriveEncoders;
import frc.team5104.subsystems.drive.DriveObjects.DriveSignal;
import frc.team5104.subsystems.drive.DriveObjects.DriveUnits;
import frc.team5104.util.WebappTuner.tunerOutput;
import frc.team5104.util.managers.Subsystem;
import frc.team5104.util.managers.Subsystem.Interface;
import frc.team5104.util.managers.Subsystem.Looper;

public class Drive extends Subsystem.Actions {
	//Meta
	protected String getName() { return "Drive"; }
	protected static DriveInterface _interface = new DriveInterface();
	protected Interface getInterface() { return _interface; }
	protected static DriveLooper _looper = new DriveLooper();
	protected Looper getLooper() { return _looper; }

	//Actions
	public static void set(DriveSignal signal) { _looper.setDriveSignal(signal); }
	public static void stop() { _looper.setDriveSignal(new DriveSignal()); }
	
	@tunerOutput
	public static double getLeftEncoderFPS() { return DriveUnits.wheelRevolutionsToFeet(getEncoders().leftVelocityRevs); }
	@tunerOutput
	public static double getRightEncoderFPS() { return DriveUnits.wheelRevolutionsToFeet(getEncoders().rightVelocityRevs); }
	public static DriveEncoders getEncoders() { return _interface.getEncoders(); }
	public static void resetEncoders() { _interface.resetEncoders(); }
	
	public static double getGyro() { return _interface.getGyro(); }
	public static void resetGyro() { _interface.resetGyro(); }
	
	public static double getLeftGearboxOutputVoltage() { return _interface.getLeftGearboxOutputVoltage(); }
	public static double getRightGearboxOutputVoltage() { return _interface.getRightGearboxOutputVoltage(); }
}
