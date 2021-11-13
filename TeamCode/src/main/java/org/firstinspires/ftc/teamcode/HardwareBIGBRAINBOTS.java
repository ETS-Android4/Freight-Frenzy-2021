package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class HardwareBIGBRAINBOTS {
    public DcMotor FrontLeftDrive = null;
    public DcMotor FrontRightDrive = null;
    public DcMotor RearLeftDrive = null;
    public DcMotor RearRightDrive = null;
    public DcMotor CarouselMotor = null;
    public DcMotor IntakeMotor = null;

    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    public HardwareBIGBRAINBOTS () {

    }

    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;
        // Define and Initialize Motors
        FrontLeftDrive = hwMap.get(DcMotor.class, "FL_DCmotor");
        FrontRightDrive = hwMap.get(DcMotor.class, "FR_DCmotor");
        RearLeftDrive = hwMap.get(DcMotor.class, "RL_DCmotor");
        RearRightDrive = hwMap.get(DcMotor.class, "RR_DCmotor");
        CarouselMotor = hwMap.get(DcMotor.class, "CarouselMotor");
        IntakeMotor = hwMap.get(DcMotor.class, "IntakeMotor");



        FrontLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        FrontRightDrive.setDirection(DcMotor.Direction.REVERSE);
        RearLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        RearRightDrive.setDirection(DcMotor.Direction.REVERSE);
        CarouselMotor.setDirection(DcMotor.Direction.FORWARD);
        IntakeMotor.setDirection(DcMotor.Direction.REVERSE);

        FrontLeftDrive.setPower(0);
        FrontRightDrive.setPower(0);
        RearLeftDrive.setPower(0);
        RearRightDrive.setPower(0);
        CarouselMotor.setPower(0);
        IntakeMotor.setPower(0);

        FrontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RearLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RearRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        CarouselMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        IntakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        FrontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RearLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RearRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        CarouselMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        IntakeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        FrontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FrontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RearLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RearRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        CarouselMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        IntakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void drive(double power, int EncoderCounts) {
        power *= -1;
        FrontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RearLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RearRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeftDrive.setTargetPosition(EncoderCounts);
        FrontRightDrive.setTargetPosition(EncoderCounts);
        RearLeftDrive.setTargetPosition(EncoderCounts);
        RearRightDrive.setTargetPosition(EncoderCounts);
        FrontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RearLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RearRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontLeftDrive.setPower(power);
        FrontRightDrive.setPower(power);
        RearLeftDrive.setPower(power);
        RearRightDrive.setPower(power);
        while (FrontLeftDrive.isBusy() || FrontRightDrive.isBusy() || RearLeftDrive.isBusy() || RearRightDrive.isBusy()) {
            //telemetry.addData("Path0", "Starting at %7d :%7d :%7d :%7d",
            //        FrontLeftDrive.getCurrentPosition(),
            //            FrontRightDrive.getCurrentPosition(),
            //          RearLeftDrive.getCurrentPosition(),
            //        RearRightDrive.getCurrentPosition());
            //telemetry.update();
        }
        FrontLeftDrive.setPower(0);
        FrontRightDrive.setPower(0);
        RearLeftDrive.setPower(0);
        RearRightDrive.setPower(0);

        FrontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FrontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RearLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RearRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void strafe(double power, int EncoderCounts) {
        FrontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RearLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RearRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        FrontLeftDrive.setTargetPosition(-1 * EncoderCounts);
        FrontRightDrive.setTargetPosition(EncoderCounts);
        RearLeftDrive.setTargetPosition(EncoderCounts);
        RearRightDrive.setTargetPosition(-1 * EncoderCounts);
        FrontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RearLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RearRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontLeftDrive.setPower(-1 * power);
        FrontRightDrive.setPower(power);
        RearLeftDrive.setPower(power);
        RearRightDrive.setPower(-1 * power);
        while (FrontLeftDrive.isBusy() || FrontRightDrive.isBusy() || RearLeftDrive.isBusy() || RearRightDrive.isBusy()) {
            //telemetry.addData("Path0", "Starting at %7d :%7d :%7d :%7d",
            //        FrontLeftDrive.getCurrentPosition(),
            //            FrontRightDrive.getCurrentPosition(),
            //          RearLeftDrive.getCurrentPosition(),
            //        RearRightDrive.getCurrentPosition());
            //telemetry.update();
        }
        FrontLeftDrive.setPower(0);
        FrontRightDrive.setPower(0);
        RearLeftDrive.setPower(0);
        RearRightDrive.setPower(0);

        FrontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FrontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RearLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RearRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //telemetry.addData("Motors", "left (%.2f), right (%.2f)", 0,power);
    }

    public void turn(double power, int EncoderCounts) {
        FrontLeftDrive.setTargetPosition(-1 * EncoderCounts);
        FrontRightDrive.setTargetPosition(EncoderCounts);
        RearLeftDrive.setTargetPosition(-1 * EncoderCounts);
        RearRightDrive.setTargetPosition(EncoderCounts);
        FrontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RearLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RearRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontLeftDrive.setPower(-1 * power);
        FrontRightDrive.setPower(power);
        RearLeftDrive.setPower(-1 * power);
        RearRightDrive.setPower(power);
        while (FrontLeftDrive.isBusy() || FrontRightDrive.isBusy() || RearLeftDrive.isBusy() || RearRightDrive.isBusy()) {
            //telemetry.addData("Path0", "Starting at %7d :%7d :%7d :%7d",
            //        FrontLeftDrive.getCurrentPosition(),
            //            FrontRightDrive.getCurrentPosition(),
            //          RearLeftDrive.getCurrentPosition(),
            //        RearRightDrive.getCurrentPosition());
            //telemetry.update();
        }
        FrontLeftDrive.setPower(0);
        FrontRightDrive.setPower(0);
        RearLeftDrive.setPower(0);
        RearRightDrive.setPower(0);
        FrontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RearLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RearRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FrontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RearLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RearRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void turncarousel(double power, int EncoderCounts) {
        CarouselMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        CarouselMotor.setTargetPosition(EncoderCounts);
        CarouselMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        CarouselMotor.setPower(power);
        while (CarouselMotor.isBusy()) {
            //telemetry.addData("Path0", "Starting at %7d :%7d :%7d :%7d",
            //        FrontLeftDrive.getCurrentPosition(),
            //            FrontRightDrive.getCurrentPosition(),
            //          RearLeftDrive.getCurrentPosition(),
            //        RearRightDrive.getCurrentPosition());
            //telemetry.update();
        }
        CarouselMotor.setPower(0);
        CarouselMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void runIntake(double power, int EncoderCounts) {
        IntakeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        IntakeMotor.setTargetPosition(EncoderCounts);
        IntakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        IntakeMotor.setPower(power);
        //CarouselMotor.setPower(0);
        CarouselMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void stopIntake() {
        IntakeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        IntakeMotor.setTargetPosition(0);
        IntakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        CarouselMotor.setPower(0);
        CarouselMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

}