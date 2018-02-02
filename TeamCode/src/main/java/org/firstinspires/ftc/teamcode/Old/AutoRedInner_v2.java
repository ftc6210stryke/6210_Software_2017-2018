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
@Autonomous (name="AutoRedInner_Testing", group="Auto")
public class AutoRedInner_v2 extends AutoLibrary_v2 {

    private int targetColumn;

    @Override
    public void runOpMode() throws InterruptedException {

        initialize();
        double angle = getAngle();
/*        extendGem(1300, true);
        sleep(500);
        getGem(5, true);
        sleep(500);
        extendGem(1300, false);*/
        sleep(500);
        move_encoder(.25, 0, 35);
        sleep(500);
        //move_encoder(0, -.35, 1700);
        move_advanced_x(.35, angle, .86, 1700);
        sleep(500);
        turn_gyro(-.35, 90, 16); //-.4, 90, 2
        angle = getAngle() + 5; //used to be -=
        telemetry.addData("Final Angle", angle);
        sleep(1000);
        //move_encoder(0, -.35, 650); //Natural Right
        move_advanced_x(.35, angle, .86, 525); //535
        sleep(100);
        while (!gamepad1.a && !gamepad1.b && !gamepad1.y && opModeIsActive()) {}
        if (gamepad1.a)
        {
            targetColumn = 3;
        }
        else if (gamepad1.b)
        {
            targetColumn = 2;
        }
        else if (gamepad1.y)
        {
            targetColumn = 1;
        }
        //LEFT
        if (targetColumn == 1)
        {
            //move_encoder(0, -.4, 1000); //1030
            move_advanced_x(.35, angle, .86, 975); //975
            telemetry.addLine("Move : ToRight : Complete");
            telemetry.update();
        }
        else if (targetColumn == 2)
        {
            //move_encoder(0, -.25, 530); //560
            move_advanced_x(.35, angle, .86, 490); //500
            telemetry.addLine("Move : ToCenter : Complete");
            telemetry.update();
        }
        else
        {
            telemetry.addLine("Move : ToLeft or Unknown : Complete");
            telemetry.update();
        }
        sleep(1000);
        sleep(500);
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