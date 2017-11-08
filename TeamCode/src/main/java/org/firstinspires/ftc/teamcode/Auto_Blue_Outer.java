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
import com.qualcomm.robotcore.util.ElapsedTime;

@Disabled
@Autonomous (name="MainAuto_v1.0", group="Auto")
public class Auto_Blue_Outer extends AutoLibrary_v1{

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        initialize();
        waitForStart();
        runtime.reset();
        //More later
        double angle = getAngle();
        move_advanced(.25, 0, angle, 3, 1, 200);
        getGem(1, 3);
        move_advanced(0, .25, angle, 3, 1, 200);
    }
}
