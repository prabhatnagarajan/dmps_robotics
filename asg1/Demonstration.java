import java.util.*;
import java.io.*;
public class Demonstration
{
	public String filePath;
	public Point start;
	public Point goal;
	public ArrayList<Point> demonstration;
	public ArrayList<Double> velocitiesx;
	public ArrayList<Double> velocitiesy;
	public ArrayList<Double> accelerationsx;
	public ArrayList<Double> accelerationsy;
	public double timeStepSize;
	public double numCycles;
	public double pointsPerCycle;
	public double tau;
	public Demonstration(String filePath, double xstart, double timeStepSize, int pointsPerCycle, double tau, boolean noisy)
	{
		this.start = start;
		this.filePath = filePath;
		this.timeStepSize = timeStepSize;
		//total time divided by time increments gives number of points, 
		//and divide the number of points by the number of points per cycle to get number of cycles
		this.numCycles = tau/timeStepSize/((double)pointsPerCycle);
		this.pointsPerCycle = (double) pointsPerCycle;
		this.tau = tau;
		velocitiesx = new ArrayList<Double>();
		velocitiesy = new ArrayList<Double>();
		accelerationsx = new ArrayList<Double>();
		accelerationsy = new ArrayList<Double>();
		generate(xstart, noisy);
	}

	public void generate(double xstart, boolean noisy)
	{
		demonstration = new ArrayList<Point>();
		//We use a variance of 0.1
		GaussianNoiseGenerator gng = new GaussianNoiseGenerator(0, 0.0);
		for (double x = xstart; x < xstart + (numCycles * Math.PI); x += Math.PI/pointsPerCycle)
		{
			if (noisy)
			{
				demonstration.add(new Point(x + gng.generateNoise(), Math.sin(x) + gng.generateNoise()));
			}
			else
			{
				Point p = new Point (x, Math.sin(x));
				demonstration.add(p);
			}
		}
		start = demonstration.get(0);
		goal = demonstration.get(demonstration.size() - 1);
		setVelocities();
		setAccelerations();
		try
		{
			printDemoToFile();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void setVelocities()
	{
		for (int i = 0; i < demonstration.size(); i++)
		{
			//set beginning and final velocities to 0
			if (i == 0 || i == demonstration.size() - 1)
			{
				velocitiesx.add(0.0);
				velocitiesy.add(0.0);
				continue;
			}
			double velocityx = (demonstration.get(i + 1).x - demonstration.get(i).x)/timeStepSize;
			double velocityy = (demonstration.get(i + 1).y - demonstration.get(i).y)/timeStepSize;
			velocitiesx.add(velocityx);
			velocitiesy.add(velocityy);
		}
	}

	public void setAccelerations()
	{
		for (int i = 0; i < demonstration.size(); i++)
		{
			//set beginning and final velocities to 0
			if (i == 0 || i == demonstration.size() - 1)
			{
				accelerationsx.add(0.0);
				accelerationsy.add(0.0);
				continue;
			}
			double accelerationx = (velocitiesx.get(i + 1) - velocitiesx.get(i))/timeStepSize;
			double accelerationy = (velocitiesy.get(i + 1) - velocitiesy.get(i))/timeStepSize;
			accelerationsx.add(accelerationx);
			accelerationsy.add(accelerationy);
		}
	}

	public void printDemoToFile() throws IOException
	{
		PrintWriter writer = new PrintWriter(filePath);
		writer.println("x,y");
		for (Point p : demonstration)
		{
			writer.println(p.x + "," + p.y);
		}
		writer.close();
	}
}