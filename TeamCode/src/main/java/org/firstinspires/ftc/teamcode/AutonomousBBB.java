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


        robot.init(this.hardwareMap);

        waitForStart();
        //robot.drive(-1, 500);
        //sleep(30);
        robot.drive(1, 500);
        telemetry.addData("drive", "finished");
        telemetry.update();
    }
}

