/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */



public class Robot extends TimedRobot {

  Joystick stick = new Joystick(0);
  private static double stickX;
  private static double stickY;

  private static final CANSparkMax left1 = new CANSparkMax(1, MotorType.kBrushless);
  private static final CANSparkMax left2 = new CANSparkMax(2, MotorType.kBrushless);
  private static final CANSparkMax left3 = new CANSparkMax(3, MotorType.kBrushless);
  private static final CANSparkMax right1 = new CANSparkMax(4, MotorType.kBrushless);
  private static final CANSparkMax right2 = new CANSparkMax(5, MotorType.kBrushless);
  private static final CANSparkMax right3 = new CANSparkMax(6, MotorType.kBrushless);

  private static final Joystick driver = new Joystick(0);
  private static final Joystick codriver = new Joystick(1);
  Ultrasonic ultra = new Ultrasonic(1,1); // creates the ultra object andassigns ultra to be an ultrasonic sensor which uses DigitalOutput 1 for 
        // the echo pulse and DigitalInput 1 for the trigger pulse
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */

  private static final DoubleSolenoid hatchPiston = new DoubleSolenoid(0, 7);

  static {

    // Sets the solenoid to a default start position. 
    // This will be retracted (value is false)
    // hatchPiston.set(Constants.HATCH_SOLENOID_START);
    hatchPiston.set(Value.kForward);
    
  }


  @Override
  public void robotInit() {
    ultra.setAutomaticMode(true); // turns on automatic mode
    

   

    // Mirror primary motor controllers on each side
    left2.follow(left1);
    left3.follow(left1);
    
    right2.follow(right1);
    right3.follow(right1); 
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
    double range = ultra.getRangeInches(); // reads the range on the ultrasonic sensor
  
    stickX = driver.getRawAxis(4);
    stickY = driver.getRawAxis(1);

    // Cubing values to create smoother function
    stickX = -Math.pow(stickX,3);
    stickY = Math.pow(stickY,3);
    stickX *= .75;

    // Joystick deadband
    if(Math.abs(stickX) < .000124){
        stickX = 0;
    }
    if(Math.abs(stickY) < .000124){
        stickY = 0;
    }

    //Getting the rangle from unltrasonic sensor and setting a rumble to controller
    //if(range <= 10 && hatchPiston.get() == Value.kReverse){ //TODO: Add check to make sure hatch manipulator is closed on hatch
    if(range <= 10 && hatchPiston.get() == Value.kForward /*&& Elevator.atIntakeLvl1Position()*/)
    {
      setHatchPiston(true);
      setRumble(1);
    }
    else
    {
      setRumble(0);
    }

    // Arcade drive
    runAt((stickY + stickX), -(stickY - stickX));
  }

  public static void runAt(double leftSpeed, double rightSpeed) {
        
    left1.set(leftSpeed);
    right1.set(rightSpeed);

}

  public static void setHatchPiston(boolean state){
          
    if(state){
        hatchPiston.set(Value.kForward);
    }
    else{
        hatchPiston.set(Value.kReverse);
    }
  }
  
  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  //method for setting controller viberations(rumbles)
  public void setRumble(double intensity){
    stick.setRumble(GenericHID.RumbleType.kLeftRumble, intensity); //Setting left controller viberation
    stick.setRumble(GenericHID.RumbleType.kRightRumble, intensity); //Setting right controller viberation
  }

}