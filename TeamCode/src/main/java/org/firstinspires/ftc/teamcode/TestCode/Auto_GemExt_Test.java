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
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.AutoLibrary_v2;

@Autonomous (name="gemTest", group="TeleOp")
public class Auto_GemExt_Test extends AutoLibrary_v2 {

    @Override
    public void runOpMode() throws InterruptedException{

        gemServo_track = hardwareMap.get(CRServo.class, "GsT");

        waitForStart();

        while (!gamepad1.a && opModeIsActive()){}
        double timeStart = System.currentTimeMillis();
        while (gamepad1.a && opModeIsActive())
        {
            gemServo_track.setPower(.5);
            telemetry.addData("Time Elapsed :", System.currentTimeMillis() - timeStart);
            telemetry.update();
            //1292
            //1239
        }
        gemServo_track.setPower(0);
        while (!gamepad1.b && opModeIsActive()){}
        timeStart = System.currentTimeMillis();
        while(gamepad1.b && opModeIsActive())
        {
            gemServo_track.setPower(-.5);
            telemetry.addData("Time Elapsed :", System.currentTimeMillis() - timeStart);
            telemetry.update();
        }
        gemServo_track.setPower(0);
        while(!gamepad1.y && opModeIsActive()){}
    }
}
