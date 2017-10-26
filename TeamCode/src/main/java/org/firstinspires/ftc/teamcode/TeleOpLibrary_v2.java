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
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;


public abstract class TeleOpLibrary_v2 extends OpMode {
    public DcMotor bldrive;
    public DcMotor brdrive;
    public DcMotor fldrive;
    public DcMotor frdrive;
 /*   public DcMotor topTrack;
    public DcMotor botrIntake; //vex
    public DcMotor botlIntake; //vex
    public DcMotor toprIntake; //vex
    public DcMotor toplIntake; // vex*/
    public double ypower;
    public double xpower;
    public double rturnpower;
    public double lturnpower;
    public double toggleguard;
    public double angle;
    public int outputLevel;
    BNO055IMU gyro;
    Orientation angles;
    Acceleration gravity;

    public void initialize()
    {
        frdrive  = hardwareMap.get(DcMotor.class, "fr");
        fldrive = hardwareMap.get(DcMotor.class, "fl");
        brdrive  = hardwareMap.get(DcMotor.class, "br");
        bldrive = hardwareMap.get(DcMotor.class, "bl");
        /**
         * intake1 = hardwareMap.get(DcMotor.class, "i1");
         * intake2 = hardwareMap.get(DcMotor.class, "i2");
         */
        xpower = 0;
        ypower = 0;
        lturnpower = 0;
        rturnpower = 0;
        toggleguard = 0;

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "GRYO";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        gyro = hardwareMap.get(BNO055IMU.class, "gyro");
        gyro.initialize(parameters);

        angle = getAngle();

        telemetry.addLine("Init complete");
        telemetry.update();
    }

// ================================= MOVEMENT METHODS ==============================

    public void drive_mecanum(double drivePowerMod) {
        xpower = getMecanumPower1(false);
        ypower = getMecanumPower1(true);

        rturnpower = gamepad1.right_trigger;
        lturnpower = gamepad1.left_trigger;

        //If rturnpower if greater than .1, turn right
        if (rturnpower > .1)
        {
            turn(true, drivePowerMod);
            angle = getAngle();
        }
        //If lturnpower if greater than .1, turn left
        else if (lturnpower > .1)
        {
            turn(false, drivePowerMod);
            angle = getAngle();
        }
        //if either joystick is over .1, engage mecanum drive
        else if (Math.abs(gamepad1.right_stick_x) > .1 || Math.abs(gamepad1.right_stick_y) > .1)
        {
            //subtpower && pluspower in formulas specific to controlling the wheels in mecanum drive
            double subtpower = ypower - xpower;
            double pluspower = ypower + xpower;
            //as long as subtpower is over .1 (so as not to take sqaureroot of zero) power subt motors
            if (Math.abs(subtpower) > .1)
            {
                fldrive.setPower(-getMecanumPower2(subtpower, drivePowerMod));
                brdrive.setPower(getMecanumPower2(subtpower, drivePowerMod));
            }
            //otherwise, subtpower motors are turned off
            else
            {
                frdrive.setPower(0);
                bldrive.setPower(0);
            }
            //as long as pluspower is over .1 (so as not to take sqaureroot of zero) power plus motors
            if (Math.abs(pluspower) > .1)
            {
                frdrive.setPower(getMecanumPower2(pluspower, drivePowerMod));
                bldrive.setPower(-getMecanumPower2(pluspower, drivePowerMod));
            }
            //otherwise, pluspower motors are turned off
            else
            {
                fldrive.setPower(0);
                brdrive.setPower(0);
            }
        }
        //if not input from triggers or stick, turn motors off
        else
        {
            frdrive.setPower(0);
            fldrive.setPower(0);
            bldrive.setPower(0);
            brdrive.setPower(0);
        }
    }

    //Determine initial power from squaring the gamestick multipled by +/-
    //MUST BE FINISHED BY getMecanumPower2
    public double getMecanumPower1(boolean isY)
    {
        double output = 0;
        if (isY)
        {
            if (Math.abs(gamepad1.right_stick_y) > .1)
            {
                output = -Math.pow(gamepad1.right_stick_y, 2) * gamepad1.right_stick_y / Math.abs(gamepad1.right_stick_y);
                //if output is greater than .45, reduce to .45 to prevent going over 1
                if (Math.abs(output) > .45)
                {
                    output = (output / Math.abs(output)) * .45;
                }

            }
        } else {
            if (Math.abs(gamepad1.right_stick_x) > .1)
            {
                output = Math.pow(gamepad1.right_stick_x, 2) * gamepad1.right_stick_x / Math.abs(gamepad1.right_stick_x);
                //if xpower is greater than .45, reduce to .45 to prevent going over 1
                if (Math.abs(output) > .45)
                {
                    output = (output / Math.abs(output)) * .45;
                }
            }
        }
        return output;
    }

    //find squareroot of xpower +- ypower while keeping sign
    //MUST FOLLOW getMecanumPower1
    public double getMecanumPower2(double xplusy, double drivePowerMod)
    {
        return Math.pow(Math.abs(xplusy), .5) * (xplusy) / Math.abs(xplusy) * drivePowerMod;
    }

