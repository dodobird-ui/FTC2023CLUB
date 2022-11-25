package org.firstinspires.ftc.teamcode.Tele;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class stateMachineExample extends LinearOpMode {
    static DcMotor motor1 = null;
    static Servo servo1 = null;
    static int servoPos = 0;
    @Override
    public void runOpMode() throws InterruptedException{
        //hardware mapp
        motor1 = hardwareMap.dcMotor.get("motor1");
        servo1 = hardwareMap.servo.get("servo1");
        motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        servo1.setPosition(0);
        waitForStart();
        // gamepad1, functions for motors
        while(opModeIsActive()){
            double motorSpeed = -gamepad1.left_stick_y;
            forwardBackWardMovement(motorSpeed);
            if(gamepad1.a){
                spin180();
            }
            switchStates(gamepad1.left_bumper, gamepad1.right_bumper, gamepad1.right_trigger);
        }
    }
    public enum states{LOW, MEDIUM, HIGH, TOLOW, TOMID, TOHIGH }
    public static states state = states.LOW;
    public static void switchStates(boolean leftBumper, boolean  rightBumper, double rightTrigger){
        switch(state){
            case LOW:
                if(rightBumper){
                    state = states.TOMID;
                }
                else if (rightTrigger > 0.7){
                    state = states.TOHIGH;
                }
                break;
            case MEDIUM:
                if(leftBumper){
                    state = states.TOLOW;
                }
                else if(rightTrigger >0.7){
                    state = states.TOHIGH;
                }
                break;
            case HIGH:
                if(leftBumper){
                    state = states.TOLOW;
                }
                else if(rightBumper){
                    state = states.TOMID;
                }
                break;
            case TOLOW:
                if (motor1.getCurrentPosition() > 0){
                    motor1.setTargetPosition(0);
                    motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    motor1.setPower(-1);
                }
                else{
                    state = states.LOW;
                    motor1.setPower(0);
                }
                break;
            case TOMID: //Middle value is 2000
                if(motor1.getCurrentPosition() >2000){
                    motor1.setTargetPosition(2000);
                    motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    motor1.setPower(-1);
                }
                else if (motor1.getCurrentPosition() <2000){
                    motor1.setTargetPosition(2000);
                    motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    motor1.setPower(1);
                }
                else{
                    state = states.MEDIUM;
                    motor1.setPower(0);
                }
                break;
            case TOHIGH: //4000
                if(motor1.getCurrentPosition()<4000){
                    motor1.setTargetPosition(4000);
                    motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    motor1.setPower(1);
                }
               else{
                   state = states.HIGH;
                    motor1.setPower(0);
                }
                break;
        }
    }
    public static void forwardBackWardMovement (double speed){
        motor1.setPower(speed);
    }
    public static void spin180(){
        if(servoPos==0){
            servo1.setPosition(1);
            servoPos = 1;
        }
        else if (servoPos==1){
            servo1.setPosition(0);
            servoPos = 0;
        }
    }
}