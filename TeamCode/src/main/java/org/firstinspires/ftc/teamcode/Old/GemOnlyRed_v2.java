/*
AutoMain_v1
9/18/2017
6210 Software
- William Fisher
- Rohit Chawla
- Nihal Kyasa

Controls robot with methods from AutoLibrary class in the
autonomous period of FTC's Relic Recovery competition.
 */

package org.firstinspires.ftc.teamcode.Old;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.AutoLibrary_v2;

@Disabled
@Autonomous (name="GemOnlyRed_v2.2", group="Auto")
public class GemOnlyRed_v2 extends AutoLibrary_v2 {

    private ElapsedTime runtime = new ElapsedTime();
    private RelicRecoveryVuMark targetColumn;

    @Override
    public void runOpMode() throws InterruptedException {

        initialize();
        double time = System.currentTimeMillis();
        while (Math.abs(System.currentTimeMillis() - time) < 400)
        {
            move_yaxis_basic(-.35);
        }
        stop_motors();
        getGem(3, true);
        time = System.currentTimeMillis();
        while (Math.abs(System.currentTimeMillis() - time) < 600)
        {
            move_yaxis_basic(.35);
        }
        stop_motors();
//        gemFlick.setPosition(1 - gemFlick.getPosition());
        sleep(500);
    }
}
