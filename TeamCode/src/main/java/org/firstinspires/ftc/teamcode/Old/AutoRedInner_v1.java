///*
//AutoRedInnerMain_v1
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
//package org.firstinspires.ftc.teamcode;
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
//@Autonomous (name="AutoRedInner_v1", group="Auto")
//public class AutoRedInner_v1 extends AutoLibrary_v2{
//
//    private RelicRecoveryVuMark targetColumn;

//    @Override
//    public void runOpMode() {
/*  move for positioning for jewel
    turn for positioning for jewel
    get jewel
    move for pictograph viewing
    get pictograph formation
    move/gyro positioning for output
    output unfold
    output
    move to park on balancing stone

    ask will about deleting elevator and creating conveyor code
    ask will about strategy in his blue inner and if that was based on previous manip or current

    -Rohit
 */
//        initialize();
//        waitForStart();
//        double angle = getAngle();
//        move_advanced();
//        turn_gyro();
//        getGem();
//        move_advanced();
//        targetColumn = getSymbol_multitry();
//        move_advanced();
//        angle = angle - 90;
//        turn_gyro();
//        move2Line();
//        if (targetColumn == RelicRecoveryVuMark.CENTER)
//        {
//            move_advanced();
//        }
//        else if (targetColumn == RelicRecoveryVuMark.LEFT)
//        {
//            move_advanced();
//        }
//        turn_gyro();
//        move_advances();
//        startIntake();
//        sleep();
//        stopIntake();
//        outputStart();
//        sleep();
//        outputStop();
//
//    }
//}