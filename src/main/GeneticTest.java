package main;

import java.util.ArrayList;

import genetic_algorithm.FitnessFunctions;
import genetic_algorithm.GeneticUtils;
import genetic_algorithm.Genotype;
import genetic_algorithm.GenotypeInnovator;

public class GeneticTest {

	public static void main(String... strings) {

		int startgen = 0;
		Genotype type = new Genotype(12);
		type.randomlyInitPopulation();

		for (int i = startgen + 1; true; i++) {
			type.increaseAges();
			ArrayList<Float> values = FitnessFunctions.getFitnessTrial2(type);

			// interesting metadata for the log file
			int fitCount = 0;
			for (Float f : values) {
				if (f != 0)
					fitCount++;
			}

			System.out.println(String.format("Gen. %s, Div. %s, Pop. %s, Metadata %s: \n---------------\n*                 Name - %s\n*      Instant Fitness - %s\n*  Accumulated Fitness - %s\n*                  Age - %s\n\n-------------------------------------------------", i, GeneticUtils.measure_diverisity(type.population), type.population.size(), fitCount, type.getNames(), type.getFitnessScores(), type.getAccumScores(), type.getAges()));

			GenotypeInnovator.log("new_test_v2.txt", type, i, type, fitCount);
			type = GenotypeInnovator.createNewGenoTypeTrial5(type);
		}
	}

}
