package org.firstinspires.ftc.teamcode.opmodes.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.BumbleBot;

@TeleOp
public class MotorTest extends OpMode {
    private double[] motors;
    private BumbleBot robot;

    public void init() {
        robot = new BumbleBot(hardwareMap);
        motors = new double[1];
    }

    public void loop() {
        setMotors(gamepad1.a,0);

        //robot.drive(motors[0],motors[1],motors[2],motors[3]);
    }

    private double setMotors(boolean button, int id) {
        if(button) {
            motors[id] = 1;
            return 1;
        }
        else {
            motors[id] = 0;
            return 0;
        }
    }
}
