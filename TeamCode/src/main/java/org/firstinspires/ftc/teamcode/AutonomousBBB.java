package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@Autonomous
public class AutonomousBBB extends LinearOpMode {
    HardwareBIGBRAINBOTS robot = new HardwareBIGBRAINBOTS();

    @Override
    public void runOpMode() {
        //super.runOpMode();
        final double COUNTS_PER_MOTOR_REV  =28;
        final double DRIVE_GEAR_REDUCTION  =1;
        final double WHEEL_DIAMETER_INCHES = 4;
        final double COUNTS_PER_INCH = 44.64;
        final double STRAFE_COUNTS_PER_INCH = 49.02;


        robot.init(this.hardwareMap);
        robot.DumperServo.setPosition(0.5);
        waitForStart();

        robot.strafe(1,-1176);
        //sleep(30);
       //robot.gyroTurn(1, -45, 0.01);
        robot.turncarousel(1, 40);
        sleep(2000);
        robot.CarouselMotor.setPower(0);
        robot.CarouselMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.strafe(1,(int)(24*2*STRAFE_COUNTS_PER_INCH));
        robot.gyroTurn(1, 180, 0.01);
        robot.drive(1,(int)(-20*COUNTS_PER_INCH));
        telemetry.addData("strafe", "finished");
        telemetry.update();
    }
}

