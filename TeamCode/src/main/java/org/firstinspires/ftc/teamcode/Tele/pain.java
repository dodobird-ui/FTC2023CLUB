package org.firstinspires.ftc.teamcode.Tele;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class pain extends LinearOpMode {
    static DcMotor linSlide;
    static DcMotor wheelFL;
    static DcMotor wheelFR;
    static DcMotor wheelBL;
    static DcMotor wheelBR;
    static Servo clawL;
    static Servo clawR;
    static int slideState;
    @Override


    public void runOpMode() throws InterruptedException {
        linSlide = hardwareMap.dcMotor.get("linSlide");
        wheelFL = hardwareMap.dcMotor.get("wheelFL");
        wheelFR = hardwareMap.dcMotor.get("wheelFR");
        wheelBL = hardwareMap.dcMotor.get("wheelBL");
        wheelBR = hardwareMap.dcMotor.get("wheelBR");
        clawL = hardwareMap.servo.get("clawL");
        clawR = hardwareMap.servo.get("clawR");

        wheelFL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        wheelFR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        wheelBL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        wheelBR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        linSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        linSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        clawL.setPosition(0);
        clawR.setPosition(0);

        slideState = 1;

        waitForStart();

        while(opModeIsActive()) {
            turn(gamepad1.left_stick_x);
            drive(gamepad1.right_stick_y);
            slide(gamepad1.right_stick_x);
            moveSlide(gamepad1.left_bumper, gamepad1.right_bumper, gamepad1.left_trigger);
            moveClaw(gamepad1.x, gamepad1.y);
        }
    }

    public static void slide(float power) {
        wheelFL.setPower(power);
        wheelFR.setPower(power);
        wheelBL.setPower(-power);
        wheelBR.setPower(-power);
    }

    public static void turn(float power) {
        wheelFL.setPower(power);
        wheelFR.setPower(-power);
        wheelBL.setPower(power);
        wheelBR.setPower(-power);
    }

    public static void drive(float y) {
        setAllMotors(y);
    }


    public static void moveClaw(boolean buttonX, boolean buttonY) {
        if(buttonX) {
            clawL.setPosition(0.25);
            clawR.setPosition(0.25);
        } else if(buttonY) { //how the fuck are these meant to spin
            clawL.setPosition(0);
            clawR.setPosition(0);
        }
    }

    public static void moveSlide(boolean leftB, boolean rightB, float leftT) {
        boolean leftTrig = leftT > 0.7;
        int encoder = linSlide.getCurrentPosition();
        switch(slideState) {
            case 1: //low
                if(rightB) {
                    slideState = 5;
                } else if(leftTrig) {
                    slideState = 6;
                }
                break;

            case 2: //medium
                if(leftB) {
                    slideState = 4;
                } else if(leftTrig) {
                    slideState = 6;
                }
                break;

            case 3: //high
                if(leftB) {
                    slideState = 4;
                } else if(rightB) {
                    slideState = 5;
                }
                break;

            case 4: //go to low
                if(encoder > 0) {
                    minh(0, -1);
                } else {
                    slideState = 1;
                }

                break;

            case 5: //go to medium
                if(encoder > 2000) {
                    minh(2000, -1);
                } else if(encoder < 2000) {
                    minh(2000, 1);
                } else {
                    slideState = 2;
                }
                break;

            case 6: //go to high
                if(encoder < 4000) {
                    minh(4000, 1);
                } else {
                    slideState = 3;
                }
                break;
        }
    }

    public static void minh(int position, int power) {
        linSlide.setTargetPosition(position);
        linSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linSlide.setPower(power);
    }

    public static void setAllMotors(float power) {
        wheelFL.setPower(power);
        wheelFR.setPower(power);
        wheelBL.setPower(power);
        wheelBR.setPower(power);
    }
}
