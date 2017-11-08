package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Rohit on 11/8/17.
 */

public class Practice_Auto_Rohit extends Practice_Auto_Library_Rohit{

    @Override
    public void runOpMode() throws InterruptedException

    {
        initialize();
        waitForStart();
        //runtime.reset();
        cs_move_to_line(0.5);

        cs_move_to_line(0.5);


    }
}

