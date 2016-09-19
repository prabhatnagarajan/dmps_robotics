import org.apache.commons.math3.linear.*;
import org.apache.commons.math3.analysis.function.*;
public class CouplingTerm
{
	public double gamma;
	public double phi;
	public Point obs;
	public double mean;
	public double variance;
	public Gaussian g;
	public CouplingTerm(double gamma, double phi, Point obs, double mean, double variance)
	{
		this.gamma = gamma;
		this.phi = phi;
		this.obs = obs;
		this.mean = mean;
		this.variance = variance;
		g = new Gaussian(mean, Math.sqrt(variance));
	}

	public double getAccelerationMagnitude(Point currentLoc)
	{
		return 25000 * g.value(getDistance(currentLoc, obs));
	}

	public double getDistance(Point a, Point b)
	{
		return Math.sqrt(Math.pow(a.x - b.x, 2) +  Math.pow(a.y - b.y, 2));
	}

	public double getXAcceleration(Point currentLoc)
	{
		double mag = getAccelerationMagnitude(currentLoc);
		double theta = Math.atan2(currentLoc.y - obs.y, currentLoc.x - obs.x);
		return mag * Math.cos(theta);
	}

	public double getYAcceleration(Point currentLoc)
	{
		double mag = getAccelerationMagnitude(currentLoc);
		double theta = Math.atan2(currentLoc.y - obs.y, currentLoc.x - obs.x);
		return mag * Math.sin(theta);
	}
} 