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

package org.firstinspires.ftc.teamcode.TestCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.AutoLibrary_v2;

@Autonomous (name="Single Move Test", group="Auto")
public class Auto_Single_Move_Test extends AutoLibrary_v2 {

    @Override
    public void runOpMode() throws InterruptedException{

        initialize();
        double angle = getAngle();
        move_advanced_y(.4, angle, .86, 2000);
        /*

        - 48
        - 50.5
        - 50.5
        - 51.5
        - 50.5
        - 50.5
        - 48
        - 51.5
        - 51.5
        - 52
        - 49.5
        - 52

        Average = 50.5
        actual = 48.5

         */
    }
}
