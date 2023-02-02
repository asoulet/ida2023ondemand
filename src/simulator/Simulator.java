package simulator;

import tools.Distribution;

public abstract class Simulator {

	protected Distribution distribution;
	protected double mass;

	public Simulator(Distribution distribution) {
		this.distribution = distribution;
		for (int i = 0; i < distribution.size(); i++) {
			mass += distribution.getValue(i) * distribution.getCount(i);
		}
	}
	
	public void prepare(Cost cost, int load) {
		
	}
	abstract public double simulateStorageCost(Cost cost, int load);
	abstract public double simulateCompuationalCost(Cost cost, int load);
	abstract public double getUtilityRatio(Cost cost, int load);

}
