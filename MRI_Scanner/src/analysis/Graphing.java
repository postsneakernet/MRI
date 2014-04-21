package analysis;

import com.xeiam.xchart.BitmapEncoder;
import com.xeiam.xchart.Chart;
import com.xeiam.xchart.Series;
import com.xeiam.xchart.SeriesLineStyle;
import com.xeiam.xchart.SeriesMarker;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;

public class Graphing {

	// final double pixelToCm = .0000185254;
	static double alpha =  1.5;
	static double beta =  1.5;
	static double gamma = 1;
	// static double p = .75;
	// static double c = .1;

	final static double pixelToCm = .0000185254;

	public double calcXCoord(double myVolume, double prevVolume) {

		return 0.0;
	}

	public static Image createGraph(List<Double> myData, double p, double c) {
		BufferedImage img = null;

		double[] datVolume = new double[myData.size() + 1];
		double[] myMonths = new double[myData.size() + 1];
		datVolume[0] = 0;
		myMonths[0] = 0;

		for (int i = 0; i < myData.size(); i++) {
			myMonths[i + 1] = i + 1;
			datVolume[i + 1] = myData.get(i);
		}

		Chart chart = new Chart(564, 372);
		chart.addSeries("Volume", myMonths, datVolume);
		chart.setChartTitle("Tumor Mapping");
		chart.setXAxisTitle("Months");
		chart.setYAxisTitle("Volume");
		Color colorMarker = new Color(0xa503a5);
		chart.getSeriesMap().get("Volume").setLineStyle(SeriesLineStyle.NONE);
		chart.getSeriesMap().get("Volume").setMarkerColor(colorMarker);

		double uSeriesX[] = new double[61];
		double uSeriesY[] = new double[61];
		double C2 = 0;
		double V = 0;
		int i = 0;
		
	
		//beta = (datVolume[2]*datVolume[2] - datVolume[1]*datVolume[3] + (datVolume[3] - datVolume[2]) * c)/(datVolume[3]*Math.pow(datVolume[2], p) - datVolume[2]*datVolume[3]);
		//gamma = Math.pow(datVolume[2], p-1) * beta + (datVolume[1] / datVolume[2]) - (c/Math.pow(datVolume[2], p-1)) -1;
		
		//beta = (datVolume[2]*datVolume[2] - datVolume[1]*datVolume[3])/(datVolume[3]*Math.pow(datVolume[2], p)-datVolume[2]*datVolume[3]);
		//gamma = Math.pow(datVolume[2], p-1)*beta + (datVolume[1] / datVolume[2]);
		
		
		//System.out.println(beta + "  " + gamma);
		
		for (double u = 0.0; u < 6; u += 0.1) {
			// System.out.println(p);
			

			V = (alpha) * (Math.pow(u, p))* (beta - (gamma) * Math.pow(u, 1-p)) - c;
//			if( u == 0.0){
//				C2 = (alpha) * (Math.pow(u, p))* (beta - (gamma) * Math.pow(u, 1-p)) - c;
//				V = Math.sqrt(C2*C2 -.01) + 0;
//			}
//			else{
//				C2 = (alpha) * (Math.pow(u, p))* (beta - (gamma) * Math.pow(u, 1-p)) - c; //+ uSeriesY[i-1];
//				V = Math.sqrt(C2*C2 -.01) + uSeriesY[i-1];
//			}
			// System.out.println(i);
			uSeriesY[i] = V;
			uSeriesX[i] = u;

			i++;
		}

		Series series2 = chart.addSeries("GUE", uSeriesX, uSeriesY);
		series2.setMarker(SeriesMarker.NONE);
		Color colorLine = new Color(0xFF00FF);
		series2.setLineColor(colorLine);

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
