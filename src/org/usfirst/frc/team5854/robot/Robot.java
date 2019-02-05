package org.usfirst.frc.team5854.robot;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.IterativeRobot;
public class Robot extends IterativeRobot { 
	AnalogPotentiometer pot = new AnalogPotentiometer(0);
	
	@Override 
	public void robotInit() { 
		
	} 
	@Override 
	public void autonomousInit() {
		
	} 
	@Override 
	public void autonomousPeriodic() {
		
	} 
	@Override 
	public void teleopInit() {
		
	} 
	@Override 
	public void teleopPeriodic() {
		pot.get();
	} 
	@Override 
	public void testInit() {
		
	} 
	@Override 
	public void testPeriodic() {
		
	} 
}
