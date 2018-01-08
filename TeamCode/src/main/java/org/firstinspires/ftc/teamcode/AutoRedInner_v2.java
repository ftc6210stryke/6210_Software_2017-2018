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
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;

@Autonomous (name="AutoRedInner_v2.0", group="Auto")
public class AutoRedInner_v2 extends AutoLibrary_v2{

    private RelicRecoveryVuMark targetColumn;

    @Override
    public void runOpMode() throws InterruptedException {

        initialize();
        double angle = getAngle();
//        extendGemArm(true);
//        sleep(500);
//        getGem(10, false);
//        sleep(500);
//        extendGemArm(false);
//        sleep(500);
//        gemFlick.setPosition(.15);
        sleep(500);
        move_encoder(.25, 0, 35);
        sleep(500);
        move_encoder(0, -.25, 1000);
        turn_gyro(.25, 90, 2);
        angle += 90;
        move_encoder(0, -.25, 1000);
        if (targetColumn == RelicRecoveryVuMark.RIGHT)
        {
            move_encoder(0, -.4, 1000); //1030
            telemetry.addLine("Move : ToRight : Complete");
            telemetry.update();
        }
        else if (targetColumn == RelicRecoveryVuMark.CENTER)
        {
            move_encoder(0, -.25, 530); //560
            telemetry.addLine("Move : ToCenter : Complete");
            telemetry.update();
        }
        else
        {
            telemetry.addLine("Move : ToLeft or Unknown : Complete");
            telemetry.update();
        }
        sleep(1000);
        move_encoder(-.25, 0, 300); //250
        telemetry.addLine("Move : ToGlyphBox : Complete");
        telemetry.update();
        startOutput(-.7);
        sleep(2000);
        stopOutput();
        sleep(100);
        move_encoder(-.2, 0, 100);
        sleep(100);
        move_encoder(.25, 0, 400);
        sleep(100);
    }
}