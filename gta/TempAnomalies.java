package HFCC;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TempAnomalies {
	public static void main(String[] args) {
		ArrayList<TempAnomaly> anomaliesByYear = loadData("globalTempAnomalies.csv");
		Map<Integer, List<TempAnomaly>> anomaliesByMonth =
				anomaliesByYear.stream().collect(
						Collectors.groupingBy(a -> a.getMonth(), Collectors.toList()));


		List<List<TempAnomaly>> sortedByMonth = anomaliesByMonth.entrySet()
			.stream()
			.map(entry -> { // sort each list value in the Map
				List<TempAnomaly> v = entry.getValue();
				List<TempAnomaly> sortedValues = v.stream().sorted((a1, a2) -> {
					if (a1.getValue() < a2.getValue()) {
						return 1;
					} else {
						return -1;
					}	
				}).limit(10).collect(Collectors.toList());
				return sortedValues;
			})
			.collect(Collectors.toList()); // List of lists

		// output JSON
		System.out.println("[");
		sortedByMonth.forEach(l -> {
			System.out.println(" [");
			l.forEach(ta -> {
				System.out.println(
						"  { \"year\": " + ta.year + ", " +
								" \"month\": " + ta.month + ", " +
								" \"value\": " + ta.value + " }, ");
			});
			System.out.println(" ], ");
		});
		System.out.println("]");

		try {
			writeData(sortedByMonth, "tempAnaByMonthTop7.js");
		} catch (IOException e) {
			System.out.println("Error writing to output file");
		} 
	}

	public static void writeData(List<List<TempAnomaly>> sortedByMonth, String filename) throws IOException {
		Path path = Paths.get(filename);
		BufferedWriter writer = Files.newBufferedWriter(path);
		writer.write("[");
		int count1 = 0;
		for (List<TempAnomaly> la : sortedByMonth) {
			count1++;
			writer.write(" [\n");
			int count2 = 0;
			for (TempAnomaly ta : la) {
				count2++;
				writer.write(
						"  { \"year\": " + ta.year + ", " +
						" \"month\": " + ta.month + ", " +
						" \"value\": " + ta.value + " }");
				if (count2 < la.size()) writer.write(",");
				writer.write("\n");
			};
			writer.write(" ]");
			if (count1 < sortedByMonth.size()) writer.write(",");
			writer.write("\n");
		};
		writer.write("]");
		writer.flush(); writer.close();
	}
	public static ArrayList<TempAnomaly> loadData(String filename) {
		ArrayList<TempAnomaly> ta = null;
		try (Stream<String> stream = Files.lines(Paths.get(filename))) {
			ta = stream.map(line -> {
				String[] values = line.split(",");
				Integer year = Integer.parseInt(values[0].substring(0, 4));
				Integer month = Integer.parseInt(values[0].substring(4, 6));
				Float value = Float.parseFloat(values[1]);
				TempAnomaly dataPoint = new TempAnomaly(year, month, value);
				return dataPoint;
			}).collect(Collectors.toCollection(ArrayList::new));
		} catch (IOException e) {
			System.out.println("Error reading data");
			e.printStackTrace();
		}
		return ta;
	}
}

class TempAnomaly {
	Integer year;
	Integer month;
	Float value;
	
	public TempAnomaly(Integer year, Integer month, Float value) {
		this.year = year;
		this.month = month;
		this.value = value;
	}
	
	public String toString() {
		return year + " " + month + ": " + value;
	}
	
	public Integer getYear() { return this.year; }
	public Integer getMonth() { return this.month; }
	public Float getValue() { return this.value; }
}



