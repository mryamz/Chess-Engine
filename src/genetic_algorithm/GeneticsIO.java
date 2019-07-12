package genetic_algorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class GeneticsIO {
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
						pop.add(GenotypeInnovator.uniformCrossover(c1, c2));
						GenotypeInnovator.bitFlipMutationRealNumberDerivation(pop.get(i), mutationRate);
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
	 *            GENERATION,DIVERSITY,FIT_COUNT,AVG_GENOTYPE_FITNESS,AVG_ACCUM_FITNESS,AVG_AGE
	 */
	public static void log(String filename, Genotype g, int generation, Genotype type, int nonZeroFitness, float ave_accum, float ave_age) {

		StringBuilder sb = new StringBuilder();

		// GENERATION,DIVERSITY,FIT_COUNT,AVG_GENOTYPE_FITNESS,AVG_ACCUM_FITNESS,AVG_AGE
		sb.append(generation).append(",").append(GeneticUtils.measure_diverisity(type.population)).append(",").append(nonZeroFitness).append(",").append(g.getAverageFitness()).append(",").append(ave_accum).append(",").append(ave_age);

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

	/**
	 * 
	 * @param path
	 * @param generation
	 * @param type
	 * 
	 *            It's important to save where the calculation is at in case of
	 *            unforeseen thrown runtime exceptions
	 */
	public static void saveGenerationToFile(String path, int generation, Genotype type) {
		StringBuilder sb = new StringBuilder();
		// generations
		// size
		sb.append(generation).append(System.lineSeparator()).append(type.population.size()).append(System.lineSeparator());

		// cN,accum_fitnessN,a1 a2 a3 ... aN
		for (int i = 0; i < type.population.size(); i++) {
			Chromosome member = type.population.get(i);

			sb.append(member.getID()).append(",").append(member.accumulative_fitness_score).append(",");
			for (int j = 0; j < Chromosome.LENGTH; j++) {
				sb.append(member.getAllele(j)).append(" ");
			}
			sb.append(System.lineSeparator());
		}

		try {
			FileWriter fileWriter = new FileWriter(path);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.println(sb.toString());
			printWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
