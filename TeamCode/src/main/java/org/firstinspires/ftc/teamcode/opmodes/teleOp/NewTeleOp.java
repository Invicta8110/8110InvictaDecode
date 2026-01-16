package org.firstinspires.ftc.teamcode.opmodes.teleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.robot.BumbleBot;

@TeleOp
public class NewTeleOp extends OpMode {
    private BumbleBot robot;
    private Gamepad gamepad;
    private boolean yPress;
    private boolean yToggle;
    private boolean aPress;
    private boolean aToggle;
    private static long kickTimer;
    private static long revolveTimer;

    public void init() {
        robot = new BumbleBot(hardwareMap);
        gamepad = gamepad1;
        telemetry.addData("Test","test");
        yPress = false;
    }

    public void loop() {
        drive();
        outtake();
        revolveOnEncode();
        intake();
        kick();
        fullLaunch();
    }

    public void outtake() {
        if(gamepad.y&&!yPress) {
            yPress = true;
            yToggle = !yToggle;
        }
        if(!gamepad.y) {
            yPress = false;
        }

        if(yToggle)
            robot.outtake(-.7);
        else
            robot.outtake(0);
    }

    public void revolve() {
        if(gamepad.x) {
            robot.revolve(.5);
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
        if(gamepad.x&&System.currentTimeMillis()-revolveTimer>100) {
            robot.revolve(.3);
            revolveTimer = System.currentTimeMillis();
        }
        if(System.currentTimeMillis()-revolveTimer>100) {
            robot.revolve(0);
        }
    }

    public void revolveOnEncode() {
        if(gamepad.x&&System.currentTimeMillis()-revolveTimer>1000) {
            revolveTimer = System.currentTimeMillis();
            robot.revolveEncoder();
            telemetry.addData("Revolver",robot.getRevolverPos());
        }
    }
    private void drive() {
        double y = -gamepad.left_stick_y; // Remember, Y stick is reversed!
        double x = -gamepad.left_stick_x;
        double rx = gamepad.right_stick_x;

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

        if(gamepad.a) {
            robot.intake(1);
        }
        else {
            robot.intake(0);
        }

        if(gamepad.right_bumper) {
            robot.reverseIntake();
        }

    }

    private void kick() {
//        if(gamepad.dpad_down) {
//            telemetry.addData("kick",0);
//            robot.kick();
//        }
        if(gamepad.dpad_up) {
            telemetry.addData("kick",0);
            robot.kick();
            kickTimer = System.currentTimeMillis();
        }
        if(System.currentTimeMillis()-kickTimer>1000) {
            robot.unkick();
        }
    }

    private void fullLaunch() {
        if(gamepad.b) {
            robot.fullLaunchSequence(true);
        }
        else {
            robot.fullLaunchSequence(false);
        }
    }
}
