package org.usfirst.frc.team5854.robot;


public class DriveSystem {
	private TalonSRX fl, fr, bl, br;
	public DriveSystem(int flID, int frID, int blID, int brID, int flID2, int frID2, int blID2, int brID2) {
		fl = new TalonSRX(flID);
		fr = new TalonSRX(frID);
		bl = new TalonSRX(blID);
		br = new TalonSRX(brID);
		TalonSRX fl2 = new TalonSRX(flID2);
		fl2.set(ControlMode.Follower, fl.getDeviceID());
		TalonSRX fr2 = new TalonSRX(frID2);
		fr2.set(ControlMode.Follower, fl.getDeviceID());
		TalonSRX bl2 = new TalonSRX(blID2);
		bl2.set(ControlMode.Follower, fl.getDeviceID());
		TalonSRX br2 = new TalonSRX(brID2);
		br2.set(ControlMode.Follower, fl.getDeviceID());
	}
	
	public void drive(double y, double x, double r) {
		double fl = y-x-r;
		double fr = y+x+r;
		double bl = y+x-r;
		double br = y-x+r;
		this.fl.set(ControlMode.PercentOutput, fl);
		this.fr.set(ControlMode.PercentOutput, fr);
		this.bl.set(ControlMode.PercentOutput, bl);
		this.br.set(ControlMode.PercentOutput, br);
	}
}
