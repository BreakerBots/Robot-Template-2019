package frc.team5104.subsystem.drive;

import frc.team5104.subsystem.drive.DriveSignal.DriveUnit;
import frc.team5104.util.BreakerMath;
import frc.team5104.util.console;
import frc.team5104.util.console.c;

public class DriveAutoTuneMS {
	private static enum AutoTuneStage {
		forward,
		reverse,
		turnRight,
		turnLeft
	};
	private static AutoTuneStage currentStage = AutoTuneStage.forward;
	
	private static double[] measuredSpeeds = new double[4];
	
	private static double speed = 0;
	private static boolean finished = true;
	
	private static final int _delayLength = 2000;
	private static long delayStart = System.currentTimeMillis() - _delayLength;
	
	public static void init() {
		finished = false;
		currentStage = AutoTuneStage.forward;
		
		forwardInit(false);
	}
	
	public static DriveSignal getNextSignal() {
		if (finished) return new DriveSignal(0, 0, DriveUnit.voltage);
		DriveSignal signal = new DriveSignal(0, 0, DriveUnit.voltage);
		
		if (System.currentTimeMillis() > delayStart + _delayLength) {
			switch (currentStage) {
				case forward:
					signal = getForwardSignal(false);
					break;
				case reverse:
					signal = getForwardSignal(true);
					break;
				case turnRight:
					signal = getTurnSignal(true);
					break;
				case turnLeft:
					signal = getTurnSignal(false);
					break;
			}
		}
		
		return signal;
	}
	
	private static void nextStage() {
		delayStart = System.currentTimeMillis();
		
		switch (currentStage) {
			case forward:
				measuredSpeeds[0] = speed;
				currentStage = AutoTuneStage.reverse;
				forwardInit(true);
				break;
			case reverse:
				measuredSpeeds[1] = speed;
				currentStage = AutoTuneStage.turnRight;
				turnInit(true);
				break;
			case turnRight:
				measuredSpeeds[2] = speed;
				currentStage = AutoTuneStage.turnLeft;
				turnInit(false);
				break;
			case turnLeft:
				measuredSpeeds[3] = speed;
				
				console.divider();
				console.log(c.TUNING, "Measured Min-Speeds: ");
				console.log(c.TUNING, " - Forward: " + BreakerMath.round((measuredSpeeds[0] + measuredSpeeds[1]) / 2.0, 2));
				console.log(c.TUNING, " - Turn: " 	 + BreakerMath.round((measuredSpeeds[2] + measuredSpeeds[3]) / 2.0, 2));
				console.divider();
				
				finished = true;
				break;
		}
	}
	
	private static void forwardInit(boolean reverse) {
		speed = 0;
		console.log(c.TUNING, (reverse ? "Reverse" : "Forward") + " min-speed measure started");
	}
	private static DriveSignal getForwardSignal(boolean reverse) {
		if ((Math.round((speed)*1000.0)/1000.0) % 0.1 == 0)
			console.log(c.TUNING, (reverse ? "Reverse" : "Forward") + " min-speed set " + String.format("%.2f", speed));
		if (speed > 6) {
			nextStage();
			//console.log(c.TUNING, (reverse ? "Reverse" : "Forward") + " min-speed measure failed (exceeded max speed)");
		}
		if (Math.abs(DriveSystems.encoders.getLeftVelocityFeet()) > 0.3) {
			nextStage();
			//console.log(c.TUNING, (reverse ? "Reverse" : "Forward") + " min-speed measured at " + String.format("%.2f", speed));
		}
		speed += 0.005;
		return new DriveSignal(speed * (reverse ? -1 : 1), speed * (reverse ? -1 : 1), DriveUnit.voltage);
	}

	private static void turnInit(boolean right) {
		speed = 0.8;
		console.log(c.TUNING, "Turning " + (right ? "right" : "left") + " min-speed measure started");
	}
	private static DriveSignal getTurnSignal(boolean right) {
		if ((Math.round((speed)*1000.0)/1000.0) % 0.1 == 0)
			console.log(c.TUNING, "Turning min-speed set " + String.format("%.2f", speed));
		if (speed > 6) {
			nextStage();
			//console.log(c.TUNING, "Turning min-speed measure failed (exceeded max speed)");
		}
		if (Math.abs(DriveSystems.encoders.getLeftVelocityFeet()) > 0.3) {
			nextStage();
			//console.log(c.TUNING, "Turning min-speed measured at " + String.format("%.2f", speed));
		}
		speed += 0.005;
		return new DriveSignal(speed * (right ? 1 : -1), speed * (right ? -1 : 1), DriveUnit.voltage);
	}
}