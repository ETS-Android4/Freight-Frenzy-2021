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
            double strafe =-gamepad1.right_stick_x;
            double turn =-gamepad1.left_stick_x;//changed to negative to fix problem with turning the wrong direction
            double FLPower = Range.clip(drive + strafe + turn, -1.0, 1.0);
            double FRPower = Range.clip(drive - strafe - turn, -1.0, 1.0);
            double BLPower = Range.clip(drive - strafe + turn, -1.0, 1.0);
            double BRPower = Range.clip(drive + strafe - turn, -1.0, 1.0);
            boolean wheelSpin = gamepad1.a;
            robot.FrontLeftDrive.setPower(FLPower);
            robot.FrontRightDrive.setPower(FRPower);
            robot.RearLeftDrive.setPower(BLPower);
            robot.RearRightDrive.setPower(BRPower);

            telemetry.addData("Mode", "Running");
            telemetry.addData("Power", "Frontleft=%.3f, Frontright=%.3f", FLPower, FRPower);
            telemetry.addData("Power", "Backleft=%.3f, Backright=%.3f", BLPower, BRPower);
            telemetry.update();

            if (wheelSpin){
                robot.Flywheel.setPower(1);
            }
            if (wheelSpin == false){
                robot.Flywheel.setPower(0);
            }
            sleep(69);
            idle();
        }
    }
}
