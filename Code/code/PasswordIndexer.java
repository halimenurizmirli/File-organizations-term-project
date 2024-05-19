package code;

import java.io.*;
import java.util.*;
import java.nio.file.*;

public class PasswordIndexer {

	public static void main(String[] args) throws IOException {
		
		FileHandler fileHandler = new FileHandler();
        PasswordProcessor passwordProcessor = new PasswordProcessor();
        File processed = new File("Processed");
        if (processed.isDirectory()) {
            File[] files = processed.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete(); 
                }
            } else {
                System.out.println("Klasör boş veya okunamıyor.");
            }
        } else {
            System.out.println("Belirtilen yol bir klasör değil.");
        }
        // "Unprocessed-Passwords" klasöründeki dosyaları listele
        List<File> files = fileHandler.listFilesInDirectory("Unprocessed-Passwords");
        long startTime = System.nanoTime();
        for (File file : files) {
        	// Her dosyayı işle
            passwordProcessor.processPasswords(file);
            file.renameTo(new File("Processed/" + file.getName()));
        }
        long endTime = System.nanoTime();
		
        
        System.out.println("bitti.");
        Double time = ((double)(endTime-startTime)/1000000000);
        System.out.println("Time is "+ time+" seconds");
        System.out.println("Time is "+ time/60 +" minutes");
	}
	
	
	
	

}
