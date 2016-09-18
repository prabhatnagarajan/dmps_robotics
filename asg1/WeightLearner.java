import org.apache.commons.math3.stat.regression.*;
public class WeightLearner
{
	public WeightLearner()
	{
	}

	//in this you learn two f(s)'s
	public double[] learnWeights(double [][] design, double []ftarget)
	{
		OLSMultipleLinearRegression regressor = new OLSMultipleLinearRegression();
		regressor.newSampleData(ftarget, design);
		return regressor.estimateRegressionParameters();
	}
	/*
	
	*/
}