package main;

import java.io.IOException;
import java.util.ArrayList;

public class CsvFile {

	private Line header;
	private ArrayList<Line> lines;
	private int currentItem, difference;
	private final int size;

	public CsvFile(ArrayList<Line> lines) throws IOException {
		this.lines = lines;
		this.size = lines.size();
		this.difference = 10;
		this.currentItem = 0;
	}

	public void setHeader(Line header) {
		this.header = header;
	}

	public void setCurrentItem(int currentItem) {
		if (currentItem >= 0 && currentItem < size) {
			this.currentItem = currentItem;
		}
	}

	public void setDifference(int difference) {
		this.difference = difference;
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

}
