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

@Autonomous (name="AutoBlueOuter_GlyphOnly_v3", group="Auto")
public class AutoBlueOuter_GlyphOnly_v3 extends AutoLibrary_v2 {

    private int targetColumn;

    @Override
    public void runOpMode() throws InterruptedException {

        initialize();
        double angle = getAngle();
        sleep(250);
        targetColumn = getSymbol();
        sleep(250);
        move_encoder(.25, 0, 35);
        sleep(250);
        move_advanced_x(-.35, angle, .86, 1850);
        telemetry.addLine("Move : ToLine : Complete");
        telemetry.update();
        sleep(250);
        if (targetColumn == 3) {
            move_advanced_x(-.35, angle, .86, 1050); //1000
            telemetry.addLine("Move : ToRight : Complete");
            telemetry.update();
        }
        //CENTER
        else if (targetColumn == 2) {
            move_advanced_x(-.35, angle, .86, 500); //525
            telemetry.addLine("Move : ToCenter : Complete");
            telemetry.update();
        }
        //LEFT or UNKNOWN
        else {
            telemetry.addLine("Move : ToLeft or Unknown : Complete");
            telemetry.update();
        }
        sleep(250);
        move_encoder(-.25, 0, 250);
        telemetry.addLine("Move : ToGlyphBox : Complete");
        telemetry.update();
        startOutput(-1);
        sleep(1500);
        move_encoder(.25, 0, 200); //100
        sleep(100);
        move_encoder(-.2, 0, 200); //150
        sleep(100);
        move_encoder(.25, 0, 400);
        stopOutput();
        sleep(100);
    }
}