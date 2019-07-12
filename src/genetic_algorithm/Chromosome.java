package genetic_algorithm;

import perceptron.MultilayerPerceptron;

public class Chromosome {

	public static final int LENGTH = (63 * 64 + 5) * 2;
	private static int instance_counter;
	private int id;

	public float instantaneous_fitness_score;
	public float accumulative_fitness_score;
	public int age = 0;

	private double[] chromosomes;

	public Chromosome(double[] chromosomes) {
		this.chromosomes = chromosomes;
		id = ++instance_counter;
	}

	public double getAllele(int gene) {
		return chromosomes[gene];
	}

	public void setAllele(int gene, double allele) {
		chromosomes[gene] = allele;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < LENGTH; i++) {
			builder.append(chromosomes[i]).append(" ");
		}
		return builder.toString();
	}

	public MultilayerPerceptron getPerceptron() {
		MultilayerPerceptron mp = MultilayerPerceptron.createEmptyPerceptron();
		int index = 0;
		for (int j = 0; j < mp.getLayers().length; j++) {
			for (int k = 0; k < mp.getLayers()[j].neurons.length; k++) {
				if (index < Chromosome.LENGTH / 2) {
					mp.getLayers()[j].weights[k] = chromosomes[index * 2];
					mp.getLayers()[j].biases[k] = chromosomes[index++ * 2 + 1];
				}
			}
		}

		return mp;
	}

	public float getAverageFitness() {
		return accumulative_fitness_score / age;
	}

	public String getID() {
		return "c" + id;
	}

	public void setID(int id) {
		this.id = id;
		if (id > instance_counter) {
			instance_counter = id;
		}
	}
}
