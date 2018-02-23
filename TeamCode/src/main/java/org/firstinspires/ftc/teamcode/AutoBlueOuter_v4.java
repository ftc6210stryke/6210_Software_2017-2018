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

@Disabled
@Autonomous (name="AutoBlueOuter_v4", group="Auto")
public class AutoBlueOuter_v4 extends AutoLibrary_v2 {

    private int targetColumn;

    @Override
    public void runOpMode() throws InterruptedException {

        initialize();
        double angle = getAngle();
        sleep(250);
        targetColumn = getSymbol();
        sleep(250);
        extendGem(1300, true);
        sleep(250);
        getGem(5, false);
        sleep(250);
        extendGem(1300, false);
        sleep(250);
        move_y_PIDGyro_Preset(.25, 35, angle);
        sleep(250);
        move_x_PIDGyro_Preset(-.35, 1850, angle);
        telemetry.addLine("Move : ToLine : Complete");
        telemetry.update();
        sleep(250);
        if (targetColumn == 3) {
            move_x_PIDGyro_Preset(-.35, 1050, angle);
            telemetry.addLine("Move : ToRight : Complete");
            telemetry.update();
        }
        //CENTER
        else if (targetColumn == 2) {
            move_x_PIDGyro_Preset(-.35, 500, angle);
            telemetry.addLine("Move : ToCenter : Complete");
            telemetry.update();
        }
        //LEFT or UNKNOWN
        else {
            telemetry.addLine("Move : ToLeft or Unknown : Complete");
            telemetry.update();
        }
        sleep(250);
        move_y_PIDGyro_Preset(-.25, 250, angle);
        telemetry.addLine("Move : ToGlyphBox : Complete");
        telemetry.update();
        startOutput(-1);
        sleep(1500);
        move_y_PIDGyro_Preset(.25, 200, angle);
        sleep(100);
        move_y_PIDGyro_Preset(-.25, 250, angle);
        sleep(100);
        move_y_PIDGyro_Preset(.25, 400, angle);
        stopOutput();
        sleep(100);
    }
}