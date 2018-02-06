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
        frdrive = hardwareMap.get(DcMotor.class, "fr");
        fldrive = hardwareMap.get(DcMotor.class, "fl");
        brdrive = hardwareMap.get(DcMotor.class, "br");
        bldrive = hardwareMap.get(DcMotor.class, "bl");

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        double angle = getAngle();
        move_advanced_y(.4, angle, .86, 2000);
    }
}
