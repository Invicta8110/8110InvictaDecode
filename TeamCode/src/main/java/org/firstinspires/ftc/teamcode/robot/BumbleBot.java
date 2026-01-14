package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.mechanisms.Motor;

public class BumbleBot {
    private Motor fRight;
    private Motor fLeft;
    private Motor bRight;
    private Motor bLeft;
    private Motor outtake;
    private Motor testMotor;
    private Motor intake;
    private Motor revolver;
    private Servo kicker;

    public BumbleBot(HardwareMap hardwareMap) {
        fRight = new Motor("front_right_drive", hardwareMap);
        fLeft = new Motor("front_left_drive", hardwareMap);
        bRight = new Motor("back_right_drive", hardwareMap);
        bLeft = new Motor("back_left_drive", hardwareMap);

        fRight.setDirectionReverse();
        bRight.setDirectionReverse();

        outtake = new Motor("OuttakeMotor", hardwareMap);
        intake = new Motor ("IntakeMotor",hardwareMap);
        intake.setDirectionReverse();
        revolver = new Motor("RevolverMotor",hardwareMap);

        kicker = hardwareMap.get(Servo.class,"Kicker");
        kicker.setPosition(.1);
        kicker.setDirection(Servo.Direction.REVERSE);
        
        //testMotor = new Motor("TestMotor", hardwareMap);
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

    public void kick() {
    kicker.setPosition(0);
    }

    public void unkick() {
        kicker.setPosition(.1);
    }

    public void intake(double power) {
        intake.setPower(power);
    }

    public void revolve(double direction) {
        revolver.setPower(direction);
    }

//    public void testMotor(double power) {
//        testMotor.setPower(power);
//    }

}
