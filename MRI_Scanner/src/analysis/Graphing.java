package analysis;

import com.xeiam.xchart.BitmapEncoder;
import com.xeiam.xchart.Chart;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class Graphing {

	public static Image createGraph(/*ArrayList<double[]> myData*/) {
		BufferedImage img = null;

		ArrayList<double[]> abc = new ArrayList<double[]>();

		for (int i = 1; i < 30.0; i++) {
			double insert[] = new double[2];
			insert[0] = i;
			insert[1] = Math.log(i) / Math.log(2);
			abc.add(i - 1, insert);

		}

		double[] datVolume = new double[abc.size()];
		double[] myMonths = new double[abc.size()];

		for (int i = 0; i < abc.size(); i++) {

			myMonths[i] = abc.get(i)[0];

			datVolume[i] = abc.get(i)[1];

		}
		
//		double[] datVolume = new double[myData.size()];
//		double[] myMonths = new double[myData.size()];
//
//		for (int i = 0; i < myData.size(); i++) {
//
//			myMonths[i] = myData.get(i)[0];
//
//			datVolume[i] = myData.get(i)[1];
//
//		}

		
		Chart chart = new Chart(564, 372);
		chart.addSeries("Volume", myMonths, datVolume);
		chart.setChartTitle("Tumor Mapping");
		chart.setXAxisTitle("Months");
		chart.setYAxisTitle("Volume");

		try {

			byte[] imageInBytes = BitmapEncoder.getPNGBytes(chart);

			InputStream in = new ByteArrayInputStream(imageInBytes);
			img = ImageIO.read(in);

		} catch (IOException e) {

			e.printStackTrace();
		}

		return SwingFXUtils.toFXImage(img, null);
	}

}
