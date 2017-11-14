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


public abstract class Practice_Auto_Library_Rohit extends LinearOpMode {

    BNO055IMU gyro;
    Orientation angles;
    Acceleration gravity;

    public static final String TAG = "Vuforia VuMark Sample";
    OpenGLMatrix lastLocation = null;
    VuforiaLocalizer vuforia;
    VuforiaTrackable relicTemplate;
    VuforiaTrackables relicTrackables;

    public DcMotor bldrive;
    public DcMotor brdrive;
    public DcMotor fldrive;
    public DcMotor frdrive;
    public DcMotor output;
    public ColorSensor cs;
    public ColorSensor gs;

    public void initialize() throws InterruptedException {
        frdrive = hardwareMap.get(DcMotor.class, "a");
        fldrive = hardwareMap.get(DcMotor.class, "b");
        brdrive = hardwareMap.get(DcMotor.class, "c");
        bldrive = hardwareMap.get(DcMotor.class, "d");
        output = hardwareMap.get(DcMotor.class, "out");
        cs = hardwareMap.get(ColorSensor.class, "cs");
        gs = hardwareMap.get(ColorSensor.class, "gs");
        fldrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frdrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fldrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frdrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        brdrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bldrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "GRYO";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        gyro = hardwareMap.get(BNO055IMU.class, "gyro");
        gyro.initialize(parameters);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        vision_init();

        waitForStart();
    }

    public void vision_init() throws InterruptedException {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "ARUX4tP/////AAAAGXY2Dg+/sUl6gWdYntfHvN8GT9v/tqySPvCz3Nt2dTXFWQC7TJriGnCTY/vvHRRUFiSSI11yfUxGSTkNzXbHM0zBmGf3WiW6+kZsArc76UHXbUG1fHmPyIAljbqRBiNz8Kki/PlrJCwpNwmcZKNu8wvnYzGZ5phfZHXE6yyr2HvuEyX6IEYUvrvDtMImiHWHSbjK5wbgDyMinQU/FsVmDy0S1OHL+xVDk6yhjBsPBO2bsVMTKA3GRZAo+Qxjqd9nh95+jPt1EbE11VgPHzr/Zm8bKrr+gz24uxfsTgXU3sc6YLgdcegkRd6dxM5gvsu4xisSks+gkLismFPmNASP0JpDkom80KZ9MmEcbl7GnLO+";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary
    }


    //==================================================== BASIC MOVEMENT METHODS ===================================================

    public void move_yaxis_basic(double power)
    {
        frdrive.setPower(power);
        brdrive.setPower(power);
        fldrive.setPower(-power);
        bldrive.setPower(-power);
    }

    public void move_xaxis_basic(double power)
    {
        frdrive.setPower(power);
        brdrive.setPower(-power);
        fldrive.setPower(power);
        bldrive.setPower(-power);
    }

    public void turn_basic(double power)
    {
        frdrive.setPower(-power);
        brdrive.setPower(-power);
        fldrive.setPower(-power);
        brdrive.setPower(-power);
    }

    public void stop_motors()
    {
        frdrive.setPower(0);
        brdrive.setPower(0);
        fldrive.setPower(0);
        brdrive.setPower(0);
    }

    //================================================ Gyro Turn ===================================================================

    public void gyro_turn(double target_angle, double threshold) {
        double currentAngle = getAngle();
        while (Math.abs(target_angle - getAngle()) > threshold) {
            if (target_angle - currentAngle > 0) {
                turn_basic(1);
            } else {
                turn_basic(-1);
            }
        }
        stop_motors();
    }

    //================================================= Gyro Correct ==================================================================

    public void move_gyro_correct(double power, double targetAngle, double threshold)
    {
        frdrive.setPower(power * getRcorrect(targetAngle, threshold));
        brdrive.setPower(power * getRcorrect(targetAngle, threshold));
        fldrive.setPower(-power * getLcorrect(targetAngle, threshold));
        bldrive.setPower(-power * getLcorrect(targetAngle, threshold));
    }

    public double getRcorrect(double targetAngle, double threshold)
    {
        if (targetAngle - getAngle() > threshold) {
            return 1 + (Math.abs(targetAngle - getAngle())) / 90;
        } else if (targetAngle - getAngle() < threshold) {
            return 1 - (Math.abs(targetAngle - getAngle())) / 90;
        }
        return 1;
    }

    public double getLcorrect(double targetAngle, double threshold)
    {
        if (targetAngle - getAngle() < threshold) {
            return 1 + (Math.abs(targetAngle - getAngle())) / 90;
        } else if (targetAngle - getAngle() > threshold) {
            return 1 - (Math.abs(targetAngle - getAngle())) / 90;
        }
        return 1;
    }

    //================================================= Get Encoder =====================================================================

    public double getEncoderAvg()
    {
        return (frdrive.getCurrentPosition() + fldrive.getCurrentPosition() + brdrive.getCurrentPosition() + bldrive.getCurrentPosition()) / 4;
    }

    //=============================================== Get Angle ============================================================================

    public double getAngle()
    {
        angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return angles.firstAngle;
    }

    //=============================================Color Sensor Move to Line=============================================================

    public void cs_move_to_line(double power, double targetAngle, double threshold)
    {
        double encoder_Start = getEncoderAvg();
        while (opModeIsActive() && cs.alpha() < 100 && (Math.abs(getEncoderAvg() - encoder_Start)) < 100)
            ;
        {
            move_gyro_correct(power, targetAngle, threshold);

        }
        stop_motors();
    }

    //============================================= Move Encoder Y Axis ======================================================================

    public void move_encoder_yaxis(double power, double distance)
    {
        double start = getEncoderAvg();
        while (Math.abs(getEncoderAvg() - start) < distance)
        {
            move_yaxis_basic(power);
        }
        stop_motors();
    }

    //===================================================== Move Encoder X Axis =============================================================

    public void move_encoder_xaxis(double power, double distance)
    {
        double start = getEncoderAvg();
        while (Math.abs(distance - getEncoderAvg()) < distance)
        {
            move_xaxis_basic(power);
        }
        stop_motors();
    }

    //============================================ Get Gem ===========================================================================

    public boolean getBlueGem(double threshold) {
        if (gs.blue() > threshold) {
            return true;
        }
        return false;
    }

    public boolean getBlueGem_Short(double threshold) {
        return (gs.blue() > threshold);
    }

    //Uses vuforia to detect the mark
    //Note - RelicRecoveryVuMark is a Class unique to vuforia code - think of it as a data type like boolean or double
    //can return RelicRecoveryVuMark.UNKNOWN, RelicRecoveryVuMark.RIGHT, R-k.LEFT, or R-k.CENTER
    public RelicRecoveryVuMark getSymbol() {
        relicTrackables.activate();
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
        relicTrackables.deactivate();
        return vuMark;
    }

}