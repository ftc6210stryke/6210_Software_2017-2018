package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TeleOp v1.0", group="TeleOp")
public class TeleOp_v1 extends OpMode
{
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor bldrive = null;
    private DcMotor brdrive = null;
    private DcMotor fldrive = null;
    private DcMotor frdrive = null;
    private double ypower;
    private double xpower;
    private double rturnpower;
    private double lturnpower;

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        bldrive  = hardwareMap.get(DcMotor.class, "bldrive");
        brdrive = hardwareMap.get(DcMotor.class, "brdrive");
        fldrive  = hardwareMap.get(DcMotor.class, "fldrive");
        frdrive = hardwareMap.get(DcMotor.class, "frdrive");

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

        //assuming pushing joysticks at 45 will return 1,1 rather than .5,.5 must be tested

        ypower = Math.pow(gamepad1.right_stick_y, 2);
        xpower = Math.pow(gamepad1.right_stick_x, 2);
        rturnpower = gamepad1.right_trigger;
        lturnpower = -gampad1.left_trigger;

        if (rturnpower > .1 && lturnpower < .1 && xpower < .1 && ypower < .1)
        {
            frdrive.setPower(rturnpower);
            fldrive.setPower(rturnpower);
            brdrive.setPower(rturnpower);
            bldrive.setPower(rturnpower);
        }
        else if (lturnpower > .1 && xpower < .1 && ypower < .1)
        {
            frdrive.setPower(lturnpower);
            fldrive.setPower(lturnpower);
            brdrive.setPower(lturnpower);
            bldrive.setPower(lturnpower);
        }
        else if (xpower > .1 || ypower >.1)
        {
            frdrive.setPower(Math.pow(ypower + xpower, .5));
            fldrive.setPower(-Math.pow(ypower - xpower, .5));
            brdrive.setPower(Math.pow(ypower - xpower, .5));
            bldrive.setPower(-Math.pow(ypower + xpower, .5));
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
