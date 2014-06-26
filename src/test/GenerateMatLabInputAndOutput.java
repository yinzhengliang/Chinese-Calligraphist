package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import constants.Constant;

public class GenerateMatLabInputAndOutput {
	public static void main(String[] args) {
		String dataSource = Constant.RESOURCE_PATH + "MySolutionFeatureExtraction/myResult.txt";
		String input = Constant.RESOURCE_PATH + "input.txt";
		String output = Constant.RESOURCE_PATH + "output.txt";

		BufferedReader reader;

		int j = 0;

		try {
			reader = new BufferedReader(new FileReader(dataSource));

			BufferedWriter inWriter = new BufferedWriter(new FileWriter(input));
			BufferedWriter outWriter = new BufferedWriter(new FileWriter(output));

			inWriter.write("[");
			outWriter.write("[");

			String line;
			reader.readLine(); // skip first line
			while ((line = reader.readLine()) != null) {
				System.out.println(j);
				j++;
				String[] numbers = line.split("\t");
				String inputLine = "";
				String outputLine = "";
				int i = 0;
				for (i = 0; i < 85; i++) {
					inputLine = inputLine + numbers[i] + ",";
				}
				inputLine = inputLine + numbers[i++] + ";";
				inWriter.write(inputLine);
				inWriter.newLine();

				for (; i < numbers.length - 1; i++) {
					outputLine = outputLine + numbers[i] + ",";
				}
				outputLine = outputLine + numbers[i++] + ";";
				outWriter.write(outputLine);
				outWriter.newLine();
			}
			inWriter.write("];");
			outWriter.write("];");

			inWriter.close();
			outWriter.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(j);
			e.printStackTrace();
		}
	}
}
