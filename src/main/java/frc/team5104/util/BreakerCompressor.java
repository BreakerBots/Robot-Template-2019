/* BreakerBots Robotics Team (FRC 5104) 2020 */
package frc.team5104.util;

import edu.wpi.first.wpilibj.Compressor;

/** A simple static class for handling the compressor */
public class BreakerCompressor {
	private static Compressor wpi_compressor_reference = new Compressor();
	public static void run() { wpi_compressor_reference.start(); }
	public static void stop() { wpi_compressor_reference.stop(); }
	public static boolean isRunning() { return wpi_compressor_reference.enabled(); }
}
