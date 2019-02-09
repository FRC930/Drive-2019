/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

import java.sql.Time;
import edu.wpi.first.wpilibj.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Joystick;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    private static final CANSparkMax left1 = new CANSparkMax(1, MotorType.kBrushless);
    private static final CANSparkMax left2 = new CANSparkMax(2, MotorType.kBrushless);
    private static final CANSparkMax left3 = new CANSparkMax(3, MotorType.kBrushless);
    private static final CANSparkMax right1 = new CANSparkMax(4, MotorType.kBrushless);
    private static final CANSparkMax right2 = new CANSparkMax(5, MotorType.kBrushless);
    private static final CANSparkMax right3 = new CANSparkMax(6, MotorType.kBrushless);
    private static final Joystick stick = new Joystick(0);
    private static double stickX;
    private static double stickY;
    /* end game
    private static final TalonSRX EndGameLift = new TalonSRX(1);
    private static final TalonSRX endgameLiftFollow1 = new TalonSRX(2);
    private static final TalonSRX endgameLiftFollow2 = new TalonSRX(3);
    */
    /* evelvator
    public static TalonSRX lift1 = new TalonSRX(1);
    public static TalonSRX lift2 = new TalonSRX(2);
    */
   
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    
      left2.follow(left1);
        left3.follow(left1);
        
        right2.follow(right1);
        right3.follow(right2);
        /*
        endgameLiftFollow1.follow(EndGameLift);
        endgameLiftFollow2.follow(EndGameLift);
        */
        /* elevator
        lift2.follow(lift1);
        */
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {

  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    stickX = stick.getRawAxis(4);
    stickY = stick.getRawAxis(1);
    stickX = -Math.pow(stickX,3);
        stickY = Math.pow(stickY,3);

        // Joystick deadband
        if(Math.abs(stickX) < 0.000124){
            stickX = 0;
        }
        if(Math.abs(stickY) < 0.000124){
            stickY = 0;
        }

        // Arcade drive
        
        runAt((stickY+stickX), -(stickY-stickX));
    /* end game
    if(stick.getRawButton(1) == true){
      EndGameLift.set(ControlMode.PercentOutput, 1);
    }
      else if(stick.getRawButton(2) == true){
         EndGameLift.set(ControlMode.PercentOutput, -1);
      }
      else{
         EndGameLift.set(ControlMode.PercentOutput, 0);
      }
    */

    /*elevator
    if(stick.getRawButton(3) == true){
      lift1.set(ControlMode.PercentOutput, 1);
    }

    else if(stick.getRawButton(4) == true){
        lift1.set(ControlMode.PercentOutput, -1);
    }
    
    else{
      lift1.set(ControlMode.PercentOutput, 0);
    }
    */
      
  

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
  public static void runAt(double leftSpeed, double rightSpeed) {
        
        left1.set(leftSpeed);
        right1.set(rightSpeed);

    }
}
