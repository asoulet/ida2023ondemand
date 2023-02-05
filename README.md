# Should We Consider On-Demand Analysis in Scale-Free Networks?

Networks are structures used in many fields for which it is necessary to have analytical systems. Often, the size of networks increases over the time so that the connectivity of the nodes follows a power law. This scale-free nature also causes analytical queries to be concentrated on nodes with higher connectivity. Rather than computing the query results for each node in advance, this paper considers an on-demand approach to evaluate its potential gain. To this end, we propose a cost model dedicated to scale-free networks for which we compute the cost for both the offline and on-demand systems. It is reasonable in an on-demand approach to cache part of the results on the fly. We study theoretically and on real-world networks three policies: caching nothing, caching everything and minimizing the total cost. Experiments show that the on-demand approach is relevant if some of the results are cached, especially when the query load is low and the query complexity is reasonable.

## Publication

*Should We Consider On-Demand Analysis in Scale-Free Networks?* [Arnaud Soulet](https://www.info.univ-tours.fr/~soulet/), Full paper at [IDA 2023](https://ida2023.org/)

## Source code

We provide the Java source code of the prototype in `src/` directory. For reproducing all the experiments of the paper, just lauch the runnable class `src/simulator/ComparisonStudy.java`.
