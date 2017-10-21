package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Rohit on 10/17/17.
 */

public class Practice_TeleOP extends OpMode{

    DcMotor frdrive;
    DcMotor fldrive;
    DcMotor brdrive;
    DcMotor bldrive;
    double xpower;
    double ypower;


    public void init()
    {
        frdrive = hardwareMap.get(DcMotor.class, "c");
        fldrive = hardwareMap.get(DcMotor.class, "d");
        brdrive = hardwareMap.get(DcMotor.class, "a");
        bldrive = hardwareMap.get(DcMotor.class, "b");
        xpower = 0;
        ypower = 0;

    }

    public void loop()
    {
        xpower = gamepad1.right_stick_x;
        ypower = gamepad1.right_stick_y;

     if (xpower < .1)
     {
         xpower = 0;
     }
     if (ypower < .1)
     {
         ypower = 0;
     }
        fldrive.setPower(ypower + xpower);
        frdrive.setPower(ypower - xpower);
        brdrive.setPower(-(ypower + xpower));
        bldrive.setPower(-(ypower - xpower));



    }

}
