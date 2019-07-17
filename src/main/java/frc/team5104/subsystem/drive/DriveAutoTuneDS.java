package frc.team5104.subsystem.drive;

import frc.team5104.subsystem.drive.DriveSignal.DriveUnit;
import frc.team5104.subsystem.drive.DriveSystems.encoders;
import frc.team5104.util.BreakerMath;
import frc.team5104.util.console;
import frc.team5104.util.console.c;

public class DriveAutoTuneDS {
	private static enum AutoTuneStage {
		forward,
		reverse
	};
	
	private static AutoTuneStage currentStage = AutoTuneStage.forward;
	private static int measureIndex = 0;

	private static double[] measuredWeights = {1, 1, 1, 1};

	private static final double moveSpeed = 3;
	
	private static final double _stageLength = 3000;
	private static long stageStart = System.currentTimeMillis();
	
	private static boolean finished = true;

	private static final int _delayLength = 2000;
	private static long delayStart = System.currentTimeMillis() - _delayLength;

	public static void init() {
		finished = false;
		measureIndex = 0;
		currentStage = AutoTuneStage.forward;
		stageStart = System.currentTimeMillis();
		console.log(c.TUNING, "Forward measure started");
	}

	public static DriveSignal getNextSignal() {
		if (finished) return new DriveSignal(0, 0, DriveUnit.voltage);

		if (System.currentTimeMillis() > delayStart + _delayLength) {
			if (System.currentTimeMillis() > stageStart + _stageLength) {
				nextStage();
			}
			if (currentStage == AutoTuneStage.forward)
				return new DriveSignal(moveSpeed * measuredWeights[0], moveSpeed * measuredWeights[1], DriveUnit.voltage);
			else
				return new DriveSignal(moveSpeed * measuredWeights[2], moveSpeed * measuredWeights[3], DriveUnit.voltage);
		}

		return new DriveSignal(0, 0, DriveUnit.voltage);
	}

	private static void nextStage() {
		delayStart = System.currentTimeMillis();

		switch (currentStage) {
		case forward:
			double leftOffset = encoders.getRawLeftPosition();
			double rightOffset = encoders.getRawRightPosition();
			
			double[] a = getWeight(measuredWeights[0], measuredWeights[1], leftOffset, rightOffset);
			measuredWeights[0] = a[0];
			measuredWeights[1] = a[1];
			
			stageStart = System.currentTimeMillis() + _delayLength;
			currentStage = AutoTuneStage.reverse;
			console.log(c.TUNING, "Reverse measure started");
			break;
		case reverse:
			leftOffset = encoders.getRawLeftPosition();
			rightOffset = encoders.getRawRightPosition();
			
			double[] b = getWeight(measuredWeights[2], measuredWeights[3], leftOffset, rightOffset);
			measuredWeights[2] = b[0];
			measuredWeights[3] = b[1];
			console.divider();
			console.log(c.TUNING, "Measured Differences: ");
			console.log(c.TUNING, " - Forward_Left: "  + BreakerMath.round((measuredWeights[0]), 2));
			console.log(c.TUNING, " - Forward_Right: " + BreakerMath.round((measuredWeights[1]), 2));
			console.log(c.TUNING, " - Reverse_Left: "  + BreakerMath.round((measuredWeights[2]), 2));
			console.log(c.TUNING, " - Reverse_Right: " + BreakerMath.round((measuredWeights[3]), 2));
			console.divider();

			if (measureIndex > 5) {
				console.divider();
				console.log(c.TUNING, "Measured Differences: ");
				console.log(c.TUNING, " - Forward_Left: "  + BreakerMath.round((measuredWeights[0]), 2));
				console.log(c.TUNING, " - Forward_Right: " + BreakerMath.round((measuredWeights[1]), 2));
				console.log(c.TUNING, " - Reverse_Left: "  + BreakerMath.round((measuredWeights[2]), 2));
				console.log(c.TUNING, " - Reverse_Right: " + BreakerMath.round((measuredWeights[3]), 2));
				console.divider();

				finished = true;
			}
			
			measureIndex++;
			break;
		}
	}
	
	private static double[] getWeight(double valL, double valR, double measL, double measR) {
		if(valL <= valR && measR < measL) {
			return new double[] {valL * measR, valR};
		} 
		else if(valR <= valL && measL < measR) {
			return new double[] {valL, valR * measL};
		} 
		else if(valL > valR && measL > measR) {
			return new double[] {valL, measR / valR};
		} 
		else if(valR > valL && measR > measL) {
			return new double[] {measL / valL, valR};
		}
		return new double[] {0, 0};
	}
}
