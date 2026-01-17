package org.firstinspires.ftc.teamcode.opmodes.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.robot.AutonBumbleBot;
import org.firstinspires.ftc.teamcode.robot.BumbleBot;

@TeleOp
public class NewTeleOp extends OpMode {
    private AutonBumbleBot robot;
    //private Gamepad gamepad;
    private boolean yPress;
    private boolean yToggle;

    private boolean aPress;
    private boolean aToggle;
    private static long kickTimer;
    private static long revolveTimer;
    private static long launchTimer;

    public void init() {
        robot = new AutonBumbleBot(hardwareMap,24);
        telemetry.addData("Test","test");
        yPress = false;
    }

    public void loop() {
        drive();
        robot.outtakeSpeedControl(gamepad1.dpad_right,gamepad1.dpad_left);
        outtake();
        revolve();
        intake();
        kick(gamepad1.dpad_up);
        fullLaunch();
        launchKick(false);
    }

    public void outtake() {
        if(gamepad1.y&&!yPress) {
            yPress = true;
            yToggle = !yToggle;
        }
        if(!gamepad1.y) {
            yPress = false;
        }

        if(yToggle) {
            robot.outtake();
        }else {
            robot.outtake();
        }
    }

    public void revolve() {
        if(gamepad1.x) {
            robot.revolve(.3);
            //revolveTimer = System.currentTimeMillis();
        }
        //else if(gamepad.b) {
        //    robot.revolve(-.5);
        //}
        else {
            robot.revolve(0);
        }
        telemetry.addData("Revolver",robot.getRevolverPos());
    }

    public void revolveOnTimer() {
        if(gamepad1.x&&System.currentTimeMillis()-revolveTimer>100) {
            robot.revolve(.3);
            revolveTimer = System.currentTimeMillis();
        }
        if(System.currentTimeMillis()-revolveTimer>100) {
            robot.revolve(0);
        }
    }

    public void revolveOnEncode() {
        if(gamepad1.x&&System.currentTimeMillis()-revolveTimer>1000) {
            revolveTimer = System.currentTimeMillis();
            robot.revolveEncoder();
            telemetry.addData("Revolver",robot.getRevolverPos());
        }
    }
    private void drive() {
        double y = -gamepad1.left_stick_y; // Remember, Y stick is reversed!
        double x = -gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x;

        double fRightPower = y - x - rx;
        double fLeftPower = y + x + rx;
        double bRightPower = y + x - rx;
        double bLeftPower = y - x + rx;

        robot.drive(fRightPower, fLeftPower, bRightPower, bLeftPower);
    }

    private void intake() {
//        if(gamepad.a&&!oldA) {
//            aToggle = !aToggle;
//        }
//
//        if(aToggle)
//            robot.intake(-1);
//        else
//            robot.intake(0);
//
//        if(gamepad.dpad_down) {
//            aToggle = false;
//        }

        if(gamepad1.a) {
            robot.intake(1);
        }
        else {
            robot.intake(0);
        }

        if(gamepad1.right_bumper) {
            robot.reverseIntake();
        }

    }

    private void kick(boolean kicking) {
//        if(gamepad.dpad_down) {
//            telemetry.addData("kick",0);
//            robot.kick();
//        }
        if(kicking) {
            telemetry.addData("kick",0);
            robot.kick();
            kickTimer = System.currentTimeMillis();
        }
        if(System.currentTimeMillis()-kickTimer>1000) {
            robot.unkick();
        }
    }

    private void fullLaunch() {
        if(gamepad1.b) {
            robot.setPipeline(0);
            boolean result = robot.findTag(1);
            while(!result) {
                result = robot.findTag(1);
            }

            result = robot.centerTag(1);
            while(!result) {
                telemetry.addData("Refining", result);
                result = robot.centerTag(2);
            }
            robot.fullLaunchSequence(true);
            launchKick(true);
            telemetry.addData("Launching",0);
        }
        else {
            robot.fullLaunchSequence(false);
        }
    }

    private void launchKick(boolean kicking) {
        if(kicking) {
            launchTimer = System.currentTimeMillis();
        }
        else if(System.currentTimeMillis()-launchTimer>4000&&System.currentTimeMillis()-launchTimer<5000) {
            kick(true);
        }
    }
}
