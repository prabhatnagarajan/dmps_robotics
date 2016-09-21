import java.util.*;
import java.io.*;
import org.apache.commons.math3.linear.*;
public class DMP
{
	public double alpha;
	public Point start;
	public Point goal;
	public HashMap<Double, Double> fTargetX;
	public HashMap<Double, Double> fTargetY;
	public ArrayList<HashMap<Double, Double>> fTargetXs;
	public ArrayList<HashMap<Double, Double>> fTargetYs;
	public HashMap<Double, Double> sValues;
	public ArrayList<HashMap<Double, Double>> sValList;
	public ArrayList<ArrayList<GaussianBasisFunction>> gbfs;
	public ArrayList<ArrayList<Double>> weights;
	public double targetx[];
	public double actualx[];
	public double targety[];
	public double actualy[];
	public DMP()
	{
		this.alpha = 0.0 - Math.log(0.01);
		fTargetX = new HashMap<Double, Double>();
		fTargetY = new HashMap<Double, Double>();
		sValues = new HashMap<Double, Double>();
		weights = new ArrayList<ArrayList<Double>>();
		gbfs = new ArrayList<ArrayList<GaussianBasisFunction>>();
		sValList = new ArrayList<HashMap<Double, Double>> ();
		fTargetXs = new ArrayList<HashMap<Double, Double>>();
		fTargetYs = new ArrayList<HashMap<Double, Double>>();
	}

	/*takes in Cartesian Demonstration trajectory, the spring/damping constants K and D. 
	  It uses the demonstration to learn the parameters of a DMP, which are stored for later use
	*/
	public void learn(ArrayList<Demonstration> demonstrations, double K, double D)
	{
		if (demonstrations.size() == 1)
		{
			learnOneDemo(demonstrations.get(0), K, D);
		}
		else
		{
			learnMultipleDemos(demonstrations, K, D);
		}
	}

	/*
	Since FTargetX and FTargetY are global, there is no need to take them in as parameters.

	Takes in stored DMP parameters, a cartesian starting state, a starting velocity, a cartesian goal state, 
	tau (the length of the desired plan in second), and dt (the time resolution of the plan)
	This function returns a planned cartesian trajectory from the start to the goal that is tau seconds long
	with waypoints spaced out every dt seconds. It returns the trajectory of the time-stamped poses, we ignore the corresponding velocities
	*/
	public Trajectory plan(Point start, Point goal, double v0, double tau, double dt, boolean oneDemo, double K, double D, Point obstacle, double mag)
	{
		return planOneTraj(start, goal, v0, tau, dt, K, D, oneDemo, obstacle, mag);
	}

	//calls the main plan function
	public Trajectory plan(double tau, double dt, boolean oneDemo, double K, double D, Point obstacle, double mag)
	{
		return plan(this.start, this.goal, 0.0, tau, dt, oneDemo, K, D, obstacle, mag);
	}

	//calls the main plan function, but allows for new goals
	public Trajectory plan(Point start, Point goal, double tau, double dt, boolean oneDemo, double K, double D, Point obstacle, double mag)
	{
		return plan(start, goal, 0.0, tau, dt, oneDemo, K, D, obstacle, mag);
	}

	/*
	  takes in Cartesian Demonstration trajectory, the spring/damping constants K and D. 
	  It uses the demonstration to learn the parameters of a DMP, which are stored for later use
	*/
	public void learnOneDemo(Demonstration demonstration, double K, double D)
	{
		start = demonstration.start;
		goal = demonstration.goal;
		populateSValues(demonstration, sValues);
		populateFTarget(demonstration, fTargetX, fTargetY, sValues, D, K);
	}

