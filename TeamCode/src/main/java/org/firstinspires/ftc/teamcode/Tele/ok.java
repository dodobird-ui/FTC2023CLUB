package org.firstinspires.ftc.teamcode.Tele;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class ok extends LinearOpMode {
    static DcMotor motorFL;
    static DcMotor motorFR;
    static DcMotor motorBL;
    static DcMotor motorBR;
    @Override

    public void runOpMode() throws InterruptedException {
        motorFL = hardwareMap.dcMotor.get("motor1");
        motorFR = hardwareMap.dcMotor.get("motor2");
        motorBL = hardwareMap.dcMotor.get("motor3");
        motorBR = hardwareMap.dcMotor.get("motor4");

        waitForStart();

        while(opModeIsActive()) {
            double joystickPosY = -gamepad1.left_stick_y;
            double joystickPosX = gamepad1.left_stick_x;
            drive(joystickPosY);
            turn(joystickPosX);
        }
    }

    public static void drive(double speed) {
        setSpeeds(speed);
    }

    public static void turn(double speed) {
        motorFL.setPower(-speed);
        motorFR.setPower(speed);
        motorBL.setPower(-speed);
        motorBR.setPower(speed);
    }

    public static void setSpeeds(double s) {
        motorFL.setPower(s);
        motorFR.setPower(s);
        motorBL.setPower(s);
        motorBR.setPower(s);
    }
}
