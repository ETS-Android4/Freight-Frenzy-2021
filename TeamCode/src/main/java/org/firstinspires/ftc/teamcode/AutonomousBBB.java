package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
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

        waitForStart();
        robot.gyroTurn(0.5, 90, 0.01);
        sleep(30);
        telemetry.addData("strafe", "finished");
        telemetry.update();
    }
}

