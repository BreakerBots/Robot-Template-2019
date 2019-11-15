/* BreakerBots Robotics Team (FRC 5104) 2020 */
package frc.team5104.teleop;

import frc.team5104.main.Controls;
import frc.team5104.util.BreakerCompressor;
import frc.team5104.util.managers.TeleopController;

public class CompressorController extends TeleopController {
	protected String getName() { return "Compressor Controller"; }

	protected void update() {
		if (Controls.COMPRESSOR_TOGGLE.getPressed()) {
			if (BreakerCompressor.isRunning())
				BreakerCompressor.stop();
			else
				BreakerCompressor.run();
		}
	}
}
