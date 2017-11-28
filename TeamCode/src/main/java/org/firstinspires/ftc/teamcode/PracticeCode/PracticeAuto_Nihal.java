//package org.firstinspires.ftc.teamcode;
//
//import com.qualcomm.hardware.bosch.BNO055IMU;
//import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
//import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.ColorSensor;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
//import com.qualcomm.robotcore.hardware.NormalizedRGBA;
//import com.qualcomm.robotcore.hardware.Servo;
//
//import org.firstinspires.ftc.robotcore.external.ClassFactory;
//import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
//import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
//import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
//import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
//import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
//import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
//import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
//import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
//import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
//import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
//
//public class PracticeAuto_Nihal extends LinearOpMode{
//
//    BNO055IMU gyro;
//    Orientation angles;
//    Acceleration gravity;
//
//    public DcMotor bldrive;
//    public DcMotor brdrive;
//    public DcMotor fldrive;
//    public DcMotor frdrive;
//    public double angle;
//    public ColorSensor groundSensor;
//
//    public void initialize() {
//        frdrive = hardwareMap.get(DcMotor.class, "a");
//        fldrive = hardwareMap.get(DcMotor.class, "b");
//        brdrive = hardwareMap.get(DcMotor.class, "c");
//        bldrive = hardwareMap.get(DcMotor.class, "d");
//        groundSensor = hardwareMap.get(ColorSensor.class, "cs");
//        fldrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        frdrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        fldrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        frdrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        brdrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        bldrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//
//        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
//        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
//        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
//        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
//        parameters.loggingEnabled      = true;
//        parameters.loggingTag          = "GRYO";
//        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
//        gyro = hardwareMap.get(BNO055IMU.class, "gyro");
//        gyro.initialize(parameters);
//
//        telemetry.addData("Status", "Initialized");
//        telemetry.update();
//
//        waitForStart();
//    }
//
//    @Override
//
////******************************************************* Move in a Box *************************************************************
//    public void runOpMode() {
//        initialize();
//        distance(1, 500);
//        turn_gyro(1, 90);
//        distance(1, 500);
//        turn_gyro(1, 90);
//        distance(1,500);
//        turn_gyro(1, 90);
//        distance(1, 500);
//
//    }
//
//
////******************************************** Basic Movements **********************************************************************
//    public void move_basic(double power) {
//        frdrive.setPower(power);
//        brdrive.setPower(power);
//        fldrive.setPower(-power);
//        bldrive.setPower(-power);
//    }
//
//    public void turn(double power) {
//        frdrive.setPower(power);
//        brdrive.setPower(power);
//        fldrive.setPower(power);
//        bldrive.setPower(power);
//    }
//
////******************************************************* Gyro Turn *****************************************************************
//    public void turn_gyro(double power, double angle)
//    {
//        while (math.abs(angle - getAngle()) > 3) {
//            if (angle > getAngle)
//            {
//                turn(power);
//            }
//            else {
//                turn(-power);
//            }
//        }
//        turn(0);
//    }
//
////**************************************************** Get Angle ********************************************************************
//    public double getAngle()
//    {
//        angles   = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
//        return angles.firstAngle;
//    }
//
////************************************************* Get Encoder ***********************************************************************
//    public double getEncoderAverage()
//    {
//        average = (fldrive.getCurrentPosition() + frdrive.getCurrentPosition() + bldrive.getCurrentPosition() + brdrive.getCurrentPosition())/4;
//        return average;
//    }
//
//    public void distance(double power, double distance)
//    {
//        double start = getEncoderAverage();
//        while (Math.abs(getEncoderAverage() - start) < distance)
//        {
//            move_basic(power);
//        }
//        move_basic(0);
//    }
//
////************************************************ Move Corrections *****************************************************************
//    public void move_corrections(double power, double threshold, double intensity)
//    {
//        frdrive.setPower(power * correction_R(threshold, intensity));
//        brdrive.setPower(power * correction_R(threshold, intensity));
//        fldrive.setPower(power * correction_L(threshold, intensity));
//        bldrive.setPower(power * correction_L(threshold, intensity));
//    }
//
//    public double correction_R (double threshold, double intensity)
//    {
//        if (math.abs(angle - getAngle()) > threshold)
//        {
//            return math.abs(angle - getAngle() )/90 + 1;
//        }
//        else if (math.abs(angle - getAngle()) < threshold)
//        {
//            return 1 - math.abs(angle - getAngle() )/90;
//        }
//    }
//
//    public double correction_L (double threshold, double intensity)
//    {
//
//    }
//
////******************************************************Color Sensor Move to Line************************************************************************
//    public void groundSensor_move_to_line(double power){
//        double.encoder_Start = getEncoderAvg();
//        while (opModeIsActive() && groundSensor.alpha < 100 && Math.abs(getEncoderAverage() - encoder_Start < 100)) {
//            move_basic(power);
//        }
//        move_basic(0);
//
//    }
//
//
//}
//
