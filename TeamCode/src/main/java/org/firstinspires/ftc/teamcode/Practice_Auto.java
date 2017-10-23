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


public abstract class Practice_Auto extends LinearOpMode {

    BNO055IMU gyro;
    Orientation angles;
    Acceleration gravity;

    public DcMotor bldrive;
    public DcMotor brdrive;
    public DcMotor fldrive;
    public DcMotor frdrive;

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

        waitForStart();
    }


    //====================== BASIC MOVEMENT METHODS ======================

    public void move_yaxis_basic(double power) {
        frdrive.setPower(power);
        brdrive.setPower(power);
        fldrive.setPower(-power);
        bldrive.setPower(-power);
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

    public void gyro_turn(double target_angle, double threshold) {
        double currentAngle = getAngle();
        while (Math.abs(target_angle - getAngle()) > threshold)
        {
            if (target_angle - currentAngle > 0) {
            turn_basic(1);
            }
            else {
            turn_basic(-1);
            }
        }
        stop_motors();
    }

    public void move_gyro_correct(double power, double targetAngle, double threshold)
    {
        frdrive.setPower(power * getRcorrect());
        brdrive.setPower(power * getRcorrect());
        fldrive.setPower(-power * getLcorrect());
        bldrive.setPower(-power * getLcorrect());
    }

    public double getRcorrect(double targetAngle, double threshold) {
        if (targetAngle - getAngle() > threshold) {
            return 1 + (Math.abs(targetAngle - getAngle())) / 90;
        } else if (targetAngle - getAngle() < threshold) {
            return 1 - (Math.abs(targetAngle - getAngle())) / 90;
        }
        return 1;
    }

    public double getLcorrect(double targetAngle, double threshold) {
        if (targetAngle - getAngle() < threshold) {
            return 1 + (Math.abs(targetAngle - getAngle())) / 90;
        } else if (targetAngle - getAngle() > threshold) {
            return 1 - (Math.abs(targetAngle - getAngle())) / 90;
        }
        return 1;
    }

    public double getAngle(){
        angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return angles.firstAngle;
    }
}