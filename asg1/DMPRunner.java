import java.util.*;
import java.io.*;
public class DMPRunner
{
	public static void main(String args[]) throws IOException
	{
		System.out.println("Entering Program");
		Demonstration demo1 = new Demonstration("demo1.csv", 0, 0.1, 25, 17.5, false);
		ArrayList<Demonstration> demonstrations = new ArrayList<Demonstration>();
		demonstrations.add(demo1);
		DMP dmp = new DMP();
		//2000 is ok for X
		//250 is ok for x and y
		double K = 2500;
		double D = getD(K);
		dmp.learn(demonstrations, K, D);
		Trajectory trajForDemo1 = dmp.plan(17.5, 0.1, true, K, D, null);
		trajForDemo1.printTrajectory("plan_demo1_dt_point1.csv");
		Trajectory traj2ForDemo1 = dmp.plan(17.5, 0.12, true, K, D, null);
		traj2ForDemo1.printTrajectory("plan_demo1_dt_point12.csv");

		//Generate a Plan for a different Start and End Goal
		double K2 = 2500;
		double D2 = getD(K2);
		dmp = new DMP();
		dmp.learn(demonstrations, K2, D2);
		Point start = new Point(1, -1);
		Point goal = new Point(25, 1);
		Trajectory trajForDemo2 = dmp.plan(start, goal, 17.5, 0.1, true, K2, D2, null);
		trajForDemo2.printTrajectory("plan_newgoal_demo1_dt_point1.csv");
		Trajectory traj2ForDemo2 = dmp.plan(start, goal, 17.5, 0.12, true, K2, D2, null);
		traj2ForDemo2.printTrajectory("plan__newgoal_demo1_dt_point12.csv");
		//Change xstart to be 0.75, instead of 0
		/*
		Demonstration demo2 = new Demonstration("demo2.csv", 0.75, 0.1, 25, 17.5);
		ArrayList<Demonstration> demonstrations2 = new ArrayList<Demonstration>();
		demonstrations2.add(demo2);

		double K2 = 2500;
		double D2 = getD(K2);
		dmp = new DMP();
		dmp.learn(demonstrations2, K2, D2);
		Trajectory trajForDemo2 = dmp.plan(17.5, 0.1, true, K2, D2);
		trajForDemo2.printTrajectory("plan_demo2_dt_point1.csv");
		Trajectory traj2ForDemo2 = dmp.plan(17.5, 0.12, true, K2, D2);
		traj2ForDemo2.printTrajectory("plan_demo2_dt_point12.csv");
		*/

		// Generates plans that are half as fast as original demonstrations
		//Original Demonstration
		dmp = new DMP();
		dmp.learn(demonstrations, K, D);
		Trajectory traj1HalfSpeedDemo1 = dmp.plan(17.5 * 2, 0.1, true, K, D, null);
		traj1HalfSpeedDemo1.printTrajectory("plan_demo1_dt_point1_halfx.csv");
		Trajectory traj2HalfSpeedDemo1 = dmp.plan(17.5 * 2, 0.12, true, K, D, null);
		traj2HalfSpeedDemo1.printTrajectory("plan_demo1_dt_point12_halfx.csv");
		//TODO: new start and goal at half speed

		//Generates plans that are twice as fast as original demonstrations
		//Original Demonstration
		Trajectory traj1DoubleSpeedDemo1 = dmp.plan(17.5/2, 0.1, true, K, D, null);
		traj1DoubleSpeedDemo1.printTrajectory("plan_demo1_dt_point1_2x.csv");
		Trajectory traj2DoubleSpeedDemo1 = dmp.plan(17.5/2, 0.12, true, K, D, null);
		traj2DoubleSpeedDemo1.printTrajectory("plan_demo1_dt_point12_2x.csv");
		//TODO: Different Goal

		//Generate a Noisy Demonstration
		double K3 = 100;
		double D3 = getD(K3);
		Demonstration noisyDemo = new Demonstration("noisydemo.csv", 0, 0.1, 25, 17.5, true);
		demonstrations.add(noisyDemo);

		//learn from noisy and normal demonstration
		dmp = new DMP();
		dmp.learn(demonstrations, K3, D3);
		Trajectory trajForTwoDemos = dmp.plan(new Point(0, 0), new Point(21.99, 0), 17.5, 0.1, false, K3, D3, null);
		trajForTwoDemos.printTrajectory("plan_2demos_dt_point1.csv");

		//obstacle
		demonstrations.clear();
		demonstrations.add(demo1);
		dmp = new DMP();
		dmp.learn(demonstrations, K, D);
		try
		{
			PrintWriter writer = new PrintWriter("obstacle.csv");
			writer.println("x,y");
			writer.println("" + 4 + "," + -0.75);
			writer.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		Trajectory trajForObstacle = dmp.plan(17.5, 0.1, true, K, D, new Point (4, -0.75));
		trajForObstacle.printTrajectory("plan_obstacle_dt_point1.csv");
	}

	public static double getD(double K)
	{
		return (2 * Math.sqrt(K));
	}
}