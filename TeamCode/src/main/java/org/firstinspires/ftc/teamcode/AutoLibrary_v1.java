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
}
