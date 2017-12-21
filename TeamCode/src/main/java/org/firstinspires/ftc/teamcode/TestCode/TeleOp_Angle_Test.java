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

package org.firstinspires.ftc.teamcode.TestCode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.TeleOpLibrary_v2;

@TeleOp(name="Angle Tests", group="TeleOp")
public class TeleOp_Angle_Test extends TeleOpLibrary_Testing_DriveOnly
{
    private double drivePowerMod;
    private double angle;

    @Override
    public void init() {
        drivePowerMod = .8;
        initialize();
        angle = getAngle();

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

        drive_mecanum(drivePowerMod);
        drivePowerMod = toggleDouble(drivePowerMod, gamepad1.x, .8, .2);
        telemetry.addData("Current Angle :", getAngle());
        telemetry.addData("Angle Change :", angle_delta(getAngle(), angle));
        telemetry.update();

    }
    @Override
    public void stop() {
    }

}
