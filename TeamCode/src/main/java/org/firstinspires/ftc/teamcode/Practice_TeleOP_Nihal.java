package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Nihal on 10/17/17.
 */

public class Practice_TeleOP_Nihal extends OpMode{

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
        boolean half_speed_on = false;
        double time = System.currentTimeMillis();
    }

    public void loop()
    {
        double power = gamepad1.right_stick_y;

        half_speed();
        if (half_speed_on) {
            power = power / 2;
        }
        fldrive.setPower(power);
        frdrive.setPower(power);
        bldrive.setPower(power);
        brdrive.setPower(power);


    }

//*************************************************** Toggle Half Speed *************************************************
    public void half_speed()
    {
        if (half_speed_on == false) {
            if (gamepad1.b == true) {
                half_speed_on = true;
            }
            else half_speed_on = false;
        }
        else if (half_speed_on == true) {
            if (gamepad1.b == true) {
                 half_speed_on = false;
             }
            else { half_speed_on = true; }
        }

    }

    public void half_speed_simple()
    {
        if (gamepad1.b && System.currentTimeMillis() - toggletime > 100)
        {
            if (half_speed_on)
            {
                half_speed_on = false;
            }
            else
            {
                half_speed_on = true;
            }
            toggletime = System.currentTimeMillis();
        }

    }

}
