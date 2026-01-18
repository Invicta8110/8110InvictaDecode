package org.firstinspires.ftc.teamcode.opmodes.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robot.AutonBumbleBot;

@Autonomous
public class RedFrontAuton extends LinearOpMode {
    AutonBumbleBot robot;

    public void runOpMode() {
        robot = new AutonBumbleBot(hardwareMap,24);
        robot.setPipeline(0);

        waitForStart();

        telemetry.addData("Tags",robot.getTags());

//        boolean result = robot.findTag(1);
//        while(opModeIsActive()&&!result) {
//            result = robot.findTag(1);
//        }
//
//        result = robot.centerTag(1);
//        while(opModeIsActive()&&!result) {
//            telemetry.addData("Refining",result);
//            result = robot.centerTag(.5);
//        }

        robot.outtake(-.6);
        sleep(4000);
//        for(int i=0; i<3; i++) {
//            robot.kick();
//            sleep(500);
//            robot.unkick();
//            sleep(500);
//            robot.revolve(1);
//            sleep(32-i*2);
//            robot.revolve(0);
//            sleep(1000);
//        }

        robot.kick();
        sleep(500);
        robot.unkick();
        sleep(500);
//        robot.revolve(1);
//        sleep(50);
//        robot.revolve(0);
//        sleep(1000);
//
//        robot.kick();
//        sleep(500);
//        robot.unkick();
//        sleep(500);
//        robot.revolve(1);
//        sleep(60);
//        robot.revolve(0);
//        sleep(1000);
//
//        robot.kick();
//        sleep(500);
//        robot.unkick();
//        sleep(500);
//        robot.revolve(1);
//        sleep(60);
//        robot.revolve(0);
//        sleep(1000);
    }
}