	public void learnMultipleDemos(ArrayList<Demonstration> demonstrations, double K, double D)
	{
		//used later for Gaussian Basis Functions
		double maxdt = Double.MIN_VALUE;
		double maxtau = Double.MIN_VALUE;
		for (Demonstration demo : demonstrations)
		{
			if (demo.timeStepSize > maxdt)
			{
				maxdt = demo.timeStepSize;
			}
			if (demo.tau > maxtau)
			{
				maxtau = demo.tau;
			}
		}


		//Compute the sVals for all the demonstrations
		for (int i = 0; i < demonstrations.size(); i++)
		{
			HashMap<Double, Double> sVal = new HashMap<Double, Double>();
			populateSValues(demonstrations.get(i), sVal);
			sValList.add(sVal);
		}
		//Compute the FTargets for all the demonstrations
		for (int i = 0; i < demonstrations.size(); i++)
		{
			HashMap<Double, Double> targetX = new HashMap<Double, Double>();
			HashMap<Double, Double> targetY = new HashMap<Double, Double>();
			populateFTarget(demonstrations.get(i), targetX, targetY, sValList.get(i), D, K);
			fTargetXs.add(targetX);
			fTargetYs.add(targetY);
		}
		//Find the lengths of the Y Vector for Linear regression
		int YxLen = 0;
		int YyLen = 0;
		for (int i = 0; i < fTargetXs.size(); i++)
		{
			YxLen += fTargetXs.get(i).size();
			YyLen += fTargetYs.get(i).size();
		}
		double Yx[] = new double[YxLen];
		double Yy[] = new double[YyLen];
		actualx = new double [YxLen];
		targetx = new double [YxLen];
		actualy = new double [YxLen];
		targety = new double [YxLen];
		//Fill in the Y vector with the fTargetValues
		int index = 0;
		for (int i = 0; i < fTargetXs.size(); i++)
		{
			HashMap<Double, Double> targetX = fTargetXs.get(i);
			HashMap<Double, Double> targetY = fTargetYs.get(i);
			Double []svals = new Double[sValList.get(i).values().size()];
			sValList.get(i).values().toArray(svals);
			Arrays.sort(svals);
			for (int k = 0; k < svals.length; k++)
			{
				Yx[index] = targetX.get(svals[k]);
				Yy[index] = targetY.get(svals[k]);
				index++;
			}
		}

		generateBasisFunctions(maxtau, maxdt);

		//compute the design matrices
		double designx[][] = computeDesignMatrix(true); 
		double designy[][] = computeDesignMatrix(false);

		//index 0 stores learned x weights, index 1 stores learned y weights 
		WeightLearner wl = new WeightLearner();
		ArrayList<Double> xweights = new ArrayList<Double>();
		for (double d : wl.learnWeights(designx, Yx))
		{
			xweights.add(d);
		}
		ArrayList<Double> yweights = new ArrayList<Double>();
		for (double d : wl.learnWeights(designy, Yy))
		{
			yweights.add(d);
		}
		weights.add(xweights);
		weights.add(yweights);

		HashMap<Double, Double> targetX = fTargetXs.get(0);
		HashMap<Double, Double> targetY = fTargetYs.get(0);
		try{
			PrintWriter writer = new PrintWriter("ftargetx.csv");
			writer.println("s,ftarget");
			int num = 0;
			for (double d : new TreeSet<Double>(targetX.keySet()))
			{
				targetx[num] = targetX.get(d);
				writer.println(d + "," + targetX.get(d));
				num++;
			}
			writer.close();

			PrintWriter writer2 = new PrintWriter("ftargety.csv");
			writer2.println("s,ftarget");
			int num2 = 0;
			for (double d : new TreeSet<Double>(targetY.keySet()))
			{
				targety[num2] = targetY.get(d);
				writer2.println(d + "," + targetY.get(d));
				num2++;
			}
			writer2.close();

			PrintWriter writer3 = new PrintWriter("fx.csv");
			writer3.println("s,f");
			int num3 = 0;
			for (double d : new TreeSet<Double>(targetX.keySet()))
			{
				actualx[num3] = fx(d, false);
				writer3.println(d + "," + fx(d, false));
				num3++;
			}
			writer3.close();

			PrintWriter writer4 = new PrintWriter("fy.csv");
			writer4.println("s,f");
			int num4 = 0;
			for (double d : new TreeSet<Double>(targetY.keySet()))
			{
				actualy[num4] = fy(d, false);
				writer4.println(d + "," + fy(d, false));
				num4++;
			}
			writer4.close();
			System.out.println("X then Y");
			printL2Norm(actualx, targetx);
			printL2Norm(actualy, targety);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/*
	Takes in stored DMP parameters, a cartesian starting state, a starting velocity, a cartesian goal state, 
	tau (the length of the desired plan in second), and dt (the time resolution of the plan)
	This function returns a planned cartesian trajectory from the start to the goal that is tau seconds longs
	with waypoints spaced out every dt seconds. It returns the trajectory of the time-stamped poses, we ignore the corresponding velocities
	*/
	public Trajectory planOneTraj(Point start, Point goal, double v0, double tau, double dt, double K, double D, boolean oneDemo, Point obs, double mag)
	{
		CouplingTerm obstacle = null;
		if (obs != null)
		{
			System.out.println("" + obs.x + " " + obs.y);
			//specify force of obstacle
			obstacle = new CouplingTerm(0, 0, obs, 0, 10, mag);
		}
		Trajectory traj = new Trajectory();
		traj.timeStepSize = dt;
		double time = dt;
		traj.trajectory.add(start);
		double x = start.x;
		double y = start.y;
		double vx = 0;
		double vy = 0; 
		//do the deltas for each direction, generate new Points add the points to the trajectory
		while (time <= tau)
		{
			double s = s(time - dt, tau);
			//integrate
			double newx = x + dt * getdx(tau, vx);
			Point currentLoc = new Point(x, y);
			double newvx = vx + dt * getdv(tau, K, start.x, goal.x, x, currentLoc, D, vx, s, fx(s, oneDemo), obstacle, true);
			double newy = y + dt * getdx(tau, vy);
			double newvy = vy + dt * getdv(tau, K, start.y, goal.y, y, currentLoc, D, vy, s, fy(s, oneDemo), obstacle, false);
			x = newx;
			y = newy;
			vx = newvx;
			vy = newvy;
			traj.trajectory.add(new Point(x, y));
			time += dt;
		}
		return traj;
	}

	public double getdx(double tau, double v)
	{
		return v/tau;
	}

	public double getdv(double tau, double K, double start, double goal, double loc, Point currentLoc, double D, double v, double s, double fs, CouplingTerm obstacle, boolean x)
	{
		double term1 = K * (goal - loc);
		double term2 = - (D * v);
		double term3 =  (-K) * (goal - start) * s;
		double term4 = K * fs;
		double term5 = 0;
		if (obstacle != null)
		{
			if (x)
			{
				term5 = obstacle.getXAcceleration(currentLoc);
			}
			else
			{
				term5 = obstacle.getYAcceleration(currentLoc);
			}
		}
		return (term1 + term2 + term3 + term4 + term5)/tau;
	}

	public void populateSValues(Demonstration demon, HashMap<Double, Double> sValues)
	{
		double time = 0;
		for (int i = 0; i < demon.demonstration.size(); i++)
		{
			sValues.put(time, s(time, demon.tau));
			time += demon.timeStepSize;
		}
	}

	public double s(double time, double tau)
	{
		return Math.pow(Math.E, 0.0 - (alpha * time / tau));
	}

	public void populateFTarget(Demonstration demon, HashMap<Double, Double> fTargetX, HashMap<Double, Double> fTargetY, HashMap<Double, Double> sValues, double D, double K)
	{
		double time = 0;
		for (int i = 0; i < demon.demonstration.size(); i++)
		{
			double s = sValues.get(time);
			double term1x = (demon.tau * demon.tau * demon.accelerationsx.get(i) + D * demon.tau * demon.velocitiesx.get(i))/(K);
			double term2x = 0.0 - (demon.goal.x - demon.demonstration.get(i).x);
			double term3x = (demon.goal.x - demon.start.x) * s;
			fTargetX.put(s, term1x + term2x + term3x);
			double term1y = (demon.tau * demon.tau * demon.accelerationsy.get(i) + D * demon.tau * demon.velocitiesy.get(i))/(K);
			double term2y = 0.0 - (demon.goal.y - demon.demonstration.get(i).y);
			double term3y = (demon.goal.y - demon.start.y) * s;
			fTargetY.put(s, term1y + term2y + term3y);
			time += demon.timeStepSize;
		}
	}

	public double fx(double s, boolean singleDemo)
	{
		if (singleDemo)
		{
			if (fTargetX.containsKey(s))
			{
				return fTargetX.get(s);
			}
			else
			{
				double s1 = 0;
				double s2  = 1.0;
				for (double d : sValues.values())
				{
					if (d < s)
					{
						if (d > s1)
						{
							s1 = d;
						}
					}
					if (d > s)
					{
						if (d < s2)
						{
							s2 = d;
						}
					}
				}
				return linearInterpolate(s1, s2, s, true);
			}
		}
		else
		{
			//use Gaussian basis functions
			double res = 0;
			ArrayList<GaussianBasisFunction> basisFuncs = gbfs.get(0);
			for (int i = 0; i < basisFuncs.size(); i++)
			{
				double funcRes = basisFuncs.get(i).evaluate(s);
				res += (funcRes * weights.get(0).get(i) * s);
			}
			return res;
		}
	}

	public double fy(double s, boolean singleDemo)
	{
		if (singleDemo)
		{
			if (fTargetY.containsKey(s))
			{
				return fTargetY.get(s);
			}
			else
			{
				double s1 = 0;
				double s2  = 1.0;
				for (double d : sValues.values())
				{
					if (d < s)
					{
						if (d > s1)
						{
							s1 = d;
						}
					}
					if (d > s)
					{
						if (d < s2)
						{
							s2 = d;
						}
					}
				}
				return linearInterpolate(s1, s2, s, false);
			}
		}
		else
		{
			double res = 0;
			ArrayList<GaussianBasisFunction> basisFuncs = gbfs.get(1);
			for (int i = 0; i < basisFuncs.size(); i++)
			{
				double funcRes = basisFuncs.get(i).evaluate(s);
				res += (funcRes * weights.get(1).get(i) * s);
			}
			return res;
		}
	}

	public double[][] computeDesignMatrix(boolean x)
	{
		double [][]design;
		int numCols;
		int numRows = 0;
		
		//compute the number of data points
		for (HashMap<Double, Double> sval : sValList)
		{
			numRows += sval.size();
		}

		//compute the number of rows
		ArrayList<GaussianBasisFunction> basisFuncs;
		if (x)
		{
			numCols = gbfs.get(0).size(); 
			basisFuncs = gbfs.get(0);
		}
		else
		{
			numCols = gbfs.get(1).size();
			basisFuncs = gbfs.get(1);
		}

		design = new double[numRows][numCols];

		int rowNum = 0;;
		for (HashMap<Double, Double> sval : sValList)
		{
			Double []svals = new Double[sval.values().size()];
			sval.values().toArray(svals);
			Arrays.sort(svals);
			for (double s : svals)
			{
				for (int k = 0; k < numCols; k++)
				{
					design[rowNum][k] = basisFuncs.get(k).evaluate(s) * s;
				}
				rowNum++;
			}
		}
		return design;
	}

	public double linearInterpolate(double s1, double s2, double s, boolean x)
	{
		if (x)
		{
			return fTargetX.get(s1) + (fTargetX.get(s2) - fTargetX.get(s1))*((s - s1)/(s2 - s1));
		}
		else
		{
			return fTargetY.get(s1) + (fTargetY.get(s2) - fTargetY.get(s1))*((s - s1)/(s2 - s1));
		}
	}

	public void generateBasisFunctions(double tau, double dt)
	{
		double halfdt = Math.pow(Math.E, 0 - ((alpha * dt/2)/tau));
		double sigma = halfdt/2;
		//double h = 1/(2 * sigma * sigma);
		ArrayList<GaussianBasisFunction> xfuncs = new ArrayList<GaussianBasisFunction>();
		ArrayList<GaussianBasisFunction> yfuncs = new ArrayList<GaussianBasisFunction>();
		TreeSet<Double> sVals = new TreeSet<Double>();
		for (HashMap<Double, Double> targetX : fTargetXs)
		{
			sVals.addAll(targetX.keySet());
		}
		double hx = 0.003; //0.006 ok
		double hy = 0.003; //0.0003 works
		for (Double s : sVals)
		{
			//5, 5 is ok & 8 and 4.25 is ok 8 and 4.25
			//compute the corresponding time
			xfuncs.add(new GaussianBasisFunction(hx, s));
			yfuncs.add(new GaussianBasisFunction(hy, s));
			hx = hx * 1.4; //60.46
			hy = hy * 1.3;
		}


		gbfs.add(xfuncs);
		gbfs.add(yfuncs);
	}

	public void printL2Norm(double []d, double[]d2)
	{
		ArrayRealVector a = new ArrayRealVector(d);
		ArrayRealVector b = new ArrayRealVector(d2);
		System.out.println((a.subtract(b)).getNorm());
	}
}