package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class BBBTeleOp extends LinearOpMode {
    HardwareBIGBRAINBOTS robot   = new HardwareBIGBRAINBOTS();   // Use BIGBRAINBOTS's hardware

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(this.hardwareMap);

        telemetry.addData("Mode", "waiting");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {
            double drive = gamepad1.left_stick_y;
            double strafe = -gamepad1.right_stick_x;
            double turn = -gamepad1.left_stick_x;
            boolean turncarousel = gamepad1.x;
            boolean turnIntake = gamepad1.a;
            boolean reverseIntake = gamepad1.b;
            double FLPower = Range.clip(drive + strafe + turn, -1.0, 1.0);
            double FRPower = Range.clip(drive - strafe - turn, -1.0, 1.0);
            double BLPower = Range.clip(drive - strafe + turn, -1.0, 1.0);
            double BRPower = Range.clip(drive + strafe - turn, -1.0, 1.0);


            robot.FrontLeftDrive.setPower(FLPower);
            robot.FrontRightDrive.setPower(FRPower);
            robot.RearLeftDrive.setPower(BLPower);
            robot.RearRightDrive.setPower(BRPower);
            if (turncarousel){
                robot.CarouselMotor.setPower(1);
            }else{
                robot.CarouselMotor.setPower(0);
            }
            if (turnIntake){
                robot.IntakeMotor.setPower(.2);
            }else if(reverseIntake){
                robot.IntakeMotor.setPower(-0.2);
            }else{
                robot.IntakeMotor.setPower(0);
            }

            telemetry.addData("Mode", "Running");
            telemetry.addData("Power", "Frontleft=%.3f, Frontright=%.3f", FLPower, FRPower);
            telemetry.addData("Power", "Backleft=%.3f, Backright=%.3f", BLPower, BRPower);

            idle();
        }
    }
}
