package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Rohit on 10/17/17.
 */

public class Practice_TeleOP_Rohit extends OpMode{

    DcMotor frdrive;
    DcMotor fldrive;
    DcMotor brdrive;
    DcMotor bldrive;
    double xpower;
    double ypower;
    boolean half_speed_on;
    double toggletime;


    public void init()
    {
        frdrive = hardwareMap.get(DcMotor.class, "c");
        fldrive = hardwareMap.get(DcMotor.class, "d");
        brdrive = hardwareMap.get(DcMotor.class, "a");
        bldrive = hardwareMap.get(DcMotor.class, "b");
        xpower = 0;
        ypower = 0;
        half_speed_on = false;
        double toggletime = (System.currentTimeMillis());

    }

    //=============================================== Mecanum Drive Loop ==============================================================

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
     if (half_speed_on)
     {
         ypower = ypower/2;
         xpower = xpower/2;
     }
        fldrive.setPower(ypower + xpower);
        frdrive.setPower(ypower - xpower);
        brdrive.setPower(-(ypower + xpower));
        bldrive.setPower(-(ypower - xpower));
        half_speed();
    }

    //=================================================Toggle (with example of half speed) ===============================================

    public void half_speed()
    {

        if (gamepad1.b && (System.currentTimeMillis() - toggletime > 100))
        {
            toggletime = System.currentTimeMillis();
            if (!half_speed_on) {
                half_speed_on = true;
            }
            else
            {
               half_speed_on = false;
            }
        }
    }

}
