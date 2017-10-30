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
        }1
        //intake(gamepad1.a);
        angle_adjust(gamepad2.left_stick_y);
        intake(gamepad2.right_bumper, gamepad2.left_bumper);
        output(gamepad2.right_trigger > .1);
        tank = toggle(tank, gamepad1.b);
        drivePowerMod = toggleDouble(drivePowerMod, gamepad1.x, .8, .2);

    }
    @Override
    public void stop() {
    }

}
