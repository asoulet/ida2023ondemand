package simulator;

public class CostConstant extends Cost {
	
	private double constant = 1;
	
	public CostConstant(double constant) {
		this.constant = constant;
	}

	@Override
	double getCost(int k) {
		return constant;
	}

}
