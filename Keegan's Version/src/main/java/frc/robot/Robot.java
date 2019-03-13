/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import org.opencv.core.Mat;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.mechanisms.Cargo;
import frc.robot.mechanisms.Hatch;

public class Robot extends IterativeRobot {
  Joystick joystick = new Joystick(0);
  Joystick secondary = new Joystick(1);
  Drivetrain drivetrain = new Drivetrain(13, 10, 3, 1, 11, 9, 4, 2);
  Compressor compressor = new Compressor(14);
  CameraSystem system;
  @Override
  public void robotInit() {
    system = new CameraSystem(2, new Camproc(){
      @Override
      public void process(Mat image, int cameraID) {
        
      }
    });
    system.start();
  }

  
  @Override
  public void robotPeriodic() {
  }

 
  @Override
  public void autonomousInit() {
    
  }

  @Override
  public void autonomousPeriodic() {
    
  }

  boolean first = true;
  @Override
  public void teleopPeriodic() {
    if (first) {
      Cargo.setup();
      first = false;
    }
    drivetrain.drive(joystick.getRawAxis(1), joystick.getRawAxis(0), joystick.getRawAxis(2));
    //Hatch.actuate(secondary.getRawButton(11), secondary.getRawButton(12), secondary.getRawButton(9));
    Cargo.actuate(secondary.getRawButton(2), secondary.getRawButton(5), secondary.getRawButton(1));
    Cargo.winch(secondary.getRawButton(7), secondary.getRawButton(9), secondary.getRawButton(11));
  }

  @Override
  public void testPeriodic() {
    Cargo.setup();
  }
}
