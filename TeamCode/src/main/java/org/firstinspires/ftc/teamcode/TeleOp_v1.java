/*
TeleOP_v1
9/11/2017
6210 Software
- William Fisher
- Rohit Chawla
- Nihal Kyasa

Allows driver to control the robot using a gamepad during
the driver controlled period of FTC's Relic Recovery competition.
 */

package org.firstinspires.ftc.teamcode;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TeleOp v1.3", group="TeleOp")
public class TeleOp_v1 extends TeleOpLibrary_v1
{
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor bldrive;
    private DcMotor brdrive;
    private DcMotor fldrive;
    private DcMotor frdrive;
    private double ypower;
    private double xpower;
    private double rturnpower;
    private double lturnpower;
    private double toggleguard;
    private double drivePowerMod;
    private boolean tank;


    @Override
    public void init() {

        brdrive  = hardwareMap.get(DcMotor.class, "c");
        bldrive = hardwareMap.get(DcMotor.class, "d");
        frdrive  = hardwareMap.get(DcMotor.class, "a");
        fldrive = hardwareMap.get(DcMotor.class, "b");
        drivePowerMod = 1;
        xpower = 0;
        ypower = 0;
        lturnpower = 0;
        rturnpower = 0;
        tank = false;

        telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY

    @Override
    public void init_loop() {
    }
    */

    /*
     * Code to run ONCE when the driver hits PLAY

    @Override
    public void start() {
        runtime.reset();
    }
    */

    @Override
    public void loop() {

        toggleguard = 0;

        // Mecanum Drive
        if (tank == false) {
            xpower = 0;
            ypower = 0;
            //Determine ypower from squaring the ystick multipled by +/-
            if (Math.abs(gamepad1.right_stick_y) > .1) {
                ypower = Math.pow(gamepad1.right_stick_y, 2) * gamepad1.right_stick_y / Math.abs(gamepad1.right_stick_y);
                //if ypower is greater than .45, reduce to .45 to prevent going over 1
                if (Math.abs(ypower) > .45) {
                    ypower = (ypower / Math.abs(ypower)) * .45;
                }
            }
            //Determine xpower from squaring the xstick multipled by +/-
            if (Math.abs(gamepad1.right_stick_x) > .1) {
                xpower = Math.pow(gamepad1.right_stick_x, 2) * gamepad1.right_stick_x / Math.abs(gamepad1.right_stick_x);
                //if xpower is greater than .45, reduce to .45 to prevent going over 1
                if (Math.abs(xpower) > .45) {
                    xpower = (xpower / Math.abs(xpower)) * .45;
                }
            }
            //set turnpowers to the power of the different triggers
            rturnpower = gamepad1.right_trigger;
            lturnpower = gamepad1.left_trigger;


            //If rturnpower if greater than .1, turn right
            if (rturnpower > .1) {
                frdrive.setPower(-rturnpower * drivePowerMod);
                fldrive.setPower(-rturnpower * drivePowerMod);
                brdrive.setPower(-rturnpower * drivePowerMod);
                bldrive.setPower(-rturnpower * drivePowerMod);
            }
            //If lturnpower if greater than .1, turn left
            else if (lturnpower > .1) {
                frdrive.setPower(lturnpower * drivePowerMod);
                fldrive.setPower(lturnpower * drivePowerMod);
                brdrive.setPower(lturnpower * drivePowerMod);
                bldrive.setPower(lturnpower * drivePowerMod);
            }
            //if either joystick is over .1, engage mecanum drive
            else if (Math.abs(gamepad1.right_stick_x) > .1 || Math.abs(gamepad1.right_stick_y) > .1) {
                //subtpower && pluspower in formulas specific to controlling the wheels in mecanum drive
                double subtpower = ypower - xpower;
                double pluspower = ypower + xpower;
                //as long as subtpower is over .1 (so as not to take sqaureroot of zero) power subt motors
                // using the squareroot of subtpower times +/-
                if (Math.abs(subtpower) > .1) {
                    fldrive.setPower(-Math.pow(Math.abs(subtpower), .5) * (subtpower) / Math.abs(subtpower) * drivePowerMod);
                    brdrive.setPower(Math.pow(Math.abs(subtpower), .5) * (subtpower) / Math.abs(subtpower) * drivePowerMod);
                }
                //otherwise, subtpower motors are turned off
                else {
                    frdrive.setPower(0);
                    bldrive.setPower(0);
                }
                //as long as pluspower is over .1 (so as not to take sqaureroot of zero) power plus motors
                // using the squareroot of pluspower times +/-
                if (Math.abs(pluspower) > .1) {
                    frdrive.setPower(Math.pow(Math.abs(pluspower), .5) * (pluspower) / Math.abs(pluspower) * drivePowerMod);
                    bldrive.setPower(-Math.pow(Math.abs(pluspower), .5) * (pluspower) / Math.abs(pluspower) * drivePowerMod);
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
        } else {

            if (Math.abs(gamepad1.left_stick_y) > .1)
            {
                bldrive.setPower(-gamepad1.left_stick_y);
                fldrive.setPower(-gamepad1.left_stick_y);
            }
            else {bldrive.setPower(0);
                fldrive.setPower(0);}

            if (Math.abs(gamepad1.right_stick_y) > .1)
            {
                brdrive.setPower(gamepad1.right_stick_y);
                frdrive.setPower(gamepad1.right_stick_y);
            }
            else {brdrive.setPower(0);
                frdrive.setPower(0);}

        }

        // Mecanum-Tank Toggle
        if (gamepad1.b && System.currentTimeMillis() - toggleguard > 100)
        {
            toggleguard = System.currentTimeMillis();
            if (tank == false)
            {
                tank = true;
            } else {
                tank = false;
            }
        }

        // toggle reduces drive speed to 2/3s power
        if (gamepad1.y && System.currentTimeMillis() - toggleguard > 100)
        {
            toggleguard = System.currentTimeMillis();
            if (drivePowerMod == 1)
            {
                drivePowerMod = .66;
            } else {
                drivePowerMod = 1;
            }
        }

        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Joystick", "xpower (%.2f), ypower (%.2f)", xpower, ypower);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */

    @Override
    public void stop() {
    }

}
