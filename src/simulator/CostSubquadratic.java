package simulator;

public class CostSubquadratic extends Cost {
	
	private double constant = 1;
	
	public CostSubquadratic(double constant) {
		this.constant = constant;
	}

	@Override
	double getCost(int k) {
		return ((double)k * Math.log(k)) * constant;
	}

}
