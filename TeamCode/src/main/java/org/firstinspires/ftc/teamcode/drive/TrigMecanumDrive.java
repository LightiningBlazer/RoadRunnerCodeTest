package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name = "TrigMecanumDrive")
public class TrigMecanumDrive extends OpMode {

    DcMotor RFMotor;
    DcMotor LFMotor;
    DcMotor RBMotor;
    DcMotor LBMotor;

    @Override
    public void init(){
        LFMotor = hardwareMap.dcMotor.get("LFMotor");
        RFMotor = hardwareMap.dcMotor.get("RFMotor");
        LBMotor = hardwareMap.dcMotor.get("LBMotor");
        RBMotor = hardwareMap.dcMotor.get("RBMotor");
    }

    @Override
    public void loop(){
        double lateral = gamepad1.left_stick_x;
        double longitudinal = -gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;
        double wheelPower = Math.hypot(lateral, longitudinal);
        double stickAngleRadians = Math.atan2(longitudinal, lateral);
        stickAngleRadians = stickAngleRadians - Math.PI/4;
        double sinAngleRadians = Math.sin(stickAngleRadians);
        double cosAngleRadians = Math.cos(stickAngleRadians);
        double factor = 1 / Math.max(Math.abs(sinAngleRadians), Math.abs(cosAngleRadians));
        LFMotor.setPower(wheelPower * cosAngleRadians * factor + turn);
        RFMotor.setPower(wheelPower * sinAngleRadians * factor - turn);
        LBMotor.setPower(wheelPower * sinAngleRadians * factor + turn);
        RBMotor.setPower(wheelPower * cosAngleRadians * factor - turn);
    }
}
//Find Ticks Per Motor Rotation and Multiply the internal Power Value by that to Switch it to Velocity