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
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Disabled
@TeleOp(name="TeleOp v2.2", group="TeleOp")
public class TeleOp_v2 extends TeleOpLibrary_v1
{
    private double drivePowerMod;
    private boolean tank;
    private boolean correction_actve;

    @Override
    public void init() {
        initialize();
        drivePowerMod = .8;
        tank = false;
        correction_actve = true;
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

        // Mecanum Drive
        if (!tank)
        {
           drive_mecanum(drivePowerMod, correction_actve);
        }
        //tank drive
        else
        {
            drive_tank(drivePowerMod);
        }
        //intake(gamepad1.a);
        //moveTopTrack(gamepad2.right_trigger, gamepad2.left_trigger);
        correction_actve = toggle(correction_actve, gamepad1.y);
        tank = toggle(tank, gamepad1.b);
        drivePowerMod = toggleDouble(drivePowerMod, gamepad1.x, .8, .2);

    }
    @Override
    public void stop() {
    }

}
