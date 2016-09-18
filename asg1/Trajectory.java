import java.util.*;
import java.io.*;
public class Trajectory
{
	public ArrayList<Point> trajectory;
	public double timeStepSize;
	public Trajectory()
	{
		trajectory = new ArrayList<Point>();
	}

	public void printTrajectory(String filePath) throws IOException
	{
		PrintWriter writer = new PrintWriter(filePath);
		writer.println("x,y");
		for (Point p : trajectory)
		{
			writer.println(p.x + "," + p.y);
		}
		writer.close();
	}
}