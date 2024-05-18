package code;

import java.io.*;
import java.util.*;

//FileHandler sınıfı, dosya işlemlerini gerçekleştirir.
public class FileHandler {
	// Belirtilen dizindeki dosyaları listeler
	public List<File> listFilesInDirectory(String directoryPath) {
		File folder = new File(directoryPath);
		return Arrays.asList(folder.listFiles(File::isFile));
	}

	// Belirtilen dosyadan satırları okur ve bir liste olarak döner
	public List<String> readLinesFromFile(File file) throws IOException {
		List<String> lines = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
		}
		return lines;
	}

}
