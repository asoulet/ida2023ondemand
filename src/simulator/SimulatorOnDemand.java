package simulator;

import tools.Distribution;

public class SimulatorOnDemand extends Simulator {
	
	public enum CacheStrategy {
		NO_CACHE,
		ALL,
		BEST,
		APPROXIMATE,
		MANUAL
	};
	private double cacheDegree = 1;
	private CacheStrategy strategy = CacheStrategy.APPROXIMATE;
	private double coefficientStorage = 1;
	private double coefficientComputation = 1;
	
	public SimulatorOnDemand(Distribution distribution, CacheStrategy strategy) {	
		super(distribution);
		this.strategy = strategy;
	}
	
	public void prepare(Cost cost, int load) {
		switch (strategy) {
		case NO_CACHE: cacheDegree = Double.MAX_VALUE; break;
		case ALL: cacheDegree = 0; break;
		case BEST: approximateCacheDegree(cost, load); break;
		case APPROXIMATE: cacheDegree = (mass / load) * (coefficientComputation + coefficientStorage) / coefficientComputation; break;
		default:
			break;
		}
	}

	public void approximateCacheDegree(Cost cost, int load) {
		cacheDegree = approximateCacheDegree(cost, load, 0, distribution.getMaximumDegree(), simulateCost(cost, load, 0), simulateCost(cost, load, distribution.getMaximumDegree()));		
	}

	private int approximateCacheDegree(Cost cost, int load, int min, int max, double costMin, double costMax) {		
		if (max - min <= 1)
			if (costMin < costMax)
				return min;
			else
				return max;
		int d = (min + max) / 2;
		double c = simulateCost(cost, load, d);
		if (costMin > costMax)
			return approximateCacheDegree(cost, load, d, max, c, costMax);
		else
			return approximateCacheDegree(cost, load, min, d, costMin, c);
	}
	
	public double simulateCost(Cost cost, int load, int cd) {
		cacheDegree = cd;
		return simulateStorageCost(cost, load) + simulateCompuationalCost(cost, load);
	}

	public SimulatorOnDemand(Distribution distribution, double cacheDegree) {
		super(distribution);
		this.strategy = CacheStrategy.MANUAL;
		this.cacheDegree = cacheDegree;
	}
	
	
	public double getCacheDegree() {
		return cacheDegree;
	}

	public void setCacheDegree(double cacheDegree) {
		this.cacheDegree = cacheDegree;
	}

	@Override
	public double simulateStorageCost(Cost cost, int load) {
		double c = 0;
		for (int i = 0; i < distribution.size(); i++) {
			if (distribution.getValue(i) >= cacheDegree) {	
				c += (1 - Math.pow(1 - (distribution.getValue(i) / mass), load)) * cost.getCost(distribution.getValue(i)) * distribution.getCount(i) * coefficientStorage;
			}
		}			
		return c;
	}

	@Override
	public double simulateCompuationalCost(Cost cost, int load) {
		double c = 0;
		for (int i = 0; i < distribution.size(); i++) {
			if (distribution.getValue(i) >= cacheDegree)
				c += (1 - Math.pow(1 - (distribution.getValue(i) / mass), load)) * cost.getCost(distribution.getValue(i)) * distribution.getCount(i) * coefficientComputation;
			else
				c += load * (distribution.getValue(i) / mass) *  cost.getCost(distribution.getValue(i)) * distribution.getCount(i) * coefficientComputation;
		}			
		return c;
	}

	@Override
	public double getUtilityRatio(Cost cost, int load) {
		double done = simulateStorageCost(cost,load);
		double c = 0;
		for (int i = 0; i < distribution.size(); i++) {
			if (distribution.getValue(i) >= cacheDegree) {
				c += (1 - Math.pow(1 - (distribution.getValue(i) / mass), load) * (1 + load * (distribution.getValue(i) / mass))) * cost.getCost(distribution.getValue(i)) * distribution.getCount(i) * coefficientStorage;
			}
		}			
		return c / done;
	}

	@Override
	public String toString() {
		return "OnDemand/" + strategy;
	}

	
}
