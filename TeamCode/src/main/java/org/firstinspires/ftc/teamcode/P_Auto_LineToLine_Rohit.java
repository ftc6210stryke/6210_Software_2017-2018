package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Rohit on 11/8/17.
 */

public class P_Auto_LineToLine_Rohit extends Practice_Auto_Library_Rohit{

    @Override
    public void runOpMode() throws InterruptedException

    {
        initialize();
        double angle = getAngle();
        cs_move_to_line(0.5, angle, 3);
        move_encoder(0.5, 10, 2);
        cs_move_to_line(0.5, angle, 3);
        if (getBlueGem(10))
        {
            gyro_turn(-90, 3);
        }
        stop_motors();


    }
}

