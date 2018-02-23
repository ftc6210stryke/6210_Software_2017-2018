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

@Autonomous (name="AutoRedOuter_3", group="Auto")
public class AutoRedOuter_v3 extends AutoLibrary_v2{

    private int targetColumn;

    @Override
    public void runOpMode() throws InterruptedException {

        initialize();
        double angle = getAngle();
        sleep(250);
        targetColumn = getSymbol();
        sleep(250);
        extendGem(1900, true); //1300 //1600
        sleep(250);
        getGem(5, true);
        sleep(250);
        extendGem(1900, false); //1300
        sleep(250);
        move_encoder(.25, 0, 35);
        sleep(250);
        move_advanced_x(.35, angle, .86, 1750); //1850
        telemetry.addLine("Move : ToLine : Complete");
        telemetry.update();
        sleep(100);
        //LEFT
        if (targetColumn == 1)
        {
            move_advanced_x(.35, angle, .86, 1000);
            telemetry.addLine("Move : ToLeft : Complete");
            telemetry.update();
        }
        //CENTER
        else if (targetColumn == 2)
        {
            move_advanced_x(.35, angle, .86, 475); //500
            telemetry.addLine("Move : ToCenter : Complete");
            telemetry.update();
        }
        else
        {
            telemetry.addLine("Move : ToRight or Unknown : Complete");
            telemetry.update();
        }
        sleep(250);
        move_encoder(-.25, 0, 250);
        startOutput(-1);
        sleep(1500);
        move_encoder(.25, 0, 200);
        sleep(100);
        move_encoder(-.2, 0, 200);
        sleep(100);
        move_encoder(.25, 0, 400);
        stopOutput();
        sleep(100);
    }
}