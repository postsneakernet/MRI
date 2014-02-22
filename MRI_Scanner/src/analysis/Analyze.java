package analysis;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Analyze {
	
	public Analyze() {
		
	}
	
	public static String analyzeImage(String fileName) {
		BufferedImage imageIn;
		BufferedImage imageOut;
		String newFileName = null;
		
		try {
			int minRGB = Integer.parseInt("000000", 16);
			imageIn = ImageIO.read(new File(fileName));
			newFileName = fileName + "_analyzed.jpg";
			File output = new File(newFileName);
			imageOut = new BufferedImage(imageIn.getWidth(), imageIn.getHeight(),imageIn.getType());
			
                        System.out.printf("running on %s %n", fileName);
//			System.out.printf("image height %s: image width %s %n", imageIn.getHeight(), imageIn.getWidth());
			for (int i = 0; i < imageIn.getWidth(); i++) {
				for (int j = 0; j < imageIn.getHeight(); j++) {
//					System.out.printf("i %s :j %s%n", i, j);
					if (Math.abs(imageIn.getRGB(i, j)) <= Integer.parseInt("111111", 16) &&
							Math.abs(imageIn.getRGB(i, j)) > minRGB) {
						imageOut.setRGB(i, j, Integer.parseInt("FF0000", 16));
					}
					else {
						imageOut.setRGB(i, j, imageIn.getRGB(i, j));
					}
				}
			}
			
			ImageIO.write(imageOut, "jpg", output);
		}
		catch (IOException | IllegalArgumentException e) {
			System.out.printf("Exception from try block %n");
			e.printStackTrace();
		}
		
		return newFileName;
	}

//	public static void main(String[] args) {
//		analyzeImage("mri2.jpg");
//	}
}