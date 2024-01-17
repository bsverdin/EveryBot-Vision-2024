package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.Intake;
import static frc.robot.Constants.Ports.*;

public class Shoot extends SubsystemBase {

    private TalonFX aim;
    private TalonFX fire;
    private double currentAngle = 0;//AAAAAAAAAAAAAAAAA if only these were servos

    public Shoot() {
        aim = new TalonFX(AIM, MotorType.kBrushless);
        fire1 = new TalonFX(FIRE1, MotorType.kBrushless);
        fire2 = new TalonFX(FIRE2, MotorType.kBrushless);

        aim.setNeutralMode(NeutralMode.Brake);
        fire1.setNeutralMode(NeutralMode.Brake);
        fire2.setNeutralMode(NeutralMode.Brake);
    }

    public void aimTurret(double speed) {
        aim.set(speed);
    }

    public void fireTurret(double speed) {
        aim.set(0);
        fire1.set(speed);
        fire2.set(speed);
    }

    public void stop() {
        aim.set(ControlMode.Disabled, 0);
        fire1.set(ControlMode.Disabled, 0);
        fire2.set(ControlMode.Disabled, 0);
    }

}
