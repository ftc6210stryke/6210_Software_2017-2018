package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;

/**
 * Created by Rohit on 11/13/17.
 */

// All values are place holders

public class P_Auto_VuMark_Rohit extends Practice_Auto_Library_Rohit {

    @Override
    public void runOpMode() throws InterruptedException
    {
        RelicRecoveryVuMark mark = getSymbol();
        move_encoder_yaxis(-0.3, 10);
        move_encoder_xaxis(-0.3, 10);

        if (mark == RelicRecoveryVuMark.LEFT)
        {
            move_encoder_xaxis(-0.3, 5);
        }
        else if (mark == RelicRecoveryVuMark.RIGHT)
        {
            move_encoder_xaxis(0.3, 5);
        }
        move_encoder_yaxis(0.3, 5);
        double start = System.currentTimeMillis();
        while ((System.currentTimeMillis() - start) < 500 && opModeIsActive())
        {
            output.setPower(1);
        }
    }
}

