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

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.TeleOpLibrary_v2;

@Disabled
@TeleOp(name="Angle Tests", group="TeleOp")
public class TeleOp_Angle_Test extends TeleOpLibrary_Testing_DriveOnly
{
    private double drivePowerMod;
    private double Testangle;

    @Override
    public void init() {
        drivePowerMod = .8;
        initialize();
    }

    @Override
    public void start() {
        Testangle = getAngle();
        telemetry.addData("angle :", Testangle);
        telemetry.update();

    }

    @Override
    public void loop() {

        drive_mecanum(drivePowerMod);
        drivePowerMod = toggleDouble(drivePowerMod, gamepad1.x, .8, .2);
        telemetry.addData("Current Angle :", getAngle());
        telemetry.addData("Angle Change :", angle_delta(getAngle(), Testangle));
        telemetry.update();

    }
    @Override
    public void stop() {
    }

}
