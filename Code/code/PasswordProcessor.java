package code;

import java.io.*;
import java.nio.file.*;
import java.security.*;
import java.util.*;

//PasswordProcessor sınıfı, şifreleri işler ve ilgili klasörlere kaydeder.
public class PasswordProcessor {
	private static final int MAX_PASSWORDS_PER_FILE = 10000;
	private static FileHandler fileHandler = new FileHandler();
	// Belirtilen dosyadaki şifreleri işler
	public void processPasswords(File file) {
		try {
			List<String> passwords = fileHandler.readLinesFromFile(file);// Dosyadan şifreleri oku
			for (String password : passwords) {
				if (!password.isEmpty()) {
					String indexFolder = getIndexFolder(password); // Şifrenin yazılacağı klasörünü al
					if (checkPassword(indexFolder, password)) // şifrenin daha önce kayıtlı olmadığı kontrolü yapılıyor.
					{
						savePassword(indexFolder, password, file.getName()); // Şifreyi klasöre kaydet

					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//eğer böyle bir şifre varsa false döndürür.
	private Boolean checkPassword(String indexFolder, String password) throws IOException {
		File subfolder = new File(indexFolder);
		if (!subfolder.exists()) {
			return true;
		}
		String line = "";
		File[] files = subfolder.listFiles() ;
		for (File file : files) {
			BufferedReader br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null) {
				String[] firstString = line.split("\\|");	//"|" özel karakterdir. Kaçış karakteri kullanılmalıdır.
				if (firstString[0].equals(password)) {
					return false;
				}
			}
		}

		return true;
	}

	private String getIndexFolder(String password) {
		char firstChar = password.charAt(0); // İlk karakteri al
		int asciiValue = (int) firstChar; // İlk karakterin ASCII değerini al
		return "Index/" + asciiValue; // ASCII değerini kullanarak klasör yolunu oluştur
	}

	// Şifreyi belirtilen klasöre kaydeder
	private void savePassword(String indexFolder, String password, String sourceFileName) {
		File subfolder = new File(indexFolder);
		if (!subfolder.exists()) {
			subfolder.mkdirs(); // Klasör yoksa oluştur
		}

		try {
			String md5 = HashUtil.generateMD5Hash(password);
			String sha128 = HashUtil.generateSHA128Hash(password);
			String sha256 = HashUtil.generateSHA256Hash(password);

			// Şifrelerin kaydedileceği dosyayı al
			File outputFile = getOutputFile(subfolder);
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile, true))) {
				bw.write(password + "|" + md5 + "|" + sha128 + "|" + sha256 + "|" + sourceFileName);
				bw.newLine();
			}
		} catch (IOException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	// Klasördeki mevcut dosyalar arasında bir dosya seçer veya yenisini oluşturur
	private File getOutputFile(File folder) throws IOException {
		File[] files = folder.listFiles((dir, name) -> name.startsWith("passwords_"));
		if (files == null || files.length == 0) {
			return new File(folder, "passwords_1.txt");// Klasörde hiç dosya yoksa ilk dosyayı oluştur
		}

		File lastFile = files[files.length - 1];
		if (getFileLineCount(lastFile) < MAX_PASSWORDS_PER_FILE) {
			return lastFile; // Son dosyada hala yer varsa onu kullan
		} else {
			int nextFileIndex = files.length + 1;
			return new File(folder, "passwords_" + nextFileIndex + ".txt"); // Yeni bir dosya oluştur
		}
	}

	// Bir dosyadaki satır sayısını döner
	private int getFileLineCount(File file) throws IOException {
		
		long lineCount = Files.lines(Paths.get(file.getPath())).count();
		return (int) lineCount;
	}
//	private int getFileLineCount(File file) {
//		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
//			int lines = 0;
//			while (br.readLine() != null) {
//				lines++;
//			}
//			return lines;
//		} catch (IOException e) {
//			e.printStackTrace();
//			return 0;
//		}
//	}

}
