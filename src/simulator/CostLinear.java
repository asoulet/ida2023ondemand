package simulator;

public class CostLinear extends Cost {
	
	private double constant = 1;
	
	public CostLinear(double constant) {
		this.constant = constant;
	}

	@Override
	double getCost(int k) {
		return k * constant;
	}

}
