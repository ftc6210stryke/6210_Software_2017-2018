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
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
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


public abstract class AutoLibrary_v1 extends LinearOpMode {

    BNO055IMU gyro;
    Orientation angles;
    Acceleration gravity;
    NormalizedColorSensor colorSensor;
    NormalizedColorSensor gemSensor;

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
    public DcMotor rIntake; //vexmotor
    public DcMotor lIntake; //vexmotor
    public DcMotor rOutput; //vexmotor
    public DcMotor lOutput; //vexmotor

    public Servo gemArm;
    public Servo gemFlick;

    public void initialize() {
        frdrive = hardwareMap.get(DcMotor.class, "a");
        fldrive = hardwareMap.get(DcMotor.class, "b");
        brdrive = hardwareMap.get(DcMotor.class, "c");
        bldrive = hardwareMap.get(DcMotor.class, "d");
        fldrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frdrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fldrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frdrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        brdrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bldrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        vision_init();

        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "colorSensor");
        gemSensor = hardwareMap.get(NormalizedColorSensor.class, "gemSensor");
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

        waitForStart();
    }

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

    public void move_yaxis_basic(double power) {
        frdrive.setPower(power);
        brdrive.setPower(power);
        fldrive.setPower(-power);
        bldrive.setPower(-power);
    }

    public void move_x_axis_basic(double power) {
        frdrive.setPower(power);
        brdrive.setPower(-power);
        fldrive.setPower(power);
        bldrive.setPower(-power);
    }

    public void move_biaxis_basic(double ypower, double xpower) {
        frdrive.setPower(ypower + xpower);
        brdrive.setPower(ypower - xpower);
        fldrive.setPower(-(ypower - xpower));
        bldrive.setPower(-(ypower + xpower));
    }

    public void turn_basic(double power) {
        frdrive.setPower(-power);
        brdrive.setPower(-power);
        fldrive.setPower(-power);
        brdrive.setPower(-power);
    }

    public void stop_motors() {
        frdrive.setPower(0);
        brdrive.setPower(0);
        fldrive.setPower(0);
        brdrive.setPower(0);
    }

    //====================== ENCODER ONLY MOVEMENT METHODS ======================

    public double getEncoderAvg() {
        return ((frdrive.getCurrentPosition() + fldrive.getCurrentPosition() + brdrive.getCurrentPosition() + bldrive.getCurrentPosition()) / 4);
    }

    public void move_encoder(double ypower, double xpower, double distance) {
        double start = getEncoderAvg();
        while (Math.abs(getEncoderAvg() - start) < distance) {
            move_biaxis_basic(ypower, xpower);
        }
        stop_motors();
    }

    public void turn_encoder(double power, double distance) {
        double start = getEncoderAvg();
        while (Math.abs(getEncoderAvg() - start) < distance) {
            turn_basic(power);
        }
        stop_motors();
    }

    //====================== GYRO CORRECTION MOVEMENT ===================

    //DO NOT SET POWER ABOVE .8 when using standard intensity (1)
    //Intensity should be a decimal number close to 1, not greater than 1.5
    public double getlcorrection(double targetAngle, double threshold, double intensity) {
        double lcorrection = 1;
        if (targetAngle - getAngle() > threshold) {
            lcorrection = -Math.atan(Math.abs(targetAngle - getAngle()) - threshold) * intensity / 6.28;
            // OLD 1-AXIS FORMULA lcorrection = 1 - Math.atan(Math.abs(targetAngle - getAngle()) - threshold) * intensity / 6.28;
        } else if (targetAngle - getAngle() < -threshold) {
            lcorrection = Math.atan(Math.abs(targetAngle - getAngle()) - threshold) * intensity / 6.28;
            // OLD 1-AXIS FORMULA lcorrection = 1 + Math.atan(Math.abs(targetAngle - getAngle()) - threshold) * intensity / 6.28;
        }
        return lcorrection;
    }

    public double getrcorrection(double targetAngle, double threshold, double intensity) {
        double rcorrection = 1;
        if (targetAngle - getAngle() > threshold) {
            rcorrection = Math.atan(Math.abs(targetAngle - getAngle()) - threshold) * intensity / 6.28;
            // OLD 1-AXIS FORMULA rcorrection = 1 + Math.atan(Math.abs(targetAngle - getAngle()) - threshold) * intensity / 6.28;
        } else if (targetAngle - getAngle() < -threshold) {
            rcorrection = -Math.atan(Math.abs(targetAngle - getAngle()) - threshold) * intensity / 6.28;
            // OLD 1-AXIS FORMULA rcorrection = 1 - Math.atan(Math.abs(targetAngle - getAngle()) - threshold) * intensity / 6.28;
        }
        return rcorrection;
    }

    public void turn_gyro(double power, double targetAngle, double threshold)
    {
        while (Math.abs(angle_delta(getAngle(), targetAngle)) > threshold)
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

    //Finds angles relative to 0 instead to avoid issues with 360 to 0 gap
    public double angle_delta(double currentAngle, double targetAngle) {
        double delta = Math.abs(currentAngle - targetAngle);
        if (Math.abs(delta) > 180) {
            delta -= 360;
        }
        return delta;
    }

    //====================== ENCODER + GYRO MOVE ======================
    public void move_advanced(double ypower, double xpower, double targetAngle, double threshold, double intensity, double distance) {
        double start = getEncoderAvg();
        while (Math.abs(getEncoderAvg() - start) < distance) {
            frdrive.setPower((ypower + xpower) + getrcorrection(targetAngle, threshold, intensity));
            brdrive.setPower((ypower - xpower) + getrcorrection(targetAngle, threshold, intensity));
            fldrive.setPower(-(ypower - xpower) + getlcorrection(targetAngle, threshold, intensity));
            bldrive.setPower(-(ypower + xpower) + getlcorrection(targetAngle, threshold, intensity));
        }
        stop_motors();
    }

    //====================== PID =============================

    public void move_PID(double ypower, double xpower, double kporp, double kintg, double kderv, double distance, double threshold) {
        double error = distance;
        double totalError = 0;
        double prevTime = System.currentTimeMillis();
        while (Math.abs(error) > threshold) {
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

    public void move_advancedplus(double ypower, double xpower, double kporp, double kintg, double kderv, double distance, double angle, double thresholdPID, double thresholdGyro, double intensityGryo) {
        double error = distance;
        double totalError = 0;
        double prevTime = System.currentTimeMillis();
        while (Math.abs(error) > thresholdPID) {
            double currTime = System.currentTimeMillis();
            double deltaTime = currTime - prevTime;
            prevTime = currTime;
            error = distance - getEncoderAvg();
            totalError = error * deltaTime;
            double prop = kporp * error;
            double intg = kintg * totalError;
            double derv = kderv * (error / deltaTime);
            double PIDmod = prop + intg + derv;
            frdrive.setPower((ypower + xpower) * PIDmod + getrcorrection(angle, thresholdGyro, intensityGryo));
            brdrive.setPower((ypower - xpower) * PIDmod + getrcorrection(angle, thresholdGyro, intensityGryo));
            fldrive.setPower(-(ypower - xpower) * PIDmod + getlcorrection(angle, thresholdGyro, intensityGryo));
            bldrive.setPower(-(ypower + xpower) * PIDmod + getlcorrection(angle, thresholdGyro, intensityGryo));
        }
        stop_motors();
    }

    public void turn_PID(double power, double kporp, double kintg, double kderv, double targetAngle, double threshold)
    {
        double error = Math.abs(angle_delta(getAngle(), targetAngle));
        double totalError = 0;
        double prevTime = System.currentTimeMillis();
        while (Math.abs(error) > threshold)
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

    public void move2Line(double ypower, double xpower, double cutoff, double targetAngle, double threshold, double intensity, double thresholdColor, boolean isRed) {
        double start = getEncoderAvg();
        if (isRed) {
            while (getFloorRed() < thresholdColor && Math.abs(getEncoderAvg() - start) < cutoff) {
                frdrive.setPower((ypower + xpower) + getrcorrection(targetAngle, threshold, intensity));
                brdrive.setPower((ypower - xpower) + getrcorrection(targetAngle, threshold, intensity));
                fldrive.setPower(-(ypower - xpower) + getlcorrection(targetAngle, threshold, intensity));
                bldrive.setPower(-(ypower + xpower) + getlcorrection(targetAngle, threshold, intensity));
            }
            stop_motors();
        } else {
            while (getFloorBlue() < thresholdColor && Math.abs(getEncoderAvg() - start) < cutoff) {
                frdrive.setPower((ypower + xpower) + getrcorrection(targetAngle, threshold, intensity));
                brdrive.setPower((ypower - xpower) + getrcorrection(targetAngle, threshold, intensity));
                fldrive.setPower(-(ypower - xpower) + getlcorrection(targetAngle, threshold, intensity));
                bldrive.setPower(-(ypower + xpower) + getlcorrection(targetAngle, threshold, intensity));
            }

        }
        stop_motors();
    }

    //====================== MANIPULATORS ===================================

    public void startIntake(double power) {
        lIntake.setPower(-power);
        rIntake.setPower(power);
    }

    public void stopIntake() {
        startIntake(0);
    }

    public void startOutput(double power)
    {
        lOutput.setPower(-power);
        rOutput.setPower(power);
    }

    public void stopOutput() { startOutput(0); }

    public void moveTopTrack(double power, double distance) {
        double start = topTrack.getCurrentPosition();
        while (Math.abs(topTrack.getCurrentPosition() - start) < distance) {
            topTrack.setPower(power);
        }
        topTrack.setPower(0);
    }

    public void getGem(double extension, double threshold) {
        double start = gemArm.getPosition();
        gemArm.setPosition(extension);
        if (getBlue() > threshold && getRed() < threshold) {
            gemFlick.setPosition(1);
        } else if (getRed() > threshold && getBlue() < threshold) {
            gemFlick.setPosition(0);
        }
        sleep(250);
        gemFlick.setPosition(.5);
        sleep(250);
        gemArm.setPosition(start);
    }

    public void relic() {
        //empty
    }

    //====================== SENSORS ======================

    public double getAngle()
    {
        angles   = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return angles.firstAngle;
    }


    public double getRed() {
        NormalizedRGBA colors = gemSensor.getNormalizedColors();
        return colors.red;
    }

    public double getBlue() {
        NormalizedRGBA colors = gemSensor.getNormalizedColors();
        return colors.blue;
    }

    public double getBrightness() {
        NormalizedRGBA colors = gemSensor.getNormalizedColors();
        return colors.alpha;
    }

    public double getFloorRed() {
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        return colors.red;
    }

    public double getFloorBlue() {
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        return colors.blue;
    }

    //can return RelicRecoveryVuMark.UNKNOWN, R-.RIGHT, R-.LEFT, or R-.CENTER
    public RelicRecoveryVuMark getSymbol() {
        relicTrackables.activate();
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
        relicTrackables.deactivate();
        return vuMark;
    }

    public RelicRecoveryVuMark getSymbol_multitry(int tries, double angle)
    {
        relicTrackables.activate();
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
        for (int i = 1; i < tries; i++) {
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
                turn_gyro(.2, angle, 2);
                vuMark = RelicRecoveryVuMark.from(relicTemplate);
            }
        }
        return  vuMark;
    }
}
