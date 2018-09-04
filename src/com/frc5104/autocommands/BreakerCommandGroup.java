package com.frc5104.autocommands;

/*Breakerbots Robotics Team 2018*/
public abstract class BreakerCommandGroup {
	public BreakerCommand[] cs = new BreakerCommand[10];
	public int cl = 0;
	
	public void add(BreakerCommand command) {
		cs[cl] = command;
		cl++;
	}
	
	abstract public void init();
}