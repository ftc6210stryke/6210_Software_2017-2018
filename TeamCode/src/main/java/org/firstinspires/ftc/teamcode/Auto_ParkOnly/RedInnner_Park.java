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

package org.firstinspires.ftc.teamcode.Auto_ParkOnly;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.AutoLibrary_v2;

@Disabled
@Autonomous (name="RedInner_Park", group="Auto")
public class RedInnner_Park extends AutoLibrary_v2 {

    @Override
    public void runOpMode() throws InterruptedException {

        initialize();
        move_encoder(0, .25, 1000);
        sleep(500);
        move_encoder(-.25, 0, 400);
    }
}