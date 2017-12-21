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

package org.firstinspires.ftc.teamcode.Auto_GemOnly;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.AutoLibrary_v2;

@Autonomous (name="GemOnlyRed_v3", group="Auto")
public class GemOnlyRed_v3 extends AutoLibrary_v2 {

    @Override
    public void runOpMode() throws InterruptedException {

        initialize();
        extendGemArm(true);
        sleep(500);
        getGem(10, true);
        sleep(500);
        extendGemArm(false);
        sleep(500);
        gemFlick.setPosition(.85);
        sleep(100);
}
}