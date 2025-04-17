package com.iss.test.imageprocessingusingopencv;

//import java.awt.image.BufferedImage;
//import java.awt.image.DataBufferByte;
//
//import org.opencv.core.Mat;
//import org.opencv.imgcodecs.Imgcodecs;
//
//import javafx.application.Application;
//import javafx.embed.swing.SwingFXUtils;
//import javafx.scene.Scene;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.StackPane;
//import javafx.stage.Stage;
//
//public class ShowImage extends Application{
//	static {
//		System.load("C:\\Users\\bvina\\Downloads\\opencv\\build\\java\\x64\\opencv_java4110.dll");
//	}
//
//	@Override
//	 public void start(Stage stage) {
//        // Read image
//        Mat image = Imgcodecs.imread("D:/face-ing/Screenshot 2025-03-24 103725.png");
//        if (image.empty()) {
//            System.out.println("Error: Image not found!");
//            return;
//        }
//
//        // Convert Mat to BufferedImage
//        BufferedImage bufferedImage = matToBufferedImage(image);
//        Image fxImage = SwingFXUtils.toFXImage(bufferedImage, null);
//
//        // Show image in JavaFX
//        ImageView imageView = new ImageView(fxImage);
//        StackPane root = new StackPane(imageView);
//        Scene scene = new Scene(root, 800, 600);
//        stage.setScene(scene);
//        stage.setTitle("OpenCV Image Viewer");
//        stage.show();
//    }
//	
//	 public BufferedImage matToBufferedImage(Mat mat) {
//	        int type = (mat.channels() > 1) ? BufferedImage.TYPE_3BYTE_BGR : BufferedImage.TYPE_BYTE_GRAY;
//	        BufferedImage image = new BufferedImage(mat.width(), mat.height(), type);
//	        byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
//	        mat.get(0, 0, data);
//	        return image;
//	    }
//	 
//	 public static void main(String[] args) {
//	        launch(args);
//	    }
//
//}
