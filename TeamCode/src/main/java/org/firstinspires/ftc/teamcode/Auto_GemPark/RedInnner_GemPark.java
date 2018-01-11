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

package org.firstinspires.ftc.teamcode.Auto_GemPark;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.AutoLibrary_v2;

@Disabled
@Autonomous (name="RedInner_GemPark", group="Auto")
public class RedInnner_GemPark extends AutoLibrary_v2 {

    @Override
    public void runOpMode() throws InterruptedException {

        initialize();
//        extendGemArm(true);
        sleep(500);
        getGem(10, true);
        sleep(500);
//        extendGemArm(false);
        sleep(500);
//        gemFlick.setPosition(.85);
        sleep(100);
        move_encoder(0, .25, 1000);
        sleep(500);
        move_encoder(-.25, 0, 400);
    }
}