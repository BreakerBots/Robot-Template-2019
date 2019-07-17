/*BreakerBots Robotics Team 2019*/
package frc.team5104.util;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import frc.team5104.util.CrashLogger.Crash;
import edu.wpi.first.wpilibj.Joystick;

/**
 * <h1>Controller</h1>
 * A much simpler to use version of WPI's Joystick (controller) class
 * There is also a bunch of added functional for rumble and click events
 * Every single control in the Control Enum is treated as a Button and Axis
 * Features:
 *  - Rumble (Soft, Hard)
 *  - Rumble for Duration (Soft, Hard)
 *	- Get Held Time (time button has been held for)
 *	- Get Axis Value
 *	- Get Button Down
 *	- Button Pressed/Released Event (Events are true for one tick (and then false) when triggered)
 *  
 * For Xbox One Controllers references the official control sheet: https://support.xbox.com/en-US/xbox-one/accessories/xbox-one-wireless-controller
 */
public class Controller {
	//List of Controllers
	public static enum Controllers {
		Main(0);
		//Secondary(1);
		private Joystick handler;
		private Controllers(int slot) { this.handler = new Joystick(slot); }
	};
	
	//Xbox One Controller Controls
	public static enum ControlList { 
		//Buttons
		A(1, 1), B(2, 1), X(3, 1), Y(4, 1),	
		LeftBumper(5, 1),	RightBumper(6, 1),	
		Menu(7, 1),	List(8, 1),	
				
		//Direction Pad (D-Pad)
		DirectionPadUpLeft(315, 3), DirectionPadUp(0, 3), DirectionPadUpRight(45, 3), 
		DirectionPadRight(90, 3),	
		DirectionPadDownLeft(225, 3), DirectionPadDown(180, 3), DirectionPadDownRight(135, 3), 
		DirectionPadLeft(270, 3),
		
		//Joysticks
		LeftJoystickX(0, 2), LeftJoystickY(1, 2), LeftJoystickPress(9, 1),
		RightJoystickX(4, 2), RightJoystickY(5, 2), RightJoystickPress(10, 1),
		
		//Triggers
		LeftTrigger(2, 2), RightTrigger(3, 2);					 
		
		private int slot, type;
		private ControlList(int slot, int type) { this.slot = slot; this.type = type; }
	}
		
	private static abstract class ControllerElement { abstract void update(); }
	private static List<ControllerElement> register = new ArrayList<ControllerElement>();
	
	public static class Control extends ControllerElement {
		public ControlList control;
		public Controllers controller;
		public boolean reversed; 
		public double deadzone;
		private boolean val, lastVal, pressed, released;
		private long time;
		private boolean heldEventReturned = false;
		private int heldEventLength = 600;
		int doubleClickKilloff = 500, doubleClickIndex = 0;
		long doubleClickTime;
		
		/**
		 * Creates a control object that can be referenced later.
		 * @param control The specified control
		 */
		public Control(ControlList control) { this(control, Controllers.Main); }
		/**
		 * Creates a control object that can be referenced later.
		 * @param control The specified control
		 * @param controller The target controller for this rumble.
		 */
		public Control(ControlList control, Controllers controller) { this(control, controller, false, 0.6); }
		/**
		 * Creates a control object that can be referenced later.
		 * @param control The specified control
		 * @param reversed (For axis only) When converting from axis to button: if the axis should be flipped before
		 * @param deadzone (For axis only) When converting from axis to button: the crossover point in which the axis should be considered pressed
		 */
		public Control(ControlList control, boolean reversed, double deadzone) {
			this(control, Controllers.Main, reversed, deadzone);
		}
		/**
		 * Creates a control object that can be referenced later.
		 * @param control The specified control
		 * @param controller The target controller for this rumble.
		 * @param reversed (For axis only) When converting from axis to button: if the axis should be flipped before
		 * @param deadzone (For axis only) When converting from axis to button: the crossover point in which the axis should be considered pressed
		 */
		public Control(ControlList control, Controllers controller, boolean reversed, double deadzone) {
			this.control = control;
			this.controller = controller;
			this.reversed = reversed;
			this.deadzone = deadzone;
			register.add(this);
		}
		
