package org.firstinspires.ftc.teamcode.robot;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.DcMotor;
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
    private int motorPos;
    private static long launchTimer;
    private boolean kicked;
    private boolean unkicked;

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
        revolver.reset();

        kicker = hardwareMap.get(Servo.class,"Kicker");
        //kicker.setPosition(.1);
        kicker.setDirection(Servo.Direction.REVERSE);
        motorPos = revolver.getCurrentPosition();
        
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

    public void unkick() {
        kicker.setPosition(.18);
    }

    public void kick() {
        kicker.setPosition(0);
    }

    public void intake(double power) {
        intake.setPower(power);
    }

    public void reverseIntake() {
        intake.setPower(-1);
    }

    public void revolve(double direction) {
        revolver.setPower(direction);
        //revolver.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorPos += 20;
        //revolver.runToPosition(motorPos,.1);
    }

    public void revolveEncoder() {
        revolver.reset();
        revolver.runToPosition(100,.2);
    }

    public double getRevolverPos() {
        return revolver.getCurrentPosition();
    }

//    public void testMotor(double power) {
//        testMotor.setPower(power);
//    }

    public void fullLaunchSequence(boolean start) {
        if(start) {
            launchTimer = System.currentTimeMillis();
        }

        //warm up outtake
        //kick
        //unkick

        if(System.currentTimeMillis()-launchTimer>7000&&!unkicked) {
            unkick();
            outtake.setPower(0);
            unkicked = true;
        }
        else if(System.currentTimeMillis()-launchTimer>5000&&!kicked) {
            kick();
            kicked = true;
        }
        else if(System.currentTimeMillis()-launchTimer<5000){
            outtake.setPower(.7);
            kicked = false;
            unkicked = false;
        }
    }

}
