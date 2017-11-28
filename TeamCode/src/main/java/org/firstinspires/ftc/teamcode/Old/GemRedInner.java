//package org.firstinspires.ftc.teamcode;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.util.ElapsedTime;
//
//import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
//
///**
// * Created by Rohit on 11/3/17.
// */
//
//@Disabled
//@Autonomous(name="GemRedInner", group="Auto")
//public class GemRedInner extends AutoLibrary_v2{
//
//    private ElapsedTime runtime = new ElapsedTime();
//    private RelicRecoveryVuMark targetColumn
//
//    @Override
//    public void runOpMode() {
//
//        initialize();
//        waitForStart();
//        double angle = getAngle();
//        move_advanced(.3, 0, angle, 2, 1, 200);
//        turn_gyro(.3, angle, 3);
//        getGem(1, 3, true);
//        move_advanced(-.3, 0, angle, 2, 1, 100);
//        move_advanced(0, -.3, angle, 2, 1, 500);
//
//
//
//    }
//
//
//}
