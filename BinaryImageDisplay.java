import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class BinaryImageDisplay {	
	private static final int DEFAULT_WIDTH = 640;
	private static final int DEFAULT_HEIGHT = 480;
	private JFrame window;
	private JButton saveButton;
	private ImagePanel imagePanel;
	private JFileChooser fileChooser;
	private File selectedFile;
	ImageSorter sorter;
		
	public BinaryImageDisplay() {
		window = new JFrame("BinaryImage");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		window.setVisible(true);
		
		saveButton = new JButton("Save");
		imagePanel = new ImagePanel();
		fileChooser = new JFileChooser(System.getProperty("user.dir"));	
		sorter = new ImageSorter();
		
		JMenuBar menuBar = new JMenuBar();
		window.setJMenuBar(menuBar);
		
		JMenu menu = new JMenu("Menu");
		menuBar.add(menu);
		
		JMenuItem mntmChooseSourceData = new JMenuItem("Choose source data");
		mntmChooseSourceData.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.META_MASK));
		mntmChooseSourceData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectFile();
				displayImage();
			}
		});
		menu.add(mntmChooseSourceData);
		
		JMenuItem mntmSaveImage = new JMenuItem("Save image");
		mntmSaveImage.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.META_MASK));
		mntmSaveImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveImage();
			}
		});
		menu.add(mntmSaveImage);		
	}
	
	private void selectFile() {
		JFrame dialogWindow = new JFrame("Choose an image file");
		fileChooser.showOpenDialog(dialogWindow);
		selectedFile = fileChooser.getSelectedFile();
	}
	
	private void displayImage() {
		ImageInterpreter rawData = new ImageInterpreter(selectedFile);
		imagePanel.setPixelData(rawData.getPixelArray());
		window.getContentPane().add(imagePanel);
		window.validate();
	}
	
	private void saveImage() {
		BufferedImage currentDisplay = imagePanel.getImage();
		File outputfile;	
		JFrame dialogWindow = new JFrame("Save image");
		
		int returnVal = fileChooser.showSaveDialog(dialogWindow);
	    if (returnVal == JFileChooser.APPROVE_OPTION) {
	    	outputfile = fileChooser.getSelectedFile();	
			try {
				ImageIO.write(currentDisplay, "PNG", outputfile);
			} catch (IOException io) {
				
			}
	    }
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BinaryImageDisplay display = new BinaryImageDisplay();
		display.selectFile();
		display.displayImage();
	}
}
