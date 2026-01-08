package org.firstinspires.ftc.teamcode.opmodes.auton;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.robot.BumbleBot;

import java.util.List;

@Autonomous
public class RedBackAuton extends LinearOpMode {
    private Limelight3A camera;
    private int motifNum;
    private double tagX;
    private double tagY;
    private BumbleBot robot;

    public void runOpMode() {
        telemetry.addData("null", "null?");
        camera = hardwareMap.get(Limelight3A.class,"Limelight");
        camera.setPollRateHz(100);
        camera.start();
        camera.pipelineSwitch(0);

        robot = new BumbleBot(hardwareMap);


        waitForStart();

        //read();
        findTag();
        goToGoal();
        launch();
    }

    private void findTag() {
        //this should be in while look for if not close to tag
        LLResult result = camera.getLatestResult();
        boolean found = false;
        while(!found&&opModeIsActive()) {
            result = camera.getLatestResult();
            if (result != null && result.isValid()) {
                telemetry.addData("Tag", result.getFiducialResults());
                for(int i=0; i<result.getFiducialResults().size(); i++) {
                    if(result.getFiducialResults().get(i).getFiducialId()==24) {
                        found = true;
                        tagX = result.getFiducialResults().get(i).getTargetXDegrees();
                        tagY = result.getFiducialResults().get(i).getTargetYDegrees();
                        telemetry.addData("Found",i);
                        robot.drive(0,0,0,0);
                    }
                }
            }

            if(!found) {
                robot.drive(.1,-.1,.1,-.1);
            }

            telemetry.update();
        }
    }

    private void goToGoal() {
        LLResult result;
        while(opModeIsActive()&&Math.abs(tagX)>5) {
            telemetry.addData("moving",tagX);
            result = camera.getLatestResult();
            if(result!=null&&result.isValid()) {
                for(int i=0; i<result.getFiducialResults().size(); i++) {
                    if(result.getFiducialResults().get(i).getFiducialId()==24) {
                        tagX = result.getFiducialResults().get(i).getTargetXDegrees();
                        tagY = result.getFiducialResults().get(i).getTargetYDegrees();
                    }
                }
                robot.drive(-tagX/90,tagX/90,-tagX/90,tagX/90);
            }
            telemetry.update();
        }
        robot.drive(0,0,0,0);
    }



    private void launch() {
        telemetry.addData("launching","");
        telemetry.update();
        robot.outtake(-1);
        sleep(1500);
        robot.outtake(0);
        telemetry.addData("launched","");
        telemetry.update();
    }

    private void read() {
        LLResult result=camera.getLatestResult();
        while(opModeIsActive()) {
            telemetry.addData("Status",camera.getStatus());
            telemetry.addData("test"," ");
            result = camera.getLatestResult();
            if(result!=null) {
                telemetry.addData("Pipeline",result.getPipelineIndex());
                telemetry.addData("List: ",result.getFiducialResults());
                if(result.isValid()) {
                    telemetry.addData("Valid",result.getFiducialResults().get(0).getFiducialId());
                }
            }
            telemetry.update();
        }
    }
}
