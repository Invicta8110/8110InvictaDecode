package org.firstinspires.ftc.teamcode.opmodes.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robot.AutonBumbleBot;

@Autonomous
public class RedBackAuton extends LinearOpMode {
    AutonBumbleBot robot;

    public void runOpMode() {
        robot = new AutonBumbleBot(hardwareMap,24);
        robot.setPipeline(0);

        waitForStart();

        telemetry.addData("Tags",robot.getTags());

        boolean result = robot.findTag(1);
        while(opModeIsActive()&&!result) {
            result = robot.findTag(1);
        }

        result = robot.centerTag(1);
        while(opModeIsActive()&&!result) {
            telemetry.addData("Refining",result);
            result = robot.centerTag(.5);
        }

        robot.outtake(-.8);
        sleep(5000);
        robot.kick();
        sleep(1000);
        robot.unkick();
    }

}
