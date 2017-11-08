/*
AutoLibrary_v1
September 2017
6210 Software
- William Fisher
- Rohit Chawla
- Nihal Kyasa

Holds methods to be used for Autonomous programs in FTC's Relic Recovery Competition.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;


public abstract class AutoLibrary_v2 extends LinearOpMode {

    BNO055IMU gyro;
    Orientation angles;
    Acceleration gravity;
    NormalizedColorSensor colorSensor;
    ColorSensor gemSensor;

    public static final String TAG = "Vuforia VuMark Sample";
    OpenGLMatrix lastLocation = null;
    VuforiaLocalizer vuforia;
    VuforiaTrackable relicTemplate;
    VuforiaTrackables relicTrackables;

    public DcMotor bldrive;
    public DcMotor brdrive;
    public DcMotor fldrive;
    public DcMotor frdrive;
    public DcMotor topTrack;
    public DcMotor rOutput;
    public DcMotor lOutput;
    public CRServo belt;
    public CRServo rIntake;
    public CRServo lIntake;

    public Servo gemArm;
    public Servo gemFlick;

    boolean hold = false;

    //method initialize's the robot
    public void initialize() throws InterruptedException {
        frdrive = hardwareMap.get(DcMotor.class, "fr");
        fldrive = hardwareMap.get(DcMotor.class, "fl");
        brdrive = hardwareMap.get(DcMotor.class, "br");
        bldrive = hardwareMap.get(DcMotor.class, "bl");
        topTrack = hardwareMap.get(DcMotor.class, "topt");
        rOutput = hardwareMap.get(DcMotor.class, "rOut");
        lOutput = hardwareMap.get(DcMotor.class, "lOut");
        belt = hardwareMap.get(CRServo.class, "belt");
        rIntake = hardwareMap.get(CRServo.class, "rIn");
        lIntake = hardwareMap.get(CRServo.class, "lIn");
        gemFlick = hardwareMap.get(Servo.class, "gF");

//        fldrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        frdrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        fldrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        frdrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        brdrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        bldrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

//        vision_init();

        gemSensor = hardwareMap.get(ColorSensor.class, "csGem");
//        gemSensor = hardwareMap.get(NormalizedColorSensor.class, "gemSensor");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "GRYO";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        gyro = hardwareMap.get(BNO055IMU.class, "gyro");
        gyro.initialize(parameters);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        gemFlick.setPosition(.5);

        waitForStart();
    }

    // Method condenses init of Vuforia for readability
    public void vision_init() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "ARUX4tP/////AAAAGXY2Dg+/sUl6gWdYntfHvN8GT9v/tqySPvCz3Nt2dTXFWQC7TJriGnCTY/vvHRRUFiSSI11yfUxGSTkNzXbHM0zBmGf3WiW6+kZsArc76UHXbUG1fHmPyIAljbqRBiNz8Kki/PlrJCwpNwmcZKNu8wvnYzGZ5phfZHXE6yyr2HvuEyX6IEYUvrvDtMImiHWHSbjK5wbgDyMinQU/FsVmDy0S1OHL+xVDk6yhjBsPBO2bsVMTKA3GRZAo+Qxjqd9nh95+jPt1EbE11VgPHzr/Zm8bKrr+gz24uxfsTgXU3sc6YLgdcegkRd6dxM5gvsu4xisSks+gkLismFPmNASP0JpDkom80KZ9MmEcbl7GnLO+";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary
    }

    //====================== BASIC MOVEMENT METHODS ======================
    //These methods set motor power only

    //set motors to power such that they move in the y-axis
    public void move_yaxis_basic(double power) {
        frdrive.setPower(power);
        brdrive.setPower(power);
        fldrive.setPower(-power);
        bldrive.setPower(-power);
    }

    //set motors to power such that they move in the x-axis
    public void move_x_axis_basic(double power) {
        frdrive.setPower(power);
        brdrive.setPower(-power);
        fldrive.setPower(power);
        bldrive.setPower(-power);
    }

    //set motors to power such that they move in any direction in the xy plane
    public void move_biaxis_basic(double ypower, double xpower) {
        frdrive.setPower(ypower + xpower);
        brdrive.setPower(ypower - xpower);
        fldrive.setPower(-(ypower - xpower));
        bldrive.setPower(-(ypower + xpower));
    }

    //set motors to power such that the robot turns in place
    public void turn_basic(double power) {
        frdrive.setPower(-power);
        brdrive.setPower(-power);
        fldrive.setPower(-power);
        bldrive.setPower(-power);
    }

    //stop motors
    public void stop_motors() {
        frdrive.setPower(0);
        brdrive.setPower(0);
        fldrive.setPower(0);
        bldrive.setPower(0);
    }

    //====================== ENCODER ONLY MOVEMENT METHODS ======================
    //Uses encoders to move the robot a set distance

    //recieves average enccoder value between the 4 drive motors
    public double getEncoderAvg() {
        return ((frdrive.getCurrentPosition() + fldrive.getCurrentPosition() + brdrive.getCurrentPosition() + bldrive.getCurrentPosition()) / 4);
    }

    //Uses encoders to move a set distance in xy plane
    public void move_encoder(double ypower, double xpower, double distance) {
        double start = getEncoderAvg();
        while (Math.abs(getEncoderAvg() - start) < distance && opModeIsActive()) {
            move_biaxis_basic(ypower, xpower);
        }
        stop_motors();
    }

    //Uses encoders to turn a set distance (not angle)
    public void turn_encoder(double power, double distance) {
        double start = getEncoderAvg();
        while (Math.abs(getEncoderAvg() - start) < distance && opModeIsActive()) {
            turn_basic(power);
        }
        stop_motors();
    }

    //====================== GYRO CORRECTION MOVEMENT ===================
    //Specific gyro correction movement methods, mostly support.
    //Use gyro to get accurate turn or correction angle while moving

    //Returns shortest difference between two angles (relative angle), accounting for 360 to 0 skip
    //Use this whenever calculating angle difference
    public double angle_delta(double currentAngle, double targetAngle) {
        double delta = targetAngle - currentAngle;
        if (delta < -180) {
            delta += 360;
        }
        else if (delta > 180)
        {
            delta -= 360;
        }
        return delta;
    }

    /* Motor correction methods
    The next four methods use the gyro to edit the power of a specific motor. This allows the robot correct it's angle.
    * The method returns a coeffienct that the motor power must be multiplied by.
    * Never use power of .8 when using intensity of 1.
    * Ypower power formula is unique for each motor - that's why there is four methods

    NOTE flipped y power values on all corrections since y axis was working in general formula. If doesn't work, switch between two linear corrections for auto
    */
    public double getfrcorrection(double ypower, double xpower, double targetAngle, double threshold, double intensity)
    {
        double output = 1;
        if (angle_delta(getAngle(), targetAngle) < threshold)
        {
            output = 1 - (-ypower - xpower) / Math.abs(ypower - xpower) * Math.atan(Math.abs(angle_delta(getAngle(), targetAngle) - threshold)) * intensity / 6.28;
        }
        else if (angle_delta(getAngle(), targetAngle) > threshold)
        {
            output = 1 + (-ypower - xpower) / Math.abs(ypower - xpower) * Math.atan(Math.abs(angle_delta(getAngle(), targetAngle) - threshold)) * intensity / 6.28;
        }
        return output;
    }

    public double getbrcorrection(double ypower, double xpower, double targetAngle, double threshold, double intensity)
    {
        double output = 1;
        if (angle_delta(getAngle(), targetAngle) < threshold)
        {
            output = 1 - (-ypower + xpower) / Math.abs(ypower + xpower) * Math.atan(Math.abs(angle_delta(getAngle(), targetAngle) - threshold)) * intensity / 6.28;
        }
        else if (angle_delta(getAngle(), targetAngle) > threshold)
        {
            output = 1 + (-ypower + xpower) / Math.abs(ypower + xpower) * Math.atan(Math.abs(angle_delta(getAngle(), targetAngle) - threshold)) * intensity / 6.28;
        }
        return output;
    }

    public double getflcorrection(double ypower, double xpower, double targetAngle, double threshold, double intensity)
    {
        double output = 1;
        if (angle_delta(getAngle(), targetAngle) < threshold)
        {
            output = 1 - (ypower - xpower) / Math.abs(-ypower - xpower) * Math.atan(Math.abs(angle_delta(getAngle(), targetAngle) - threshold)) * intensity / 6.28;
        }
        else if (angle_delta(getAngle(), targetAngle) > threshold)
        {
            output = 1 + (ypower - xpower) / Math.abs(-ypower - xpower) * Math.atan(Math.abs(angle_delta(getAngle(), targetAngle) - threshold)) * intensity / 6.28;
        }
        return output;
    }

    public double getblcorrection(double ypower, double xpower, double targetAngle, double threshold, double intensity)
    {
        double output = 1;
        if (angle_delta(getAngle(), targetAngle) < threshold)
        {
            output = 1 - (-ypower + xpower) / Math.abs(ypower + xpower) * Math.atan(Math.abs(angle_delta(getAngle(), targetAngle) - threshold)) * intensity / 6.28;
        }
        else if (angle_delta(getAngle(), targetAngle) > threshold)
        {
            output = 1 + (-ypower + xpower) / Math.abs(ypower + xpower) * Math.atan(Math.abs(angle_delta(getAngle(), targetAngle) - threshold)) * intensity / 6.28;
        }
        return output;
    }

    //uses gyro to turn a set angle
    public void turn_gyro(double power, double targetAngle, double threshold)
    {
        while (Math.abs(angle_delta(getAngle(), targetAngle)) > threshold && opModeIsActive())
        {
            if (angle_delta(getAngle(), targetAngle) > 0)
            {
                turn_basic(power);
            }
            else
            {
                turn_basic(-power);
            }
        }
    }

    //====================== ENCODER + GYRO MOVE ======================

    // Combines encoder and gyro movement for a angle corrected move at a set distance
    // Never use power of .8 when using intensity of 1.
    public void move_advanced(double ypower, double xpower, double targetAngle, double threshold, double intensity, double distance) {
        double start = getEncoderAvg();
        while (Math.abs(getEncoderAvg() - start) < distance && opModeIsActive()) {
            frdrive.setPower((ypower + xpower) * getfrcorrection(ypower, xpower, targetAngle, threshold, intensity));
            brdrive.setPower((ypower - xpower) * getbrcorrection(ypower, xpower, targetAngle, threshold, intensity));
            fldrive.setPower(-(ypower - xpower) * getflcorrection(ypower, xpower, targetAngle, threshold, intensity));
            bldrive.setPower(-(ypower + xpower) * getblcorrection(ypower, xpower, targetAngle, threshold, intensity));
        }
        stop_motors();
    }

    //====================== TIME ONLY MOVEMENT METHODS ======================
    //These methods use time to move a set distance (as opposed to encoder)

    //Uses time to move a set distance
    public void move_timed(double xpower, double ypower, double duration)
    {
        double start = System.currentTimeMillis();
        while (Math.abs(System.currentTimeMillis() - start) < duration && opModeIsActive())
        {
            move_biaxis_basic(ypower, xpower);
        }
        stop_motors();
    }

    //Uses time to move a set distance with angle corrections
    public void move_advanced_timed(double ypower, double xpower, double targetAngle, double threshold, double intensity, double duration)
    {
        double start = System.currentTimeMillis();
        while (Math.abs(System.currentTimeMillis() - start) < duration && opModeIsActive()) {
            frdrive.setPower((ypower + xpower) * getfrcorrection(ypower, xpower, targetAngle, threshold, intensity));
            brdrive.setPower((ypower - xpower) * getbrcorrection(ypower, xpower, targetAngle, threshold, intensity));
            fldrive.setPower(-(ypower - xpower) * getflcorrection(ypower, xpower, targetAngle, threshold, intensity));
            bldrive.setPower(-(ypower + xpower) * getblcorrection(ypower, xpower, targetAngle, threshold, intensity));
        }
        stop_motors();
    }

    //====================== PID =============================
    //Advanced movement method which uses calculus move an very precise set distance.

    //Uses PID to move a very precise set distance
    //kporp - Proportional - power is proportional to distance remaining (ERROR) - slowing down near the end
    //kintg - Integral - power is risen based on ERROR*TIME - counterbalancing kprop and ensuring it reachs 0 error
    //kderv - Derivative - power is based on rate of change rate of change of error - predicts future path and corrects for it
    public void move_PID(double ypower, double xpower, double kporp, double kintg, double kderv, double distance, double threshold) {
        double error = distance;
        double totalError = 0;
        double prevTime = System.currentTimeMillis();
        while (Math.abs(error) > threshold && opModeIsActive()) {
            double currTime = System.currentTimeMillis();
            double deltaTime = currTime - prevTime;
            prevTime = currTime;
            error = distance - getEncoderAvg();
            totalError = error * deltaTime;
            double prop = kporp * error;
            double intg = kintg * totalError;
            double derv = kderv * (error / deltaTime);
            double PIDmod = prop + intg + derv;
            frdrive.setPower((ypower + xpower) * PIDmod);
            brdrive.setPower((ypower - xpower) * PIDmod);
            fldrive.setPower(-(ypower - xpower) * PIDmod);
            bldrive.setPower(-(ypower + xpower) * PIDmod);
        }
        stop_motors();
    }

    //======================= PID + GRYO =========================

    //move method using both PID and gyro correction for a very precise move
    public void move_advancedplus(double ypower, double xpower, double kporp, double kintg, double kderv, double distance, double angle, double thresholdPID, double thresholdGyro, double intensityGryo) {
        double error = distance;
        double totalError = 0;
        double prevTime = System.currentTimeMillis();
        while (Math.abs(error) > thresholdPID && opModeIsActive()) {
            double currTime = System.currentTimeMillis();
            double deltaTime = currTime - prevTime;
            prevTime = currTime;
            error = distance - getEncoderAvg();
            totalError = error * deltaTime;
            double prop = kporp * error;
            double intg = kintg * totalError;
            double derv = kderv * (error / deltaTime);
            double PIDmod = prop + intg + derv;
            frdrive.setPower((ypower + xpower) * PIDmod * getfrcorrection(ypower, xpower, angle, thresholdGyro, intensityGryo));
            brdrive.setPower((ypower - xpower) * PIDmod * getbrcorrection(ypower, xpower, angle, thresholdGyro, intensityGryo));
            fldrive.setPower(-(ypower - xpower) * PIDmod * getflcorrection(ypower, xpower, angle, thresholdGyro, intensityGryo));
            bldrive.setPower(-(ypower + xpower) * PIDmod * getblcorrection(ypower, xpower, angle, thresholdGyro, intensityGryo));
        }
        stop_motors();
    }

    //turn method that uses PID logic with the gyro for a very accurate turn (ERROR = angle remaining)
    public void turn_PID(double power, double kporp, double kintg, double kderv, double targetAngle, double threshold)
    {
        double error = Math.abs(angle_delta(getAngle(), targetAngle));
        double totalError = 0;
        double prevTime = System.currentTimeMillis();
        while (Math.abs(error) > threshold && opModeIsActive())
        {
            double currTime = System.currentTimeMillis();
            double deltaTime = currTime - prevTime;
            prevTime = currTime;
            error = Math.abs(angle_delta(getAngle(), targetAngle));
            totalError = error * deltaTime;
            double prop = kporp * error;
            double intg = kintg * totalError;
            double derv = kderv * (error / deltaTime);
            double PIDmod = prop + intg + derv;
            if (angle_delta(getAngle(), targetAngle) > 0)
            {
                turn_basic(power * PIDmod);
            }
            else
            {
                turn_basic(-power * PIDmod);
            }
        }
        stop_motors();
    }

    //====================== SPECIALIZED MOVEMENT / PATH-ING ========================

    //Movement method (w/ gryo correction) that moves till it finds a taped line on the floor. IsRed controls whether it detects blue or red lines
    public void move2Line(double ypower, double xpower, double cutoff, double targetAngle, double threshold, double intensity, double thresholdColor, boolean isRed) {
        double start = getEncoderAvg();
        if (isRed) {
            while (getFloorRed() < thresholdColor && Math.abs(getEncoderAvg() - start) < cutoff && opModeIsActive()) {
                frdrive.setPower((ypower + xpower) * getfrcorrection(ypower, xpower, targetAngle, threshold, intensity));
                brdrive.setPower((ypower - xpower) * getbrcorrection(ypower, xpower, targetAngle, threshold, intensity));
                fldrive.setPower(-(ypower - xpower) * getflcorrection(ypower, xpower, targetAngle, threshold, intensity));
                bldrive.setPower(-(ypower + xpower) * getblcorrection(ypower, xpower, targetAngle, threshold, intensity));
            }
        } else {
            while (getFloorBlue() < thresholdColor && Math.abs(getEncoderAvg() - start) < cutoff && opModeIsActive()) {
                frdrive.setPower((ypower + xpower) * getfrcorrection(ypower, xpower, targetAngle, threshold, intensity));
                brdrive.setPower((ypower - xpower) * getbrcorrection(ypower, xpower, targetAngle, threshold, intensity));
                fldrive.setPower(-(ypower - xpower) * getflcorrection(ypower, xpower, targetAngle, threshold, intensity));
                bldrive.setPower(-(ypower + xpower) * getblcorrection(ypower, xpower, targetAngle, threshold, intensity));
            }

        }
        stop_motors();
    }

    //above - but with power oscillating from on to 0. Motion makes color sensors inaccurate, so staggering motion may improve accuracy
    public void move2Line_stagger(double ypower, double xpower, double cutoff, double targetAngle, double threshold, double intensity, double thresholdColor, boolean isRed) {
        double start = getEncoderAvg();
        if (isRed) {
            while (getFloorRed() < thresholdColor && Math.abs(getEncoderAvg() - start) < cutoff && opModeIsActive()) {
                frdrive.setPower((ypower + xpower) * getfrcorrection(ypower, xpower, targetAngle, threshold, intensity));
                brdrive.setPower((ypower - xpower) * getbrcorrection(ypower, xpower, targetAngle, threshold, intensity));
                fldrive.setPower(-(ypower - xpower) * getflcorrection(ypower, xpower, targetAngle, threshold, intensity));
                bldrive.setPower(-(ypower + xpower) * getblcorrection(ypower, xpower, targetAngle, threshold, intensity));
                sleep(100);
                stop_motors();
                sleep(50);
            }
        } else {
            while (getFloorBlue() < thresholdColor && Math.abs(getEncoderAvg() - start) < cutoff && opModeIsActive()) {
                frdrive.setPower((ypower + xpower) * getfrcorrection(ypower, xpower, targetAngle, threshold, intensity));
                brdrive.setPower((ypower - xpower) * getbrcorrection(ypower, xpower, targetAngle, threshold, intensity));
                fldrive.setPower(-(ypower - xpower) * getflcorrection(ypower, xpower, targetAngle, threshold, intensity));
                bldrive.setPower(-(ypower + xpower) * getblcorrection(ypower, xpower, targetAngle, threshold, intensity));
                sleep(100);
                stop_motors();
                sleep(50);
            }
        }
        stop_motors();
    }

    //====================== MANIPULATORS ===================================

    //starts intake motors. will run until stopped
    public void startIntake(double power) {
        lIntake.setPower(-power);
        rIntake.setPower(power);
        belt.setPower(power);
    }

    //stops intake motors
    public void stopIntake() {
        startIntake(0);
    }

    //starts output motors, will run until stopped
    public void startOutput(double power)
    {
        lOutput.setPower(-power);
        rOutput.setPower(power);
    }

    //stops output motors
    public void stopOutput() { startOutput(0); }

    //Moves top track a set distance based on encoder values
    public void moveTopTrack(double power, double distance) {
        double start = topTrack.getCurrentPosition();
        while (Math.abs(topTrack.getCurrentPosition() - start) < distance && opModeIsActive()) {
            topTrack.setPower(-power);
        }
        if (hold)
        {
            topTrack.setPower(.4);
        }
        else
        {
            topTrack.setPower(0);
        }
    }

    //toggles whether the top track should hold or not
    public void holdTopTrackToggle(double power)
    {
        if (!hold)
        {
            hold = true;
        }
        else
        {
            hold = false;
        }
    }

    //unfolds robot (do not use with hold)
    public void unfoldRobo()
    {
        moveTopTrack(-.6, 300);
    }

    //Uses gem Flick servo and color sensor to detect the the jewel and knock off the correct one
    //returns true if successful
    public boolean getGem(double extension, int threshold, boolean isRed) {
//        gemArm.setPosition(extension);
        telemetry.addLine("starting getGEM");
        telemetry.update();
        sleep(100);
        if (getBlue() > getRed() && getBlue() > threshold) {
            telemetry.addLine("blue detected");
            telemetry.update();
            if (isRed) {gemFlick.setPosition(1);}
            else {gemFlick.setPosition(0);}
            sleep(500);
            return true;
        }
        else if (getRed() > getBlue() && getRed() > threshold) {
            telemetry.addLine("red detected");
            telemetry.update();
            if(isRed) {gemFlick.setPosition(0);}
            else {gemFlick.setPosition(1);}
            sleep(500);
            return true;
        }
        else {
            telemetry.addLine("color sensing failed");
            telemetry.update();
            sleep(100);
            return false;
        }
    }

    //Above, but makes multiple attempts
    public void getGemMultitry(double extension, int threshold, boolean isRed, int tries, double angle)
    {
        for (int i = 1; i < tries; i++) {
            if (!getGem(extension, threshold, isRed)) {
                turn_gyro(.2, angle, 2);
            }
        }
    }

    //sets gem arm back to start position, .5
    public void resetGemArm()
    {
        gemFlick.setPosition(.5);
    }

    public void relic() {
        //empty
    }

    //====================== SENSORS ======================

    //returns the z axis angle from the robot
    public double getAngle()
    {
        angles   = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return angles.firstAngle;
    }

    //returns the red value from the gemsensor
    public double getRed() {
        telemetry.addData("red", gemSensor.red());
        telemetry.update();
        sleep(500);
        return gemSensor.red();
    }

    //returns the blue value from the gemsensor
    public double getBlue() {
        telemetry.addData("blue", gemSensor.blue());
        telemetry.update();
        sleep(500);
        return gemSensor.blue();
    }

    //get the alpha value forom the gem sensor
    public double getBrightness() {
        return gemSensor.alpha();
    }

    //gets the red value from the floor
    public double getFloorRed() {
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        return colors.red;
    }

    //gets the blue value from the floor
    public double getFloorBlue() {
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        return colors.blue;
    }

    //Uses vuforia to detect the mark
    //Note - RelicRecoveryVuMark is a Class unique to vuforia code - think of it as a data type like boolean or double
    //can return RelicRecoveryVuMark.UNKNOWN, R-.RIGHT, R-.LEFT, or R-.CENTER
    public RelicRecoveryVuMark getSymbol() {
        relicTrackables.activate();
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
        relicTrackables.deactivate();
        return vuMark;
    }

    //Above, but attempts multiple times
    public RelicRecoveryVuMark getSymbol_multitry(int tries, double angle)
    {
        relicTrackables.activate();
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
        for (int i = 1; i < tries; i++) {
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
                turn_gyro(.2, angle, 2);
                vuMark = RelicRecoveryVuMark.from(relicTemplate);
            }
            sleep(100);
        }
        return  vuMark;
    }
}
