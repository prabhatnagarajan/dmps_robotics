import java.util.*;
public class GaussianNoiseGenerator
{
	public double mean;
	public double variance;
	public GaussianNoiseGenerator(double mean, double variance)
	{
		this.mean = mean;
		this.variance = variance;
	}

	public double generateNoise()
	{
		Random rnd = new Random();
		return (rnd.nextGaussian() * Math.sqrt(variance) + mean);
	}

}