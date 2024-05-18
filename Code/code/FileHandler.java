package code;

import java.io.*;
import java.util.*;

public class FileHandler {
	public List<File> listFilesInDirectory(String directoryPath) {
        File folder = new File(directoryPath);
        return Arrays.asList(folder.listFiles(File::isFile));
    }

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
