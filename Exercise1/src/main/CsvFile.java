package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CsvFile {

	private Line header;
	private ArrayList<Line> lines;
	private int currentItem, difference;
	private final int size;

	public CsvFile(File file) throws IOException {
		lines = new ArrayList<Line>();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		header = new Line(reader.readLine());
		while ((line = reader.readLine()) != null) {
			lines.add(new Line(line));
		}
		size = lines.size();
		difference = 10;
		currentItem = 0;
	}

	public void show() {
		showHeader();
		for (Line line : lines) {
			System.out.println(line);
		}
	}

	public void showHeader() {
		System.out.println(header + "\n");
	}

	public void showCurrentItems() {
		showHeader();
		for (int i = currentItem; i < currentItem + difference; i++) {
			if (i >= size)
				break;
			System.out.println(lines.get(i));
		}
	}

	public void next() {
		if (currentItem + difference < size) {
			currentItem += difference;
		}
	}

	public void prev() {
		if (currentItem - difference >= 0) {
			currentItem -= difference;
		}
	}

	public void setCurrentItem(int currentItem) {
		if (currentItem >= 0 && currentItem < size) {
			this.currentItem = currentItem;
		}
	}

	public void setDifference(int difference) {
		this.difference = difference;
	}

}
