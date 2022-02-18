package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.util.Range;


import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;


import java.util.List;

import static java.lang.Math.abs;

@Autonomous (name ="ImuTest")
public class ImuTest extends LinearOpMode {

    HardwareBIGBRAINBOTS robot=new HardwareBIGBRAINBOTS();

    private BNO055IMU imu;
    static final double TURN_SPEED = 0.75;
    static final double P_TURN_COEFF_1 = 0.025;
    static final double P_TURN_COEEF_2 = 0.0035;
    static final double HEADING_THRESHOLD = 0.5;
    //imu stuff end

    static final double COUNTS_PER_INCH = 43.5; //handy for using inches

    @Override
    public void runOpMode() throws InterruptedException {

        //gets all the hardware from HARDWAREBIGBRAINBOTS
        robot.init(this.hardwareMap);

        //initialize IMU
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
        Orientation orientation = imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
        telemetry.addData("Third angle", "%.1f", orientation.thirdAngle);
        telemetry.update();
        //initialize IMU end

    /*    telemetry.addData("Mode", "waiting");
        telemetry.update();  */
        waitForStart();
   //     gyroCheck();

        gyroTurn(TURN_SPEED, 90, P_TURN_COEFF_1);
        sleep(5000);
        gyroTurn(TURN_SPEED, 0, P_TURN_COEEF_2);
        telemetry.addData("turn", "finished");
        telemetry.update();


        //      gyroTurn(TURN_SPEED, 0, P_TURN_COEFF_1);

        //     gyroTurn(TURN_SPEED, 90, P_TURN_COEEF_2);


    }
    public void gyroCheck(){

        robot.FrontLeftDrive.setPower(0.5);
        robot.RearLeftDrive.setPower(0.5);
        robot.FrontRightDrive.setPower(-0.5);
        robot.RearRightDrive.setPower(-0.5);
        sleep(1000);
        robot.FrontLeftDrive.setPower(0);
        robot.RearLeftDrive.setPower(0);
        robot.FrontRightDrive.setPower(0);
        robot.RearRightDrive.setPower(0);
        Orientation orientation = imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
        telemetry.addData("Third angle", "%.1f", orientation.thirdAngle);
        telemetry.update();

        sleep(10000);

        robot.FrontLeftDrive.setPower(-0.5);
        robot.RearLeftDrive.setPower(-0.5);
        robot.FrontRightDrive.setPower(0.5);
        robot.RearRightDrive.setPower(0.5);
        sleep(1000);
        robot.FrontLeftDrive.setPower(0);
        robot.RearLeftDrive.setPower(0);
        robot.FrontRightDrive.setPower(0);
        robot.RearRightDrive.setPower(0);
        orientation = imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
        telemetry.addData("Third angle", "%.1f", orientation.thirdAngle);
        telemetry.update();

        sleep(10000);

    }

    public void gyroTurn(double speed, double angle, double coeff) {
        double error;
        double steer;
        double leftSpeed, rightSpeed;
        boolean onTarget = false;
        error = getError(angle);
        while (Math.abs(error) > HEADING_THRESHOLD) {
            steer = Range.clip(coeff * error, -speed, speed);
            rightSpeed = steer;
            leftSpeed = -rightSpeed;
            robot.FrontLeftDrive.setPower(leftSpeed);
            robot.RearLeftDrive.setPower(leftSpeed);
            robot.FrontRightDrive.setPower(rightSpeed);
            robot.RearRightDrive.setPower(rightSpeed);

            telemetry.addData("Target", "%5.2f", angle);
            telemetry.addData("Err/St", "%5.2f/%5.2f", error, steer);
            telemetry.addData("Speed.", "%5.4f:%5.4f", leftSpeed, rightSpeed);
            telemetry.update();
            error = getError(angle);
        }
        robot.FrontLeftDrive.setPower(0);
        robot.FrontRightDrive.setPower(0);
        robot.RearLeftDrive.setPower(0);
        robot.RearRightDrive.setPower(0);
        sleep(1000);
  /*      telemetry.addData("turn","stopped");
        telemetry.update();   */
    }

    public double getError(double targetAngle) {
        double angleError;
        Orientation orientation = imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
        angleError = targetAngle - orientation.thirdAngle;

        if (angleError > 180) {
            angleError = angleError - 360;
        }
        if (angleError <= -180) {
            angleError = angleError + 360;
        }
        return angleError;
    }


}