    public void turn (boolean isRight, double drivePowerMod)
    {
        if(isRight)
        {
            frdrive.setPower(-rturnpower * drivePowerMod);
            fldrive.setPower(-rturnpower * drivePowerMod);
            brdrive.setPower(-rturnpower * drivePowerMod);
            bldrive.setPower(-rturnpower * drivePowerMod);
        } else
        {
            frdrive.setPower(lturnpower * drivePowerMod);
            fldrive.setPower(lturnpower * drivePowerMod);
            brdrive.setPower(lturnpower * drivePowerMod);
            bldrive.setPower(lturnpower * drivePowerMod);
        }
    }

    public void drive_tank(double drivePowerMod)
    {
        if (Math.abs(gamepad1.left_stick_y) > .1) {
            bldrive.setPower(-gamepad1.left_stick_y);
            fldrive.setPower(-gamepad1.left_stick_y);
        } else {
            bldrive.setPower(0);
            fldrive.setPower(0);
        }

        if (Math.abs(gamepad1.right_stick_y) > .1) {
            brdrive.setPower(gamepad1.right_stick_y);
            frdrive.setPower(gamepad1.right_stick_y);
        } else {
            brdrive.setPower(0);
            frdrive.setPower(0);
        }
    }

//    ========= GYRO METHODS =================
    public double getAngle()
    {
        angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return angles.firstAngle;
    }

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

//    //DO NOT SET POWER ABOVE .8 when using standard intensity (1)
//    //Intensity should be a decimal number close to 1, not greater than 1.5
//    public double getlcorrection(double targetAngle, double threshold, double intensity)
//    {
//        double lcorrection = 1;
//        if (targetAngle - getAngle() > threshold)
//        {
//            lcorrection = Math.atan(Math.abs(targetAngle - getAngle()) - threshold) * intensity/6.28;
//        }
//        else if (targetAngle - getAngle() < -threshold)
//        {
//            lcorrection = -Math.atan(Math.abs(targetAngle - getAngle()) - threshold) * intensity/6.28;
//        }
//        return lcorrection;
//    }
//
//    //DO NOT SET POWER ABOVE .8 when using standard intensity (1)
//    //Intensity should be a decimal number close to 1, not greater than 1.5
//    public double getrcorrection(double targetAngle, double threshold, double intensity)
//    {
//        double rcorrection = 1;
//        if (targetAngle - getAngle() > threshold)
//        {
//            rcorrection = -Math.atan(Math.abs(targetAngle - getAngle()) - threshold) * intensity/6.28;
//        }
//        else if (targetAngle - getAngle() < -threshold)
//        {
//            rcorrection = Math.atan(Math.abs(targetAngle - getAngle()) - threshold) * intensity/6.28;
//        }
//        return rcorrection;
//    }

/*//====================================== MANIPULATORS METHODS =================================

    public void intake(boolean control)
    {
        if (control)
        {
            botlIntake.setPower(-1);
            botrIntake.setPower(1);
            toplIntake.setPower(-1);
            toprIntake.setPower(1);

        } else {
            botlIntake.setPower(0);
            botrIntake.setPower(0);
            toplIntake.setPower(0);
            toprIntake.setPower(0);
        }
    }

    public void relic()
    {
        //empty
    }

    public void moveTopTrack(double power, boolean control0, boolean control1, boolean control2, boolean control3)
    {
        double start = topTrack.getCurrentPosition();
        if (control0 && outputLevel != 0)
        {
                while (Math.abs(topTrack.getCurrentPosition - start) > (100*outputLevel))
                {
                    topTrack.setPower(-power);
                }
                outputLevel = 0;
        }
        else if (control1 && outputLevel != 1)
        {
            if (topTrack.getCurrentPosition < start)
            {
                while (Math.abs(topTrack.getCurrentPosition - start) > (100*(1 - outputLevel))
                {
                    topTrack.setPower(power);
                }
            }
            else
            {
                while (Math.abs(topTrack.getCurrentPosition -start) > 100*(outputLevel-1));
                topTrack.setPower(-power);
            }
            outputLevel = 1;
        }
        else if (control2 && outputLevel != 2)
        {
            if (topTrack.getCurrentPosition < start)
            {
                while (Math.abs(topTrack.getCurrentPosition - start) > 100*(2 - outputLevel))
                {
                    topTrack.setPower(power);
                }
            }
            else
            {
                while (Math.abs(topTrack.getCurrentPosition -start) > 100*(outputLevel-2));
                topTrack.setPower(-power);
            }
            outputLevel = 2;
        }
        else if (control3 && outputLevel != 3)
        {
            while (Math.abs(topTrack.getCurrentPosition - start) > 100*(3 - outputLevel))
            {
                topTrack.setPower(power);
            }
            outputLevel = 3;
        }

    }

*/

// ===================================== UTILITY METHODS ==================================

    public boolean toggle(boolean target, boolean control)
    {
        if (control && System.currentTimeMillis() - toggleguard > 500) {
            toggleguard = System.currentTimeMillis();
            if (target == false) {
                target = true;
            } else {
                target = false;
            }
        }
        return target;
    }

    public double toggleDouble (double target, boolean control, double high, double low)
    {
        if (control && System.currentTimeMillis() - toggleguard > 500) {
            toggleguard = System.currentTimeMillis();
            if (target == high) {
                target = low;
            } else {
                target = high;
            }
        }
        return target;
    }

}

