/*
AutoLibrary_v1
September 2017
6210 Software
- William Fisher
- Rohit Chawla
- Nihal Kyasa

Holds methods to be used for Autonomous programs in FTC's Relic Recovery Competition.
 */

package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;


public class Vuforia extends LinearOpMode {

    public static final String TAG = "Vuforia VuMark Sample";
    OpenGLMatrix lastLocation = null;
    VuforiaLocalizer vuforia;
    VuforiaTrackable relicTemplate;
    VuforiaTrackables relicTrackables;
    int cameraMonitorViewId;

    @Override
    public void runOpMode() throws InterruptedException {}

    public Vuforia(int cID)
    {
        cameraMonitorViewId = cID;
    }

    public int runVuforia()
    {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "ARUX4tP/////AAAAGXY2Dg+/sUl6gWdYntfHvN8GT9v/tqySPvCz3Nt2dTXFWQC7TJriGnCTY/vvHRRUFiSSI11yfUxGSTkNzXbHM0zBmGf3WiW6+kZsArc76UHXbUG1fHmPyIAljbqRBiNz8Kki/PlrJCwpNwmcZKNu8wvnYzGZ5phfZHXE6yyr2HvuEyX6IEYUvrvDtMImiHWHSbjK5wbgDyMinQU/FsVmDy0S1OHL+xVDk6yhjBsPBO2bsVMTKA3GRZAo+Qxjqd9nh95+jPt1EbE11VgPHzr/Zm8bKrr+gz24uxfsTgXU3sc6YLgdcegkRd6dxM5gvsu4xisSks+gkLismFPmNASP0JpDkom80KZ9MmEcbl7GnLO+";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

        relicTrackables.activate();
        sleep(2000);
        switch (RelicRecoveryVuMark.from(relicTemplate)) {
            case LEFT:
                telemetry.addLine("Left");
                telemetry.update();
                return 1;
            case CENTER:
                telemetry.addLine("Center");
                telemetry.update();
                return 2;
            case RIGHT:
                telemetry.addLine("Right");
                telemetry.update();
                return 3;
            case UNKNOWN:
                telemetry.addLine("Failed");
                telemetry.update();
                return 0;
            default:
                telemetry.addLine("Failed");
                telemetry.update();
                return 0;
        }
    }

}