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

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.AutoLibrary_v2;

@Autonomous (name="Correction Test 2", group="Auto")
public class Auto_Correction_Tests extends AutoLibrary_v2 {

    @Override
    public void runOpMode() throws InterruptedException{
        frdrive = hardwareMap.get(DcMotor.class, "fr");
        fldrive = hardwareMap.get(DcMotor.class, "fl");
        brdrive = hardwareMap.get(DcMotor.class, "br");
        bldrive = hardwareMap.get(DcMotor.class, "bl");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "GRYO";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        gyro = hardwareMap.get(BNO055IMU.class, "gyro");
        gyro.initialize(parameters);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        double angle = getAngle();
        sleep(100);
        move_advanced_y(.3, angle, .86, 4000);
        if (Math.abs(angle_delta(getAngle(), angle)) > 3)
        {
            telemetry.addLine("Correction Failed");
            telemetry.update();
;        }
        sleep(1000);
        move_advanced_y(-.3, angle, .86, 4000);
        if (Math.abs(angle_delta(getAngle(), angle)) > 3)
        {
            telemetry.addLine("Correction Failed");
            telemetry.update();
        }
        sleep(1000);
        move_advanced_x(.3, angle, .86, 4000);
        if (Math.abs(angle_delta(getAngle(), angle)) > 3)
        {
            telemetry.addLine("Correction Failed");
            telemetry.update();
        }
        sleep(1000);
        move_advanced_x(-.3, angle, .86, 4000);
        if (Math.abs(angle_delta(getAngle(), angle)) > 3)
        {
            telemetry.addLine("Correction Failed");
            telemetry.update();
        }
    }
}
