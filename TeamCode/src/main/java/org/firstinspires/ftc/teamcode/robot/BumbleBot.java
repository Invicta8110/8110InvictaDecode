package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.mechanisms.Motor;

public class BumbleBot {
    private Motor fRight;
    private Motor fLeft;
    private Motor bRight;
    private Motor bLeft;
    private Motor outtake;

    public BumbleBot(HardwareMap hardwareMap) {
        fRight = new Motor("front_right_drive", hardwareMap);
        fLeft = new Motor("front_left_drive", hardwareMap);
        bRight = new Motor("back_right_drive", hardwareMap);
        bLeft = new Motor("back_left_drive", hardwareMap);

        fRight.setDirectionReverse();
        bRight.setDirectionReverse();

        outtake = new Motor("OuttakeMotor", hardwareMap);
    }

    public void drive(double fRightPower, double fLeftPower, double bRightPower, double bLeftPower) {
        fRight.setPower(fRightPower);
        fLeft.setPower(fLeftPower);
        bRight.setPower(bRightPower);
        bLeft.setPower(bLeftPower);

    }

    public void outtake(double power) {
        outtake.setPower(power);
    }

}
