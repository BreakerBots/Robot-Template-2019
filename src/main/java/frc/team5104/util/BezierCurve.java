/*BreakerBots Robotics Team 2019*/
package frc.team5104.util;

/**
 * <h1>Curve</h1>
 * Processes bezier curves between two points.
 * Mostly uses in drive augmentation.
 * Desmos Link: https://www.desmos.com/calculator/da8zwxpgzo
 */
public class BezierCurve {
	public double x1, y1, x2, y2;
	public BezierCurve(double x1, double y1, double x2, double y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	/**
	 * Gets point along the curve at (t) percent[-1 to 1]
	 * @param t Percent point along curve [-1 to 1]
	 */
	public double getPoint(double t) {
		boolean tn = t < 0;
		t = Math.abs(t);
		double x0 = 0.0;
		double y0 = 0.0;
		double x3 = 1.0;
		double y3 = 1.0;
		
		t = (1-t);
		t = 1 - ( ((1-t) * getP(t, x0, x1, x2)) + (t * getP(t, x1, x2, x3)) );
		t =	( ((1-t) * getP(t, y0, y1, y2)) + (t * getP(t, y1, y2, y3)) );
		
		return t * (tn ? -1 : 1);
	}
	
	private double getP(double t, double a, double b, double c) {
		return 
		(
			(1-t) *
			( (1-t) * a + (t * b) ) 
		)
		+ 
		(
			t *
			( (1-t) * b + (t * c) )
		);
	}
}