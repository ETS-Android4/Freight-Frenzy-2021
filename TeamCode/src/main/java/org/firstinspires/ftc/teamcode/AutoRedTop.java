package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;

@Autonomous
public class AutoRedTop extends LinearOpMode {
    HardwareBIGBRAINBOTS robot = new HardwareBIGBRAINBOTS();
    OpenCvWebcam webCam;
    DuckDetectionPipeline pipeline;

    public enum DuckPosition
    {
        LEFT,
        CENTER,
        RIGHT
    }

    @Override
    public void runOpMode() {
        //super.runOpMode();
        final double COUNTS_PER_MOTOR_REV  =28;
        final double DRIVE_GEAR_REDUCTION  =1;
        final double WHEEL_DIAMETER_INCHES = 4;
        final double COUNTS_PER_INCH = 44.64;

        final double STRAFE_COUNTS_PER_INCH = 49.02;

        final int LOW = 1250;
        final int MID = 2000;
        final int HI = 2730;

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webCam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class,"Webcam 1"),cameraMonitorViewId);
        pipeline = new DuckDetectionPipeline();
        webCam.setPipeline(pipeline);

        webCam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                webCam.startStreaming(320,240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });

        robot.init(this.hardwareMap);
        robot.extendArm(1, -600);
        robot.DumperServo.setPosition(0.5);
        robot.extendArm(1, 600);

        int waitTime = 0;
        if (gamepad2.left_bumper){
            waitTime+=6000;
        }
        if (gamepad2.right_bumper){
            waitTime+=6000;
        }

        telemetry.addData("Wait time", waitTime);
        telemetry.update();
        waitForStart();

        sleep(waitTime);
        DuckPosition position = pipeline.getAnalysis();
        telemetry.addData("Analysis", position);
        telemetry.update();

        robot.drive(0.8,(int)(16*COUNTS_PER_INCH));
        robot.strafe(0.8,(int)(20*STRAFE_COUNTS_PER_INCH));

        robot.gyroTurn(1, 180, 0.01);
        robot.drive(0.5,(int)(-4*COUNTS_PER_INCH));

        if(position== DuckPosition.LEFT) {
            //robot.drive(1,(int)(6*COUNTS_PER_INCH));
            robot.extendArm(1, -LOW);
        }
        if(position== DuckPosition.CENTER){
            robot.extendArm(1, -MID);
        }
        if(position== DuckPosition.RIGHT) {
            //robot.drive(0.2,(int)(-1*COUNTS_PER_INCH));
            robot.extendArm(1, -HI);
        }
        robot.DumperServo.setPosition(1);
        sleep(500);
        robot.DumperServo.setPosition(0.5);
        if(position== DuckPosition.LEFT) {
            //robot.drive(1,(int)(6*COUNTS_PER_INCH));
            robot.extendArm(1, LOW);
        }
        if(position== DuckPosition.CENTER){
            robot.extendArm(1, MID);
        }
        if(position== DuckPosition.RIGHT) {
            //robot.drive(0.2,(int)(-1*COUNTS_PER_INCH));
            robot.extendArm(1, HI);
        }

        robot.drive(1,(int)(12*COUNTS_PER_INCH));
        robot.gyroTurn(1, -90, 0.01);
        robot.strafe(0.4, (int)(-11*STRAFE_COUNTS_PER_INCH));
        robot.drive(1,(int)(48*COUNTS_PER_INCH));


        telemetry.addData("program", "finished");
        telemetry.update();
    }

    public static class DuckDetectionPipeline extends OpenCvPipeline
    {

        // An enum to define the Duck position

        // Some color constants
        static final Scalar BLUE = new Scalar(0, 0, 255);
        static final Scalar GREEN = new Scalar(0, 255, 0);

        /*
         * The core values which define the location and size of the sample regions
         */
        static final Point REGION1_TOPLEFT_ANCHOR_POINT = new Point(30,98);
        static final Point REGION2_TOPLEFT_ANCHOR_POINT = new Point(160,98);
        static final Point REGION3_TOPLEFT_ANCHOR_POINT = new Point(280,98);
        static final int REGION_WIDTH = 30;
        static final int REGION_HEIGHT = 30;

        /*
         * Points which actually define the sample region rectangles, derived from above values
         *
         * Example of how points A and B work to define a rectangle
         *
         *   ------------------------------------
         *   | (0,0) Point A                    |
         *   |                                  |
         *   |                                  |
         *   |                                  |
         *   |                                  |
         *   |                                  |
         *   |                                  |
         *   |                  Point B (70,50) |
         *   ------------------------------------
         *
         */
        Point region1_pointA = new Point(
                REGION1_TOPLEFT_ANCHOR_POINT.x,
                REGION1_TOPLEFT_ANCHOR_POINT.y);
        Point region1_pointB = new Point(
                REGION1_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
                REGION1_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);
        Point region2_pointA = new Point(
                REGION2_TOPLEFT_ANCHOR_POINT.x,
                REGION2_TOPLEFT_ANCHOR_POINT.y);
        Point region2_pointB = new Point(
                REGION2_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
                REGION2_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);
        Point region3_pointA = new Point(
                REGION3_TOPLEFT_ANCHOR_POINT.x,
                REGION3_TOPLEFT_ANCHOR_POINT.y);
        Point region3_pointB = new Point(
                REGION3_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
                REGION3_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);

        /*
         * Working variables, LAB color space, Channel a, Channel b
         * Channel b is used to distinguish Yellow from Blue
         * Channel a is used to distinguish Yellow form Red, also works to distinguish from Blue
         */
        Mat region1_Cb, region2_Cb, region3_Cb;
        Mat region1_Ca, region2_Ca, region3_Ca;
        //   Mat BGR = new Mat();
        Mat LAB = new Mat();
        Mat Cb = new Mat();
        Mat Ca = new Mat();
        int avg1, avg2, avg3;

        // Volatile since accessed by OpMode thread w/o synchronization
        private volatile DuckPosition position = DuckPosition.LEFT;

        /*
         * This function takes the RGB frame, converts to LAB,
         * and extracts the Cb or Ca channel to the 'Cb', or 'Ca' variable
         */
        void inputToCb(Mat input)
        {
            Imgproc.cvtColor(input,LAB,Imgproc.COLOR_RGB2Lab);
            Core.extractChannel(LAB,Cb,2);
        }
        void inputToCa(Mat input){
            Imgproc.cvtColor(input,LAB,Imgproc.COLOR_RGB2Lab);
            Core.extractChannel(LAB,Ca,1);
        }

        @Override
        public void init(Mat firstFrame)
        {
            /*
             * We need to call this in order to make sure the 'Cb'
             * object is initialized, so that the submats we make
             * will still be linked to it on subsequent frames. (If
             * the object were to only be initialized in processFrame,
             * then the submats would become delinked because the backing
             * buffer would be re-allocated the first time a real frame
             * was crunched)
             */

              inputToCb(firstFrame);    // For Red alliance
            /*
             * Submats are a persistent reference to a region of the parent
             * buffer. Any changes to the child affect the parent, and the
             * reverse also holds true.
             */
             region1_Cb = Cb.submat(new Rect(region1_pointA, region1_pointB));
             region2_Cb = Cb.submat(new Rect(region2_pointA, region2_pointB));
             region3_Cb = Cb.submat(new Rect(region3_pointA, region3_pointB));
        }

        @Override
        public Mat processFrame(Mat input)
        {

            // Get the Cb or Ca channel of the input frame after conversion to LAB
             inputToCb(input);
            /*
             * Compute the average pixel value of each submat region. We're
             * taking the average of a single channel buffer, so the value
             * we need is at index 0. We could have also taken the average
             * pixel value of the 3-channel image, and referenced the value
             * at index 2 here.
             */
             avg1 = (int) Core.mean(region1_Cb).val[0];
             avg2 = (int) Core.mean(region2_Cb).val[0];
             avg3 = (int) Core.mean(region3_Cb).val[0];
            /*
             * Draw a rectangle showing sample region 1 on the screen.
             * Simply a visual aid. Serves no functional purpose.
             */
            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region1_pointA, // First point which defines the rectangle
                    region1_pointB, // Second point which defines the rectangle
                    BLUE, // The color the rectangle is drawn in
                    2); // Thickness of the rectangle lines

            /*
             * Draw a rectangle showing sample region 2 on the screen.
             * Simply a visual aid. Serves no functional purpose.
             */
            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region2_pointA, // First point which defines the rectangle
                    region2_pointB, // Second point which defines the rectangle
                    BLUE, // The color the rectangle is drawn in
                    2); // Thickness of the rectangle lines

            /*
             * Draw a rectangle showing sample region 3 on the screen.
             * Simply a visual aid. Serves no functional purpose.
             */
            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region3_pointA, // First point which defines the rectangle
                    region3_pointB, // Second point which defines the rectangle
                    BLUE, // The color the rectangle is drawn in
                    2); // Thickness of the rectangle lines


            /*
             * Find the minimum of the 3 averages
             */

            int maxOneTwo = Math.max(avg1,avg2);
            int max = Math.max(maxOneTwo,avg3);

            /*
             * Now that we found the max, we actually need to go and
             * figure out which sample region that value was from
             */
            if(max == avg1) // Was it from region 1?
            {
                position = DuckPosition.LEFT; // Record our analysis

                /*
                 * Draw a solid rectangle on top of the chosen region.
                 * Simply a visual aid. Serves no functional purpose.
                 */
                Imgproc.rectangle(
                        input, // Buffer to draw on
                        region1_pointA, // First point which defines the rectangle
                        region1_pointB, // Second point which defines the rectangle
                        GREEN, // The color the rectangle is drawn in
                        -1); // Negative thickness means solid fill
            }
            else if (max == avg2) // Was it from region 2?
            {
                position = DuckPosition.CENTER; // Record our analysis

                /*
                 * Draw a solid rectangle on top of the chosen region.
                 * Simply a visual aid. Serves no functional purpose.
                 */
                Imgproc.rectangle(
                        input, // Buffer to draw on
                        region2_pointA, // First point which defines the rectangle
                        region2_pointB, // Second point which defines the rectangle
                        GREEN, // The color the rectangle is drawn in
                        -1); // Negative thickness means solid fill
            }
            else if(max == avg3) // Was it from region 3?
            {
                position = DuckPosition.RIGHT; // Record our analysis
                /*
                 * Draw a solid rectangle on top of the chosen region.
                 * Simply a visual aid. Serves no functional purpose.
                 */
                Imgproc.rectangle(
                        input, // Buffer to draw on
                        region3_pointA, // First point which defines the rectangle
                        region3_pointB, // Second point which defines the rectangle
                        GREEN, // The color the rectangle is drawn in
                        -1); // Negative thickness means solid fill
            }

            /*
             * Render the 'input' buffer to the viewport. But note this is not
             * simply rendering the raw camera feed, because we called functions
             * to add some annotations to this buffer earlier up.
             */
            return input;
        }

        /*
         * Call this from the OpMode thread to obtain the latest analysis
         */
        public DuckPosition getAnalysis()
        {
            return position;
        }
    }
}
