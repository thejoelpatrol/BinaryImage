import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ImageInterpreter {
	ArrayList<Integer> pixels;
	
	public ImageInterpreter(File inputFile) {
		pixels = new ArrayList<Integer>();
		
		try {
			FileInputStream file = new FileInputStream(inputFile);
			byte[] bytes = new byte[3];
			while (file.read(bytes) > 0) {
				int r = 0xFF & bytes[0];
				int g = 0xFF & bytes[1];
				int b = 0xFF & bytes[2];
				int color = (0xFF << 24) | (r << 16) | (g << 8) | b;
				pixels.add(color);
			}
		} catch (IOException ioerror) {
			System.out.println("IO error");
		}
	}

	public ArrayList<Integer> getPixelArray() {
		return pixels;
	}
}
