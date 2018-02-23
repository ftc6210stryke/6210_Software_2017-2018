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
@Autonomous (name="AutoRedInner_v4", group="Auto")
public class AutoRedInner_v4 extends AutoLibrary_v2{

    private int targetColumn;

    @Override
    public void runOpMode() throws InterruptedException {

        initialize();
        double angle = getAngle();
        sleep(250);
        targetColumn = getSymbol();
        sleep(250);
        extendGem(1600, false); //1300
        sleep(250);
        getGem(5, true);
        sleep(250);
        extendGem(1300, false);
        sleep(250);
        move_y_PIDGyro_Preset(.25, 35, angle);
        sleep(250);
        move_x_PIDGyro_Preset(.35, 1700, angle);
        sleep(250);
        turn_gyro(-.35, 90, 16); //-.4, 90, 2
        angle = getAngle() + 5; //used to be -=
        telemetry.addData("Final Angle", angle);
        sleep(250);
        move_x_PIDGyro_Preset(.35, 525, angle);
        sleep(100);
        //LEFT
        if (targetColumn == 1)
        {
            move_x_PIDGyro_Preset(.35, 975, angle);
            telemetry.addLine("Move : ToRight : Complete");
            telemetry.update();
        }
        else if (targetColumn == 2)
        {
            move_x_PIDGyro_Preset(.35, 490, angle);
            telemetry.addLine("Move : ToCenter : Complete");
            telemetry.update();
        }
        else
        {
            telemetry.addLine("Move : ToLeft or Unknown : Complete");
            telemetry.update();
        }
        sleep(250);
        move_y_PIDGyro_Preset(-.25, 250, angle);
        startOutput(-1);
        sleep(1500);
        move_y_PIDGyro_Preset(.25, 200, angle);
        sleep(100);
        move_y_PIDGyro_Preset(-.25, 200, angle);
        sleep(100);
        move_y_PIDGyro_Preset(.25, 400, angle);
        stopOutput();
        sleep(100);
    }
}