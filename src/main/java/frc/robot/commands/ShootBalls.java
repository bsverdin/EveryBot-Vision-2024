// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;
import frc.robot.Constants.ShootBalls.*;

public class ShootBalls extends Command {

    private Intake intake;
    private Shooter aim;
    private Shooter fire;

    public ShootBalls(Intake intake) {
        this.intake = intake;
        addRequirements(intake);
    }
    public ShootBalls(Shooter aim) {
        this.aim = aim;
        addRequirements(aim);
    }
    public ShootBalls(Shooter fire) {
        this.fire = fire;
        addRequirements(fire);
    }

    @Override
    public void initialize() {
        Commands.sequence(
            // Move arm up
            if(DriverStation.getAlliance() == Alliance.Red){
                if ((tid == 5 || tid == 4 || tid == 3) && intakeNote == true){
                    if ((tid == 5 && currentAngle < ampAngle) || ((tid == 4 || tid == 3) && currentAngle < speakerAngle)){ //need to create a variable for the current angle
                        Commands.deadline(
                            Commands.waitSeconds(0.5),
                            Commands.run(() -> aim.aimTurret(0.5), aim)
                        ),
                    }
                    else if ((tid == 5 && currentAngle > ampAngle) || ((tid == 4 || tid == 3) && currentAngle > speakerAngle)){
                        Commands.deadline(
                            Commands.waitSeconds(0.5),
                            Commands.run(() -> aim.aimTurret(0.5), aim)
                        ),
                    }
                }
                else if ((tid == 11 || tid == 12 || tid == 13) && intakeNote == true && (timer.getFPGATimestamp() - 150) <= 20){
                    if (currentAngle < trapAngle){ //need to create a variable for the current angle
                        Commands.deadline(
                            Commands.waitSeconds(0.5),
                            Commands.run(() -> aim.aimTurret(0.5), aim)
                        ),
                    }
                    else if (currentAngle > trapAngle){
                        Commands.deadline(
                            Commands.waitSeconds(0.5),
                            Commands.run(() -> aim.aimTurret(0.5), aim)
                        ),
                    }
                }
            }
            else if (DriverStation.getAlliance() == Alliance.Blue){
                if ((tid == 6 || tid == 7 || tid == 8) && intakeNote == true){
                    if ((tid == 6 && currentAngle < ampAngle) || ((tid == 7 || tid == 8) && currentAngle < speakerAngle)){ //need to create a variable for the current angle
                        Commands.deadline(
                            Commands.waitSeconds(0.5),
                            Commands.run(() -> aim.aimTurret(0.5), aim)
                        ),
                    }
                    else if ((tid == 5 && currentAngle > ampAngle) || ((tid == 7 || tid == 8) && currentAngle > speakerAngle)){
                        Commands.deadline(
                            Commands.waitSeconds(0.5),
                            Commands.run(() -> aim.aimTurret(0.5), aim)
                        ),
                    }
                }
                else if ((tid == 14 || tid == 15 || tid == 16) && intakeNote == true && (timer.getFPGATimestamp() - 150) <= 20){//need to make match time variable
                    if (currentAngle < trapAngle){ //need to create a variable for the current angle
                        Commands.deadline(
                            Commands.waitSeconds(0.5),
                            Commands.run(() -> aim.aimTurret(0.5), aim)
                        ),
                    }
                    else if (currentAngle > trapAngle){
                        Commands.deadline(
                            Commands.waitSeconds(0.5),
                            Commands.run(() -> aim.aimTurret(0.5), aim)
                        ),
                    }
                }
            }

            // Stop arm

            if (tid == 5 || tid == 6) {
                Commands.waitSeconds(abs(ampAngle - currentAngle) * degreePerSecond / speed);
            }
            else if (tid == 7 || tid == 8 || tid == 3 || tid == 4) {
                Commands.waitSeconds(abs(speakerAngle - currentAngle) * degreePerSecond /speed);
            }
            else if (tid == 11 || tid == 12 || tid == 13 || tid == 14 || tid == 15 || tid ==16) {
                Commands.waitSeconds(abs(trapAngle - currentAngle) * degreePerSecond /speed);
            }
            Commands.runOnce(() -> aim.aimTurret(0), aim),

            // Wait a bit
            Commands.waitSeconds(0.1),

            // Shoot balls
            Commands.deadline(
                Commands.waitSeconds(1),
                Commands.run(() -> fire.fireTurret(0.5), fire)
            ),

            // Stop intake
            Commands.runOnce(() -> fire.fireTurret(0), fire),
            intakeNote = false;
        ).handleInterrupt(() -> {
            shoot.setTurret(0);
            intake.setIntake(0);
        }).schedule();
    }
}