		/**Returns the percent of the axis, Just The Default Axis*/
		public double getAxis() { return controller.handler.getRawAxis(control.slot); }
		/**Returns true if button is down, Just The Default Button State*/
		public boolean getHeld() { return val; }
		/**Returns how long the button has been held down for (milliseconds), if not held down returns 0*/
		public double getHeldTime() { return val ? ((double)(System.currentTimeMillis() - time)) : 0; }
		/**Returns true for one tick if button goes from up to down*/
		public boolean getPressed() { return pressed; }
		/**Returns true for one tick if button goes from down to up*/
		public boolean getReleased() { return released; }
		/**Returns the time the click lasted for, for one tick when button goes from down to up*/
		public double getClickTime() { return released ? ((double)(System.currentTimeMillis() - time)) : 0; }
		
		public boolean getHeldEvent() { 
			boolean temp = (getHeldTime() > heldEventLength) && (!heldEventReturned);
			if (temp)
				heldEventReturned = true;
			return temp; 
		}
		
		public int getDoubleClick() {
			return doubleClickIndex;
		}
		
		void update() {
			pressed = false;
			released = false;
			
			//Get Button Values
			if (control.type == 1)
				val = controller.handler.getRawButton(control.slot);
			else if (control.type == 2)
				val = (controller.handler.getRawAxis(control.slot) * (reversed ? -1 : 1)) > deadzone ? true : false;
			else if (control.type == 3)
				val = controller.handler.getPOV() == control.slot;
				
			//Pressed/Released
			if (val != lastVal) {
				lastVal = val;
				if (val == true) { 
					pressed = true; 
					time = System.currentTimeMillis(); 
				}
				else released = true;
			}
			
			//Held Event
			if (val == false)
				heldEventReturned = false;
			
			//Double Click
			doubleClickIndex = 0;
			if (doubleClickTime != -1 && System.currentTimeMillis() > doubleClickTime + doubleClickKilloff) {
				//Killoff
				doubleClickIndex = 1;
				doubleClickTime = -1;
			}
			if (pressed) {
				//First Click
				if (doubleClickTime == -1) {
					doubleClickTime = System.currentTimeMillis();
				}
				//Second Click
				else {
					doubleClickIndex = 2;
					doubleClickTime = -1;
				}
			}
		}
	}
	
	public static class Rumble extends ControllerElement {
		private long timerStart;
		private boolean timerRunning = false;
		private Controllers controller;
		private boolean bounce;
		private long timeoutMs;
		private double strength;
		private RumbleType rumbleType;
		
		/**
		 * Creates a saveable rumble object that can be referenced later.
		 * @param strength (0-1) Perecent strength of the rumble
		 * @param hard If the rumble should be hard (a deeper rumble) or soft (a lighter rumble)
		 * @param timeoutMs The time (milliseconds) in which the rumble should automatically stop.
		 */
		public Rumble(double strength, boolean hard, boolean bounce, long timeoutMs) {
			this(strength, hard, bounce, timeoutMs, Controllers.Main);
		}
		/**
		 * Creates a saveable rumble object that can be referenced later.
		 * @param strength (0-1) Perecent strength of the rumble
		 * @param hard If the rumble should be hard (a deeper rumble) or soft (a lighter rumble)
		 * @param timeoutMs The time (milliseconds) in which the rumble should automatically stop.
		 * @param controller The target controller for this rumble.
		 */
		public Rumble(double strength, boolean hard, boolean bounce, long timeoutMs, Controllers controller) {
			this.strength = strength;
			this.timeoutMs = timeoutMs;
			this.controller = controller;
			this.bounce = bounce;
			rumbleType = hard ? RumbleType.kLeftRumble : RumbleType.kRightRumble;
			register.add(this);
		}
		
		/**
		 * Starts the rumble (specified in the constructor)
		 */
		public void start() {
			timerStart = System.currentTimeMillis(); 
			timerRunning = true;
		}
		
		void update() {
			if (timerRunning) {
				if (System.currentTimeMillis() > timerStart + timeoutMs) {
					controller.handler.setRumble(rumbleType, 0);
					timerRunning = false;
				}
				else {
					if (bounce) {
						if (System.currentTimeMillis() > timerStart + (timeoutMs/3.0)) {
							if (System.currentTimeMillis() > timerStart + (timeoutMs/3.0*2))
								controller.handler.setRumble(rumbleType, strength);
							else 
								controller.handler.setRumble(rumbleType, 0);
						}
						else controller.handler.setRumble(rumbleType, strength);
					}
					else controller.handler.setRumble(rumbleType, strength);
				}
			}
		}
	}
	
	public static void handle() {
		try { update(); } catch (Exception e) { CrashLogger.logCrash(new Crash("main", e)); }
	}
	private static void update() {
		for (ControllerElement obj : register) { 
			try {
				obj.update(); 
			} catch (Exception e) {
				CrashLogger.logCrash(new Crash("main", e));
			}
		} 
	}
}