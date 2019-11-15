/* BreakerBots Robotics Team (FRC 5104) 2020 */
package frc.team5104.statemachines;

import frc.team5104.util.managers.StateMachine;

/** Father State Machine for the Intake, Wrist, and Elevator */
public class ExampleStatemachine extends StateMachine {
	protected String getName() { return "Example-Statemachine"; }

	public static enum ExampleState { example1, example2 }
	@SuppressWarnings("unused")
	private static ExampleState currentExampleState = ExampleState.example1;
	
	//Loop
	protected void update() {
		
	}

	//Enabled/Disabled
	protected void enabled() {
		currentExampleState = ExampleState.example1;
	}
	protected void disabled() {
		currentExampleState = ExampleState.example1;
	}
}
