package genetic_algorithm;

import java.util.ArrayList;

import perceptron.MultilayerPerceptron;

public class Genotype {

	public ArrayList<Chromosome> population = new ArrayList<>();
	private int n;

	public Genotype(ArrayList<Chromosome> population) {
		this.population = population;
		n = population.size();
	}

	public Genotype(int n) {
		this.n = n;
	}

	public ArrayList<Float> getFitnessScores() {
		ArrayList<Float> fitnesses = new ArrayList<>();
		for (int i = 0; i < population.size(); i++) {
			fitnesses.add(population.get(i).instantaneous_fitness_score);
		}
		return fitnesses;
	}

	public ArrayList<Float> getMaturedFitnessScores() {
		ArrayList<Float> fitnesses = new ArrayList<>();
		for (int i = 0; i < population.size(); i++) {
			fitnesses.add(population.get(i).getAverageFitness());
		}
		return fitnesses;
	}

	public ArrayList<Float> getAges() {
		ArrayList<Float> ages = new ArrayList<>();
		for (int i = 0; i < population.size(); i++) {
			ages.add((float) population.get(i).age);
		}
		return ages;
	}

	public float getAverageFitness() {
		float ave = 0;

		for (Chromosome some : population) {
			ave += some.instantaneous_fitness_score;
		}

		return ave / n;
	}

	public void randomlyInitPopulation() {
		for (int i = 0; i < n; i++) {
			// make random chromosome
			double[] chromosome = new double[Chromosome.LENGTH];
			for (int j = 0; j < Chromosome.LENGTH / 2; j++) {
				chromosome[j * 2] = (Math.random() * 2) - 1; // weights

				chromosome[j * 2 + 1] = (Math.random() * 2) - 1; // biases
			}
			population.add(new Chromosome(chromosome));
		}
	}

	public ArrayList<MultilayerPerceptron> getPhenotype() {
		ArrayList<MultilayerPerceptron> phenos = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			phenos.add(this.population.get(i).getPerceptron());
		}
		return phenos;
	}

	/**
	 * 
	 * @param shouldPop
	 *            modifies fixed constant N
	 * @return
	 */
	@Deprecated
	public Chromosome popMostFitSolution(boolean shouldPop) {
		int maxIndex = 0;
		float maxValue = 0;
		for (int i = 0; i < n; i++) {
			if (population.get(i).instantaneous_fitness_score > maxValue) {
				maxValue = population.get(i).instantaneous_fitness_score;
				maxIndex = i;
			}
		}
		Chromosome c = population.get(maxIndex);

		if (shouldPop) {
			population.remove(maxIndex);
			n--;
		}
		return c;
	}

	/**
	 * The original population size
	 * 
	 * @return
	 */
	public int getOriginalValueN() {
		return n;
	}
}
