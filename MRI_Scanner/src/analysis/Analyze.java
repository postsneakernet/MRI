package analysis;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Analyze {
	
	public Analyze() {
		
	}
	
	public static String analyzeImage(String dir, String sep, String fileName) {
		final int MIN_RGB = Integer.parseInt("000000", 16);
		final int MAX_RGB = Integer.parseInt("444444", 16);
		final int RED = Integer.parseInt("FF0000", 16);
		
		BufferedImage imageIn;
		BufferedImage imageOut;
		String newFileName = null;
		
		try {
			imageIn = ImageIO.read(new File(fileName));
			imageOut = new BufferedImage(imageIn.getWidth(), imageIn.getHeight(),imageIn.getType());
			
			newFileName = dir + sep + "last-image" + ".analyzed.jpg";
			File output = new File(newFileName);
			
            System.out.printf("Analyzing image %s %n", fileName);

			for (int i = 0; i < imageIn.getWidth(); i++) {
				for (int j = 0; j < imageIn.getHeight(); j++) {
					if (Math.abs(imageIn.getRGB(i, j)) <= MAX_RGB &&
							Math.abs(imageIn.getRGB(i, j)) > MIN_RGB) {
						imageOut.setRGB(i, j, RED);
					}
					else {
						imageOut.setRGB(i, j, imageIn.getRGB(i, j));
					}
				}
			}
			
			ImageIO.write(imageOut, "jpg", output);
		}
		catch (IOException | IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		return newFileName;
	}

//	public static void main(String[] args) {
//		analyzeImage("mri2.jpg");
//	}
}