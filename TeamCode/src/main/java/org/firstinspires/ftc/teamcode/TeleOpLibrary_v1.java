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
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gyroscope;


public abstract class TeleOpLibrary_v1 extends OpMode {
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
    public double ypower;
    public double xpower;
    public double rturnpower;
    public double lturnpower;
    public double toggleguard;
/** GRYO NEEDED    ModernRoboticsI2cGyro gyro;*/

    public void initialize()
    {
        brdrive  = hardwareMap.get(DcMotor.class, "c");
        bldrive = hardwareMap.get(DcMotor.class, "d");
        frdrive  = hardwareMap.get(DcMotor.class, "a");
        fldrive = hardwareMap.get(DcMotor.class, "b");
        xpower = 0;
        ypower = 0;
        lturnpower = 0;
        rturnpower = 0;
        toggleguard = 0;

/** GRYO NEEDED
 *         gyro = hardwareMap.get(ModernRoboticsI2cGyro.class, "gyro");
        telemetry.log().add("Gyro Calibrating. Do Not Move!");
        gyro.calibrate();
        while (!isStopRequested() && gyro.isCalibrating()) {sleep(50);} */

        telemetry.addData("Status", "Initialized");
    }

// ================================= MOVEMENT METHODS ==============================

    public void drive_mecanum(double drivePowerMod/**, GYRO NEEDED double angle*/) {
        xpower = getMecanumPower1(false);
        ypower = getMecanumPower1(true);

        /** GRYO NEEDED double newAngle = angle;*/

        rturnpower = gamepad1.right_trigger;
        lturnpower = gamepad1.left_trigger;

        //If rturnpower if greater than .1, turn right
        if (rturnpower > .1)
        {
            turn(true, drivePowerMod);
            /** GRYO NEEDED newAngle = getAngle();*/
        }
        //If lturnpower if greater than .1, turn left
        else if (lturnpower > .1)
        {
            turn(false, drivePowerMod);
            /** GRYO NEEDED newAngle = getAngle();*/
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
                fldrive.setPower(-getMecanumPower2(subtpower, drivePowerMod)/** GRYO NEEDED * getlcorrection(newAngle, 3, 1.2)*/);
                brdrive.setPower(getMecanumPower2(subtpower, drivePowerMod)/** GRYO NEEDED * getrcorrection(newAngle, 3, 1.2)*/);
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
                frdrive.setPower(getMecanumPower2(pluspower, drivePowerMod)/** GRYO NEEDED * getrcorrection(angle, 3, 1.2)*/);
                bldrive.setPower(-getMecanumPower2(pluspower, drivePowerMod)/** GYRO NEEDED * getlcorrection(angle, 3, 1.2)*/);
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
                output = Math.pow(gamepad1.right_stick_y, 2) * gamepad1.right_stick_y / Math.abs(gamepad1.right_stick_y);
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
            frdrive.setPower(rturnpower * drivePowerMod);
            fldrive.setPower(rturnpower * drivePowerMod);
            brdrive.setPower(rturnpower * drivePowerMod);
            bldrive.setPower(rturnpower * drivePowerMod);
        } else
        {
            frdrive.setPower(-lturnpower * drivePowerMod);
            fldrive.setPower(-lturnpower * drivePowerMod);
            brdrive.setPower(-lturnpower * drivePowerMod);
            bldrive.setPower(-lturnpower * drivePowerMod);
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
/** GRYO METHODS : GRYO NEEDED
 *
    public double getAngle()
    {
 return gyro.rawZ();
    }

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

    //DO NOT SET POWER ABOVE .8 when using standard intensity (1)
    //Intensity should be a decimal number close to 1, not greater than 1.5
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
*/

//====================================== MANIPULATORS METHODS =================================

    public void intake(boolean control)
    {
        if (control)
        {
            intake1.setPower(1);
            intake2.setPower(-1);
        } else {
            intake1.setPower(0);
            intake2.setPower(0);
        }
    }

    public void elevator(boolean controlUp, boolean controlDown, boolean controlOut)
    {
        if (controlUp)
        {
            elevatorV1.setPower(1);
            elevatorV2.setPower(-1);
        }
        else if (controlDown)
        {
            elevatorV1.setPower(-1);
            elevatorV2.setPower(1);
        }
        else
        {
            elevatorV1.setPower(0);
            elevatorV2.setPower(0);
        }
        if (controlOut)
        {
            elevatorH1.setPower(1);
            elevatorH2.setPower(-1);
        }
        else
        {
            elevatorH1.setPower(0);
            elevatorH2.setPower(0);
        }
    }

    public void relic()
    {
        //empty
    }


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

