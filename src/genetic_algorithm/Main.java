package genetic_algorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws Throwable {

		ArrayList<Float> data = new ArrayList<>();

		BufferedReader reader = new BufferedReader(new FileReader(new File("C:\\Users\\admin\\workspace\\Chess-Engine\\new_test_v4.txt")));

		while (reader.ready()) {
			String it[] = reader.readLine().split(",");
			data.add(Float.parseFloat(it[2]));
		}

		reader.close();

		int rolling_ave = 500;
		boolean fullest_ave = true;
		
		for (int i = 0; i < data.size(); i++) {
			float ave = 0;
			for (int j = (i - rolling_ave < 0 || fullest_ave ? 0 : i - rolling_ave); j < i; j++) {
				ave += data.get(j);
			}
			ave /= (i + 1);
			System.out.println(ave);
		}
	}

}
