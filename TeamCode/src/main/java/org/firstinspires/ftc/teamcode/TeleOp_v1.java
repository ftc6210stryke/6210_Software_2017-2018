package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TeleOp v1.1", group="TeleOp")
public class TeleOp_v1 extends OpMode
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

    @Override
    public void init() {

        bldrive  = hardwareMap.get(DcMotor.class, "c");
        brdrive = hardwareMap.get(DcMotor.class, "d");
        fldrive  = hardwareMap.get(DcMotor.class, "a");
        frdrive = hardwareMap.get(DcMotor.class, "b");
        xpower = 0;
        ypower = 0;
        lturnpower = 0;
        rturnpower = 0;

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

        xpower = 0;
        ypower = 0;
        if (Math.abs(gamepad1.right_stick_y) > .1)
        {
            ypower = Math.pow(gamepad1.right_stick_y, 2) * gamepad1.right_stick_y / Math.abs(gamepad1.right_stick_y);
            if (Math.abs(ypower) > .45) {
                ypower = (ypower / Math.abs(ypower)) * .45;
            }
        }
        if (Math.abs(gamepad1.right_stick_x) > .1)
        {
            xpower = Math.pow(gamepad1.right_stick_x, 2) * gamepad1.right_stick_x / Math.abs(gamepad1.right_stick_x);
            if (Math.abs(xpower) > .45) {
                xpower = (xpower / Math.abs(xpower)) * .45;
            }
        }
        rturnpower = gamepad1.right_trigger;
        lturnpower = -gamepad1.left_trigger;

        if (rturnpower > .2)
        {
            frdrive.setPower(rturnpower);
            fldrive.setPower(rturnpower);
            brdrive.setPower(rturnpower);
            bldrive.setPower(rturnpower);
        }
        else if (lturnpower > .2)
        {
            frdrive.setPower(lturnpower);
            fldrive.setPower(lturnpower);
            brdrive.setPower(lturnpower);
            bldrive.setPower(lturnpower);
        }
        else if (Math.abs(gamepad1.right_stick_x) > .2 || Math.abs(gamepad1.right_stick_y) > .2)
        {
            frdrive.setPower(Math.pow(Math.abs(ypower + xpower), .5) * (ypower+xpower)/Math.abs(ypower+xpower));
            fldrive.setPower(-Math.pow(Math.abs(ypower - xpower), .5) * (ypower-xpower)/Math.abs(ypower-xpower));
            bldrive.setPower(-Math.pow(Math.abs(ypower + xpower), .5) * (ypower+xpower)/Math.abs(ypower+xpower));
            brdrive.setPower(Math.pow(Math.abs(ypower - xpower), .5) * (ypower-xpower)/Math.abs(ypower-xpower));
        } else
        {
            frdrive.setPower(0);
            fldrive.setPower(0);
            bldrive.setPower(0);
            brdrive.setPower(0);
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
