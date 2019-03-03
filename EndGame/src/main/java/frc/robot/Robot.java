package frc.robot;
/* The Objective

  This code is to control the end game of the deep space compition. 
  The endgame has a lift under the drive train that lifts the robot to top platform.
  This code does this by controling three victors that contorl the end game lift motors,
  and use a single encoder that tracks how many motor rotation have passed while robot is lifted.
  This is tracked through the number of ticks recorded by the encoder, and we use the wheels to help lift
  the robot to the top platform.
 
*/
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Joystick;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
public class Robot extends TimedRobot {

  // Lift motor controllers
  private static final TalonSRX endgameLift = new TalonSRX(5);
  private static final VictorSPX endgameLiftFollow1 = new VictorSPX(6);
  private static final VictorSPX endgameLiftFollow2 = new VictorSPX(7);
  
  //wheels
  private static final CANSparkMax wheelOne = new CANSparkMax(1, MotorType.kBrushless);
  private static final CANSparkMax wheelTwo = new CANSparkMax(2, MotorType.kBrushless);
  private static final CANSparkMax wheelThree = new CANSparkMax(3, MotorType.kBrushless);
  private static final CANSparkMax wheelFour = new CANSparkMax(4, MotorType.kBrushless);
  private static final CANSparkMax wheelFive = new CANSparkMax(5, MotorType.kBrushless);
  private static final CANSparkMax wheelSix = new CANSparkMax(6, MotorType.kBrushless);

  //sets up a pdp
  //private static final PowerDistributionPanel Power = new PowerDistributionPanel(1);
  
  //Sets up Volts Variable for later
  private static double Volt = 0.0;
  
  //sets up consents
  private static final double VoltageLimit = 30.0;
  private static final double WheelSpeed = 0.1;
  private static final double LiftSpeed = 1.0;
  private static final double joystickDeadband = 0.00124;
  private static final double maxTicks = 9000;
  private static final double minTicks = 0.0;
  
  
  //sets up  a timer
  private static Timer TimeCount = new Timer();
  //sets up a seconds variable
  public static double Seconds = 0.0;
  
  //sets up a varable for the encoder ticks
  public static double ticks = 0.0;
  
  //sets up a cubed stick value
  public static double leftYStickCubed;

  // Joystick deadband constant
  //private static final double ENDGAME_JOYSTICK_DEADBAND = 0.1;

  // Joystick object
  Joystick stick = new Joystick(0);

  @Override
  public void robotInit() {

     // Mirror primary motor controllers
     
     endgameLiftFollow1.follow(endgameLift);
     endgameLiftFollow2.follow(endgameLift);
     wheelTwo.follow(wheelOne);
     wheelThree.follow(wheelOne);
     wheelFour.follow(wheelOne);
     wheelFive.follow(wheelOne);
     wheelSix.follow(wheelOne);
     //Volt = Power.getVoltage(); 
     TimeCount.reset();
     
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

  @Override
  public void teleopPeriodic() {
    // Cubes the left y joystick
    // -- for smoother motion 
    leftYStickCubed = Math.pow(stick.getRawAxis(1), 3);

    // checks to see if RB is pressed
    /*if(stick.getRawButton(6)){

      // if the joystick cubed is above the dead band and ticks are not too high
      if(leftYStickCubed < -joystickDeadband && ticks < maxTicks){
        
        // sets ticks to the encoder position
        ticks = endgameLift.getSelectedSensorPosition(0);
        
        // sets the endgame motor to the value of the stick
        endgameLift.set(ControlMode.PercentOutput, -leftYStickCubed);

        // sets the wheels to rotate 20% positive
        wheelOne.set(0.2);
      }
      
      // if the cubed joystick value is above dead band and ticks is not too low
      else if(leftYStickCubed > joystickDeadband && ticks >= minTicks){

        // set the endgame motro to the left stick
        endgameLift.set(ControlMode.PercentOutput, -leftYStickCubed);
        
        // sets ticks to the encoder position
        ticks = endgameLift.getSelectedSensorPosition(0);

        // sets wheels to rotate negtive 20%
        wheelOne.set(-0.2);
      }

      // if driver doesn't push a direction on stick then do this
      else{

        // set the motor to do nothing
        endgameLift.set(ControlMode.PercentOutput, 0);
        
        // sets wheels to do nothing
        wheelOne.set(0.0);
      }
    }*/
    if(leftYStickCubed < -0.00124){
      endgameLift.set(ControlMode.PercentOutput, -leftYStickCubed);
      //wheelOne.set(0.2);
    }
    else if(leftYStickCubed > 0.00124){
      endgameLift.set(ControlMode.PercentOutput, -leftYStickCubed);
      //wheelOne.set(-0.2);
    }
    else{
      endgameLift.set(ControlMode.PercentOutput, 0);
      //wheelOne.set(0.0);
    }
    // /*
    // When the A button is pressed (which is 1) and voltage is less then or equal to 30 
    // then set motors to run Otherwise stop running
    // */
    // if(stick.getRawButton(1) == true && Volt <= VoltageLimit){
      
    //   //start the timer
    //   TimeCount.start();
      
    //   //set the  time to seconds
    //   Seconds = TimeCount.get();
      
    //   //setting the  lift motor to LiftSpeed
    //   endgameLift.set(ControlMode.PercentOutput, LiftSpeed); 
      
    //   //when the timer is  greater than or equal to 2 then activate wheels
    //   if(TimeCount.get() >= 2.0){
    //     WheelOne.set(WheelSpeed);
    //   }
    // }
    
    // //when button two is pressed(B) and voltage is bigger than 20 
    // else if(stick.getRawButton(2) == true && Volt <= VoltageLimit){
      
    //   //start the  timer agian
    //   TimeCount.start();
      
    //   //if the seconds is greater then seconds times 2
    //   if(TimeCount.get() < Seconds*2){
        
    //     //sets lift speed to the oppisite of liftspeed
    //     endgameLift.set(ControlMode.PercentOutput, -LiftSpeed);
        
    //     //sets wheels to 0
    //     WheelOne.set(0); 
    //   }
      
    //   // when the time is greater then seconds *2 then stop and reset timer
    //   else{
    //     TimeCount.stop();
    //     TimeCount.reset();
    //   }
    // }
    
    // //if the if and else if is not true then stop time and set motor to 0
    // else{
    //   endgameLift.set(ControlMode.PercentOutput, 0);
    //   WheelOne.set(0);
    //   TimeCount.stop();
    // }
    // Move end game lift up when right joystick is pushed up
    /*
    if (Math.abs(stick.getRawAxis(5)) >= ENDGAME_JOYSTICK_DEADBAND){  // getRawAxis(5) = right joystick
      endgameLift.set(-stick.getRawAxis(5));  // The lift's speed will be set at the right joystick's input value
    }*/

    // If the joystick isn't being touched, don't move
    /*
    else { 
      endgameLift.set(0.0);
    }*/
  }

  @Override
  public void testPeriodic() {
  }
}