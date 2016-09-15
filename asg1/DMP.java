import java.util.ArrayList;
public class DMP
{
	public DMP()
	{

	}

	/*takes in Cartesian Demonstration trajectory, the spring/damping constants K and D. 
	  It uses the demonstration to learn the parameters of a DMP, which are stored for later use
	*/
	public void learn(ArrayList<Point> demonstration, double K, double D)
	{

	}

	/*
	Takes in stored DMP parameters, a cartesian starting state, a starting velocity, a cartesian goal state, 
	tau (the length of the desired plan in second), and dt (the time resolution of the plan)
	This function returns a planned cartesian trajectory from the start to the goal that is tau seconds longs
	with waypoints spaced out every dt seconds. It returns the trajectory of the time-stamped poses, we ignore the corresponding velocities
	*/
	public void planning()
	{

	}
}