import java.util.*;
import java.io.*;
public class DMPRunner
{
	public static void main(String args[]) throws IOException
	{
		System.out.println("Entering Program");
		Demonstration demo1 = new Demonstration("demo1.csv", 0, 0.1, 25, 17.5);
		ArrayList<Demonstration> demonstrations = new ArrayList<Demonstration>();
		demonstrations.add(demo1);
		DMP dmp = new DMP();
		//2000 is ok for X
		//250 is ok for x and y
		double K = 2500;
		double D = getD(K);
		dmp.learn(demonstrations, K, D);
		Trajectory trajForDemo1 = dmp.plan(17.5, 0.1, true, K, D);
		oneDemonstration.printTrajectory("plan_demo1_dt_point1.csv");
		Trajectory traj2ForDemo1 = dmp.plan(17.5, 0.12, true, K, D);
		oneDemonstration2.printTrajectory("plan_demo1_dt_point12.csv");

		//Change xstart to be 0.75, instead of 0
		Demonstration demo2 = new Demonstration("demo2.csv", 0.75, 0.1, 25, 17.5);
		ArrayList<Demonstration> demonstrations2 = new ArrayList<Demonstration>();
		demonstrations2.add(demo2);

		double K2 = 2500;
		double D2 = getD(K2);
		dmp.learn(demonstrations2, K2, D2);
		Trajectory trajForDemo2 = dmp.plan(17.5, 0.1, true, K2, D2);
		oneDemonstration.printTrajectory("plan_demo2_dt_point1.csv");
		Trajectory traj2ForDemo2 = dmp.plan(17.5, 0.12, true, K2, D2);
		oneDemonstration2.printTrajectory("plan_demo2_dt_point12.csv");

	}

	public static double getD(double K)
	{
		return (2 * Math.sqrt(K));
	}
}