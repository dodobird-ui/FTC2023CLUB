package org.firstinspires.ftc.teamcode.Tele;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class Sample extends LinearOpMode {
    static DcMotor motor1 = null;
    static Servo servo1 = null;
    static int servoPos = 0;
    @Override
    public void runOpMode() throws InterruptedException{
        //hardware mapp
        motor1 = hardwareMap.dcMotor.get("motor1");
        servo1 = hardwareMap.servo.get("servo1");
        servo1.setPosition(0);
        waitForStart();
        // gamepad1, functions for motors
        while(opModeIsActive()){
            double motorSpeed = -gamepad1.left_stick_y;
            forwardBackWardMovement(motorSpeed);
            if(gamepad1.a){
                spin180();
            }
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
