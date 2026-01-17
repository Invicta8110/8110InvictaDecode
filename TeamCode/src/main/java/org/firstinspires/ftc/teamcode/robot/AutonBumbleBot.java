package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AutonBumbleBot extends BumbleBot{
    private Limelight3A camera;
    private LLResult result;
    private ArrayList<Integer> tags;
    private double tagX;
    private double tagY;
    private int goalTag;

    public AutonBumbleBot(HardwareMap hardwareMap, int goalTag) {
        super(hardwareMap);
        camera = hardwareMap.get(Limelight3A.class,"Limelight");
        camera.setPollRateHz(100);
        camera.start();

        this.goalTag = goalTag;
    }

    public boolean findTag(double direction) {
        getTags();
        result = camera.getLatestResult();
        if(tags.contains(goalTag)) {
            drive(0,0,0,0);
            tagX = tags.get(tags.indexOf(goalTag));
            return true;
        }
        else {
            drive(-.18 * direction, .18 * direction, -.18 * direction, .18 * direction);
        }
        return false;
    }

    public boolean centerTag(double error) {
        getTags();
        result = camera.getLatestResult();

        if(Math.abs(tagX)<error) {
            drive(0,0,0,0);
            return true;
        }

        //drive(-tagX/90,tagX/90,-tagX/90,tagX/90);
        drive(-.4,.4,-.4,.4);

        return false;
    }

    public ArrayList<Integer> getTags() {
        result = camera.getLatestResult();
        List<LLResultTypes.FiducialResult> tags = Collections.emptyList();
        if(result!=null&&result.isValid()) {
            tags = result.getFiducialResults();
        }

        ArrayList<Integer> ids = new ArrayList<Integer>();
        for (int i = 0; i < tags.size(); i++) {
            ids.add(tags.get(i).getFiducialId());
        }
        this.tags = ids;
        getTagX(tags);

        return ids;
    }

    private void getTagX(List<LLResultTypes.FiducialResult> tagInfo) {
        if(tags.contains(goalTag)) {
            tagX = tagInfo.get(tags.indexOf(goalTag)).getTargetXDegrees();
        }
    }

    public void setPipeline(int index) {
        camera.pipelineSwitch(index);
    }
}
