package simulator;

import java.util.ArrayList;

import simulator.SimulatorOnDemand.CacheStrategy;
import tools.Distribution;

public class ComparisonStudy {

	public static String SEP = "\t";

	public static void main(String[] args) {
		ArrayList<Distribution> distributions = new ArrayList<Distribution>();
		distributions.add(new Distribution("data/distribution_wikidata_in.csv", "Wikidata"));
		distributions.add(new Distribution("data/distribution_cit-patents_in.csv", "Patents"));
		distributions.add(new Distribution("data/distribution_twitch_in.csv", "Twitch"));
		distributions.add(new Distribution("data/distribution_neurons_in.csv", "Cell-Cell"));
		distributions.add(new Distribution("data/distribution_dbnary.csv", "DBnary"));
		double constant = 10;
		Cost[] costs = { new CostConstant(constant), new CostSublinear(constant), new CostLinear(constant), new CostSubquadratic(constant),
				new CostQuadratic(constant), };
		for (Distribution distribution : distributions) {
			Simulator[] simulators = { new SimulatorOffline(distribution),
					new SimulatorOnDemand(distribution, CacheStrategy.ALL),
					new SimulatorOnDemand(distribution, CacheStrategy.NO_CACHE),
					new SimulatorOnDemand(distribution, CacheStrategy.BEST),
					new SimulatorOnDemand(distribution, CacheStrategy.APPROXIMATE), };
			for (int s = 0; s < simulators.length; s++)
				for (int c = 0; c < costs.length; c++)
					for (int q = 2; q < 10; q++) {
						Cost cost = costs[c];
						int load = (int) Math.pow(10, q);
						Simulator simulator = simulators[s];
						simulator.prepare(cost, load);
						System.out.println(distribution + SEP + simulator + SEP + cost.getClass().getName() + SEP + load + SEP
								+ simulator.simulateStorageCost(cost, load) + SEP
								+ simulator.simulateCompuationalCost(cost, load) + SEP
								+ simulator.getUtilityRatio(cost, load) + SEP 
								+ (simulator.simulateStorageCost(cost, load)
										+ simulator.simulateCompuationalCost(cost, load)));
					}
		}
	}
}
