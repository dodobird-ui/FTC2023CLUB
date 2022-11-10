package org.firstinspires.ftc.teamcode.Tele;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class driveChain extends LinearOpMode { //sample drivechain code
    static DcMotor motorFrontLeft; // define as static to use in later methods
    static DcMotor motorRearLeft ;
    static DcMotor motorFrontRight;
    static DcMotor motorRearRight;
    static CRServo nerf;
    static CRServo nerf1;
    @Override
    public void runOpMode() throws InterruptedException{
        motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
        motorRearLeft = hardwareMap.dcMotor.get("motorRearLeft");
        motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight");
        motorRearRight = hardwareMap.dcMotor.get("motorRearRight");
        nerf = hardwareMap.crservo.get("nerf");
        nerf1 = hardwareMap.crservo.get("nerf");
        motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorRearLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();
        while (opModeIsActive()){
            forwardBackward(-gamepad1.left_stick_y);
            turning(gamepad1.left_stick_x);
            servoSet(gamepad1.right_stick_x);

        }
    }
    public static void servoSet(double speed){
        nerf.setPower(speed);
        nerf1.setPower(-speed);
    }
    public static void forwardBackward (double speed){
        motorFrontLeft.setPower(speed);
        motorRearLeft.setPower(speed);
        motorFrontRight.setPower(speed);
        motorRearRight.setPower(speed);
    }
    public static void turning (double speed){
        motorFrontLeft.setPower(speed);
        motorRearLeft.setPower(-speed);
        motorFrontRight.setPower(speed);
        motorRearRight.setPower(-speed);
    }
}
