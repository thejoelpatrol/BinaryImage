import javax.swing.JPanel;
import java.awt.image.*;
import javax.imageio.*;
import java.util.ArrayList;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Graphics;

public class ImagePanel extends JPanel implements ComponentListener {
	private static final int DEFAULT_WIDTH = 640;
	private static final int DEFAULT_HEIGHT = 480;
	BufferedImage image;
	ArrayList<Integer> originalPixels;
	
	public ImagePanel() {
		super();
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		addComponentListener(this);
	}
	
	public void setPixels (ArrayList<Integer> pixelArray) {
		originalPixels = pixelArray;
		int[] pixels = convertArray(pixelArray);
		setImage(pixels);
	}
	
	private void setImage (int[] pixels) {
		Dimension panelSize = this.getSize();
		image = new BufferedImage(panelSize.width, panelSize.height, BufferedImage.TYPE_INT_RGB);
		int[] pixelsSubset = subsetPixels(pixels);
		//image.setRGB(0, 0, size.width, size.height, pixelsSubset, 0, 0);
		
		WritableRaster pixelGrid = image.getRaster();
		pixelGrid.setDataElements(pixelGrid.getMinX(), pixelGrid.getMinY(), panelSize.width, panelSize.height, pixelsSubset);
		
		
	}
	
	private int[] convertArray (ArrayList<Integer> integers) {
		int[] primitiveArray = new int[integers.size()];	
		for (int i = 0; i < integers.size(); i++) 
			primitiveArray[i] = integers.get(i);	
		return primitiveArray;
	}
	
	private int[] subsetPixels (int[] fullArray) {
		Dimension size = this.getSize();
		int pixelArrayLength = size.width * size.height;
		int[] smallerArray = new int[pixelArrayLength];
		int numPixels = Math.min(pixelArrayLength, fullArray.length);
		for (int i = 0; i < numPixels; i++) {
			smallerArray[i] = fullArray[i];
		}
		return smallerArray;
	}
	
	public void updateImage () {
		//int[] currentPixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
		int[] pixelArray = convertArray(originalPixels);
		setImage(pixelArray);
		JLabel picture = new JLabel(new ImageIcon(image));
		removeAll();
		add(picture);
		repaint();
	}
	
	/*private void resize () {
		int[] currentPixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
		setImage(currentPixels);		
	}*/
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, Color.white, null);
	}
		
	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { updateImage(); }
	public void componentShown(ComponentEvent e) { }
}
