package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CvsFileParser {

	public CsvFile parse(File file) throws IOException {
		if (!file.exists()) {
			return null;
		}
		ArrayList<Line> lines = new ArrayList<Line>();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		Line header = new Line(reader.readLine());
		while ((line = reader.readLine()) != null) {
			lines.add(new Line(line));
		}
		CsvFile csvFile = new CsvFile(lines);
		csvFile.setHeader(header);
		return csvFile;
	}

}
