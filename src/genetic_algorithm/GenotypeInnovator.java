package genetic_algorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
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
			bitFlipMutationRealNumberDerivation(child, .025f);
			chromosomes.add(child);
		}

		chromosomes.add(p1);
		chromosomes.add(p2);

		return new Genotype(chromosomes);
	}

	private static void bitFlipMutationRealNumberDerivation(Chromosome target, float percentAsDecimal) {
		for (int i = 0; i < Chromosome.LENGTH; i++) {
			if (Math.random() < percentAsDecimal) {
				target.setAllele(i, (Math.random() * 2) - 1);
			}
		}
	}

	@Deprecated
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

	/**
	 * 
	 * @param parent_chromosomes
	 * @param weights
	 *            values must add up to one
	 * @return
	 */
	private static Chromosome uniformWeightedCrossover(ArrayList<Chromosome> parent_chromosomes, ArrayList<Float> weights) {
		double[] chromosomes = new double[Chromosome.LENGTH];
		if (GeneticUtils.getSumUpTo(weights, weights.size()-1) != 1)
			throw new IllegalArgumentException("Weights must add to one");

		for (int i = 0; i < Chromosome.LENGTH; i++) {
			int index = GeneticUtils.getIndexFromWeightedProbabilities(weights);
			chromosomes[i] = parent_chromosomes.get(index).getAllele(i);
		}

		return new Chromosome(chromosomes);
	}

	@Deprecated
	public static Genotype createNewGenoTypeTrial2(Genotype lastGeneration) {

		ArrayList<Chromosome> chromosomes = new ArrayList<>();

		for (int i = 0; i < lastGeneration.getOriginalValueN(); i++) {
			Chromosome child = uniformCrossoverWithProportionalGeneSwapping(lastGeneration);
			bitFlipMutationRealNumberDerivation(child, 0.05f);
			chromosomes.add(child);
		}

		return new Genotype(chromosomes);
	}

	@Deprecated
	public static Genotype createNewGenoTypeTrial3(Genotype lastGeneration, int eliteCount) {
		Collections.sort(lastGeneration.population, (x, y) -> x.instantaneous_fitness_score > y.instantaneous_fitness_score ? -1 : x.instantaneous_fitness_score < y.instantaneous_fitness_score ? 1 : 0);
		ArrayList<Chromosome> elites = new ArrayList<>();
		for (int i = 0; i < lastGeneration.getOriginalValueN(); i++) {
			if (lastGeneration.population.get(i).instantaneous_fitness_score > 0 && i < eliteCount) {
				elites.add(lastGeneration.population.get(i));
			}
		}

		ArrayList<Chromosome> chromosomes = new ArrayList<>();
		chromosomes.addAll(elites);

		for (int i = 0; i < lastGeneration.getOriginalValueN() - elites.size(); i++) {
			Chromosome child = uniformCrossoverWithProportionalGeneSwapping(lastGeneration);
			bitFlipMutationRealNumberDerivation(child, 0.1f);
			chromosomes.add(child);
		}

		return new Genotype(chromosomes);
	}

	@Deprecated
	public static Genotype createNewGenoTypeTrial4(Genotype lastGeneration) {
		boolean hasPurified = false;
		int judgementAge = 10;
		// determine chromosome's worthiness only after it has obtained a
		// certain mature age
		for (int i = 0; i < lastGeneration.population.size(); i++) {
			if (lastGeneration.population.get(i).age > judgementAge && lastGeneration.population.get(i).getAverageFitness() == 0) {
				// then this chromosome seems retarded and needs to be
				// "purified," so to speak
				lastGeneration.population.remove(i);
				hasPurified = true;
				break;
			}
		}

		// if the population size is still equal to the getN(), then everyone
		// has checkmated before
		// thus the worst chromosome should leave the gene pool
		ArrayList<Float> awaf = new ArrayList<>();// age weighted average
													// fitness (awaf)
		ArrayList<Float> normalizedAges = lastGeneration.getAges();
		GeneticUtils.normalizeSetToSumToOne(normalizedAges);
		for (int i = 0; i < lastGeneration.population.size(); i++) {
			awaf.add(normalizedAges.get(i) * lastGeneration.population.get(i).accumulative_fitness_score);
		}

		if (!hasPurified) {
			int leastFitIndex = GeneticUtils.findIndexOfSmallestValue(awaf);
			if (lastGeneration.population.get(leastFitIndex).age > judgementAge) {
				Chromosome child = uniformWeightedCrossover(lastGeneration.population, awaf);
				bitFlipMutationRealNumberDerivation(child, 0.035f);
				lastGeneration.population.remove(leastFitIndex);
				lastGeneration.population.add(child);
			}
		} else {
			// add new children after purification
			Chromosome child = uniformWeightedCrossover(lastGeneration.population, awaf);
			bitFlipMutationRealNumberDerivation(child, 0.15f);
			lastGeneration.population.add(child);
		}

		if (lastGeneration.population.size() != lastGeneration.getOriginalValueN()) {
			throw new IllegalArgumentException();
		}

		return new Genotype(lastGeneration.population);
	}

	public static Genotype createNewGenoTypeTrial5(Genotype lastGeneration) {

		int judgementAge = 10;
		boolean allMatureEnough = true;
		ArrayList<Float> averageFitness = lastGeneration.getAverageFitnessScores();
		ArrayList<Float> accumsFitness = lastGeneration.getAccumScores();

		for (int i = 0; i < lastGeneration.getOriginalValueN(); i++) {
			if (lastGeneration.population.get(i).age < judgementAge) {
				allMatureEnough = false;
				break;
			}
		}

		if (allMatureEnough) {
			int worst = GeneticUtils.findIndexOfSmallestValue(averageFitness);
			GeneticUtils.normalizeSetToSumToOne(accumsFitness);
			Chromosome child = uniformWeightedCrossover(lastGeneration.population, accumsFitness);
			bitFlipMutationRealNumberDerivation(child, 0.035f);
			lastGeneration.population.remove(worst);
			lastGeneration.population.add(child);
		}

		if (lastGeneration.population.size() != lastGeneration.getOriginalValueN()) {
			throw new IllegalArgumentException();
		}

		return new Genotype(lastGeneration.population);
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
						bitFlipMutationRealNumberDerivation(pop.get(i), mutationRate);
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
	 *            Logs data into csv file:
	 *            GENERATION,DIVERSITY,FIT_COUNT,AVG_GENOTYPE_FITNESS,CHROMOSOME
	 */
	public static void log(String filename, Genotype g, int generation, Genotype type, int nonZeroFitness) {

		StringBuilder sb = new StringBuilder();
		Chromosome target = g.popMostFitSolution(false);

		// GENERATION,DIVERSITY,FIT_COUNT,AVG_GENOTYPE_FITNESS,CHROMOSOME
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
		some.instantaneous_fitness_score = fitness;
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