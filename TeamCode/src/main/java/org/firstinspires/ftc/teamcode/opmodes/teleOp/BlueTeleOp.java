package org.firstinspires.ftc.teamcode.opmodes.teleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.robot.AutonBumbleBot;

@TeleOp
public class BlueTeleOp extends OpMode {
    private AutonBumbleBot robot;
    private Gamepad gamepad;
    private boolean yPress;
    private boolean yToggle;
    private boolean aPress;
    private boolean aToggle;
    private static long kickTimer;
    private static long revolveTimer;
    private static long launchTimer;
    private double area;

    public void init() {
        robot = new AutonBumbleBot(hardwareMap,20);
        gamepad = gamepad1;
        telemetry.addData("Test","test");
        yPress = false;
    }

    public void loop() {
        drive();
        outtake();
        revolve();
        intake();
        kick(gamepad.dpad_up);
        getArea();
        fullLaunch();
        launchKick(false);
        //robot.outtakeSpeedControl(gamepad1);
        telemetry.addData("Tag Area",robot.getTagArea());
        telemetry.addData("Square Root",Math.sqrt(robot.getTagArea()));
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
            robot.outtake(-.8);
        else
            robot.outtake(0);
    }

    public void revolve() {
        if(gamepad.x) {
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
        if(gamepad.b) {
            robot.setPipeline(0);
            boolean result = robot.findTag(-1);
            while(!result) {
                result = robot.findTag(-1);
            }

            result = robot.centerTag(1);
            while(!result) {
                telemetry.addData("Refining",result);
                result = robot.centerTag(2);
            }
            robot.fullLaunchSequence(true,estimateOuttakePower(area));
            launchKick(true);
            telemetry.addData("Launching",0);
        }
        else {
            robot.fullLaunchSequence(false,estimateOuttakePower(area));
        }
    }

    private void launchKick(boolean kicking) {
        if(kicking) {
            launchTimer = System.currentTimeMillis();
        }
        else if(System.currentTimeMillis()-launchTimer>4000&&System.currentTimeMillis()-launchTimer<5000) {
            kick(true);
            robot.launchEnd();
        }
    }

    private void getArea() {
        area = robot.getTagArea();
    }

    private double estimateOuttakePower(double area) {
        //.003 - back launch
        //.008 - just barely in front
        //.0256 - about halfway to goal

        if(area<.004) {
            return -.8;
        }
        else if(area<.009) {
            return -.7;
        }
        else if(area<.03) {
            return -.6;
        }
        else {
            return -.1;
        }
    }
}
