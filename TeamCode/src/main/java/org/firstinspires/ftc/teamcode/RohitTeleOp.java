package org.firstinspires.ftc.teamcode;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Created by Rohit on 4/4/18.
 */

public class RohitTeleOp extends OpMode{
    public DcMotor bldrive;
    public DcMotor brdrive;
    public DcMotor fldrive;
    public DcMotor frdrive;

    public void init() {

    }

    void loop() // Mecanum Drive
    {
        double y = gamepad1.left_stick_y;
        if (Math.abs(y) < .1)
            y = 0;
        double x = gamepad1.left_stick_x;
        if (Math.abs(x) < .1)
            x = 0;
        frdrive.setPower(y-x);
        fldrive.setPower(y+x);
        brdrive.setPower(y+x);
        bldrive.setPower(y-x);


    }
}
