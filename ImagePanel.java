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
	private static final int DEFAULT_WIDTH = 800;
	private static final int DEFAULT_HEIGHT = 600;
	private static final int WHITE = 0xFFFFFFFF;
	BufferedImage image;
	ArrayList<Integer> fullData;
	
	public ImagePanel() {
		super();
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		addComponentListener(this);
	}
	
	public void setPixelData (ArrayList<Integer> pixelArray) {
		fullData = pixelArray;
		updateImage();
	}
	
	private void updateImage () {
		int[] pixelArray = convertToPrimitiveArray(fullData);
		translateDataToImage(pixelArray);
		JLabel picture = new JLabel(new ImageIcon(image));
		removeAll();
		add(picture);
		repaint();
	}

	private void translateDataToImage (int[] pixelData) {
		Dimension panelSize = this.getSize();
		image = new BufferedImage(panelSize.width, panelSize.height, BufferedImage.TYPE_INT_RGB);
		int[] visiblePixels = subsetPixels(pixelData);
		
		WritableRaster pixelGrid = image.getRaster();
		pixelGrid.setDataElements(pixelGrid.getMinX(), pixelGrid.getMinY(), panelSize.width, panelSize.height, visiblePixels);		
	}
	
	private int[] convertToPrimitiveArray (ArrayList<Integer> integers) {
		int[] primitiveArray = new int[integers.size()];	
		for (int i = 0; i < integers.size(); i++) 
			primitiveArray[i] = integers.get(i);	
		return primitiveArray;
	}	
	
	private int[] subsetPixels (int[] fullData) {
		Dimension panelSize = this.getSize();
		int numPixels = panelSize.width * panelSize.height;	
		int[] resultArray = new int[numPixels];
		
		if (numPixels < fullData.length) {
			for (int i = 0; i < numPixels; i++) 
				resultArray[i] = fullData[i];
		} else {
			for (int i = 0; i < fullData.length; i++) 
				resultArray[i] = fullData[i];			
			for (int i = fullData.length; i < numPixels; i++) 
				resultArray[i] = WHITE;
		}
		return resultArray;
	}

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