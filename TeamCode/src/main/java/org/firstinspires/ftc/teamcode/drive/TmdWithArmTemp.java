package org.firstinspires.ftc.teamcode.drive;

import com.acmerobotics.roadrunner.util.Angle;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.*;

@TeleOp(name = "TmdWithArmTemp")
public class TmdWithArmTemp extends OpMode {

    DcMotorEx RFMotor;
    DcMotorEx LFMotor;
    DcMotorEx RBMotor;
    DcMotorEx LBMotor;
    DcMotorEx AngleMotor;
    DcMotorEx ExtensionMotor;

    @Override
    public void init(){
        LFMotor = (DcMotorEx) hardwareMap.dcMotor.get("LFMotor");
        RFMotor = (DcMotorEx) hardwareMap.dcMotor.get("RFMotor");
        LBMotor = (DcMotorEx) hardwareMap.dcMotor.get("LBMotor");
        RBMotor = (DcMotorEx) hardwareMap.dcMotor.get("LBMotor");
        AngleMotor = (DcMotorEx) hardwareMap.dcMotor.get("AngleMotor");
        ExtensionMotor = (DcMotorEx) hardwareMap.dcMotor.get("ExtensionMotor");

        LBMotor.setDirection(DcMotorEx.Direction.REVERSE);
        LFMotor.setDirection(DcMotorEx.Direction.REVERSE);

        AngleMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        AngleMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        AngleMotor.setTargetPosition(0);
    }

    @Override
    public void loop(){
        //Start Movement Code
        double lateral = gamepad1.left_stick_x;
        double longitudinal = -gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;
        double wheelPower = Math.hypot(lateral, longitudinal);
        double stickAngleRadians = Math.atan2(longitudinal, lateral);
        stickAngleRadians = stickAngleRadians - (Math.PI / 4);
        double sinAngleRadians = Math.sin(stickAngleRadians);
        double cosAngleRadians = Math.cos(stickAngleRadians);
        double factor = 1 / Math.max(Math.abs(sinAngleRadians), Math.abs(cosAngleRadians));
        LFMotor.setVelocity((wheelPower * cosAngleRadians * factor + turn) * 537.7);
        RFMotor.setVelocity((wheelPower * sinAngleRadians * factor - turn) * 537.7);
        LBMotor.setVelocity((wheelPower * sinAngleRadians * factor + turn) * 537.7);
        RBMotor.setVelocity((wheelPower * cosAngleRadians * factor - turn) * 537.7);
        //End Movement Code
        //Begin Arm Code
        ExtensionMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        ExtensionMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        int AngOld = AngleMotor.getTargetPosition();

        if(gamepad1.a){
            int AngNew = AngOld + 100;
            AngOld = AngNew;
            while (AngNew - 50 < AngleMotor.getCurrentPosition() && AngNew + 50 > AngleMotor.getCurrentPosition()) {
                if (AngNew - 50 < AngleMotor.getCurrentPosition()) {
                    AngleMotor.setVelocity(-200);
                } else if (AngNew + 50 > AngleMotor.getCurrentPosition()) {
                    AngleMotor.setVelocity(200);
                } else {
                    break;
                }
            }
        }

        if(gamepad1.b){
            int AngNew = AngOld - 100;
            AngOld = AngNew;
            while (AngNew - 50 < AngleMotor.getCurrentPosition() && AngNew + 50 > AngleMotor.getCurrentPosition()) {
                if (AngNew - 50 < AngleMotor.getCurrentPosition()) {
                    AngleMotor.setVelocity(-200);
                } else if (AngNew + 50 > AngleMotor.getCurrentPosition()) {
                    AngleMotor.setVelocity(200);
                } else {
                    break;
                }
            }
        }

    }
}