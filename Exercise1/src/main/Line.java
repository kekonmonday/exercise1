package main;

public class Line {

	private String line;

	public Line(String line) {
		this.line = line;
	}

	public String[] getElementsArray() {
		return line.split(",");
	}

	public String getLine() {
		return line;
	}

	@Override
	public String toString() {
		return line.replace(",", " | ");
	}
}
