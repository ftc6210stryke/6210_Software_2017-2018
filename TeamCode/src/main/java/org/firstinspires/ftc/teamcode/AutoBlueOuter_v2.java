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

@Autonomous (name="AutoBlueOuter_v2.0", group="Auto")
public class AutoBlueOuter_v2 extends AutoLibrary_v2{

    private RelicRecoveryVuMark targetColumn;

    @Override
    public void runOpMode() throws InterruptedException {

        initialize();
        double angle = getAngle();
        //getGemMultitry(1, 5, false, 3, angle);
        move_encoder(0, .25, 500);
        telemetry.addLine("Move : ToSymbol : Complete");
        telemetry.update();
        sleep(1000);
        //targetColumn= getSymbol_multitry(3, angle);
        move_encoder(0, .3, 1300);
        telemetry.addLine("Move : ToLine : Complete");
        telemetry.update();
        sleep(1000);
        targetColumn = RelicRecoveryVuMark.UNKNOWN;
        if (targetColumn == RelicRecoveryVuMark.LEFT)
        {
            telemetry.addLine("Move : ToLeft : Complete");
            telemetry.update();
        }
        else if (targetColumn == RelicRecoveryVuMark.CENTER)
        {
            move_encoder(0, .25, 500);
            telemetry.addLine("Move : ToCenter : Complete");
            telemetry.update();
        }
        else
        {
            move_encoder(0, .4, 1000);
            telemetry.addLine("Move : ToRight or Unknown : Complete");
            telemetry.update();
        }
        sleep(1000);
        move_encoder(.25, 0, 200);
        telemetry.addLine("Move : ToGlyphBox : Complete");
        telemetry.update();
        startOutput(1);
        sleep(2000);
        stopOutput();
    }
}