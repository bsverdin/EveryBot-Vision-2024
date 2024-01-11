package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.TalonFX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.Intake;
import static frc.robot.Constants.Ports.*;

public class Shoot extends SubsystemBase {

    private TalonFX aim;
    private TalonFX fire;
    private double currentAngle = 0;//AAAAAAAAAAAAAAAAA if only these were servos

    public Shoot() {
        aim = new TalonFX(AIM, MotorType.kBrushless);
        fire = new TalonFX(FIRE, MotorType.kBrushless);

        aim.setNeutralMode(NeutralMode.Brake);
        fire.setNeutralMode(NeutralMode.Brake);
    }

    public void setTurret(double speed) {
        aim.set(speed);
    }

    public void fireTurret(double speed) {
        fire.set(speed)
    }

    public void stop() {
        turret.set(0);
        turret.set(ControlMode.Disabled, 0);
    }

}
