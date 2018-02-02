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

package org.firstinspires.ftc.teamcode.Auto_ParkScore;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.AutoLibrary_v2;

@Disabled
@Autonomous (name="AutoBlueOuter_NO_GEM", group="Auto")
public class AutoBlueOuter_NOGEM_v1 extends AutoLibrary_v2 {

    private RelicRecoveryVuMark targetColumn;

    @Override
    public void runOpMode() throws InterruptedException {

        initialize();
        move_encoder(-.25, 0, 35);
        sleep(500);
        move_encoder(0, .25, 500);
        telemetry.addLine("Move : ToSymbol : Complete");
        telemetry.update();
        sleep(1000);
        targetColumn = RelicRecoveryVuMark.UNKNOWN;
        sleep(1000);
        move_encoder(0, .3, 1270); //1310
        telemetry.addLine("Move : ToLine : Complete");
        telemetry.update();
        sleep(1000);
        if (targetColumn == RelicRecoveryVuMark.RIGHT)
        {
            move_encoder(0, .4, 1000); //1030
            telemetry.addLine("Move : ToRight : Complete");
            telemetry.update();
        }
        else if (targetColumn == RelicRecoveryVuMark.CENTER)
        {
            move_encoder(0, .25, 530); //560
            telemetry.addLine("Move : ToCenter : Complete");
            telemetry.update();
        }
        else
        {
            telemetry.addLine("Move : ToLeft or Unknown : Complete");
            telemetry.update();
        }
        sleep(1000);
        move_encoder(.25, 0, 250);
        telemetry.addLine("Move : ToGlyphBox : Complete");
        telemetry.update();
        moveTopTrack(.6, 15);
        sleep(2000);
        move_encoder(.2, 0, 300);
        sleep(1000);
        moveTopTrack(-.6, 15);
        sleep(500);
        move_encoder(-.25, 0, 400);
    }
}