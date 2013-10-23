//import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class ImageSorterDisplay {	
	private static final int DEFAULT_WIDTH = 640;
	private static final int DEFAULT_HEIGHT = 480;
	private JFrame window;
	private ImagePanel imagePanel;
	private JFileChooser fileChooser;
	private File selectedFile;
	ImageInterpreter pixels;
	ImageSorter sorter;
		
	public ImageSorterDisplay() {
		window = new JFrame("ImageSorter");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		window.setVisible(true);
		
		imagePanel = new ImagePanel();
		
		fileChooser = new JFileChooser();	
		sorter = new ImageSorter();
	}
	
	private void selectFile() {
		JFrame dialogWindow = new JFrame("Choose an image file");
		fileChooser.showOpenDialog(dialogWindow);
		selectedFile = fileChooser.getSelectedFile();
	}
	
	private void displayImage() {
		pixels = new ImageInterpreter(selectedFile);
		//ArrayList<Integer> pixelArray = pixels.getPixelArray();
		//pixelArray = sorter.sortImage(pixelArray, imagePanel.getSize(null));		
		imagePanel.setPixels(pixels.getPixelArray());
		window.add(imagePanel);
		window.validate();

		//window.pack();		
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ImageSorterDisplay display = new ImageSorterDisplay();
		display.selectFile();
		display.displayImage();
	}
}
