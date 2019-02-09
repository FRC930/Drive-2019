package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

import java.sql.Time;
import edu.wpi.first.wpilibj.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Joystick;

public class Robot extends TimedRobot {

  // Lift motor controllers
  
  private static final TalonSRX endgameLift = new TalonSRX(1);
  private static final TalonSRX endgameLiftFollow1 = new TalonSRX(2);
  private static final TalonSRX endgameLiftFollow2 = new TalonSRX(2);
  private static final CANSparkMax WheelOne = new CANSparkMax(1, MotorType.kBrushless);
  private static final CANSparkMax WheelTwo = new CANSparkMax(2, MotorType.kBrushless);
  private static final CANSparkMax WheelThree = new CANSparkMax(3, MotorType.kBrushless);
  private static final CANSparkMax WheelFour = new CANSparkMax(4, MotorType.kBrushless);
  private static final CANSparkMax WheelFive = new CANSparkMax(5, MotorType.kBrushless);
  private static final CANSparkMax WheelSix = new CANSparkMax(6, MotorType.kBrushless);

  private static final PowerDistributionPanel Power = new PowerDistributionPanel(1);
  private static double Volt = 0.0;
  private static final double VoltageLimit = 30.0;
  private static final double WheelSpeed = 0.1;
  private static final double LiftSpeed = 1.0;
  private static Timer TimeCount = new Timer();
  public static double Seconds = 0.0;
  
  
  // Joystick deadband constant
  //private static final double ENDGAME_JOYSTICK_DEADBAND = 0.1;

  // Joystick object
  Joystick stick = new Joystick(1);

  @Override
  public void robotInit() {

     // Mirror primary motor controllers
     
     endgameLiftFollow1.follow(endgameLift);
     endgameLiftFollow2.follow(endgameLift);
     WheelTwo.follow(WheelOne);
     WheelThree.follow(WheelOne);
     WheelFour.follow(WheelOne);
     WheelFive.follow(WheelOne);
     WheelSix.follow(WheelOne);
     Volt = Power.getVoltage(); 
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
    /*
    When the A button is pressed (which is 1) and voltage is less then or equal to 30 
    then set motors to run Otherwise stop running
    */
    if(stick.getRawButton(1) == true && Volt <= VoltageLimit){
      //start the timer
      TimeCount.start();
      //set the  time to seconds
      Seconds = TimeCount.get();
      //setting the  lift motor to LiftSpeed
      endgameLift.set(ControlMode.PercentOutput, LiftSpeed); 
      //when the timer is  greater than or equal to 2 then activate wheels
      if(TimeCount.get() >= 2.0){
        WheelOne.set(WheelSpeed);
      }
    }
    //when button two is pressed(B) and voltage is bigger than 20 
    else if(stick.getRawButton(2) == true && Volt <= VoltageLimit){
      //start the  timer agian
      TimeCount.start();
      //if the seconds is greater then seconds times 2
      if(TimeCount.get() < Seconds*2){
        //sets lift speed to the oppisite of liftspeed
        endgameLift.set(ControlMode.PercentOutput, -LiftSpeed);
        //sets wheels to 0
        WheelOne.set(0); 
      }
      // when the time is greater then seconds *2 then stop and reset timer
      else{
        TimeCount.stop();
        TimeCount.reset();
      }
    }
    //if the if and else if is not true then stop time and set motor to 0
    else{
      endgameLift.set(ControlMode.PercentOutput, 0);
      WheelOne.set(0);
      TimeCount.stop();
    }
    // Move end game lift up when right joystick is pushed up
    /*
    if (Math.abs(stick.getRawAxis(5)) >= ENDGAME_JOYSTICK_DEADBAND){  // getRawAxis(5) = right joystick
      endgameLift.set(-stick.getRawAxis(5));  // The lift's speed will be set at the right joystick's input value
    */
    } 

    // If the joystick isn't being touched, don't move
    /*
    else { 
      endgameLift.set(0.0);
    }*/
  

  @Override
  public void testPeriodic() {
  }
}