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

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;

@Autonomous (name="AutoBlueInner_v2.0", group="Auto")
public class AutoBlueInner_v2 extends AutoLibrary_v2{

    private RelicRecoveryVuMark targetColumn;

    @Override
    public void runOpMode() throws InterruptedException {

        initialize();
        double angle = getAngle();
        getGemMultitry(1, 5, false, 3, angle);
        move_encoder(0, -.25, 100);
        targetColumn= getSymbol_multitry(3, angle);
        move_encoder(0, .25, 400);
        angle = angle - 90;
        turn_gyro(.25, angle, 3);
        move_encoder(0, .25, 300);
        if (targetColumn == RelicRecoveryVuMark.LEFT)
        {
            move_encoder(0, .25, 100);
        }
        else if (targetColumn == RelicRecoveryVuMark.CENTER)
        {
            move_encoder(0, .25, 200);
        }
        else
        {
            move_encoder(0, .25, 300);
        }
        turn_gyro(.3, angle, 3);
        move_encoder(-.25, 0, 100);
        moveTopTrack(-.5, 100);
        startOutput(1);
        sleep(500);
        stopOutput();
    }
}