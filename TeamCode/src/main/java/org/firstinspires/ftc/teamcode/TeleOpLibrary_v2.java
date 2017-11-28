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
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

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
    public DcMotor topTrack;
    public DcMotor rIntake;
    public DcMotor lIntake;
    public CRServo belt;
    public CRServo rOutput;
    public CRServo lOutput;
    public double ypower;
    public double xpower;
    public double rturnpower;
    public double lturnpower;
    public double toggleguard;
    public int outputLevel;
    public Servo gemArm;
    public Servo gemFlick;

    public void initialize() {
        frdrive = hardwareMap.get(DcMotor.class, "fr");
        fldrive = hardwareMap.get(DcMotor.class, "fl");
        brdrive = hardwareMap.get(DcMotor.class, "br");
        bldrive = hardwareMap.get(DcMotor.class, "bl");
        topTrack = hardwareMap.get(DcMotor.class, "topt");
        rIntake = hardwareMap.get(DcMotor.class, "rIn");
        lIntake = hardwareMap.get(DcMotor.class, "lIn");
        belt = hardwareMap.get(CRServo.class, "belt");
        rOutput = hardwareMap.get(CRServo.class, "rOut");
        lOutput = hardwareMap.get(CRServo.class, "lOut");
        gemArm = hardwareMap.get(Servo.class, "gExt");
        gemFlick = hardwareMap.get(Servo.class, "gF");

        xpower = 0;
        ypower = 0;
        lturnpower = 0;
        rturnpower = 0;
        toggleguard = 0;

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
        if (rturnpower > .1) {
            turn(true, drivePowerMod);
        }
        //If lturnpower if greater than .1, turn left
        else if (lturnpower > .1) {
            turn(false, drivePowerMod);
        }
        //if either joystick is over .1, engage mecanum drive
        else if (Math.abs(gamepad1.right_stick_x) > .1 || Math.abs(gamepad1.right_stick_y) > .1) {
            //subtpower && pluspower in formulas specific to controlling the wheels in mecanum drive
            double subtpower = ypower - xpower;
            double pluspower = ypower + xpower;
            //as long as subtpower is over .1 (so as not to take sqaureroot of zero) power subt motors
            if (Math.abs(subtpower) > .1) {
                fldrive.setPower(-getMecanumPower2(subtpower, drivePowerMod));
                brdrive.setPower(getMecanumPower2(subtpower, drivePowerMod));
            }
            //otherwise, subtpower motors are turned off
            else {
                frdrive.setPower(0);
                bldrive.setPower(0);
            }
            //as long as pluspower is over .1 (so as not to take sqaureroot of zero) power plus motors
            if (Math.abs(pluspower) > .1) {
                frdrive.setPower(getMecanumPower2(pluspower, drivePowerMod));
                bldrive.setPower(-getMecanumPower2(pluspower, drivePowerMod));
            }
            //otherwise, pluspower motors are turned off
            else {
                fldrive.setPower(0);
                brdrive.setPower(0);
            }
        }
        //if not input from triggers or stick, turn motors off
        else {
            frdrive.setPower(0);
            fldrive.setPower(0);
            bldrive.setPower(0);
            brdrive.setPower(0);
        }
    }

    //Determine initial power from squaring the gamestick multipled by +/-
    //MUST BE FINISHED BY getMecanumPower2
    public double getMecanumPower1(boolean isY) {
        double output = 0;
        if (isY) {
            if (Math.abs(gamepad1.right_stick_y) > .1) {
                output = -Math.pow(gamepad1.right_stick_y, 2) * gamepad1.right_stick_y / Math.abs(gamepad1.right_stick_y);
                //if output is greater than .45, reduce to .45 to prevent going over 1
                if (Math.abs(output) > .45) {
                    output = (output / Math.abs(output)) * .45;
                }

            }
        } else {
            if (Math.abs(gamepad1.right_stick_x) > .1) {
                output = Math.pow(gamepad1.right_stick_x, 2) * gamepad1.right_stick_x / Math.abs(gamepad1.right_stick_x);
                //if xpower is greater than .45, reduce to .45 to prevent going over 1
                if (Math.abs(output) > .45) {
                    output = (output / Math.abs(output)) * .45;
                }
            }
        }
        return output;
    }

    //find squareroot of xpower +- ypower while keeping sign
    //MUST FOLLOW getMecanumPower1
    public double getMecanumPower2(double xplusy, double drivePowerMod) {
        return Math.pow(Math.abs(xplusy), .5) * (xplusy) / Math.abs(xplusy) * drivePowerMod;
    }

    public void turn(boolean isRight, double drivePowerMod) {
        if (isRight) {
            frdrive.setPower(-rturnpower * drivePowerMod);
            fldrive.setPower(-rturnpower * drivePowerMod);
            brdrive.setPower(-rturnpower * drivePowerMod);
            bldrive.setPower(-rturnpower * drivePowerMod);
        } else {
            frdrive.setPower(lturnpower * drivePowerMod);
            fldrive.setPower(lturnpower * drivePowerMod);
            brdrive.setPower(lturnpower * drivePowerMod);
            bldrive.setPower(lturnpower * drivePowerMod);
        }
    }

    public void drive_tank(double drivePowerMod) {
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


//====================================== MANIPULATORS METHODS =================================

    public void output(boolean control, boolean control_reverse) {
        if (control) {
            lOutput.setPower(-.8);
            rOutput.setPower(.8);
            belt.setPower(.8);
            telemetry.addLine("intake command recieved");
            telemetry.update();

        } else if (control_reverse) {
            lOutput.setPower(-.8);
            rOutput.setPower(.8);
            belt.setPower(-.8);
        } else {
            lOutput.setPower(0);
            rOutput.setPower(0);
            belt.setPower(0);
        }
    }

    public void intake(boolean control, boolean control_reverse) {
        if (control)
        {
            rIntake.setPower(-.9);
            lIntake.setPower(-.9);
        }
        else if (control_reverse)
        {
            rIntake.setPower(.9);
            lIntake.setPower(.9);
        }
        else
        {
            rIntake.setPower(0);
            lIntake.setPower(0);
        }
    }


/*    public void relic()
    {
        //empty
    }*/

    public void TopTrackSet(double power, boolean control0, boolean control1, boolean control2, boolean control3) {
        telemetry.addData("track encoder", topTrack.getCurrentPosition());
        telemetry.addData("track level", outputLevel);
        telemetry.update();
        double start = topTrack.getCurrentPosition();
        if (control0 && outputLevel != 0) {
            while (Math.abs(topTrack.getCurrentPosition() - start) < (50 * outputLevel)) {
                topTrack.setPower(-power / 5);
            }
            outputLevel = 0;
        } else if (control1 && outputLevel != 1) {
            if (outputLevel == 0) {
                while (Math.abs(topTrack.getCurrentPosition() - start) < 150 * (1 - outputLevel)) {
                    topTrack.setPower(power);
                }
            } else {
                while (Math.abs(topTrack.getCurrentPosition() - start) < 150 * (outputLevel - 1)) {
                    topTrack.setPower(-power / 5);
                }
            }
            outputLevel = 1;
        } else if (control2 && outputLevel != 2) {
            if (outputLevel == 0 || outputLevel == 1) {
                while (Math.abs(topTrack.getCurrentPosition() - start) < 150 * (2 - outputLevel)) {
                    topTrack.setPower(power);
                }
            } else {
                while (Math.abs(topTrack.getCurrentPosition() - start) < 150 * (outputLevel - 2)) {
                    topTrack.setPower(-power / 5);
                }
            }
            outputLevel = 2;
        } else if (control3 && outputLevel != 3) {
            while (Math.abs(topTrack.getCurrentPosition() - start) < 150 * (3 - outputLevel)) {
                topTrack.setPower(power);
            }
            outputLevel = 3;
        } else if (gamepad2.dpad_up) {
            topTrack.setPower(power);
        } else if (gamepad2.dpad_down) {
            topTrack.setPower(-power / 5);
        } else if (gamepad2.dpad_left) {
            topTrack.setPower(.25);
        } else {
            topTrack.setPower(0);
        }
        telemetry.addData("track encoder", topTrack.getCurrentPosition());
        telemetry.addData("track level", outputLevel);
        telemetry.update();
    }

    public void topTrackManual (double control, boolean hold)
    {
        if (control < -.2)
        {
            control = control/2;
            topTrack.setPower(control);
        }
        else if (control > .2)
        {
            control = control*1.5;
            if (control > 1)
            {
                control = 1;
            }
            topTrack.setPower(control);
        }
        else if (hold)
        {
                topTrack.setPower(.4);
        }
        else
        {
            topTrack.setPower(0);
        }
    }

    void gem_Test(boolean control)
    {
        if (control)
        {
            gemFlick.setPosition(1);
            gemArm.setPosition(1);
        }
        else
        {
            gemFlick.setPosition(0);
            gemArm.setPosition(0);
        }
    }

// ===================================== UTILITY METHODS ==================================

    public boolean toggle(boolean target, boolean control) {
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

    public double toggleDouble(double target, boolean control, double high, double low) {
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

