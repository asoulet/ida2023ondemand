package simulator;

public class CostSublinear extends Cost {
	
	private double constant = 1;
	
	public CostSublinear(double constant) {
		this.constant = constant;
	}

	@Override
	double getCost(int k) {
		return Math.log(k) * constant;
	}

}
