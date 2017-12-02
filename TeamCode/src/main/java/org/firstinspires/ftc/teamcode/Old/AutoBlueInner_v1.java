///*
//AutoMain_v1
//9/18/2017
//6210 Software
//- William Fisher
//- Rohit Chawla
//- Nihal Kyasa
//
//Controls robot with methods from AutoLibrary class in the
//autonomous period of FTC's Relic Recovery competition.
// */
//
//package org.firstinspires.ftc.teamcode.Old;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.util.ElapsedTime;
//import com.qualcomm.robotcore.util.Range;
//
//import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
//
//@Disabled
//@Autonomous (name="AutoBlueInner_v1", group="Auto")
//public class AutoBlueInner_v1 extends AutoLibrary_v1{
//
//    private ElapsedTime runtime = new ElapsedTime();
//    private RelicRecoveryVuMark targetColumn;
//
//    @Override
//    public void runOpMode() {
//
//
//        initialize();
//        waitForStart();
//        runtime.reset();
//        double angle = getAngle();
//        move_advanced(.25, 0, angle, 3, 1, 200);
//        turn_gyro(.3, angle, 3);
//        getGem(1, 3);
//        move_advanced(0, .25, angle, 3, 1, 200);
//        targetColumn = getSymbol_multitry(3, angle);
//        move_advanced(0, .25, angle, 3, 1, 400);
//        angle = angle - 90;
//        turn_gyro(.7, angle, 3);
//        move2Line(0, .25, 1000, angle, 3, 1, 3, false);
//        if (targetColumn == RelicRecoveryVuMark.CENTER)
//        {
//            move_advanced(0, .25, angle, 3, 1, 100);
//        }
//        else if (targetColumn == RelicRecoveryVuMark.LEFT)
//        {
//            move_advanced(0, .25, angle, 3, 1, 200);
//        }
//        else
//        {
//            move_advanced(0, .25, angle, 3, 1, 300);
//        }
//        turn_gyro(.3, angle, 3);
//        move_advanced(.25, 0, angle, 3, 1, 100);
//        startIntake(1);
//        sleep(500);
//        stopIntake();
//        startOutput(1);
//        sleep(500);
//        stopOutput();
//    }
//}
