/*BreakerBots Robotics Team 2019*/
package frc.team5104.auto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import frc.team5104.auto.util.Trajectory;
import frc.team5104.auto.util.TrajectoryGenerator;
import frc.team5104.auto.util.TrajectoryWaypoint;
import frc.team5104.main._RobotConstants;
import frc.team5104.util.console;
import frc.team5104.util.console.c;

/**
 * 
 */
public class BreakerTrajectoryGenerator {
	private static final String cacheDirectory = "/home/lvuser/TrajectoryCache/";
	
	/**
	 * Will either return a cached version of a Trajectory under those points (~500ms)
	 * or will Generate a Trajectory a cache it (~5000ms - ~15000ms)
	 * @param points Waypoints to generate the trajectory from
	 * @return A Trajectory to follow those waypoints
	 */
	public static Trajectory getTrajectory(TrajectoryWaypoint[] points) {
//		try {
			//Parse trajectory name
			String s = "";
	    	for (TrajectoryWaypoint p : points) {
	    		s += (Double.toString(p.x) + "/" + Double.toString(p.y) + "/" + Double.toString(p.theta));
	    	}
	    	s = "_" + s.hashCode();
	    	
	    	//Read file
	    	console.log(c.AUTO, "Looking for Similar Cached Trajectory Under " + s);
	    	Trajectory t = readFile(s);
	    	
	    	//If the file does not exist, generate a path and save
	    	if (t == null) {
	    		console.log(c.AUTO, "No Similar Cached Trajectory Found => Generating Path");
	    		console.sets.create("MPGEN");
	    		t = TrajectoryGenerator.generate(points, 
	    				_AutoConstants._maxVelocity,
	    				_AutoConstants._maxAcceleration,
	    				_AutoConstants._maxJerk,
	    				1.0 / _RobotConstants.Loops._robotHz
	    			);
	    		writeFile(s, t);
	    		console.log(c.AUTO, "Trajectory Generation Took " + console.sets.getTime("MPGEN") + "s");
	    	}
	    	return t;
//		}
//		catch (Exception e) {
//			console.error(e);
//			return null;
//		}
	}
	
	/**
	 * Finds, reads, and returns the trajectory saved with name
	 */
	private static Trajectory readFile(String name) {
		try {
			File file = new File(cacheDirectory + name);
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Trajectory t = (Trajectory) ois.readObject();
			ois.close();
			fis.close();
			return t;
		}
		catch (Exception e) { return null; }
	}
	
	/**
	 * Writes the path to a new file
	 */
	private static void writeFile(String name, Trajectory t) {
		try {
			File directory = new File(cacheDirectory);
			console.log("exists: " + directory.exists());
			if (!directory.exists()) {
				console.log("missing dir, making");
				directory.mkdir();
			}
			
			FileOutputStream fos = new FileOutputStream(cacheDirectory + name);
		    ObjectOutputStream oos = new ObjectOutputStream(fos);
		    oos.writeObject(t);
		    oos.close();
		    fos.close();
		}
		catch (Exception e) {
			console.error(e);
		}
	}
}
