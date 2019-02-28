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
import jdk.jfr.Percentage;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import java.sql.Time;

import javax.lang.model.util.ElementScanner6;

import edu.wpi.first.wpilibj.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    // Sparks set up
    // private static final CANSparkMax left1 = new CANSparkMax(1, MotorType.kBrushless);
    // private static final CANSparkMax left2 = new CANSparkMax(2, MotorType.kBrushless);
    // private static final CANSparkMax left3 = new CANSparkMax(3, MotorType.kBrushless);
    // private static final CANSparkMax right1 = new CANSparkMax(4, MotorType.kBrushless);
    // private static final CANSparkMax right2 = new CANSparkMax(5, MotorType.kBrushless);
    // private static final CANSparkMax right3 = new CANSparkMax(6, MotorType.kBrushless);
    // private static double stickX;
    // private static double stickY;
    // end game
    /*private static final TalonSRX EndGameLift = new TalonSRX(5);
    private static final VictorSPX endgameLiftFollow1 = new VictorSPX(6);
    private static final VictorSPX endgameLiftFollow2 = new VictorSPX(7);*/
    
    // evelvator
    public static TalonSRX lift1 = new TalonSRX(1);
    public static TalonSRX lift2 = new TalonSRX(2);
    public static TalonSRX lift3 = new TalonSRX(3);
    public static double stickElev;

    //sets up solenoid
     //private static final Solenoid hatchPiston = new Solenoid(7);//Solenoid(0);
    // private final static Solenoid armPiston = new Solenoid(3);
    
    // //sets up driver and coDriver
     private static final Joystick stick = new Joystick(0);
    private static final Joystick stick2 = new Joystick(1);
    
    //Elevator Booleans
    // private static boolean RtPressed = false;
    // private static boolean LtPressed = false;
    // private static boolean Rcheck = false;
    // private static boolean Lcheck = false;

    //Vision traking Varabils
    // NetworkTable limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
    // private final int CAMERA1_WIDTH = 160;
    // private final int CAMERA1_HEIGHT = 120;
    // private final int CAMERA1_FPS = 30;
    // private final int CAMERA2_WIDTH = 160;
    // private final int CAMERA2_HEIGHT = 120;
    // private final int CAMERA2_FPS = 30;
    // NetworkTableEntry tx = limelightTable.getEntry("tx");
    // private final double DEFAULT_LIMELIGHT_VALUE = 0.1234;
    // private final double DEFAULT_HORIZONTAL_SPEED = -0.01;
    // private final double HORIZONTAL_ANGLE_THRESHOLD = 0.3;
    private final double JOYSTICK_DEADBAND = 0.000124;

    //Elevator motion magic
    //creates variables for the FPID and Velocity and Acceleration
    private static double F = 1.705;//Pract: 1.4614;
    private static double P = 6;//Pract&Comp: 18.0;
    private static double I = 0;//Pract: 0.07;//Comp: 0;
    private static double D = 0;//Pract: 51;//Comp: 24;
    private static int Velocity = 600;//650;
    private static int Acceleration = 500;//Comp:300;//Pract:600//Pract:1200;
    private static int pidSlotNumber = 0;
    private static int kTimeoutMs = 10;
    public static double TargetPosition = 0.0;
    public static boolean StartMotionMagicToggle = false;
    public static boolean MotionMagicToggle = false;

    //sets up booleans for intake 
    // private static boolean LtBeakToggle = false;
    // private static boolean BeakToggle = false;
    // // typecast the NetworkTable data into a double
    // getDouble() will return a default value if no value is found
    // double horizontalAngle = tx.getDouble(DEFAULT_LIMELIGHT_VALUE);
  
    // // left and right speeds for the drivetrain 
    // double leftMovement = 0.0;
    // double rightMovement = 0.0;
  
    // // speed of the robot in the forward and backward directions
    // double distanceSpeed = 0;
  
    // // speed of the robot in the left and right directions
    // double horizontalSpeed = 0;
   
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
        
        // limelightTable.getEntry("stream").setNumber(2);
        // //begin sending data to the shuffleboard
        // Shuffleboard.startRecording();

        // //initialize USB camera objects and begin sending their video streams to shuffleboard
        
        // startCapture();
        lift2.follow(lift1);
        lift3.follow(lift1);

        // left2.follow(left1);
        // left3.follow(left1);
        
        // right2.follow(right1);
        // right3.follow(right2);
        
        //endgameLiftFollow1.follow(EndGameLift);
        //endgameLiftFollow2.follow(EndGameLift);
        
        
        // elevator motion magic
        lift1.setSelectedSensorPosition(0, 0, kTimeoutMs);
        lift1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, kTimeoutMs);
    
    //sets limit of where it should go
    lift1.configForwardSoftLimitThreshold(-5500, kTimeoutMs);
    lift1.configReverseSoftLimitThreshold(-50, kTimeoutMs);
    
    //sets up the  fpid for pid functions
    lift1.selectProfileSlot(pidSlotNumber, 0);
    lift1.config_kF(pidSlotNumber, F, kTimeoutMs);
		lift1.config_kP(pidSlotNumber, P, kTimeoutMs);
    lift1.config_kI(pidSlotNumber, I, kTimeoutMs);
    lift1.config_kD(pidSlotNumber, D, kTimeoutMs);
    
    //CruiseVelocity is the no exceleration part of trapizoid / top Acceleration is getting to top
    lift1.configMotionCruiseVelocity(Velocity, kTimeoutMs);
    lift1.configMotionAcceleration(Acceleration, kTimeoutMs);
    
    lift1.setSensorPhase(false);//true);

    //Nominal out put is lowest limit and peak is highest    
    lift1.configNominalOutputReverse(0, kTimeoutMs);
    lift1.configNominalOutputForward(0, kTimeoutMs);
		lift1.configPeakOutputForward(1, kTimeoutMs);
    lift1.configPeakOutputReverse(-1, kTimeoutMs);
    
    //sets the sensor to the bootom/0
    //lift1.setSelectedSensorPosition(0, 0, kTimeoutMs);
    
    lift1.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, kTimeoutMs);
		lift1.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, kTimeoutMs);
    
        // hatchPiston.set(Out);
        // armPiston.set(Out2);
        
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
  public void teleopPeriodic(){
  // if(stick2.getRawAxis(3) >= 0.5 && RtPressed == false){
  //   RtPressed = true;
  // }

  // if(RtPressed == true && stick.getRawAxis(3) <= 0.5  ){
  //   RtPressed = false;
  //   Lcheck = false;
  //   Rcheck = !Rcheck;
  // }

  // //Button LT test to see if pressed and released 
  // if(stick2.getRawAxis(2) >= 0.5 && LtPressed == false){
  //   LtPressed = true;
  // }

  // if(LtPressed == true && stick.getRawAxis(2) <= 0.5  ){
  //   LtPressed = false;
  //   Rcheck = false;
  //   Lcheck = !Lcheck;
  // }
    
  //sets up a button press on the driver controller for intake beak
    // if(stick.getRawAxis(2) > 0.5 && LtBeakToggle == false){
    //   LtBeakToggle = true;
    // }
    // else if(stick.getRawAxis(2) < 0.5 && LtBeakToggle == true){
    //   LtBeakToggle = false;
    //   BeakToggle = !BeakToggle;
    // }
    // // On coDeriver Arm pistion is pushed out if lt is pushed
    // if(stick2.getRawAxis(2) > 0.5)
    //   armPiston.set(false); 
    // else{
    //   armPiston.set(true);
    // }
    // hatchPiston.set(BeakToggle);
    // //visoin and drive

    // // reset the horizontal and distance speeds every time the code runs
    // // this will prevent previous leftover values from moving the motors
    // horizontalSpeed = 0;
    // distanceSpeed = 0;

    // // checks if the A button is currently being pressed, returns a boolean
	  // if(stick.getRawButton(1)) {

    //   /* if the A button is currently pressed, turn on the limelight's LEDs,
    //      and if the A button is not pressed, turn off the LEDs
    //      getEntry("ledMode") is used to get the LedMode property from the NetworkTable
    //      setNumber() is used to set the state of the LedMode property (the integer 1 sets the LEDs off, 3 sets the LEDs on)
         
    //      ?: is a terenary operator in Java, it is basically a one-line if-else statement
    //      To use it, give it a boolean variable, the do ?. Then after that, you can use it as
    //      an if-else statement. 
    //      stick.getRawButton(A_BUTTON) ? 1 : 0
    //      In this example, we use stick.getRawButton(A_BUTTON) as our boolean variable
    //      if stick.getRawButton(A_BUTTON) is down (value is true), then it will return 1
    //      if stick.getRawButton(A_BUTTON) is up (value is false), then it will return 0
    //      With the 0 and 1 then, we use it to do some math. 
    //      When the stick.getRawButton(A_BUTTON) is down (true), it returns 1
    //      1 + (2 * 1) = 3. 3 is sent into .setNumber(), which turns the LEDs on
    //   */
    //   limelightTable.getEntry("ledMode").setNumber(1 + (2 * (stick.getRawButton(1) ? 1 : 0)));

    //   // rotate the robot towards the target if horizontal angle is greater than the horizontal angle threshold on either side of the target
    //   rotate(horizontalAngle);
    // }

    // // Cubing values to create smoother function
    // stickX = -Math.pow(stick.getRawAxis(4), 3);
    // stickY = Math.pow(stick.getRawAxis(1), 3);

    // // manual drive controls
    // if(Math.abs(stickX) > JOYSTICK_DEADBAND) {
    //   distanceSpeed = stickX;
    // }
    // if(Math.abs(stickY) > JOYSTICK_DEADBAND) {
    //   horizontalSpeed = stickY;
    // }

    // // left and right speeds of the drivetrain
    // leftMovement = distanceSpeed + horizontalSpeed;
    // rightMovement = distanceSpeed - horizontalSpeed;

    // runAt(leftMovement, rightMovement);

    

    // stickX = stick.getRawAxis(4);
    // stickY = stick.getRawAxis(1);
    // stickX = -Math.pow(stickX,3);
    // stickY = Math.pow(stickY,3);

    // // Joystick deadband
    // if(Math.abs(stickX) < 0.005){
    //     stickX = 0;
    // }
    // if(Math.abs(stickY) < 0.005){
    //     stickY = 0;
    // }

    // // Arcade drive
        
    // runAt((stickY+stickX), -(stickY-stickX));
    
  if(stick.getRawButton(1)){
      if(Math.abs(stick.getRawAxis(1))>0.05){
        //EndGameLift.set(ControlMode.PercentOutput, -stick.getRawAxis(1));
      }
      else{
        //EndGameLift.set(ControlMode.PercentOutput,0);
      }
  }
  else{
    //EndGameLift.set(ControlMode.PercentOutput,0);
  }

    //elevator
    /*
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

    //hatchPiston.set(true);
    //Elevator motion magic
    stickElev = Math.pow(stick2.getRawAxis(1), 3);
    double leftYstick = stick2.getRawAxis(1);
    
    if(stick2.getRawButton(8) && StartMotionMagicToggle == false){
      StartMotionMagicToggle = true;
    }
    else if(!stick2.getRawButton(8) && StartMotionMagicToggle == true){
     StartMotionMagicToggle = false;
     MotionMagicToggle = !MotionMagicToggle;
    }
    MotionMagicToggle = true;
    if(MotionMagicToggle  ){
      //if button1(A) is pressed then go to the position 500 using motion magic
      if(stick2.getRawButton(1)){
        TargetPosition = 500;//1000;//200;
        lift1.set(ControlMode.MotionMagic, TargetPosition);
      }
      //if button4(Y) is pressed then go to the highest spot at 4500 using motion magic
      else if(stick2.getRawButton(4)){
        TargetPosition = 4800;
        lift1.set(ControlMode.MotionMagic, TargetPosition);
      }
      //If button2(B) is pressed then go to the middle spot using motion magic
      else if(stick2.getRawButton(2)){
        TargetPosition = 2800;
        lift1.set(ControlMode.MotionMagic, TargetPosition);
      }
      // else if(Math.abs(leftYstick) > 0.05){
      //   TargetPosition = TargetPosition + leftYstick * -50;
      //   lift1.set(ControlMode.MotionMagic, TargetPosition);
      // }
      else if (Math.abs(stickElev) > 0.000124) {
        lift1.set(ControlMode.PercentOutput, -stickElev);
      }
      else {
        lift1.set(ControlMode.PercentOutput, 0);
      }
  }
    else if (Math.abs(stickElev) > 0.000124) {
      lift1.set(ControlMode.PercentOutput, -stickElev);
    }
    else {
      lift1.set(ControlMode.PercentOutput, 0);
    }
    SmartDashboard.putNumber("EncoderPosition", lift1.getSelectedSensorPosition());
    SmartDashboard.putNumber("CalcError", lift1.getSelectedSensorPosition() - TargetPosition);
    SmartDashboard.putNumber("Joystick", -leftYstick);
    SmartDashboard.putNumber("TargetPosition", TargetPosition);
    SmartDashboard.putNumber("TalonError", lift1.getClosedLoopError());
    SmartDashboard.putBoolean("Elevator Toggle Manual", MotionMagicToggle);
  }

  
      
  

  /**
   * This function is called periodically during test mode.
   */
  
  @Override
  public void testPeriodic() {
  }
//   public static void runAt(double leftSpeed, double rightSpeed) {
        
//         left1.set(leftSpeed);
//         right1.set(rightSpeed);

//   }

//   private void rotate(double xAngle) {
//     if(Math.abs(xAngle) > HORIZONTAL_ANGLE_THRESHOLD) 
//       horizontalSpeed = DEFAULT_HORIZONTAL_SPEED * (-xAngle / 1.5);
//  }

//     private void startCapture() {
//       // creates a thread which runs concurrently with the program
//       new Thread(() -> {
//         // Instantiate the USB cameras and begin capturing their video streams
//         UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(0);
//         UsbCamera camera2 = CameraServer.getInstance().startAutomaticCapture(1);
  
//         // set the cameras' reolutions and FPS
//         camera.setResolution(CAMERA1_WIDTH, CAMERA1_HEIGHT);
//         camera.setFPS(CAMERA1_FPS);
//         camera2.setResolution(CAMERA2_WIDTH, CAMERA2_HEIGHT);
//         camera2.setFPS(CAMERA2_FPS);
//       }).start();
//     }
}
