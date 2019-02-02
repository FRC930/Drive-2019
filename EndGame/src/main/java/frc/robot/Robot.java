package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Joystick;

public class Robot extends TimedRobot {

  //climber motor controllers
  private static final CANSparkMax left1 = new CANSparkMax(1, MotorType.kBrushless);
  private static final CANSparkMax left2 = new CANSparkMax(2, MotorType.kBrushless);
  private static final CANSparkMax left3 = new CANSparkMax(3, MotorType.kBrushless);

  //Joystick object
  Joystick stick = new Joystick(1);

  @Override
  public void robotInit() {
     // Mirror primary motor controllers
     left2.follow(left1);
     left3.follow(left1);
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
    // Move end game lift up when joystick is pushed up.
    if (Math.abs(stick.getRawAxis(5)) >= 0.1) {
      left1.set(-stick.getRawAxis(5));
    } 
    else {
      left1.set(0.0);
    }
  }

  @Override
  public void testPeriodic() {
  }
}