package main;

import genetic_algorithm.FitnessFunctions;
import genetic_algorithm.GeneticUtils;
import genetic_algorithm.GeneticsIO;
import genetic_algorithm.Genotype;
import genetic_algorithm.GenotypeInnovator;

public class GeneticTest {

	public static void main(String... strings) {

		int[] gen = new int[1];
		Genotype type = GeneticsIO.loadSaveFile("save_test.data", gen);
		if (type == null) {
			type = new Genotype(12);
			type.randomlyInitPopulation();
		}
		for (int i = gen[0] + 1; true; i++) {
			type.increaseAges();

			FitnessFunctions.getFitnessTrial3(type);

			// interesting metadata for the log file
			int fitCount = 0;
			for (Float f : type.getFitnessScores()) {
				if (f != 0)
					fitCount++;
			}

			System.out.println(String.format("\n---------------\nGen. %s, Div. %s, Pop. %s, Metadata %s: \n---------------\n*                 Name - %s\n*      Instant Fitness - %s\n*  Accumulated Fitness - %s\n*                  Age - %s\n\n-------------------------------------------------", i, GeneticUtils.measure_diverisity(type.population), type.population.size(), fitCount, type.getNames(), type.getFitnessScores(), type.getAccumScores(), type.getAges()));

			GeneticsIO.log("new_test_v3.txt", type, i, type, fitCount, (float) GeneticUtils.getAverage(type.getAccumScores()), (float) GeneticUtils.getAverage(type.getAges()));
			GeneticsIO.saveGenerationToFile("save_test.data", i, type);
			type = GenotypeInnovator.createNewGenoTypeTrial5(type);
		}
	}

}
