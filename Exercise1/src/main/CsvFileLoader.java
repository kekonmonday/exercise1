package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CsvFileLoader {

	private String path;
	private CsvFileContainer csvFileContainer;

	public CsvFileLoader(String path) {
		this.path = path;
	}

	public CsvFileContainer getCsvFileContainer() {
		if (csvFileContainer == null) {
			load();
		}
		return csvFileContainer;
	}

	public void load() {
		ArrayList<File> files = new ArrayList<File>();
		File dir = new File(path);
		for (String fileName : dir.list((folder, name) -> name.toLowerCase().endsWith(".csv"))) {
			files.add(new File(fileName));
		}
		csvFileContainer = new CsvFileContainer(files);
	}

	class CsvFileContainer {

		private ArrayList<File> files;
		private int indexCurrentFile;

		public CsvFileContainer(ArrayList<File> files) {
			this.files = files;
			this.indexCurrentFile = 0;
		}

		public int getindexCurrentFile() {
			return indexCurrentFile;
		}

		public void setindexCurrentFile(int currentFile) {
			this.indexCurrentFile = currentFile;
		}

		// Обработка файла
		public CsvFile getCurrentCsvFile() {
			CvsFileParser cvsFileParser = new CvsFileParser();
			CsvFile csvFile = null;
			try {
				csvFile = cvsFileParser.parse(new File(path + "\\" + files.get(indexCurrentFile).getName()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return csvFile;
		}

		public ArrayList<File> getFiles() {
			return files;
		}

	}

}
