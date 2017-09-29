/*
AutoMain_v1
9/18/2017
6210 Software
- William Fisher
- Rohit Chawla
- Nihal Kyasa

Holds methods to be used for Autonomous programs in FTC's Relic Recovery Competition.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

import java.util.Locale;

import static com.sun.tools.doclint.Entity.pi;
import static com.sun.tools.doclint.Entity.rceil;


public abstract class AutoLibrary_v1 extends LinearOpMode {

    BNO055IMU imu;
    Orientation angles;

    public DcMotor bldrive;
    public DcMotor brdrive;
    public DcMotor fldrive;
    public DcMotor frdrive;

    public void initialize()
    {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
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
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
        angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
    }

    //====================== BASIC MOVEMENT METHODS ======================

    public void move_yaxis_basic(double power)
    {
        frdrive.setPower(power);
        brdrive.setPower(power);
        fldrive.setPower(-power);
        bldrive.setPower(-power);
    }

    public void move_x_axis_basic(double power)
    {
        frdrive.setPower(power);
        brdrive.setPower(-power);
        fldrive.setPower(power);
        bldrive.setPower(-power);
    }

    public void move_biaxis_basic(double ypower, double xpower)
    {
        frdrive.setPower(ypower + xpower);
        brdrive.setPower(ypower - xpower);
        fldrive.setPower(-(ypower - xpower));
        bldrive.setPower(-(ypower + xpower));
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

    //====================== ENCODER ONLY MOVEMENT METHODS ======================

    public double getEncoderAvg()
    {
        return ((frdrive.getCurrentPosition() + fldrive.getCurrentPosition())/2);
    }

    public void move_encoder(double ypower, double xpower, double distance)
    {
        double start = getEncoderAvg();
        while(Math.abs(getEncoderAvg() - start) < distance)
        {
            move_biaxis_basic(ypower, xpower);
        }
        stop_motors();
    }

    public void turn_encoder(double power, double distance)
    {
        double start = getEncoderAvg();
        while(Math.abs(getEncoderAvg() - start) < distance)
        {
            turn_basic(power);
        }
        stop_motors();
    }

    //====================== GYRO CORRECTION MOVEMENT ===================

    //DO NOT SET POWER ABOVE .8 when using standard intensity (1)
    //Intensity should be a decimal number close to 1, not greater than 1.5
    public double getlcorrection(double targetAngle, double threshold, double intensity)
    {
        double lcorrection = 1;
        if (targetAngle - getAngle() > threshold)
        {
            lcorrection = 1 - Math.atan(Math.abs(targetAngle - getAngle()) - threshold) * intensity/6.28;
        }
        else if (targetAngle - getAngle() < -threshold)
        {
            lcorrection = 1 + Math.atan(Math.abs(targetAngle - getAngle()) - threshold) * intensity/6.28;
        }
        return lcorrection;
    }

    public double getrcorrection(double targetAngle, double threshold, double intensity)
    {
        double rcorrection = 1;
        if (targetAngle - getAngle() > threshold)
        {
            rcorrection = 1 + Math.atan(Math.abs(targetAngle - getAngle()) - threshold) * intensity/6.28;
        }
        else if (targetAngle - getAngle() < -threshold)
        {
            rcorrection = 1 - Math.atan(Math.abs(targetAngle - getAngle()) - threshold) * intensity/6.28;
        }
        return rcorrection;
    }

    //====================== ENCODER + GYRO MOVE ======================

    public void move_advanced (double ypower, double xpower, double targetAngle, double threshold, double intensity, double distance)
    {
        double start = getEncoderAvg();
        while(Math.abs(getEncoderAvg() - start) < distance)
        {
            frdrive.setPower((ypower + xpower) * getrcorrection(targetAngle, threshold, intensity));
            brdrive.setPower((ypower - xpower) * getrcorrection(targetAngle, threshold, intensity));
            fldrive.setPower(-(ypower - xpower) * getlcorrection(targetAngle, threshold, intensity));
            bldrive.setPower(-(ypower + xpower) * getlcorrection(targetAngle, threshold, intensity));
        }
        stop_motors();
    }

    //====================== PID =============================

    public void move_PID (double ypower, double xpower, double kporp, double kintg, double kderv, double distance, double threshold)
    {
        double error = distance;
        double totalError = 0;
        double prevTime = System.currentTimeMillis();
        while (Math.abs(error) > threshold)
        {
            double currTime = System.currentTimeMillis();
            double deltaTime = currTime - prevTime;
            prevTime = currTime;
            error = distance - getEncoderAvg();
            totalError = error*deltaTime;
            double prop = kporp*error;
            double intg = kintg*totalError;
            double derv = kderv*(error/deltaTime);
            double PIDmod = prop + intg + derv;
            frdrive.setPower((ypower + xpower) * PIDmod);
            brdrive.setPower((ypower - xpower) * PIDmod);
            fldrive.setPower(-(ypower - xpower) * PIDmod);
            bldrive.setPower(-(ypower + xpower) * PIDmod);
        }
    }

    //======================= PID + GRYO =========================

    public void move_advancedplus (double ypower, double xpower, double kporp, double kintg, double kderv, double distance, double thresholdPID, double thresholdGyro, double intensityGryo)
    {
        double error = distance;
        double totalError = 0;
        double prevTime = System.currentTimeMillis();
        while (Math.abs(error) > thresholdPID)
        {
            double currTime = System.currentTimeMillis();
            double deltaTime = currTime - prevTime;
            prevTime = currTime;
            error = distance - getEncoderAvg();
            totalError = error*deltaTime;
            double prop = kporp*error;
            double intg = kintg*totalError;
            double derv = kderv*(error/deltaTime);
            double PIDmod = prop + intg + derv;
            frdrive.setPower((ypower + xpower) * PIDmod * getrcorrection(getAngle(), thresholdGyro, intensityGryo));
            brdrive.setPower((ypower - xpower) * PIDmod * getrcorrection(getAngle(), thresholdGyro, intensityGryo));
            fldrive.setPower(-(ypower - xpower) * PIDmod * getlcorrection(getAngle(), thresholdGyro, intensityGryo));
            bldrive.setPower(-(ypower + xpower) * PIDmod * getlcorrection(getAngle(), thresholdGyro, intensityGryo));
        }
    }


    //====================== GYRO SUPPORT ======================

    public double getAngle()
    {
        return angles.firstAngle;
    }

    void composeTelemetry() {

        telemetry.addAction(new Runnable() { @Override public void run()
        {
            angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        }
        });

        telemetry.addLine()
                .addData("status", new Func<String>() {
                    @Override public String value() {
                        return imu.getSystemStatus().toShortString();
                    }
                })
                .addData("calib", new Func<String>() {
                    @Override public String value() {
                        return imu.getCalibrationStatus().toString();
                    }
                });

        telemetry.addLine()
                .addData("yaw", new Func<String>() {
                    @Override public String value() {
                        return formatAngle(angles.angleUnit, angles.firstAngle);
                    }
                })
                .addData("roll", new Func<String>() {
                    @Override public String value() {
                        return formatAngle(angles.angleUnit, angles.secondAngle);
                    }
                })
                .addData("pitch", new Func<String>() {
                    @Override public String value() {
                        return formatAngle(angles.angleUnit, angles.thirdAngle);
                    }
                });
    }

    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    String formatDegrees(double degrees){
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }
}
