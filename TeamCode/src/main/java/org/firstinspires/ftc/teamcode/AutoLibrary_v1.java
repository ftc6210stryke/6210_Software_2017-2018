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
import com.qualcomm.robotcore.util.ElapsedTime;


public abstract class AutoLibrary_v1 extends LinearOpMode {

    public DcMotor bldrive;
    public DcMotor brdrive;
    public DcMotor fldrive;
    public DcMotor frdrive;

    public void init()
    {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        fldrive = hardwareMap.get(DcMotor.class, "a");
        frdrive = hardwareMap.get(DcMotor.class, "b");
        bldrive = hardwareMap.get(DcMotor.class, "c");
        brdrive = hardwareMap.get(DcMotor.class, "d");
        fldrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frdrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fldrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frdrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        brdrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bldrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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

    public void move_biaxis_basic(double xpower, double ypower)
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
        while(getEncoderAvg() < start + distance)
        {
            move_biaxis_basic(ypower, xpower);
        }
        stop_motors();
    }

    public void turn_encoder(double power, double distance)
    {
        double start = getEncoderAvg();
        while(getEncoderAvg() < start + distance)
        {
            turn_basic(power);
        }
        stop_motors();
    }

    //====================== GYRO CORRECTED MOVEMENT METHODS ======================

}
