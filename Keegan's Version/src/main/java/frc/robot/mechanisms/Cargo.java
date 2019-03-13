package frc.robot.mechanisms;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Cargo {
    private static Encoder encoder = new Encoder(0,1);
    private static final double LOWPOS = 0, MIDPOS = 90, HIGHPOS = 120;
    private static double CURPOS = 50; 
    private static TalonSRX winch = new TalonSRX(6);
    private static double winchSpeed = 1;
    private static TalonSRX shooterLeft = new TalonSRX(8);
    private static TalonSRX shooterRight = new TalonSRX(7);
    private static double shooterSpeed = 1;
    private static TalonSRX harvester = new TalonSRX(12);
    private static double harvesterSpeed = -0.5;
    private static DoubleSolenoid solenoid = new DoubleSolenoid(14, 7, 6);
    private static boolean resetSolenoid = false;
    private static DigitalInput limitSwitch = new DigitalInput(4);
    public static void setup() {
        winch.set(ControlMode.PercentOutput, winchSpeed);
        while(!limitSwitch.get());
        System.out.println("Reset");
        encoder.reset();
        winch.set(ControlMode.PercentOutput, 0);
    }

    public static void winch(boolean high, boolean mid, boolean low) {
        if (high) {
            CURPOS = HIGHPOS;
        } else if (mid) {
            CURPOS = MIDPOS;
        } else if (low) {
            CURPOS = LOWPOS;
        }
        System.out.println("CURPOS"  + CURPOS);
        if (encoder.getRaw() > CURPOS) {
            winch.set(ControlMode.PercentOutput, -winchSpeed);
        } else if (encoder.getRaw() < CURPOS) {
            winch.set(ControlMode.PercentOutput, winchSpeed);
        } else {
            winch.set(ControlMode.PercentOutput, 0);
        }
    }


    public static void actuate(boolean out, boolean in, boolean fire) {
        if (out) {
            shooterLeft.set(ControlMode.PercentOutput, shooterSpeed);
            shooterRight.set(ControlMode.PercentOutput, -shooterSpeed);
        } else if (in) {
            shooterLeft.set(ControlMode.PercentOutput, -shooterSpeed/2);
            shooterRight.set(ControlMode.PercentOutput, shooterSpeed/2);
            harvester.set(ControlMode.PercentOutput, harvesterSpeed);
        } else {
            shooterLeft.set(ControlMode.PercentOutput, 0);
            shooterRight.set(ControlMode.PercentOutput, 0);
            harvester.set(ControlMode.PercentOutput, 0);
        }

        if (fire) {
            solenoid.set(Value.kForward);
            resetSolenoid = true;
        } else {
            if (resetSolenoid) {
                solenoid.set(Value.kReverse);
                resetSolenoid = false;
            } else {
                solenoid.set(Value.kOff);
            }
        }
    }
}