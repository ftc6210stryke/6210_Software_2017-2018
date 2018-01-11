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

@TeleOp(name="TeleOp v3.5", group="TeleOp")
public class TeleOp_v3 extends TeleOpLibrary_v2
{
    private double drivePowerMod;
    private boolean tank;
    private boolean hold;
    private boolean reverse;
    private boolean relic_claw;

    @Override
    public void init() {
        drivePowerMod = .8;
        reverse = false;
        tank = false;
        hold = false;
        relic_claw = false;
        initialize();
        RelicArm.setPosition(.75);
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
        topTrackManual(gamepad2.left_stick_y, hold);
        intake(gamepad2.right_bumper, gamepad2.left_bumper);
        output(gamepad2.right_trigger > .1, gamepad2.left_trigger > .1 );
        relic(.8, gamepad2.right_stick_y, gamepad2.dpad_down, gamepad2.dpad_up, relic_claw);
        hold = toggle(hold, gamepad2.y);
        tank = toggle(tank, gamepad1.b);
        relic_claw = toggle(relic_claw, gamepad2.a);
        drivePowerMod = toggleDouble(drivePowerMod, gamepad1.x, .8, .3);


    }
    @Override
    public void stop() {
    }

}
