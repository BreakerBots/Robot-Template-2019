package frc.team5104.util;

import java.util.ArrayList;
import java.util.List;

import frc.team5104.auto.Position;

public class Plotter {
	public static enum Color { RED, BLUE, GREEN, PURPLE, ORANGE, BLACK };
	
	private volatile static List<PlotterPoint> buffer = new ArrayList<PlotterPoint>();
	
	public static void plotAll(List<Position> positions, Color color) {
		for (int i = 0; i < positions.size(); i++) {
			plot(
				positions.get(i).getXFeet(), 
				positions.get(i).getYFeet(),
				color
			);
		}
	}
	
	public static void reset() {
		buffer.add(new PlotterPoint(true));
	}
	
	public static void plot(double x, double y, Color color) {
		buffer.add(new PlotterPoint(x, y, color));
	}
	
	protected static String readBuffer() {
		String data = "[";
		
		for (PlotterPoint point : buffer) {
			data += point.toString() + ",";
		}
		
		if (data.charAt(data.length()-1) == ',')
			data = data.substring(0, data.length() - 1);
		
		buffer.clear();
		
		return data + "]";
	}
	
	private static class PlotterPoint {
		private double x, y;
		private Color color;
		private boolean isReset;
		
		PlotterPoint(boolean isReset) {
			this.isReset = isReset;
		}
		
		public PlotterPoint(double x, double y, Color color) {
			this.x = x;
			this.y = y;
			this.color = color;
		}
		
		public String toString() {
			if (isReset)
				return "{\"color\":\"RESET\"}";
			return "{\"x\":" + x + ",\"y\":" + y + ",\"color\":\"" + color.name() + "\"}";
		}
	}
}