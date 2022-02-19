package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class BBBTeleOp extends LinearOpMode {
    HardwareBIGBRAINBOTS robot   = new HardwareBIGBRAINBOTS();   // Use BIGBRAINBOTS's hardware
    double dumperPos = 0;
    @Override
    public void runOpMode() {
        robot.init(this.hardwareMap);

        telemetry.addData("Mode", "waiting");
        telemetry.update();
        final int MAX_ARM_POS = -2700;
        final int MIN_ARM_POS = -0;

        String dumperState = "down";
        waitForStart();


        while (opModeIsActive()) {

            double drive = -gamepad1.right_stick_y;
            double strafe = -gamepad1.right_stick_x;
            double turn = -gamepad1.left_stick_x*0.8;



    /*        if(Math.abs(strafe)<0.2){
                strafe=0;
            } */
            double armPower = gamepad2.left_trigger-gamepad2.right_trigger;
            boolean turnIntake = gamepad2.a;
            boolean reverseIntake = gamepad2.b;

            double FLPower = Range.clip(drive - strafe + turn, -1.0, 1.0);
            double FRPower = Range.clip(drive + strafe - turn, -1.0, 1.0);
            double BLPower = Range.clip(drive + strafe + turn, -1.0, 1.0);
            double BRPower = Range.clip(drive - strafe - turn, -1.0, 1.0);
            if (gamepad2.left_bumper){


                robot.CarouselMotor.setPower(0.1);

            }else if (gamepad2.right_bumper) {
                robot.CarouselMotor.setPower(-0.1);
            }else{
                robot.CarouselMotor.setPower(0);
            }
            dumperPos-= gamepad2.right_stick_y/120;


            if(gamepad2.dpad_left){
                dumperPos=0.0;
                dumperState = "left";
            }
            if(gamepad2.dpad_up){
                dumperState ="up";
            }
            if(gamepad2.dpad_right){
                if(robot.ArmMotor.getCurrentPosition()<-1100){
                    dumperPos=1;
                    dumperState = "dump";
                }
            }
            switch (dumperState){
                case "up":
                    dumperPos = (0.5-dumperPos)/20 + dumperPos;
                    if(gamepad2.dpad_up){
                        //dumperPos=0.5;
                    }
                    break;

                case "left":

                    break;

                case "dump":
                    break;


            }

            if(dumperPos<0) {
                dumperPos=0;
            }

            if (dumperPos>1){
                dumperPos=1;
            }
            robot.DumperServo.setPosition(dumperPos);

            robot.FrontLeftDrive.setPower(FLPower);
            robot.FrontRightDrive.setPower(FRPower);
            robot.RearLeftDrive.setPower(BLPower);
            robot.RearRightDrive.setPower(BRPower);

            //robot.ArmMotor.setPower(0);

            //boolean canGoUp =robot.ArmMotor.getCurrentPosition()>=MAX_ARM_POS && armPower<0;
            //boolean canGoDown = robot.ArmMotor.getCurrentPosition()>=MAX_ARM_POS && armPower>0;

            if(robot.ArmMotor.getCurrentPosition()>=MAX_ARM_POS) {
                telemetry.addData("Go up", "Yes");
                if(armPower<=0){
                    robot.ArmMotor.setPower(armPower);
                }
            }else{
                if(armPower<0){
                        robot.ArmMotor.setPower(0);
                        robot.ArmMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

                }
            }

            if(robot.ArmMotor.getCurrentPosition()<=MIN_ARM_POS) {
                telemetry.addData("Go down", "Yes");
                if(armPower>=0){
                    robot.ArmMotor.setPower(armPower);
                }
            }else{
                if(armPower>0){
                    robot.ArmMotor.setPower(0);
                    robot.ArmMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                }
            }
            //telemetry.update();


            if (turnIntake){
                robot.IntakeMotor.setPower(.2);
            }else if(reverseIntake){
                robot.IntakeMotor.setPower(-0.2);
            }else{
                robot.IntakeMotor.setPower(0);
            }

            telemetry.addData("Mode", robot.ArmMotor.getCurrentPosition());
            telemetry.update();
            //telemetry.addData("Power", "Frontleft=%.3f, Frontright=%.3f", FLPower, FRPower);
           // telemetry.addData("Power", "Backleft=%.3f, Backright=%.3f", BLPower, BRPower);

            idle();
        }
    }
}
