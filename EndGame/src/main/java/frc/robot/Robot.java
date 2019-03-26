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

import java.sql.Time;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Joystick;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
public class Robot extends TimedRobot {

  // Lift motor controllers
  // private static final VictorSPX  endgameLift = new VictorSPX(7);
  // private static final VictorSPX endgameLiftFollow1 = new VictorSPX(8);
  // private static final VictorSPX endgameLiftFollow2 = new VictorSPX(9);
  private static final Solenoid backpiston = new Solenoid(0);
  private static final Encoder endgamecounter = new Encoder(0, 1);
  private static final Compressor compressor = new Compressor();

  private static final CANSparkMax rightWheelOne = new CANSparkMax(1, MotorType.kBrushless);
  private static final CANSparkMax rightWheelTwo = new CANSparkMax(2, MotorType.kBrushless);
  private static final CANSparkMax rightWheelThree = new CANSparkMax(3, MotorType.kBrushless);
  private static final CANSparkMax leftWheelOne = new CANSparkMax(4, MotorType.kBrushless);
  private static final CANSparkMax leftWheelTwo = new CANSparkMax(5, MotorType.kBrushless);
  private static final CANSparkMax leftWheelThree = new CANSparkMax(6, MotorType.kBrushless);
  //endgame lifters
   private static final CANSparkMax endGameLift = new CANSparkMax(7, MotorType.kBrushless);
   private static final CANSparkMax endGameLift2 = new CANSparkMax(8, MotorType.kBrushless);
   private static final CANSparkMax endGameLift3 = new CANSparkMax(9, MotorType.kBrushless);

   private static final Timer endgametimer = new Timer();
   private static final Timer backuptimer = new Timer();
   private static final Timer pistonTimer = new Timer();
   
  // private static final CANSparkMax wheelFour = new CANSparkMax(4, MotorType.kBrushless);
  // private static final CANSparkMax wheelFive = new CANSparkMax(5, MotorType.kBrushless);
  // private static final CANSparkMax wheelSix = new CANSparkMax(6, MotorType.kBrushless);

  // //sets up a pdp
  // private static final PowerDistributionPanel Power = new PowerDistributionPanel(1);
  
  // //Sets up Volts Variable for later
  // private static double Volt = 0.0;
  
  // //sets up consents
  // private static final double VoltageLimit = 30.0;
  // private static final double WheelSpeed = 0.1;
  // private static final double LiftSpeed = 1.0;
  // private static final double joystickDeadband = 0.00124;
  // private static final double maxTicks = 9000;
  // private static final double minTicks = 0.0;
  
  
  // //sets up  a timer
  // private static Timer TimeCount = new Timer();
  // //sets up a seconds variable
  // public static double Seconds = 0.0;
  
  // //sets up a varable for the encoder ticks
  // public static double ticks = 0.0;
  
  //sets up a cubed stick value
  public static double leftYStickCubed;
  

    private enum EndgameStates{

      BACK_PISTION_DOWN,
      FOOT_AND_WHEELS,
      STOP_FOOT,
      STOP_WHEELS,
      BACKUP_WHEELS,
      STOP;
    }

    EndgameStates EndgameState;

  

  // Joystick deadband constant
  //private static final double ENDGAME_JOYSTICK_DEADBAND = 0.1;

  // Joystick object
  Joystick stick = new Joystick(0);

