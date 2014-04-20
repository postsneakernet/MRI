package analysis;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import javax.imageio.ImageIO;

public class Analyze {

	final static double PIXEL_SQUARE = .00070004;
	final static double PIXEL_CUBE = .0000185254;
	final static int brightness3 = 85;

	/*
	 * Returns amount of pixels in tumor
	 */

	public static int getBrightness(BufferedImage image, int x, int y) {
		int brightness = 0;

		// System.out.println(x + " " + y);
		Color c = new Color(image.getRGB(x, y), true);

		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getBlue();

		brightness = (r + g + b) / 3;
		return brightness;// (int)Math.ceil(brightness);
	}

	public static boolean compareBrightness(int brightness1, int brightness2) {
		// int comp = brightness1 - brightness2;

		// if (comp + 150 <= 255 && comp - 150 >= 0 && comp <= 150 && comp >=
		// -150) {
		// return true;
		// }
		if (/* brightness2 >= 0 && brightness2 <= 255 && */brightness2 > brightness3
				|| brightness2 < 20) {
			// if(brightness2<20){
			// blackCount++;
			// }
			// if(brightness2>brightness3){
			// whiteTrigger++;
			// }

			// if(blackCount > 500){
			// blackCount = 0;
			// }
			// whiteTrigger = false;

			return true;
		}

		return false;
	}

	public static boolean findRegion(HashSet<Point> region, BufferedImage br,
			int x, int y) {

		Point seed = new Point(x, y);

		if (x <= 0 || y <= 0 || x >= br.getWidth() - 2
				|| y >= br.getHeight() - 2) {
			region.add(seed);
			return true;
		}

		// if(blackCount > 50000 && whiteTrigger != false){
		// return false;
		// }
		// if(blackCount - whiteTrigger > 500){
		// return false;
		// }

		if (region.contains(seed)) {
			return true;
		}

		region.add(seed);
		/*
		 * left Column
		 */
		if (compareBrightness(getBrightness(br, x, y),
				getBrightness(br, x - 1, y - 1))) {
			findRegion(region, br, x - 1, y - 1);

		}
		if (compareBrightness(getBrightness(br, x, y),
				getBrightness(br, x - 1, y))) {
			findRegion(region, br, x - 1, y);

		}
		if (compareBrightness(getBrightness(br, x, y),
				getBrightness(br, x - 1, y + 1))) {
			findRegion(region, br, x - 1, y + 1);

		}
		/*
		 * middle Column
		 */
		if (compareBrightness(getBrightness(br, x, y),
				getBrightness(br, x, y - 1))) {
			findRegion(region, br, x, y - 1);

		}
		if (compareBrightness(getBrightness(br, x, y),
				getBrightness(br, x, y + 1))) {
			findRegion(region, br, x, y + 1);

		}
		/*
		 * right column
		 */
		if (compareBrightness(getBrightness(br, x, y),
				getBrightness(br, x + 1, y - 1))) {
			findRegion(region, br, x + 1, y - 1);

		}
		if (compareBrightness(getBrightness(br, x, y),
				getBrightness(br, x + 1, y))) {
			findRegion(region, br, x + 1, y);

		}
		if (compareBrightness(getBrightness(br, x, y),
				getBrightness(br, x + 1, y + 1))) {
			findRegion(region, br, x + 1, y + 1);

		}
		return true;
	}

	public static Integer analyzeImage(String fileName) {
		HashSet<Point> region1 = null;
		Integer tumorPixel = 0;

		try {
			BufferedImage input1 = ImageIO.read(new File(fileName));

			region1 = new HashSet<Point>();

			Point abc = new Point();

			for (int i = 123; i < 403; i++) {

				for (int j = 187; j < 343; j++) {
					abc.x = i;
					abc.y = j;

					if (getBrightness(input1, i, j) >= 150) {

						findRegion(region1, input1, i, j);
						//System.out.println(i + " " + j);

					}

					if (getBrightness(input1, i, j) >= 110
							&& region1.contains(new Point(abc))) {
						// input1.setRGB(i, j, Color.RED.getRGB());
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

		HashSet<Point> region1 = new HashSet<Point>();
		BufferedImage imageIn;
		BufferedImage imageOut;
		String newFileName = null;
		try {
			imageIn = ImageIO.read(new File(fileName));

			newFileName = dir + sep + "last-image" + ".analyzed.jpg";
			File output = new File(newFileName);

			Point abc = new Point();

			for (int i = 123; i < 410; i++) {

				for (int j = 187; j < 343; j++) {
					abc.x = i;
					abc.y = j;

					if (getBrightness(imageIn, i, j) >= 150) {

						findRegion(region1, imageIn, i, j);

					}

					if (getBrightness(imageIn, i, j) >= 110
							&& region1.contains(new Point(abc))) {
						imageIn.setRGB(i, j, Color.RED.getRGB());
					}

				}
			}
			ImageIO.write(imageIn, "jpg", output);
		} catch (IOException | IllegalArgumentException e) {
			e.printStackTrace();
		}

		return newFileName;

	
	}

	public static double convertToCm(double pixels, boolean area) {
		if (area) {
			return pixels * PIXEL_SQUARE;
		} else {
			return pixels * PIXEL_CUBE;
		}
	}
}