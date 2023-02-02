package simulator;

import tools.Distribution;

public class SimulatorOffline extends Simulator {

	public SimulatorOffline(Distribution distribution) {
		super(distribution);
	}

	@Override
	public double simulateStorageCost(Cost cost, int load) {
		double c = 0;
		for (int i = 0; i < distribution.size(); i++) {
			c += cost.getCost(distribution.getValue(i)) * distribution.getCount(i);
		}			
		return c;
	}

	@Override
	public double simulateCompuationalCost(Cost cost, int load) {
		double c = 0;
		for (int i = 0; i < distribution.size(); i++) {
			c += cost.getCost(distribution.getValue(i)) * distribution.getCount(i);
		}			
		return c;
	}

	@Override
	public double getUtilityRatio(Cost cost, int load) {
		double done = simulateStorageCost(cost,load);
		double c = 0;
		for (int i = 0; i < distribution.size(); i++) {
				c += (1 - Math.pow(1 - (distribution.getValue(i) / mass), load)) * cost.getCost(distribution.getValue(i)) * distribution.getCount(i);
		}			
		return c / done;
	}

	@Override
	public String toString() {
		return "Offline";
	}

}
