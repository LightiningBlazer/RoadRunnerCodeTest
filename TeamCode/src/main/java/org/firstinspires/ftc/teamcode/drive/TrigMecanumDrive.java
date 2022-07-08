package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "TrigMecanumDrive")
public class TrigMecanumDrive extends OpMode {

    DcMotorEx RFMotor;
    DcMotorEx LFMotor;
    DcMotorEx RBMotor;
    DcMotorEx LBMotor;

    @Override
    public void init(){
        LFMotor = (DcMotorEx) hardwareMap.dcMotor.get("LFMotor");
        RFMotor = (DcMotorEx) hardwareMap.dcMotor.get("RFMotor");
        LBMotor = (DcMotorEx) hardwareMap.dcMotor.get("LBMotor");
        RBMotor = (DcMotorEx) hardwareMap.dcMotor.get("LBMotor");
        LBMotor.setDirection(DcMotorEx.Direction.REVERSE);
        LFMotor.setDirection(DcMotorEx.Direction.REVERSE);
    }
    //hi
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
        LFMotor.setVelocity((wheelPower * cosAngleRadians * factor + turn) * 537.7);
        RFMotor.setVelocity((wheelPower * sinAngleRadians * factor - turn) * 537.7);
        LBMotor.setVelocity((wheelPower * sinAngleRadians * factor + turn) * 537.7);
        RBMotor.setVelocity((wheelPower * cosAngleRadians * factor - turn) * 537.7);
    }
}
//Find Ticks Per Motor Rotation and Multiply the internal Power Value by that to Switch it to Velocity
//Easy to fix if it doesnt work dont have motor inverts for our current robot