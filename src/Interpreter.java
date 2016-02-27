import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class Interpreter {

	public void readfile() {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		while (sc.hasNextLine()) {
			Parser parser = new Parser(new NewScanner(sc.nextLine()));
			parser.ParseStart();
		}
	}

	public String readfile3() {
		String s = "";
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		s += sc.nextLine();
		return s;
	}

	public String readfile2() {
		String s = "";
		BufferedReader br = null;

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader("F:\\Dropbox\\workspace\\PL1\\round1"));

			while ((sCurrentLine = br.readLine()) != null) {
				if (!sCurrentLine.startsWith("//")) {
					Parser parser = new Parser(new NewScanner(sCurrentLine));
					parser.ParseStart();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return s;
	}

	public static void main(String[] args) {
		Interpreter ip = new Interpreter();
		ip.readfile();
	}
}
