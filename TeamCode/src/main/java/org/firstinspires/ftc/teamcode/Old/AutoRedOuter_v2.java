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

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.AutoLibrary_v2;

@Disabled
@Autonomous (name="AutoRedOuter_Old", group="Auto")
public class AutoRedOuter_v2 extends AutoLibrary_v2 {

    private RelicRecoveryVuMark targetColumn;

    @Override
    public void runOpMode() throws InterruptedException {

        initialize();
//        double angle = getAngle();
        extendGem(950, true);
        sleep(500);
        getGem(5, true);
        sleep(500);
        extendGem(950, false);
        sleep(500);
        move_encoder(.25, 0, 35);
        sleep(500);
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
        sleep(1500);
        move_encoder(.25, 0, 100);
        sleep(100);
        move_encoder(-.2, 0, 150);
        sleep(100);
        move_encoder(.25, 0, 400);
        stopOutput();
        sleep(100);
    }
}