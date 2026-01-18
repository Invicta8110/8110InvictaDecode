package org.firstinspires.ftc.teamcode.opmodes.auton;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;


@Autonomous
public class GreenColorSensorTest extends OpMode {
    private Limelight3A camera;
    public void init(){
        camera = hardwareMap.get(Limelight3A.class, "Limelight");
        camera.pipelineSwitch(1);

    }
    public void start() {
        camera.start();
    }

    public void loop(){
            telemetry.addData("Camera is running: ", camera.isRunning());


        LLResult llResult = camera.getLatestResult();
        if(llResult != null && llResult.isValid()){
            telemetry.addData("Target X Offset: ", llResult.getTx());
            telemetry.addData("Target Y Offset: ", llResult.getTy());
        }
        telemetry.update();
    }
}


