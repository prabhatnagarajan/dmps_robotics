import java.util.*;
public class DMP
{
	public double alpha;
	public double weights;
	public Point start;
	public Point goal;
	public HashMap<Double, Double> fTargetX;
	public HashMap<Double, Double> fTargetY;
	public HashMap<Double, Double> sValues;
	public DMP()
	{
		this.alpha = 0.0 - Math.log(0.01);
		fTargetX = new HashMap<Double, Double>();
		fTargetY = new HashMap<Double, Double>();
		sValues = new HashMap<Double, Double>();
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
	}

	/*
	Since FTargetX and FTargetY are global, there is no need to take them in as parameters.

	Takes in stored DMP parameters, a cartesian starting state, a starting velocity, a cartesian goal state, 
	tau (the length of the desired plan in second), and dt (the time resolution of the plan)
	This function returns a planned cartesian trajectory from the start to the goal that is tau seconds long
	with waypoints spaced out every dt seconds. It returns the trajectory of the time-stamped poses, we ignore the corresponding velocities
	*/
	public Trajectory plan(Point start, Point goal, double v0, double tau, double dt, boolean oneDemo, double K, double D)
	{
		if (oneDemo)
		{
			return planOneTraj(start, goal, v0, tau, dt, K, D);
		}
		return null;
	}

	//calls the main plan function
	public Trajectory plan(double tau, double dt, boolean oneDemo, double K, double D)
	{
		return plan(this.start, this.goal, 0.0, tau, dt, oneDemo, K, D);
	}

	//calls the main plan function, but allows for new goals
	public Trajectory plan(Point start, Point goal, double tau, double dt, boolean oneDemo, double K, double D)
	{
		return plan(start, goal, 0.0, tau, dt, oneDemo, K, D);
	}

	/*
	  takes in Cartesian Demonstration trajectory, the spring/damping constants K and D. 
	  It uses the demonstration to learn the parameters of a DMP, which are stored for later use
	*/
	public void learnOneDemo(Demonstration demonstration, double K, double D)
	{
		start = demonstration.start;
		goal = demonstration.goal;
		populateSValues(demonstration);
		populateFTarget(demonstration, D, K);
	}

	/*
	Takes in stored DMP parameters, a cartesian starting state, a starting velocity, a cartesian goal state, 
	tau (the length of the desired plan in second), and dt (the time resolution of the plan)
	This function returns a planned cartesian trajectory from the start to the goal that is tau seconds longs
	with waypoints spaced out every dt seconds. It returns the trajectory of the time-stamped poses, we ignore the corresponding velocities
	*/
	public Trajectory planOneTraj(Point start, Point goal, double v0, double tau, double dt, double K, double D)
	{
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
			double newvx = vx + dt * getdv(tau, K, start.x, goal.x, x, D, vx, s, fx(s, true));
			double newy = y + dt * getdx(tau, vy);
			double newvy = vy + dt * getdv(tau, K, start.y, goal.y, y, D, vy, s, fy(s, true));
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

	public double getdv(double tau, double K, double start, double goal, double loc, double D, double v, double s, double fs)
	{
		double term1 = K * (goal - loc);
		double term2 = - (D * v);
		double term3 =  (-K) * (goal - start) * s;
		double term4 = K * fs;
		return (term1 + term2 + term3 + term4)/tau;
	}

	public void populateSValues(Demonstration demon)
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

	public void populateFTarget(Demonstration demon, double D, double K)
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
			return 0.0;
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
			return 0.0;
		}
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
}