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
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.AutoLibrary_v2;

@Disabled
@Autonomous (name="GemOnlyRedOuter_v1", group="Auto")
public class GemOnlyRedOuter_v1 extends AutoLibrary_v2 {

    private ElapsedTime runtime = new ElapsedTime();
    private RelicRecoveryVuMark targetColumn;

    @Override
    public void runOpMode() throws InterruptedException {

        initialize();
        waitForStart();
        double angle = getAngle();
        move_encoder(-.3, 0, 25);
        getGem(1, true);
        move_encoder(.3, 0, 25);
//        resetGem();
        move_encoder(0, -.3, 500);
    }
}
