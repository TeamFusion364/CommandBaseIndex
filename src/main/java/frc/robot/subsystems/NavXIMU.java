// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class NavXIMU extends SubsystemBase {
	public static AHRS ahrs = null;
	/** Creates a new IMU. */
	
	public IMU() {

		ahrs = new AHRS(SPI.Port.kMXP); /* Alternatives:  SPI.Port.kMXP, I2C.Port.kMXP or SerialPort.Port.kUSB */    
	}
	//Gyro acceleration and rotation info
	public void Gyroreadout() {

		SmartDashboard.putNumber(   "IMU_Accel_X",          ahrs.getWorldLinearAccelX());
		SmartDashboard.putNumber(   "IMU_Accel_Y",          ahrs.getWorldLinearAccelY());
		SmartDashboard.putBoolean(  "IMU_IsMoving",         ahrs.isMoving());
		SmartDashboard.putBoolean(  "IMU_IsRotating",       ahrs.isRotating());

	}
//Extra gyro data for debugging
	public void GyroDebug() {

		/* Display 6-axis Processed Angle Data                                      */
		SmartDashboard.putBoolean(  "IMU_Connected",        ahrs.isConnected());
		SmartDashboard.putBoolean(  "IMU_IsCalibrating",    ahrs.isCalibrating());
		SmartDashboard.putNumber(   "IMU_Yaw",              ahrs.getYaw());
		SmartDashboard.putNumber(   "IMU_Pitch",            ahrs.getPitch());
		SmartDashboard.putNumber(   "IMU_Roll",             ahrs.getRoll());
		
		/* Display tilt-corrected, Magnetometer-based heading (requires             */
		/* magnetometer calibration to be useful)                                   */
		
		SmartDashboard.putNumber(   "IMU_CompassHeading",   ahrs.getCompassHeading());
		
		/* Display 9-axis Heading (requires magnetometer calibration to be useful)  */
		SmartDashboard.putNumber(   "IMU_FusedHeading",     ahrs.getFusedHeading());

		/* These functions are compatible w/the WPI Gyro Class, providing a simple  */
		/* path for upgrading from the Kit-of-Parts gyro to the navx MXP            */
		
		SmartDashboard.putNumber(   "IMU_TotalYaw",         ahrs.getAngle());
		SmartDashboard.putNumber(   "IMU_YawRateDPS",       ahrs.getRate());

		/* Display Processed Acceleration Data (Linear Acceleration, Motion Detect) */
		
		SmartDashboard.putNumber(   "IMU_Accel_X",          ahrs.getWorldLinearAccelX());
		SmartDashboard.putNumber(   "IMU_Accel_Y",          ahrs.getWorldLinearAccelY());
		SmartDashboard.putBoolean(  "IMU_IsMoving",         ahrs.isMoving());
		SmartDashboard.putBoolean(  "IMU_IsRotating",       ahrs.isRotating());

		/* Display estimates of velocity/displacement.  Note that these values are  */
		/* not expected to be accurate enough for estimating robot position on a    */
		/* FIRST FRC Robotics Field, due to accelerometer noise and the compounding */
		/* of these errors due to single (velocity) integration and especially      */
		/* double (displacement) integration.                                       */
		
		SmartDashboard.putNumber(   "Velocity_X",           ahrs.getVelocityX());
		SmartDashboard.putNumber(   "Velocity_Y",           ahrs.getVelocityY());
		SmartDashboard.putNumber(   "Displacement_X",       ahrs.getDisplacementX());
		SmartDashboard.putNumber(   "Displacement_Y",       ahrs.getDisplacementY());

	}

 
	@Override
	public void periodic() {

		GyroDebug();
		Gyroreadout();
		// This method will be called once per scheduler run
		//If you don't need diagnostic info, remove GyroDebug.
	}
}
