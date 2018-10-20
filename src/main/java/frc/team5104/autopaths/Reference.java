// Reference path - 2018 PowerUp Baseline

package frc.team5104.autopaths;

import frc.team5104.autocommands.*;

import jaci.pathfinder.Waypoint;

public class Reference extends BreakerPath {
	public Reference() {
		add(new DriveTrajectory(new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(9.5, 0, 0)
			}));
		add(new DriveStop());
	}
}
