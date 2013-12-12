import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.FileDialog;

public class BinaryImageDisplay {	
	private static final int DEFAULT_WIDTH = 640;
	private static final int DEFAULT_HEIGHT = 480;
	private static final String IMAGE_FORMAT = "PNG";
	private JFrame window;
	private ImagePanel imagePanel;
	private FileDialog fileChooser;
	private FileDialog fileSaver;
	private File selectedFile;
	ImageSorter sorter;
		
	public BinaryImageDisplay() {
		window = new JFrame("BinaryImage");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		window.setVisible(true);
		
		imagePanel = new ImagePanel();
		sorter = new ImageSorter();
		
		fileChooser = new FileDialog(window, "Choose source data", FileDialog.LOAD);
		fileChooser.setDirectory(System.getProperty("user.dir"));
		fileSaver = new FileDialog(window, "Save image", FileDialog.SAVE);
		fileSaver.setDirectory(System.getProperty("user.dir"));

		
		JMenuBar menuBar = new JMenuBar();
		window.setJMenuBar(menuBar);
		
		JMenu menu = new JMenu("Menu");
		menuBar.add(menu);
		
		int modifierKey;
		if (System.getProperty("os.name").equals("Mac OS X"))
			modifierKey = ActionEvent.META_MASK;
		else 
			modifierKey = ActionEvent.CTRL_MASK;
		
		JMenuItem mntmChooseSourceData = new JMenuItem("Choose source data");
		mntmChooseSourceData.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, modifierKey));
		mntmChooseSourceData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectFile();
			}
		});
		menu.add(mntmChooseSourceData);
		
		JMenuItem mntmSaveImage = new JMenuItem("Save image");
		mntmSaveImage.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, modifierKey));
		mntmSaveImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveImage();
			}
		});
		menu.add(mntmSaveImage);	
		
		JMenuItem mntmQuit = new JMenuItem("Quit");
		mntmQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, modifierKey));
		mntmQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		menu.add(mntmQuit);
		
		window.validate();
		window.getContentPane().add(imagePanel);
	}
	
	private void selectFile() {
		fileChooser.setVisible(true);
		String filename = fileChooser.getDirectory() + fileChooser.getFile();
		if (filename != null) {
			selectedFile = new File(filename);
			displayImage();
		}
	}
	
	private void displayImage() {
		ImageInterpreter rawData = new ImageInterpreter(selectedFile);
		imagePanel.setPixelData(rawData.getPixelArray());
		window.validate();
	}
	
	private void saveImage() {
		BufferedImage currentDisplay = imagePanel.getImage();
		fileSaver.setVisible(true);
		String filename = fileSaver.getDirectory() + fileSaver.getFile();
		if (filename != null) {
			try {
				ImageIO.write(currentDisplay, IMAGE_FORMAT, new File(filename));
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
	}
}
