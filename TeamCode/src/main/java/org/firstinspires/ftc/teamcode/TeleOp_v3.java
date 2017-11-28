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
    private boolean hold;
    boolean gem;

    @Override
    public void init() {
        drivePowerMod = .8;
        tank = false;
        hold = false;
        gem = false;
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
        topTrackManual(gamepad2.left_stick_y, hold);
        intake(gamepad2.right_bumper, gamepad2.left_bumper);
        output(gamepad2.right_trigger > .1, gamepad2.left_trigger > .1 );
        gem_Test(gem);
        hold = toggle(hold, gamepad2.b);
        tank = toggle(tank, gamepad1.b);
        gem = toggle(gem, gamepad2.a);
        drivePowerMod = toggleDouble(drivePowerMod, gamepad1.x, .8, .2);

    }
    @Override
    public void stop() {
    }

}
