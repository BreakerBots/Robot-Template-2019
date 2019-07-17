package frc.team5104.util;

import edu.wpi.first.wpilibj.AnalogInput;

public class AnalogToDigitalInput {
	private AnalogInput analogInput;
	public int disconnectValue;
	
	//Constructor
	public AnalogToDigitalInput(int port) { this(port, 205); }
	public AnalogToDigitalInput(int port, int disconnectValue) {
		analogInput = new AnalogInput(port);
		this.disconnectValue = disconnectValue;
	}
	
	public boolean get() {
		return !(Math.round(analogInput.getAverageVoltage() / 5.0) == 1);
	}

	public boolean disconnected() {
		return analogInput.getAverageValue() < disconnectValue;
	}
}
