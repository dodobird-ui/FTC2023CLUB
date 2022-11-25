package org.firstinspires.ftc.teamcode.Tele;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.linearOpMode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class stateSlideThingy extends LinearOpMode {
    static DcMotor linSlide;
    @Override


    public void runOpMode() throws InterruptedException {
        linSlide = hardwareMap.dcMotor.get("linSlide");
        linSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        linSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
    }

    public enum slideStates{low, medium, high, goToLow, goToMedium, goToHigh};
    public static slideStates state = slideStates.low;

    public static void stateChanges(boolean leftBump, boolean rightBump, double rightTrig) {
        double encoderValue = linSlide.getCurrentPosition();
        boolean rightTrigger = rightTrig > 0.7;
        switch(state) {
            case low:
                if(rightBump) {
                    state = slideStates.goToMedium;
                } else if(rightTrigger) {
                    state = slideStates.goToHigh;
                }
                break;


            case medium:
                if(leftBump) {
                    state = slideStates.goToLow;
                } else if(rightTrigger) {
                    state = slideStates.goToHigh;
                }

                break;

            case high:
                if(leftBump) {
                    state = slideStates.goToLow;
                } else if(rightBump) {
                    state = slideStates.goToMedium;
                }
                break;

            case goToLow:
                if(encoderValue > 0) {
                    moveSlide(0, -1);
                } else {
                    state = slideStates.low;
                    linSlide.setPower(0);
                }
                break;


            case goToMedium:
                if(encoderValue < 2000) {
                    moveSlide(2000, 1);
                } else if(encoderValue > 2000) {
                    moveSlide(2000, -1);
                } else {
                    state = slideStates.medium;
                    linSlide.setPower(0);
                }
                break;

            case goToHigh:
                if(encoderValue < 4000) {
                    moveSlide(4000, 1);
                } else {
                    state = slideStates.high;
                    linSlide.setPower(0);
                }
                break;
        }
    }

    public static void moveSlide(int pos, double power) {
        linSlide.setTargetPosition(pos);
        linSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linSlide.setPower(power);
    }


}


