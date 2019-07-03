package main;

import java.util.ArrayList;

import genetic_algorithm.FitnessFunctions;
import genetic_algorithm.Genotype;
import genetic_algorithm.GenotypeInnovator;

public class GeneticTest {

	public static void main(String... strings) {

		int startgen = 0;
		Genotype type = new Genotype(10);
		type.randomlyInitPopulation();

		for (int i = startgen + 1; true; i++) {
			ArrayList<Float> values = FitnessFunctions.getFitnessTrial2(type);
			int fitCount = 0;
			for (Float f : values) {
				if (f != 0)
					fitCount++;
			}

			GenotypeInnovator.log("chromosomes_proportional_crossover_v5.txt", type, i, type, fitCount);
			type = GenotypeInnovator.createNewGenoTypeTrial2(type);
		}
	}

}
