package org.usfirst.frc.team5854.robot;

import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

public class Vision implements Runnable{
	 public CvSink cvSink;
	 public CvSource outputStream;
	 public Mat source = new Mat();
     public Mat output = new Mat();
	 
	public Vision() {
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(640, 480);
     
		cvSink = CameraServer.getInstance().getVideo();
		outputStream = CameraServer.getInstance().putVideo("Blur", 640, 480);
     
	}
	@Override
	public void run() {
		while(!Thread.interrupted()) {
	         cvSink.grabFrame(source);
	         Mat HSV = new Mat();
	         Imgproc.cvtColor(source, HSV, Imgproc.COLOR_BGR2HSV);
	     
	         Scalar min_pink = new Scalar(172,248,227);
	         Scalar max_pink = new Scalar(180,255,253);
	         Mat rangedImage = new Mat();
	         Core.inRange(HSV, min_pink, max_pink, rangedImage);
	         Mat blurred = new Mat();
	         Imgproc.blur(rangedImage, blurred, new Size(4,4));
	         Mat dilated = new Mat();
	         Imgproc.dilate(blurred, dilated, new Mat(), new Point(0,0), 3);
	         List<MatOfPoint> contours = null;
			 Imgproc.findContours(dilated, contours, new Mat(), 1, 1);
			 
			 double contourArea = Imgproc.contourArea(contours);
			 if(contourArea < min_area || contourArea > maxArea)
			 {
			     continue;
			 }
			 
			 
			
	         outputStream.putFrame(output);
	         
	         
	     }
	}

}
