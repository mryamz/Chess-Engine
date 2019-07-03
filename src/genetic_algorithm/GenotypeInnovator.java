package genetic_algorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class GenotypeInnovator {

	@Deprecated
	private static Chromosome uniformCrossover(Chromosome parent1, Chromosome parent2) {

		double[] chromosomes = new double[Chromosome.LENGTH];

		for (int i = 0; i < Chromosome.LENGTH; i++) {
			chromosomes[i] = new Random().nextBoolean() ? parent1.getAllele(i) : parent2.getAllele(i);
		}

		Chromosome child = new Chromosome(chromosomes);
		return child;
	}

	@Deprecated
	public static Genotype createNewGenoTypeTrial1(Genotype lastGeneration) {

		ArrayList<Chromosome> chromosomes = new ArrayList<>();
		Chromosome p1 = lastGeneration.popMostFitSolution(true);
		Chromosome p2 = lastGeneration.popMostFitSolution(true);

		for (int i = 0; i < 8; i++) {
			Chromosome child = uniformCrossover(p1, p2);
			bitFlipMutationDerivation(child, .025f);
			chromosomes.add(child);
		}

		chromosomes.add(p1);
		chromosomes.add(p2);

		return new Genotype(chromosomes);
	}

	private static void bitFlipMutationDerivation(Chromosome target, float percentAsDecimal) {
		for (int i = 0; i < Chromosome.LENGTH; i++) {
			if (Math.random() < .025) {
				target.setAllele(i, (Math.random() * 2) - 1);
			}
		}
	}

	private static Chromosome uniformCrossoverWithProportionalGeneSwapping(Genotype lastGen) {
		ArrayList<Float> fitnesses = lastGen.getFitnessScores();
		GeneticUtils.normalizeSetToSumToOne(fitnesses);
		double[] chromosomes = new double[Chromosome.LENGTH];

		for (int i = 0; i < Chromosome.LENGTH; i++) {
			int index = GeneticUtils.getIndexFromWeightedProbabilities(fitnesses);
			chromosomes[i] = lastGen.population.get(index).getAllele(i);
		}

		return new Chromosome(chromosomes);
	}

	public static Genotype createNewGenoTypeTrial2(Genotype lastGeneration) {

		ArrayList<Chromosome> chromosomes = new ArrayList<>();

		for (int i = 0; i < lastGeneration.getN(); i++) {
			Chromosome child = uniformCrossoverWithProportionalGeneSwapping(lastGeneration);
			bitFlipMutationDerivation(child, 0.05f);
			chromosomes.add(child);
		}

		return new Genotype(chromosomes);
	}

	@Deprecated
	public static Genotype loadFromFile(String path, int generation, float mutationRate) {
		try {
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.isEmpty())
					continue;
				String[] data = line.split(",");
				int gen = Integer.parseInt(data[2]);
				if (gen == generation) {
					String[] genes1 = data[0].trim().split(" ");
					reader.readLine();
					String[] genes2 = reader.readLine().split(",")[0].trim().split(" ");

					double[] chromo1 = new double[Chromosome.LENGTH];
					double[] chromo2 = new double[Chromosome.LENGTH];

					for (int i = 0; i < Chromosome.LENGTH; i++) {
						chromo1[i] = Double.parseDouble(genes1[i]);
						chromo2[i] = Double.parseDouble(genes2[i]);
					}

					Chromosome c1 = new Chromosome(chromo1);
					Chromosome c2 = new Chromosome(chromo2);

					ArrayList<Chromosome> pop = new ArrayList<>();
					pop.add(c1);
					pop.add(c2);
					for (int i = 0; i < 8; i++) {
						pop.add(uniformCrossover(c1, c2));
						bitFlipMutationDerivation(pop.get(i), mutationRate);
					}
					return new Genotype(pop);
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 
	 * @param filename
	 * @param g
	 * @param generation
	 * @param type
	 * @param nonZeroFitness
	 * 
	 * Logs data into csv file: GENERATION,DIVERSITY,FIT_COUNT,AVG_GENOTYPE_FITNESS,CHROMOSOME
	 */
	public static void log(String filename, Genotype g, int generation, Genotype type, int nonZeroFitness) {

		StringBuilder sb = new StringBuilder();
		Chromosome target = g.popMostFitSolution(false);

		//GENERATION,DIVERSITY,FIT_COUNT,AVG_GENOTYPE_FITNESS,CHROMOSOME
		sb.append(generation).append(",").append(GeneticUtils.measure_diverisity(type.population)).append(",").append(nonZeroFitness).append(",").append(g.getAverageFitness()).append(",");
		for (int i = 0; i < Chromosome.LENGTH; i++) {
			sb.append(target.getAllele(i) + " ");
		}

		try {
			FileWriter fileWriter = new FileWriter(filename, true);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.println(sb.toString());
			printWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Chromosome getChromosomeFromFile(String path, int lineNumber) {
		double[] chromo = new double[Chromosome.LENGTH];
		float fitness = 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(path)));

			for (int i = 0; reader.ready(); i++) {
				String line = reader.readLine();
				if (i == lineNumber) {
					String[] data = line.split(",");
					String[] genes = data[4].trim().split(" ");
					fitness = Float.parseFloat(data[1]);
					for (int j = 0; j < Chromosome.LENGTH; j++) {
						chromo[j] = Double.parseDouble(genes[j]);
					}
				}
			}

			reader.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}

		Chromosome some = new Chromosome(chromo);
		some.fitness_score = fitness;
		return some;
	}

	public static Genotype loadFromFile2(String path, int generation, int n) {
		ArrayList<Chromosome> chromosomes = new ArrayList<>();
		for (int i = generation; i > generation - (n); i--) {
			System.out.println(i);
			chromosomes.add(getChromosomeFromFile(path, i));
		}

		return new Genotype(chromosomes);
	}

}