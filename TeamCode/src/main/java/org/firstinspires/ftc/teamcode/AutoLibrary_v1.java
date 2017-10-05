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

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;


public abstract class AutoLibrary_v1 extends LinearOpMode {

    ModernRoboticsI2cGyro gyro;
    NormalizedColorSensor colorSensor;
    NormalizedColorSensor gemSensor;

    public DcMotor bldrive;
    public DcMotor brdrive;
    public DcMotor fldrive;
    public DcMotor frdrive;
    public DcMotor intake1;
    public DcMotor intake2;
    public DcMotor elevatorV1; //vexmotor
    public DcMotor elevatorV2; //vexmotor
    public DcMotor elevatorH1; //vexmotor
    public DcMotor elevatorH2; //vexmotor

    public Servo gemArm;
    public Servo gemFlick;

    public void initialize()
    {
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

        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "colorSensor");
        gemSensor = hardwareMap.get(NormalizedColorSensor.class, "gemSensor");
        gyro = hardwareMap.get(ModernRoboticsI2cGyro.class, "gyro");
        telemetry.log().add("Gyro Calibrating. Do Not Move!");
        gyro.calibrate();
        while (!isStopRequested() && gyro.isCalibrating()) {sleep(50);}

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
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

    //====================== Stationary Correction =====================

    public void correct(double targetAngle, double threshold, double intensity)
    {
        if (Math.abs(targetAngle - getAngle()) > threshold) {
            while (targetAngle - getAngle() > threshold) {
                turn_basic(0.5);
            }
            while (targetAngle - getAngle() < threshold) {
                turn_basic(-0.5);
            }
        }
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
        stop_motors();
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
        stop_motors();
    }

    //====================== SPECIALIZED MOVEMENT / PATH-ING ========================

    public void move2Line(double ypower, double xpower, double cutoff, double targetAngle, double threshold, double intensity, double thresholdColor, boolean isRed)
    {
        double start = getEncoderAvg();
        if(isRed)
        {
            while(getFloorRed() < thresholdColor && Math.abs(getEncoderAvg() - start) < cutoff)
            {
                frdrive.setPower((ypower + xpower) * getrcorrection(targetAngle, threshold, intensity));
                brdrive.setPower((ypower - xpower) * getrcorrection(targetAngle, threshold, intensity));
                fldrive.setPower(-(ypower - xpower) * getlcorrection(targetAngle, threshold, intensity));
                bldrive.setPower(-(ypower + xpower) * getlcorrection(targetAngle, threshold, intensity));
            }
            stop_motors();
        }
        else
        {
            while(getFloorBlue() < thresholdColor && Math.abs(getEncoderAvg() - start) < cutoff)
            {
                frdrive.setPower((ypower + xpower) * getrcorrection(targetAngle, threshold, intensity));
                brdrive.setPower((ypower - xpower) * getrcorrection(targetAngle, threshold, intensity));
                fldrive.setPower(-(ypower - xpower) * getlcorrection(targetAngle, threshold, intensity));
                bldrive.setPower(-(ypower + xpower) * getlcorrection(targetAngle, threshold, intensity));
            }
            stop_motors();
        }
    }

    //====================== MANIPULATORS ===================================

    public void startIntake(double power)
    {
        intake1.setPower(power);
        intake2.setPower(-power);
    }

    public void stopIntake()
    {
        startIntake(0);
    }

    public void elevatorUp(double power, double distance)
    {
        double start = elevatorV1.getCurrentPosition();
        while(Math.abs(elevatorV1.getCurrentPosition() - start) < distance)
        {
            elevatorV1.setPower(power);
            elevatorV2.setPower(-power);
        }
        elevatorV1.setPower(0);
        elevatorV2.setPower(0);
    }

    public void output_start(double power)
    {
        elevatorH1.setPower(power);
        elevatorH2.setPower(power);
    }

    public void output_stop()
    {
        output_start(0);
    }

    public void getGem(double extension, double threshold)
    {
        double start = gemArm.getPosition();
        gemArm.setPosition(extension);
        if(getBlue() > threshold && getRed() < threshold)
        {
            gemFlick.setPosition(1);
        }
        else if (getRed() > threshold && getBlue() < threshold)
        {
            gemFlick.setPosition(0);
        }
        sleep(250);
        gemFlick.setPosition(.5);
        sleep(250);
        gemArm.setPosition(start);
    }

    public void relic()
    {
        //empty
    }

    //====================== SENSORS ======================

    public double getAngle() {return gyro.rawZ();}

    public double getRed()
    {
        NormalizedRGBA colors = gemSensor.getNormalizedColors();
        return colors.red;
    }

    public double getBlue()
    {
        NormalizedRGBA colors = gemSensor.getNormalizedColors();
        return colors.blue;
    }

    public double getBrightness()
    {
        NormalizedRGBA colors = gemSensor.getNormalizedColors();
        return colors.alpha;
    }

    public double getFloorRed()
    {
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        return colors.red;
    }

    public double getFloorBlue()
    {
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        return colors.blue;
    }
}
