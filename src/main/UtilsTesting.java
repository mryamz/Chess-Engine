package main;

import java.util.ArrayList;
import java.util.Arrays;

import genetic_algorithm.Chromosome;
import genetic_algorithm.GeneticUtils;
import genetic_algorithm.Genotype;
import genetic_algorithm.GenotypeInnovator;
import perceptron.MultilayerPerceptron;

public class UtilsTesting {

	public static void main(String[] args) {
		System.out.println("TEST 1");
		for (int i = 0; i < 1000; i += 250) {
			System.out.println(String.format("(%s, %s)", i, GeneticUtils.getPointsBasedOnTime(i)));
		}

		System.out.println("\nTEST 2");
		ArrayList<Float> nums = new ArrayList<>();
		for (int i = 0; i < 3 * 5; i += 3) {
			nums.add((float) 0);
		}
		System.out.println(nums);
		GeneticUtils.normalizeSetToSumToOne(nums);
		System.out.println(nums);

		System.out.println("\nTEST 2.1");
		System.out.println(GeneticUtils.getSumUpTo(nums, 3));

		System.out.println("\nTEST 2.2");
		ArrayList<Float> probs = new ArrayList<>(Arrays.asList(0f, 0f, 0f, 0f, 0f));
		for (int i = 0; i < 500000; i++) {
			int index = GeneticUtils.getIndexFromWeightedProbabilities(nums);
			float value = probs.get(index);
			probs.set(index, ++value);
		}
		GeneticUtils.normalizeSetToSumToOne(probs);
		System.out.println(probs);

		System.out.println("\nTEST 3");
		Genotype rand = new Genotype(2);
		rand.randomlyInitPopulation();
		System.out.println("Diversity: " + GeneticUtils.measure_diverisity(rand.population));

		System.out.println("\nTEST 3.1 - Validate conversion between genotype to phenotype");
		MultilayerPerceptron p1 = rand.population.get(0).getPerceptron();
		ArrayList<Double> data = new ArrayList<>();
		for (int i = 0; i < p1.getLayers().length; i++) {
			for (int j = 0; j < p1.getLayers()[i].neurons.length; j++) {
				data.add(p1.getLayers()[i].weights[j]);
				data.add(p1.getLayers()[i].biases[j]);
			}
		}
		System.out.println("Genetic Material: " + data.size());
		{
			Chromosome s1 = rand.population.get(0);
			for (int i = 0; i < Chromosome.LENGTH; i++) {
				if (data.contains(s1.getAllele(i))) {
					data.remove(s1.getAllele(i));
				}
			}
		}
		System.out.println("Test Passed: " + (data.size() == 0) + ", thus genetic material count is " + data.size());

		System.out.println("\nTEST 4 - Test Elite Integration to the proportional genetic breed system");
		{
			double[] x = new double[Chromosome.LENGTH];
			double[] y = new double[Chromosome.LENGTH];
			double[] z = new double[Chromosome.LENGTH];
			for (int i = 0; i < Chromosome.LENGTH; i++) {
				x[i] = 1;
				y[i] = 2;
				z[i] = 3;
			}
			Chromosome s1 = new Chromosome(x);
			Chromosome s2 = new Chromosome(y);
			Chromosome s3 = new Chromosome(z);
			
			s2.fitness_score = 6;
			s3.fitness_score = 5;
			s1.fitness_score = 4;
			rand = new Genotype(new ArrayList<>(Arrays.asList(s1, s2, s3)));
			GenotypeInnovator.createNewGenoTypeTrial3(rand, 2);
		}
	}

}
