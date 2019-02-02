package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Joystick;

public class Robot extends TimedRobot {

  // Lift motor controllers
  private static final CANSparkMax endgameLift = new CANSparkMax(1, MotorType.kBrushless);
  private static final CANSparkMax endgameLiftFollow1 = new CANSparkMax(2, MotorType.kBrushless);
  private static final CANSparkMax endgameLiftFollow2 = new CANSparkMax(3, MotorType.kBrushless);

  // Joystick deadband constant
  private static final double ENDGAME_JOYSTICK_DEADBAND = 0.1;

  // Joystick object
  Joystick stick = new Joystick(1);

  @Override
  public void robotInit() {

     // Mirror primary motor controllers
     endgameLiftFollow1.follow(endgameLift);
     endgameLiftFollow2.follow(endgameLift);
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

    // Move end game lift up when right joystick is pushed up
    if (Math.abs(stick.getRawAxis(5)) >= ENDGAME_JOYSTICK_DEADBAND){  // getRawAxis(5) = right joystick
      endgameLift.set(-stick.getRawAxis(5));  // The lift's speed will be set at the right joystick's input value
    } 

    // If the joystick isn't being touched, don't move
    else { 
      endgameLift.set(0.0);
    }
  }

  @Override
  public void testPeriodic() {
  }
}