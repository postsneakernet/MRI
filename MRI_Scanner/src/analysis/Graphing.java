package analysis;

import com.xeiam.xchart.BitmapEncoder;
import com.xeiam.xchart.Chart;
import com.xeiam.xchart.Series;
import com.xeiam.xchart.SeriesMarker;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;

public class Graphing {
	
	//final double pixelToCm = .0000185254;
	 double alpha = 1.0;
	 double beta = 1.0;
	 double gamma = 1.0;
//	 static double p = .75;
//	 static double c = .1;

	
	final double pixelToCm = .0000185254;
	
	public double calcXCoord(double myVolume, double prevVolume){
		//V1 = alphaV^p(beta-gammaVsub1^(1-p))-c
		
		
		
		
		
		return 0.0;
	}

	public static Image createGraph(List<Double> myData, double p, double c) {
		BufferedImage img = null;

//		ArrayList<double[]> abc = new ArrayList<double[]>();
//
//		for (int i = 1; i < 30.0; i++) {
//			double insert[] = new double[2];
//			insert[0] = i;
//			insert[1] = Math.log(i) / Math.log(2);
//			abc.add(i - 1, insert);
//
//		}

//		double[] datVolume = new double[abc.size()];
//		double[] myMonths = new double[abc.size()];
//
//		for (int i = 0; i < abc.size(); i++) {
//
//			myMonths[i] = abc.get(i)[0];
//
//			datVolume[i] = abc.get(i)[1];
//
//		}
		
		
		
		
		
		
		double[] datVolume = new double[myData.size()];
		double[] myMonths = new double[myData.size()];

		for (int i = 0; i < myData.size(); i++) {

			myMonths[i] = i;

			datVolume[i] = myData.get(i);

		}

		
		Chart chart = new Chart(564, 372);
		chart.addSeries("Volume", myMonths, datVolume);
		chart.setChartTitle("Tumor Mapping");
		chart.setXAxisTitle("Months");
		chart.setYAxisTitle("Volume");
		
		
		
		 double uSeriesX[] = new double[61];
		 double uSeriesY[] = new double[61];
		 double V = 0;
		 int i = 0;
		 for(double u = 0.0; u<6; u+=0.1){
			// System.out.println(p);
			 V = (Math.pow(u, p))*(1-Math.pow(u, 1-p)) - c;
			 //System.out.println(i);
			 uSeriesY[i] = V;
			 uSeriesX[i] = u;
			 
			 i++;
		 }
		 
		

		 Series series2 = chart.addSeries("another", uSeriesX, uSeriesY);
		 series2.setMarkerColor(Color.BLUE);
		 series2.setMarker(SeriesMarker.CIRCLE);
		
		
		

		try {

			byte[] imageInBytes = BitmapEncoder.getPNGBytes(chart);

			InputStream in = new ByteArrayInputStream(imageInBytes);
			img = ImageIO.read(in);

		} catch (IOException e) {

			e.printStackTrace();
		}

		return SwingFXUtils.toFXImage(img, null);
	}
	
	public double convertToCm(double myVolume) {
		return myVolume * this.pixelToCm;
	}

}
