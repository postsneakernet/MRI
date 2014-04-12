package analysis;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Analyze {
	final static int MIN_RGB = Integer.parseInt("000000", 16);
	final static int MAX_RGB = Integer.parseInt("444444", 16);
	final static int RED = Integer.parseInt("FF0000", 16);
	
	/*
	 * Returns amount of pixels in tumor
	 */
	public static Integer analyzeImage(String fileName) {
		Integer tumorPixel = 0;
		BufferedImage imageIn;
		
		try {
			imageIn = ImageIO.read(new File(fileName));

			for (int i = 0; i < imageIn.getWidth(); i++) {
				for (int j = 0; j < imageIn.getHeight(); j++) {
					if (Math.abs(imageIn.getRGB(i, j)) <= MAX_RGB &&
							Math.abs(imageIn.getRGB(i, j)) > MIN_RGB) {
						tumorPixel++;
					}
				}
			}
		} catch (IOException | IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		return tumorPixel;
	}
	
	/*
	 * Saves analyzed image and returns filename for updating main image
	 */
	public static String analyzeImage(String dir, String sep, String fileName) {
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
					} else {
						imageOut.setRGB(i, j, imageIn.getRGB(i, j));
					}
				}
			}
			
			ImageIO.write(imageOut, "jpg", output);
		} catch (IOException | IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		return newFileName;
	}
}