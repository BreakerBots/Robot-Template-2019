package frc.team5104.main;

public class RobotState {
	
	//State Machine
	public static enum RobotMode {
		Disabled, Auto, Teleop, Test, Vision
	}; 
	protected RobotMode currentMode = RobotMode.Disabled;
	protected RobotMode lastMode = RobotMode.Disabled;
	protected boolean isSandstorm;
	protected double loopTime = 0;
	
	//Access
	protected static RobotState instance;
	protected static RobotState getInstance() { 
		if (instance == null)
			instance = new RobotState();
		return instance; 
	}
	
	//External Functions
	public static boolean isSandstorm() { return getInstance().isSandstorm; }
	public static boolean isEnabled() { return getInstance().currentMode != RobotMode.Disabled; }
	public static RobotMode getMode() { return getInstance().currentMode; }
	public static void setMode(RobotMode mode) { getInstance().currentMode = mode; }
	public static double getLastLoopTime() { return getInstance().loopTime; }
}
