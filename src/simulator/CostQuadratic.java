package simulator;

public class CostQuadratic extends Cost {
	
	private double constant = 1;
	
	public CostQuadratic(double constant) {
		this.constant = constant;
	}

	@Override
	double getCost(int k) {
		return ((double)k) * ((double)k) * constant;
	}

}
