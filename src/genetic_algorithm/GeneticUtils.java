package genetic_algorithm;

import java.util.ArrayList;

public class GeneticUtils {

	/**
	 * 
	 * @param chromos
	 * @return 1.0, 100% of genes are different, 0% means same genes, population has converged
	 */
	public static double measure_diverisity(ArrayList<Chromosome> chromos) {
		double average = 0;
		for (int gene = 0; gene < Chromosome.LENGTH; gene++) {
			ArrayList<Double> alleles = new ArrayList<>();
			for (Chromosome some : chromos) {
				if (!alleles.contains(some.getAllele(gene))) {
					// new and diverse gene found
					alleles.add(some.getAllele(gene));
				}
			}

			double div = (double) (alleles.size() - 1) / (chromos.size() - 1);
			average += div;
		}

		return average / Chromosome.LENGTH;
	}

	public static void normalizeSetToSumToOne(ArrayList<Float> numbers) {

		float sum = 0;
		for (int i = 0; i < numbers.size(); i++) {
			sum += numbers.get(i);
		}

		if (sum != 0)
			for (int i = 0; i < numbers.size(); i++) {
				float value = numbers.get(i);
				numbers.set(i, value /= sum);
			}
		else
			for (int i = 0; i < numbers.size(); i++) {
				numbers.set(i, 1f / numbers.size());
			}

	}

	public static int getIndexFromWeightedProbabilities(ArrayList<Float> weights) {
		float value = (float) Math.random();

		for (int i = 0; i < weights.size() + 1; i++) {
			if (value <= getSumUpTo(weights, i)) {
				return i;
			}
		}
		return -1;
	}

	public static float getSumUpTo(ArrayList<Float> nums, int len) {
		float sum = 0;
		for (int i = 0; i < len + 1; i++) {
			sum += nums.get(i);
		}

		return sum;
	}

	public static float getPointsBasedOnTime(float x) {
		return (float) (1000 * Math.exp(-.0075 * x));
	}
	
	public static <T extends Number & Comparable<T>> int findIndexOfSmallestValue(ArrayList<T> data) {
		int index = 0;
		T min = data.get(index);
		
		for(int i = 0; i < data.size(); i++) {
			if(data.get(i).compareTo(min) < 0) {
				index = i;
				min = data.get(i);
			}
		}
		return index;
	}
}
