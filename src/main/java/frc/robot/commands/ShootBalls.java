// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;
import frc.robot.Constants.ShootBalls.*;
import com.ctre.phoenix.sensors.Cancoder;

public class ShootBalls extends CommandBase {

    private Intake intake;
    private Shooter aim;
    private Shooter fire;
    CanCoder _coder = new CanCoder(1);
    int degrees = _coder.getPosition();

    public ShootBalls(Intake intake, Shooter shooter) {
        this.intake = intake;
        this.shooter = shooter;
        addRequirements(intake, shooter);
    }

    @Override
    public void initialize() {
        Commands.sequence(
            // Move arm up
            if(DriverStation.getAlliance() == Alliance.Red){
                if ((tid == 5 || tid == 4 || tid == 3) && intakeNote == true){
                    if (tid == 5 && degree < ampAngle){ 
                        Commands.deadline(
                            Commands.waitSeconds(0.5),
                            while (degree < ampAngle){
                            Commands.run(() -> aim.aimTurret(0.5), aim);
                            }
                        ),
                    }
                    else if (tid == 5 && degree > ampAngle){
                        Commands.deadline(
                            Commands.waitSeconds(0.5),
                            Commands.run(() -> aim.aimTurret(-0.5), aim)
                        ),
                    }
                }
                else if ((tid == 4 || tid == 3) && degree < speakerAngle){
                    Commands.deadline(
                        Commands.waitSeconds(0.5),
                        while (degree < speakerAngle){
                        Commands.run(() -> aim.aimTurret(0.5), aim);
                        }
                    ),
                }
                else if ((tid == 4 || tid == 3) && degree > speakerAngle){
                    Commands.deadline(
                        Commands.waitSeconds(0.5),
                        while (degree > speakerAngle){
                        Commands.run(() -> aim.aimTurret(-0.5), aim);
                        }
                    ),
                }

                else if ((tid == 11 || tid == 12 || tid == 13) && intakeNote == true && (timer.getFPGATimestamp() - 150) <= 20){
                    if (currentAngle < trapAngle){ //need to create a variable for the current angle
                        Commands.deadline(
                            Commands.waitSeconds(0.5),
                            while (degree < trapAngle){
                            Commands.run(() -> aim.aimTurret(0.5), aim)
                            }
                        ),
                    }
                    else if (currentAngle > trapAngle){
                        Commands.deadline(
                            Commands.waitSeconds(0.5),
                            while (degree > trapAngle){
                            Commands.run(() -> aim.aimTurret(-0.5), aim)
                            }
                        ),
                    }
                }
            }
            else if (DriverStation.getAlliance() == Alliance.Blue){
                if ((tid == 6 || tid == 7 || tid == 8) && intakeNote == true){
                    if (tid == 6 && degree < ampAngle){ 
                        Commands.deadline(
                            Commands.waitSeconds(0.5),
                            while (degree < ampAngle){
                            Commands.run(() -> aim.aimTurret(0.5), aim);
                            }
                        ),
                    }
                    else if (tid == 6 && degree > ampAngle){
                        Commands.deadline(
                            Commands.waitSeconds(0.5),
                            Commands.run(() -> aim.aimTurret(-0.5), aim)
                        ),
                    }
                }
                else if ((tid == 7 || tid == 8) && degree < speakerAngle){
                    Commands.deadline(
                        Commands.waitSeconds(0.5),
                        while (degree < speakerAngle){
                        Commands.run(() -> aim.aimTurret(0.5), aim);
                        }
                    ),
                }
                else if ((tid == 7 || tid == 8) && degree > speakerAngle){
                    Commands.deadline(
                        Commands.waitSeconds(0.5),
                        while (degree > speakerAngle){
                        Commands.run(() -> aim.aimTurret(-0.5), aim);
                        }
                    ),
                }

                else if ((tid == 14 || tid == 15 || tid == 16) && intakeNote == true && (timer.getFPGATimestamp() - 150) <= 20){
                    if (currentAngle < trapAngle){ //need to create a variable for the current angle
                        Commands.deadline(
                            Commands.waitSeconds(0.5),
                            while (degree < trapAngle){
                            Commands.run(() -> aim.aimTurret(0.5), aim)
                            }
                        ),
                    }
                    else if (currentAngle > trapAngle){
                        Commands.deadline(
                            Commands.waitSeconds(0.5),
                            while (degree > trapAngle){
                            Commands.run(() -> aim.aimTurret(-0.5), aim)
                            }
                        ),
                    }
                }
            }

            // Stop arm
            Commands.runOnce(() -> aim.aimTurret(0), aim),

            // Wait a bit
            Commands.waitSeconds(0.1),

            // Shoot balls
            Commands.deadline(
                Commands.waitSeconds(1),
                Commands.run(() -> fire1.fireTurret(0.5), fire1)
                ommands.run(() -> fire2.fireTurret(0.5), fire2)
            ),

            // Stop intake
            Commands.runOnce(() -> fire1.fireTurret(0), fire1),
            Commands.runOnce(() -> fire2.fireTurret(0), fire2),
            intakeNote = false;
        ).handleInterrupt(() -> {
            while (degree > 0){
                aim.aimTurret(-0.5);
            }
            intake.setIntake(0);
        }).schedule();
    }
}
