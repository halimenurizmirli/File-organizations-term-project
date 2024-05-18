package code;

import java.io.*;
import java.security.*;
import java.util.*;


public class PasswordProcessor {
	private static final int MAX_PASSWORDS_PER_FILE = 10000;

    public void processPasswords(File file) {
        FileHandler fileHandler = new FileHandler();
        try {
            List<String> passwords = fileHandler.readLinesFromFile(file);
            for (String password : passwords) {
                if (!password.isEmpty()) {
                    String indexFolder = getIndexFolder(password);
                    savePassword(indexFolder, password, file.getName());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getIndexFolder(String password) {
        char firstChar = password.charAt(0);
        return "Index/" + firstChar;
    }

    private void savePassword(String indexFolder, String password, String sourceFileName) {
        File subfolder = new File(indexFolder);
        if (!subfolder.exists()) {
            subfolder.mkdirs();
        }

        try {
            String md5 = HashUtil.generateMD5Hash(password);
            String sha128 = HashUtil.generateSHA128Hash(password);
            String sha256 = HashUtil.generateSHA256Hash(password);

            File outputFile = getOutputFile(subfolder);
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile, true))) {
                bw.write(password + "|" + md5 + "|" + sha128 + "|" + sha256 + "|" + sourceFileName);
                bw.newLine();
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private File getOutputFile(File folder) {
        File[] files = folder.listFiles((dir, name) -> name.startsWith("passwords_"));
        if (files == null || files.length == 0) {
            return new File(folder, "passwords_1.txt");
        }

        File lastFile = files[files.length - 1];
        if (getFileLineCount(lastFile) < MAX_PASSWORDS_PER_FILE) {
            return lastFile;
        } else {
            int nextFileIndex = files.length + 1;
            return new File(folder, "passwords_" + nextFileIndex + ".txt");
        }
    }

    private int getFileLineCount(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            int lines = 0;
            while (br.readLine() != null) {
                lines++;
            }
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
