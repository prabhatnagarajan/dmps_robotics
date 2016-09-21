import org.apache.commons.math3.stat.regression.*;
import org.apache.commons.math3.linear.*;
import java.util.*;
public class WeightLearner
{
	public WeightLearner()
	{
	}

	//in this you learn two f(s)'s
	public double[] learnWeights(double [][] design, double []ftarget)
	{
		Array2DRowRealMatrix matrix = new Array2DRowRealMatrix(design);
		RealMatrix tranpose = matrix.transpose();
		SingularValueDecomposition svd = new SingularValueDecomposition(tranpose.multiply(matrix));
		DecompositionSolver solver = svd.getSolver();
		RealMatrix inverse = solver.getInverse();
		RealMatrix finalMatrix = inverse.multiply(tranpose);
		return finalMatrix.operate(ftarget);
		//OLSMultipleLinearRegression regressor = new OLSMultipleLinearRegression();
		//regressor.newSampleData(ftarget, design);
		//return regressor.estimateRegressionParameters();
	}
}