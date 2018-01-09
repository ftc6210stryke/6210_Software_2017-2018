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

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;

@Autonomous (name="AutoRedOuter_v2.9", group="Auto")
public class AutoRedOuter_v2 extends AutoLibrary_v2{

    private RelicRecoveryVuMark targetColumn;

    @Override
    public void runOpMode() throws InterruptedException {

        initialize();
        double angle = getAngle();
        extendGem(1200, true);
        sleep(500);
//        getGem(10, true);
//        sleep(500);
        extendGem(1200, false);
        sleep(500);
//        gemFlick.setPosition(.15);
//        sleep(500);
        move_encoder(.25, 0, 35);
        sleep(500);
//        move_encoder(0, -.25, 500);
        telemetry.addLine("Move : ToSymbol : Complete");
        telemetry.update();
        sleep(1000);
        targetColumn = RelicRecoveryVuMark.UNKNOWN;
        sleep(1000);
        move_encoder(0, -.3, 2400); //2200 actually right
        telemetry.addLine("Move : ToLine : Complete");
        telemetry.update();
        sleep(1000);
        if (targetColumn == RelicRecoveryVuMark.LEFT)
        {
            move_encoder(0, -.4, 1000); //1030
            telemetry.addLine("Move : ToLeft : Complete");
            telemetry.update();
        }
        else if (targetColumn == RelicRecoveryVuMark.CENTER) //not actually center
        {
            move_encoder(0, -.25, 530); //560
            telemetry.addLine("Move : ToCenter : Complete");
            telemetry.update();
        }
        else
        {
            telemetry.addLine("Move : ToRight or Unknown : Complete");
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