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

@Autonomous (name="AutoBlue_GemOnly_v3", group="Auto")
public class AutoBlue_GemOnly_v3 extends AutoLibrary_v2{

    private int targetColumn;

    @Override
    public void runOpMode() throws InterruptedException {

        initialize();
        sleep(250);
        extendGem(1900, true); //1300
        sleep(250);
        getGem(5, false);
        sleep(250);
        extendGem(1900, false); //1300
        sleep(250);
    }
}