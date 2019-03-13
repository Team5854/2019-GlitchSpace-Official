package frc.robot;

import org.opencv.core.Mat;

public interface Camproc {
    public void process(Mat image, int cameraID);
}