  @Override
  public void robotInit() {
    endgametimer.reset();
    backuptimer.reset();

    EndgameState = EndgameStates.BACK_PISTION_DOWN;
     // Mirror primary motor controllers
     
    //  endgameLiftFollow1.follow(endgameLift);
    //  endgameLiftFollow2.follow(endgameLift);
     endGameLift2.follow(endGameLift);
     endGameLift3.follow(endGameLift);
    //  wheelFour.follow(wheelOne);
    //  wheelFive.follow(wheelOne);
    //  wheelSix.follow(wheelOne);
    //  Volt = Power.getVoltage(); 
    //  TimeCount.reset();
      rightWheelTwo.follow(rightWheelOne);
      rightWheelThree.follow(rightWheelOne);
      leftWheelTwo.follow(leftWheelOne);
      leftWheelThree.follow(leftWheelOne);
    //  endgameLiftFollow1.follow(endgameLift);
    //  endgameLiftFollow2.follow(endgameLift);
     //Volt = Power.getVoltage(); 
     
     
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

    switch(EndgameState){

      case BACK_PISTION_DOWN:
          backpiston.set(true);
          pistonTimer.start();
          compressor.stop();
          if(pistonTimer.get() >= 1){
            EndgameState = EndgameStates.FOOT_AND_WHEELS;
            pistonTimer.stop();
          }
      break;
    
      case FOOT_AND_WHEELS:
          rightWheelOne.set(1.0);
          leftWheelOne.set(1.0);
          endGameLift.set(1.0);
          if(endgamecounter.getRaw() >= 3000){
            EndgameState = EndgameStates.STOP_FOOT;
          }
          break;
      
      case STOP_FOOT:
          endgametimer.start();
          endGameLift.set(0.0);
          if(endgametimer.get() >= 1.0){
            EndgameState = EndgameStates.STOP_WHEELS;
            endgametimer.stop();
          }
      break;

      case STOP_WHEELS:
          leftWheelOne.set(0.0);
          rightWheelOne.set(0.0);
          if(leftWheelOne.get() == 0 && rightWheelOne.get() == 0){
            EndgameState = EndgameStates.BACKUP_WHEELS;
          }
      break;

      case BACKUP_WHEELS:
          backuptimer.start();
          leftWheelOne.set(-0.2);
          rightWheelOne.set(-0.2);
          if(backuptimer.get() >= 2.0){
            backuptimer.stop();
            EndgameState = EndgameStates.STOP;
          }
      break;

      case STOP:
        leftWheelOne.set(0.0);
        rightWheelOne.set(0.0);
      break;    
    }




    // Cubes the left y joystick
    // -- for smoother motion 
    leftYStickCubed = Math.pow(stick.getRawAxis(1), 3);


    // //if(stick.getRawButton(6)){
    //   if(Math.abs(leftYStickCubed) > 0.00124){
    //     endGameLift.set(-leftYStickCubed);
    //     System.out.println("good");
    //   }
    //   else{
    //     endGameLift.set(0.0);
    //     System.out.println("Stop");
    //   }
    
    //   //}
    //   System.out.println(leftYStickCubed);
    
    // checks to see if RB is pressed
    // if(stick.getRawButton(6)){
    /*if(stick.getRawButton(6)){

    //   // if the joystick cubed is above the dead band and ticks are not too high
    //   if(leftYStickCubed < -joystickDeadband && ticks < maxTicks){
        
    //     // sets ticks to the encoder position
    //     ticks = endgameLift.getSelectedSensorPosition(0);
        
    //     // sets the endgame motor to the value of the stick
    //     endgameLift.set(ControlMode.PercentOutput, -leftYStickCubed);

    //     // sets the wheels to rotate 20% positive
    //     wheelOne.set(0.2);
    //   }
      
    //   // if the cubed joystick value is above dead band and ticks is not too low
    //   else if(leftYStickCubed > joystickDeadband && ticks >= minTicks){

    //     // set the endgame motro to the left stick
    //     endgameLift.set(ControlMode.PercentOutput, -leftYStickCubed);
        
    //     // sets ticks to the encoder position
    //     ticks = endgameLift.getSelectedSensorPosition(0);

    //     // sets wheels to rotate negtive 20%
    //     wheelOne.set(-0.2);
    //   }

    //   // if driver doesn't push a direction on stick then do this
    //   else{

    //     // set the motor to do nothing
    //     endgameLift.set(ControlMode.PercentOutput, 0);
        
    //     // sets wheels to do nothing
    //     wheelOne.set(0.0);
    //   }
    // }

        // sets wheels to do nothing
        wheelOne.set(0.0);
      }
    }*/
    // if(leftYStickCubed < -0.00124){
    //   endgameLift.set(ControlMode.PercentOutput, -leftYStickCubed);
    //   //wheelOne.set(0.2);
    // }
    // else if(leftYStickCubed > 0.00124){
    //   endgameLift.set(ControlMode.PercentOutput, -leftYStickCubed);
    //   //wheelOne.set(-0.2);
    // }
    // else{
    //   endgameLift.set(ControlMode.PercentOutput, 0);
    //   //wheelOne.set(0.0);
    // }
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