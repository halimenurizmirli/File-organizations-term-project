package code;

import java.io.*;
import java.util.*;

public class PasswordIndexer {

	public static void main(String[] args) {
		FileHandler fileHandler = new FileHandler();
        PasswordProcessor passwordProcessor = new PasswordProcessor();

        List<File> files = fileHandler.listFilesInDirectory("Unprocessed-Passwords");
        for (File file : files) {
            passwordProcessor.processPasswords(file);
        }

	}
	

}
