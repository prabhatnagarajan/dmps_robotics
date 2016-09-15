import java.util.ArrayList;
public class Demonstration
{
	public String filePath;
	public double tau;
	public Point start;
	public Point goal;
	public ArrayList<Point> demonstration;
	public double timeStepSize;
	public Demonstration(String filePath, double timeStepSize)
	{
		this.filePath = filePath;
		this.timeStepSize = timeStepSize;
	}

	public void generate(double xstart, int pointsPerCycle)
	{
		demonstration = new ArrayList<Point>();
		double addAmt = (double) pointsPerCycle;
		for (double x = xstart; x < xstart + (7 * Math.PI); x += Math.PI/addAmt)
		{
			Point p = new Point (x, Math.sin(x));
			demonstration.add(p);
		}
		start = demonstration.get(0);
		goal = demonstration.get(demonstration.size() - 1);
	}


}