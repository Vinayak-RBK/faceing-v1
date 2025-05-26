package com.iss.test.imageprocessingusingopencv;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

//import org.opencv.core.Mat;
//
//import org.opencv.core.Core;
//import org.opencv.core.Mat;
//import org.opencv.imgcodecs.Imgcodecs;
//
//@Configuration
public class OpenCVTest {
	
	private static final String HAAR_CASCADE_PATH = "src/main/resources/haarcascade_frontalface_default.xml";

	
	public OpenCVTest() {
		 System.out.println("OpenCV loaded successfully!111");
	}
	
//	@PostConstruct
//    public void loadOpenCV() {
////        System.loadLibrary("C:\\\\Users\\\\bvina\\\\Downloads\\\\opencv\\\\build\\\\java\\\\x64\\\\opencv_java4110.dll");
//	System.load("C:\\Users\\bvina\\Downloads\\opencv\\build\\java\\x64\\opencv_java4110.dll");
//        System.out.println("OpenCV loaded successfully!");
//    }
//
	public static void main(String[] args) {
//        // Load OpenCV native library
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		
//        
        System.load("C:\\Users\\bvina\\Downloads\\opencv\\build\\java\\x64\\opencv_java4110.dll");
//
//     // Read an image
        String imagePath = "D:/face-ing/man-image.jpg";
        String imageCopyPath = "D:/face-ing/image-copy.png";
//        Mat image = Imgcodecs.imread(imagePath);
//
//        // Check if image is loaded successfully
//        if (image.empty()) {
//            System.out.println("Error: Image not loaded!");
//        } else {
//            System.out.println("Image Loaded: " + image.size());
//        }
        
        // --------------- 1 -----------
//        Mat image = Imgcodecs.imread(imagePath);
//        if (image.empty()) {
//            System.out.println("Error: Unable to read image!");
//        }
//        else
//        {
//        	System.out.println("Loaded image");
//        }
//        
//        Mat grayImage = new Mat(image.rows(), image.cols(), CvType.CV_8UC1);
//        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);
//
//        // Save the processed image
//        Imgcodecs.imwrite(imageCopyPath, grayImage);
//        System.out.println("Copied image");
        // ------------ 1 ------------
        
        // ----------- 2 ------------
        
        // Load the image
        Mat image = Imgcodecs.imread(imagePath);
        if (image.empty()) {
            System.out.println("Error: Unable to read image!");
        }
        else
        {
        	System.out.println("Found iamge");
        }
     // Load Haar Cascade Classifier
        CascadeClassifier faceDetector = new CascadeClassifier(HAAR_CASCADE_PATH);
        if (faceDetector.empty()) {
            System.out.println("Error: Haar cascade file not found!");
        }
        else
        {
        	System.out.println("File found");
        }
        
        
     // Convert to grayscale
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);

        // Detect faces
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(grayImage, faceDetections);
        
     // Draw rectangles around faces
        for (Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(image, new Point(rect.x, rect.y),
                    new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0), 3);
        }
        
        System.out.println("Completed...");
        
     // Save the output image
        Imgcodecs.imwrite(imageCopyPath, image);
        
        System.out.println("Detected " + faceDetections.toArray().length + " face(s). Output saved at: " + imageCopyPath);
        
    }
//	
	// D:/face-ing/Screenshot 2025-03-24 103725.png
}
