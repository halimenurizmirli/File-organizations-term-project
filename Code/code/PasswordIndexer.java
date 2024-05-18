package code;

import java.io.*;
import java.util.*;
import java.nio.file.*;

public class PasswordIndexer {

	public static void main(String[] args) throws IOException {
		FileHandler fileHandler = new FileHandler();
        PasswordProcessor passwordProcessor = new PasswordProcessor();
      //  deleteFirst();

        // "Unprocessed-Passwords" klasöründeki dosyaları listele
        List<File> files = fileHandler.listFilesInDirectory("Unprocessed-Passwords");
        for (File file : files) {
        	// Her dosyayı işle
            passwordProcessor.processPasswords(file);
        }
        System.out.println("bitti.");
	}
	public static void deleteFirst() throws IOException {
//		Path klasor = Paths.get("Index");
//		Files.delete(klasor);
		File folder = new File("Index");
		for (File dosya : folder.listFiles()) {
            dosya.delete();
        }
	}
	
	
	

}
