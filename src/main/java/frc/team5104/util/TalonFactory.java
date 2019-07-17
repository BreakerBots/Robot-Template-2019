/*BreakerBots Robotics Team 2019*/
package frc.team5104.util;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * <h1>Talon Factory</h1>
 * Brewing fresh talons since 2017
 */
public class TalonFactory {
	public static class TalonSettings {
		NeutralMode neutralMode;
		boolean inverted;
		public int currentLimit;
		public boolean enableCurrentLimiting;
		
		public TalonSettings(NeutralMode neutralMode, boolean inverted, int currentLimit, boolean enableCurrentLimiting) {
			this.neutralMode = neutralMode;
			this.inverted = inverted;
			this.currentLimit = currentLimit;
			this.enableCurrentLimiting = enableCurrentLimiting;
		}
	}
	static TalonSettings defaults = new TalonSettings(NeutralMode.Brake, false, 0, false);
	
	/**
	 * Creates a TalonSRX with the default setttings
	 * @param id Device ID of the TalonSRX
	 */
	public static TalonSRX getTalon(int id) { return getTalon(id, defaults); }
	
	/**
	 * Creates a TalonSRX with the specified settings
	 * @param settings Specified Settings
	 * @param id Device ID of the TalonSRX
	 */
	public static TalonSRX getTalon(int id, TalonSettings settings) {
		TalonSRX talon = new TalonSRX(id);
		
		//Max Current (Amps)
		talon.configContinuousCurrentLimit(settings.currentLimit, 10);
		talon.enableCurrentLimit(settings.enableCurrentLimiting);
		
		//Neutral Mode
		talon.setNeutralMode(settings.neutralMode);
		
		//Inverted
		talon.setInverted(settings.inverted);
		
		//Sensor (Encoder)
		talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		talon.configVelocityMeasurementPeriod(VelocityMeasPeriod.Period_100Ms, 0);
		talon.configVelocityMeasurementWindow(64, 0);
		
		//PID (F)
		talon.config_kP(0, 0, 0);
		talon.config_kI(0, 0, 0);
		talon.config_kD(0, 0, 0);
		talon.config_kF(0, 0, 0);
		talon.config_IntegralZone(0, 0, 0);
		talon.configAllowableClosedloopError(0, 0, 0);
		talon.configMaxIntegralAccumulator(0, 0, 0);
		
		//Soft Limits
		talon.configForwardSoftLimitThreshold(0, 0);
		talon.configReverseSoftLimitThreshold(0, 0);
		talon.configForwardSoftLimitEnable(false, 0);
		talon.configReverseSoftLimitEnable(false, 0);
		
		//Ramp
		talon.configOpenloopRamp(0, 0);
		talon.configClosedloopRamp(0, 0);
		
		//Peak Percent Outputs
		talon.configPeakOutputForward(1, 0);
		talon.configPeakOutputReverse(-1, 0);
		
		
		//Motion Magic
		talon.configMotionCruiseVelocity(0, 0);
		talon.configMotionAcceleration(0, 0);
		
		//Other
		talon.configNominalOutputForward(0, 0);
		talon.configNominalOutputReverse(0, 0);
		talon.configNeutralDeadband(0.0, 0);
		talon.configVoltageCompSaturation(0, 0);
		talon.configVoltageMeasurementFilter(32, 0);
		talon.configSetCustomParam(0, 0, 0);
		
		return talon;
	}

	public static boolean magEncoderDisconnected(TalonSRX talon) {
		return talon.getSensorCollection().getPulseWidthRiseToRiseUs() == 0;
	}
 }
