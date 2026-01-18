package org.firstinspires.ftc.teamcode.opmodes.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robot.AutonBumbleBot;

@Autonomous
public class BlueFrontAuton extends LinearOpMode {
    AutonBumbleBot robot;

    public void runOpMode() {
        robot = new AutonBumbleBot(hardwareMap,20);
        robot.setPipeline(0);

        waitForStart();

        telemetry.addData("Tags",robot.getTags());

        robot.outtake(-.6);
        sleep(4000);

        robot.kick();
        sleep(500);
        robot.unkick();
        sleep(500);
    }
}
