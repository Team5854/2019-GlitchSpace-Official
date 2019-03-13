package frc.robot;

import org.opencv.core.Mat;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;

public class CameraSystem extends Thread {
	
	public static final int RESOLUTION_X = 160, RESOLUTION_Y = 120;
	public static final int FPS = 30;
	private int cameraCount = 0;
	private UsbCamera[] cameras; // list all the cameras you want here
	private CvSink[] cvsinks; // for getting the camera to a Mat object
	private CvSource[] cameraBroadcast; // for setting up a stream that will broadcast back to the robot
	private Mat cameraImageMat; // the mat object for creating the drawings over the image
	private CameraServer[] camServers;
	private Camproc camproc;
    public CameraSystem(int cameraCount, Camproc camproc) {
		this.cameraCount = cameraCount;
		this.camproc = camproc;
		cameras = new UsbCamera[cameraCount];
		camServers = new CameraServer[cameraCount];
		cvsinks = new CvSink[cameraCount];
		cameraBroadcast = new CvSource[cameraCount];
		for (int i = 0; i < cameraCount; i++) {
			cameras[i] = new UsbCamera("Camera " + i, i);
			cameras[i].setResolution(RESOLUTION_X, RESOLUTION_Y); // lower res might be preferred 
			cameras[i].setFPS(FPS);
			camServers[i] = CameraServer.getInstance();
			cvsinks[i] = camServers[i].getVideo(cameras[i]);
			cameraBroadcast[i] = camServers[i].putVideo("Camera Broadcast " + i, RESOLUTION_X, RESOLUTION_Y);
		}
		cameraImageMat = new Mat();
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			for (int i = 0; i < this.cameraCount; i++) {
				if (cvsinks[i].grabFrame(cameraImageMat) == 0) continue;
				camproc.process(cameraImageMat, i);
				cameraBroadcast[i].putFrame(cameraImageMat);
			}
		}
		cameraImageMat.release();
	}
	
}