package main;

import java.util.ArrayList;

import genetic_algorithm.FitnessFunctions;
import genetic_algorithm.Genotype;
import genetic_algorithm.GenotypeInnovator;

public class GeneticTest {

	public static void main(String... strings) {

		int startgen = 0;
		Genotype type = new Genotype(20);
		type.randomlyInitPopulation();

		for (int i = startgen + 1; true; i++) {
			ArrayList<Float> values = FitnessFunctions.getFitnessTrial2(type);
			
			// not really part of the algorithm just interesting metadata for the log file
			int fitCount = 0;
			for (Float f : values) {
				if (f != 0)
					fitCount++;
			}

			GenotypeInnovator.log("new_test_v1.txt", type, i, type, fitCount);
			type = GenotypeInnovator.createNewGenoTypeTrial4(type);
		}
	}

}
