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
    private boolean oldY;
    private boolean yToggle;
    private boolean oldA;
    private boolean aToggle;

    public void init() {
        robot = new BumbleBot(hardwareMap);
        gamepad = gamepad1;
        telemetry.addData("Test","test");
        oldY = false;
    }

    public void loop() {
        drive();
        outtake();
        revolve();
        intake();
        kick();
    }

    public void outtake() {
        if(gamepad.y&&!oldY) {
            yToggle = !yToggle;
        }

        if(yToggle)
            robot.outtake(-1);
        else
            robot.outtake(0);
    }

    public void revolve() {
        if(gamepad.x) {
            robot.revolve(.3);
        }
        else if(gamepad.b) {
            robot.revolve(-.3);
        }
        else {
            robot.revolve(0);
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

    }

    private void kick() {
        if(gamepad.dpad_down) {
            robot.kick();
        }
        if(gamepad.dpad_up) {
            robot.unkick();
        }
    }
}
