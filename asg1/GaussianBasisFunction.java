public class GaussianBasisFunction
{
	public double h;
	public double c;
	public GaussianBasisFunction(double h, double c)
	{
		this.h = h;
		this.c = c;
	}

	public double evaluate(double s)
	{
		return Math.pow(Math.E, (-h) * ((s - c) * (s - c)));
	}
}