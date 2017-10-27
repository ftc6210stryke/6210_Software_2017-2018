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
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="TeleOp v3.0", group="TeleOp")
public class TeleOp_v3 extends TeleOpLibrary_v2
{
    private double drivePowerMod;
    private boolean tank;

    @Override
    public void init() {
        drivePowerMod = .8;
        tank = false;
        initialize();
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

        // Mecanum Drive
        if (!tank)
        {
           drive_mecanum(drivePowerMod);
        }
        //tank drive
        else
        {
            drive_tank(drivePowerMod);
        }
        //intake(gamepad1.a);
        moveTopTrack(.5, gamepad2.a, gamepad2.b, gamepad2.x, gamepad2.y);
        tank = toggle(tank, gamepad1.b);
        drivePowerMod = toggleDouble(drivePowerMod, gamepad1.x, .8, .2);

    }
    @Override
    public void stop() {
    }

}
