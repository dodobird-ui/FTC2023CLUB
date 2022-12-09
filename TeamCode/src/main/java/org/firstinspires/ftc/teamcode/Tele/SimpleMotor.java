package org.firstinspires.ftc.teamcode.Tele;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class SimpleMotor extends LinearOpMode {
    //Declares our motor.
    static DcMotor veryCoolMotor;

    //This is the method where all of our code will go in.
    @Override
    public void runOpMode() throws InterruptedException {
        //Maps our motor to the app, with the name "veryCoolMotor".
        veryCoolMotor = hardwareMap.dcMotor.get("veryCoolMotor");
        //Waits for everything to start before continuing.
        waitForStart();
        //Declare our power variable.
        double power;
        //Constantly checks for controller input, and responds accordingly.
        while(opModeIsActive()){
            //Sets the power of out motor to the negative of the y-value of the stick.
            veryCoolMotor.setPower(-gamepad1.left_stick_y);
            //Alternatively, the next two lines show how you could use a variable and a method to call the setPower() method. This can be useful when you are performing multiple operations at once.
            power = -gamepad1.left_stick_y;
            basicMovement(power);
        }
    }
    //A method that sets the power of our motor.
    public static void basicMovement (double power){
        veryCoolMotor.setPower(power);
    }
}