import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Disabled
@TeleOp(name="TeleOp Tank", group="TeleOp")
public class TeleOpTank extends OpMode
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

        bldrive  = hardwareMap.get(DcMotor.class, "c");
        brdrive = hardwareMap.get(DcMotor.class, "d");
        fldrive  = hardwareMap.get(DcMotor.class, "a");
        frdrive = hardwareMap.get(DcMotor.class, "b");

        telemetry.addData("Status", "Initialized");
    }


    @Override
    public void loop() {

        if (Math.abs(gamepad1.left_stick_y) > .1)
        {
            bldrive.setPower(gamepad1.left_stick_y);
            fldrive.setPower(gamepad1.left_stick_y);
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

        telemetry.addData("Status", "Run Time: " + runtime.toString());
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
