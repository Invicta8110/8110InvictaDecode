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
    BumbleBot robot;
    Gamepad gamepad;

    public void init() {
        robot = new BumbleBot(hardwareMap);
        gamepad = gamepad1;
    }

    public void loop() {
        double y = -gamepad.left_stick_y; // Remember, Y stick is reversed!
        double x = gamepad.left_stick_x;
        double rx = gamepad.right_stick_x;

        double fRightPower = y - x - rx;
        double fLeftPower = y + x + rx;
        double bRightPower = y + x - rx;
        double bLeftPower = y - x + rx;

        robot.drive(fRightPower, fLeftPower, bRightPower, bLeftPower);
    }
}
