/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private static Timer Time;
  private static final I2C Responce = new I2C(I2C.Port.kOnboard, 2);
  private static Joystick Driver;
  private static Joystick CoDriver;
  private int pick = 0;
  private boolean toggle = true;
  private boolean mode = false;
  
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    Driver = new Joystick(1);
    CoDriver = new Joystick(2);
    Time = new Timer();
  
  }
  static private enum LEDStatus{
    PATTERN_OFF(0),
    PATTERN_SEND(1),
    ALT_BLUE_YELLOW(3),
    BLUE(4),
    GREEN(5),
    YELLOW(6),
    RED(7),
    SCROLLING_RAINBOW(8),
    FWD_SCROLL_BLUE_YELLOW(9),
    RVS_SCROLL_BLUE_YELLOW(10);

    //this holds arduino state
    private int LEDStatus_Value;
    private LEDStatus(int value){
     this.LEDStatus_Value = value;
    }
    public int getLEDStatus(){
      return this.LEDStatus_Value;
    }
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
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    pick = LEDStatus.PATTERN_OFF.getLEDStatus();
    if (toggle && Driver.getRawButton(2)) {  // Only execute once per Button push
      toggle = false;  // Prevents this section of code from being called again until the Button is released and re-pressed
      if (mode) {  // Decide which way to set the motor this time through (or use this as a motor value instead)
        mode = false;
      } 
      else{
        mode = true;
      }
      }
       if(Driver.getRawButton(2) == false) { 
        toggle = true; // Button has been released, so this allows a re-press to activate the code above.
      }
      if(mode){
       if(Driver.getRawButton(1)){
      pick = LEDStatus.PATTERN_SEND.getLEDStatus();
       }
       else{
         pick = LEDStatus.PATTERN_OFF.getLEDStatus();
       }
      }
      if(mode == false){
      if(Driver.getRawAxis(1)<-0.25 && Driver.getRawAxis(5)<-0.25){
      pick = LEDStatus.FWD_SCROLL_BLUE_YELLOW.getLEDStatus();
      }
      if(Driver.getRawAxis(1)>0.25 && Driver.getRawAxis(5)>0.25){
      pick = LEDStatus.RVS_SCROLL_BLUE_YELLOW.getLEDStatus();
      }
      if(){
      pick = LEDStatus..getLEDStatus();
      }
      if(){
      pick = LEDStatus..getLEDStatus();
      }
      if(){
      pick = LEDStatus..getLEDStatus();
      }
      if(){
      pick = LEDStatus..getLEDStatus();
      }
      if(){
      pick = LEDStatus..getLEDStatus();
      }
      if(){
      pick = LEDStatus..getLEDStatus();
      }
      }
      Responce.write(2, pick);
}

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
