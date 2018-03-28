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


public class NihalTeleOp extends OpMode {
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
        // brdrive.setPower(y+x);
        // fldrive.setPower(y+x);
        // bldrive.setPower(y-x);
        // frdrive.setPower(y-x);
        double newY = Math.pow(y, 2) * Math.abs(y)/y;
        double newX = Math.pow(x, 2) * Math.abs(x)/x;
        double XYsubtraction = newY - newX;
        double XYaddition = newY + newX;
        double subSquareRoot = Math.pow(Math.abs(XYsubtraction), 1/2) * Math.abs(XYsubtraction)/XYsubtraction;
        double addSquareRoot = Math.pow(Math.abs(XYaddition), 1/2) * Math.abs(XYaddition)/XYaddition;
        brdrive.setPower(addSquareRoot);
        fldrive.setPower(addSquareRoot);
        bldrive.setPower(subSquareRoot);
        frdrive.setPower(subSquareRoot);
    }

}