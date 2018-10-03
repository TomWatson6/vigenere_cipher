import java.io.FileReader;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.PrintWriter;


public class VigenereCipher {

	public static final String ENGLISH_TEXT_FILE_PATH = "./textFile1.txt";
	public static final String ENCRYPTED_TEXT_FILE = "./Encrypted_Text.txt";
	public static final int FIRST_LETTER_ASCII_UPPER = 65;
	public static final int FIRST_LETTER_ASCII_LOWER = 97;
	public static final int LETTERS_IN_ALPHABET = 26;
	public static final double IOC_LOWER_BOUND = 0.055;
	public static final double IOC_UPPER_BOUND = 0.075;

	private String password;

	public VigenereCipher(String password) throws Exception {
		//For the Vigenere cipher to work it needs a password longer than 0 characters
		if(password.length() == 0) {
			throw new Exception("You need to enter a longer password");
		} else {
			this.password = password;
		}
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	//This method takes a file, encrypts it, and writes it to a new file called "Encrypted_Text"
	public void encrypt(String filePath) {

		//Declare a variable to keep track of how far through the password it is
		int passwordIndex = 0;

		//Declare a variable to store the length of the password, so the program knows when to loop back to the beginning again
		int passwordLastIndex = password.length() - 1;

		//Declare file reader and buffered reader to read in the text file to encrypt
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		//Declare file writer and print writer to write to the new file
		FileWriter fileWriter = null;
		PrintWriter printWriter = null;

		try {

			//These are assigned to the file to read in, so that it can be encrypted
			fileReader = new FileReader(filePath);
			bufferedReader = new BufferedReader(fileReader);

			//These are assigned to the file to write to, so that the encrypted text can be written to this file
			fileWriter = new FileWriter(ENCRYPTED_TEXT_FILE);
			printWriter = new PrintWriter(fileWriter, false);

			//These variables are used to be able to pick the text apart and encrypt it
			String currentLine;
			char currentLetter;

			//This is a variable to store the current line to write to the new file
			String lineToWrite = "";

			while((currentLine = bufferedReader.readLine()) != null) {

				for(int i = 0; i < currentLine.length(); i++) {

					currentLetter = currentLine.charAt(i);

					if(Character.isLetter(currentLetter)) {
						if(Character.isUpperCase(currentLetter)) {

							//Makes the appropriate adjustment to the letter, then checks if it's out of bounds with ASCII alphabetic characters and corrects it accordingly
							char charToAdd = (char) (((int) currentLetter + (int) Character.toUpperCase(password.charAt(passwordIndex))) - FIRST_LETTER_ASCII_UPPER );
							if((int) charToAdd < FIRST_LETTER_ASCII_UPPER) charToAdd = (char) ((int) charToAdd + LETTERS_IN_ALPHABET);
							if((int) charToAdd >= FIRST_LETTER_ASCII_UPPER + LETTERS_IN_ALPHABET) charToAdd = (char) ((int) charToAdd - LETTERS_IN_ALPHABET);

							lineToWrite = lineToWrite + String.valueOf(charToAdd);
						} else {

							//Makes the appropriate adjustment to the letter, then checks if it's out of bounds with ASCII alphabetic characters and corrects it accordingly
							char charToAdd = (char) (((int) currentLetter + (int) Character.toLowerCase(password.charAt(passwordIndex))) - FIRST_LETTER_ASCII_LOWER);
							if((int) charToAdd < FIRST_LETTER_ASCII_LOWER) charToAdd = (char) ((int) charToAdd + LETTERS_IN_ALPHABET);
							if((int) charToAdd >= FIRST_LETTER_ASCII_LOWER + LETTERS_IN_ALPHABET) charToAdd = (char) ((int) charToAdd - LETTERS_IN_ALPHABET);

							lineToWrite = lineToWrite + String.valueOf(charToAdd);
						}
					} else {
						//This is for special characters
						lineToWrite = lineToWrite + currentLetter;
					}

					//Cycles through the password
					if(passwordIndex == passwordLastIndex) {
						passwordIndex = 0;
					} else {
						passwordIndex++;
					}

				}

				//Adds the line to the file and resets the line to write for the next iteration
				printWriter.println(lineToWrite);
				lineToWrite = "";

			}

		} catch(IOException ioe) {
			ioe.printStackTrace();
		} finally {

			try {
				//Close all of the file objects
				if(fileReader != null) fileReader.close();
				if(bufferedReader != null) bufferedReader.close();
				if(fileWriter != null) fileWriter.close();
				if(printWriter != null) printWriter.close();
			} catch(IOException ioe) {
				ioe.printStackTrace();
			}

		}

	}

	//This method takes a file, decrypts it, and writes it to a new file called "Decrypted_Text"
	public void decrypt(String filePath, String destination, String password) {

		//Declare a variable to keep track of how far through the password it is
		int passwordIndex = 0;

		//Declare a variable to store the length of the password, so the program knows when to loop back to the beginning again
		int passwordLastIndex = password.length() - 1;

		//Declare file reader and buffered reader to read in the text file to decrypt
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		//Declare file writer and print writer to write to the new file
		FileWriter fileWriter = null;
		PrintWriter printWriter = null;

		try {

			//These are assigned to the file to read in, so that it can be decrypted
			fileReader = new FileReader(filePath);
			bufferedReader = new BufferedReader(fileReader);

			//These are assigned to the file to write to, so that the decrypted text can be written to this file
			fileWriter = new FileWriter(destination);
			printWriter = new PrintWriter(fileWriter, false);

			//These variables are used to be able to pick the text apart and decrypt it
			String currentLine;
			char currentLetter;

			//This is a variable to store the current line to write to the new file
			String lineToWrite = "";

			while((currentLine = bufferedReader.readLine()) != null) {

				for(int i = 0; i < currentLine.length(); i++) {

					currentLetter = currentLine.charAt(i);

					if(Character.isLetter(currentLetter)) {
						if(Character.isUpperCase(currentLetter)) {

							//Makes the appropriate adjustment to the letter, then checks if it's out of bounds with ASCII alphabetic characters and corrects it accordingly
							char charToAdd = (char) (((int) currentLetter - (int) Character.toUpperCase(password.charAt(passwordIndex))) + FIRST_LETTER_ASCII_UPPER);
							if((int) charToAdd < FIRST_LETTER_ASCII_UPPER) charToAdd = (char) ((int) charToAdd + LETTERS_IN_ALPHABET);
							if((int) charToAdd >= FIRST_LETTER_ASCII_UPPER + LETTERS_IN_ALPHABET) charToAdd = (char) ((int) charToAdd - LETTERS_IN_ALPHABET);

							lineToWrite = lineToWrite + String.valueOf(charToAdd);
						} else {

							//Makes the appropriate adjustment to the letter, then checks if it's out of bounds with ASCII alphabetic characters and corrects it accordingly
							char charToAdd = (char) (((int) currentLetter - (int) Character.toLowerCase(password.charAt(passwordIndex))) + FIRST_LETTER_ASCII_LOWER);
							if((int) charToAdd < FIRST_LETTER_ASCII_LOWER) charToAdd = (char) ((int) charToAdd + LETTERS_IN_ALPHABET);
							if((int) charToAdd >= FIRST_LETTER_ASCII_LOWER + LETTERS_IN_ALPHABET) charToAdd = (char) ((int) charToAdd - LETTERS_IN_ALPHABET);

							lineToWrite = lineToWrite + String.valueOf(charToAdd);


						}
					} else {
						lineToWrite = lineToWrite + currentLetter;
					}

					//Cycles through the password letters
					if(passwordIndex == passwordLastIndex) {
						passwordIndex = 0;
					} else {
						passwordIndex++;
					}

				}

				//Writes the line to the file, then resets the line to write for the next iteration
				printWriter.println(lineToWrite);
				lineToWrite = "";

			}

		} catch(IOException ioe) {
			ioe.printStackTrace();
		} finally {

			try {
				//Close all of the file objects
				if(fileReader != null) fileReader.close();
				if(bufferedReader != null) bufferedReader.close();
				if(fileWriter != null) fileWriter.close();
				if(printWriter != null) printWriter.close();
			} catch(IOException ioe) {
				ioe.printStackTrace();
			}

		}

	}

	public List<LetterDetails> getCommonFrequencies(String filePath) {

		List<LetterDetails> details = new ArrayList<>();

		//Initialise details list to store the frequency of each character
		for(int i = 0; i < LETTERS_IN_ALPHABET; i++) {

			details.add(new LetterDetails((char) (FIRST_LETTER_ASCII_LOWER + i), 0));

		}

		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		try {

			//Initialise the file reader and buffered reader to read in the lines from the text file
			fileReader = new FileReader(filePath);
			bufferedReader = new BufferedReader(fileReader);

			//Make a current line variable so that each line can be read from the file
			String currentLine;

			//Make a current letter variable so that each letter can be read from the current line
			char currentLetter;

			//Loop through the file reading it line by line until the end of the file
			while((currentLine = bufferedReader.readLine()) != null) {

				//Loop through the letters of each line and tally the letters in the details list
				for (int i = 0; i < currentLine.length(); i++) {

					currentLetter = currentLine.charAt(i);

					//Increments frequencies of appropriate characters, the if statement checks for capital letters so that they can be accounted for too
					if (Character.isLetter(currentLetter)) {
						if ((int) currentLetter >= FIRST_LETTER_ASCII_LOWER && ((int) currentLetter < FIRST_LETTER_ASCII_LOWER + LETTERS_IN_ALPHABET)) {
							details.get((int) currentLetter - FIRST_LETTER_ASCII_LOWER).incrementFrequency();
						} else if((int) currentLetter >= FIRST_LETTER_ASCII_UPPER && ((int) currentLetter < FIRST_LETTER_ASCII_UPPER + LETTERS_IN_ALPHABET)){
							details.get((int) currentLetter - FIRST_LETTER_ASCII_UPPER).incrementFrequency();
						}
					}

				}

			}

		} catch(IOException ioe) {

			ioe.printStackTrace();

		}
		finally {

			try {
				//Close the file readers
				if(bufferedReader != null) bufferedReader.close();
				if(fileReader != null) fileReader.close();
			}
			catch (IOException ex) {

				ex.printStackTrace();

			}

		}

		return details;

	}

	public String findPassword(String filePath) {

		int length;
		String password = "";
		char letter;

		if((length = getCorrectLength(filePath)) == -1) {
			System.out.println("Correct length not found");
		} else {

			//Get the frequencies of the english text to compare with the encrypted frequencies
			List<LetterDetails> commonFrequencies = getCommonFrequencies(ENGLISH_TEXT_FILE_PATH);

			List<Integer> shifts = calculateShifts(commonFrequencies, filePath, length);

			for(int i = 0; i < shifts.size(); i++) {
				System.out.println("Shifts: " + shifts.get(i));
			}

			//This calculates each letters based on the shifts of each set of frequencies then uses them to find the password
			for(int i = 0; i < shifts.size(); i++) {
				letter = shifts.get(i) + FIRST_LETTER_ASCII_LOWER < FIRST_LETTER_ASCII_LOWER ?
						(char) (shifts.get(i) + FIRST_LETTER_ASCII_LOWER + LETTERS_IN_ALPHABET) :
						(char) (shifts.get(i) + FIRST_LETTER_ASCII_LOWER);
				password = password + letter;
			}

			System.out.println("The password is: " + password);

		}

		return password;

	}

	public List<Integer> calculateShifts(List<LetterDetails> commonFrequencies, String filePath, int length) {

		//Put all frequencies into a list of list of letter details
		List<List<LetterDetails>> frequencies = new ArrayList<>();

		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		String text = getTextFromFile(filePath);

		List<String> subtexts = splitText(text, length);

		for(int i = 0; i < length; i++) {
			frequencies.add(getFrequencies(subtexts.get(i)));
		}

		List<Integer> mostCommonShifts = new ArrayList<>();

		for(int i = 0; i < length; i++) {
			mostCommonShifts.add(0);
		}

		int mostCommonShiftCount = 0;
		List<List<Integer>> shifts = new ArrayList<>();

		for(int i = 0; i < length; i++) {
			shifts.add(new ArrayList<>());
			for(int j = 0; j < LETTERS_IN_ALPHABET * 2; j++) {
				shifts.get(i).add(0);
			}
		}

		for(int i = 0; i < frequencies.size(); i++) {
			Collections.sort(frequencies.get(i));
		}

		Collections.sort(commonFrequencies);

		//Loop through all the subtexts and count the amount of shift between the english text and encrypted subtexts
		//The reason for adding all of the shifts together is for more accurate results, using only 1 subtext would produce a less accurate result
		for(int i = 0; i < frequencies.size(); i++) {

			//Loop through the entirety of the lists of letter details and calculate the shifts for each letter with similar proportions of frequencies
			for(int j = 0; j < LETTERS_IN_ALPHABET; j++) {



				//Get the index by finding the difference between the current letters then adding 26 due to the list accounting for numbers down to negative 26
				//Increment the shift that already exists in that position by 1
				shifts.get(i).set((int) frequencies.get(i).get(j).getLetter() - (int) commonFrequencies.get(j).getLetter() + 26,
						shifts.get(i).get((int) frequencies.get(i).get(j).getLetter() - (int) commonFrequencies.get(j).getLetter() + 26) + 1);

			}

		}

		System.out.println("Frequencies size: " + frequencies.size());

		//This calculates all of the most common shifts so that they can used to find the password
		for(int i = 0; i < shifts.size(); i++) {
			for(int j = 0; j < shifts.get(i).size(); j++) {
				if(mostCommonShiftCount < shifts.get(i).get(j)) {
					mostCommonShiftCount = shifts.get(i).get(j);
					mostCommonShifts.set(i, j - LETTERS_IN_ALPHABET);
				}
			}
			mostCommonShiftCount = 0;
		}

		return mostCommonShifts;

	}

	public int getCorrectLength(String filePath) {

		String text = getTextFromFile(filePath);

		int correctLength = -1;
		int currentLength = 1;

		boolean correctIOC = false;

		//This loop is always true as it assumes that the Vigenere cipher is used, in the case that there is an infinite loop the program can be stopped
		while(true) {

			List<Double> indicesOfCoincidence = indexOfCoincidence(text, currentLength);
			double indexOfCoincidence = 0;

			for(int i = 0; i < indicesOfCoincidence.size(); i++) {

				indexOfCoincidence += indicesOfCoincidence.get(i);

			}

			indexOfCoincidence = indexOfCoincidence / indicesOfCoincidence.size();

			//Only if the correct index of coincidence is returned will the program complete, otherwise it will be in an endless loop showing
			//that the Vigenere cipher is most likely not being used
			if((indexOfCoincidence <= IOC_UPPER_BOUND) && (indexOfCoincidence >= IOC_LOWER_BOUND)) {

				correctLength = indicesOfCoincidence.size();
				break;

			}

			currentLength++;

		}

		return correctLength;

	}

	public String getTextFromFile(String filePath) {

		String text = "";
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		try {

			fileReader = new FileReader(filePath);
			bufferedReader = new BufferedReader(fileReader);

			String currentLine = "";

			while((currentLine = bufferedReader.readLine()) != null) {
				text = text + currentLine;
			}

		} catch(IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if(fileReader != null) fileReader.close();
				if(bufferedReader != null) bufferedReader.close();
			} catch(IOException ioe) {
				ioe.printStackTrace();
			}
		}

		return text;

	}

	public List<Double> indexOfCoincidence(String text, int length) {

		List<String> subtexts = splitText(text, length);

		List<List<LetterDetails>> subtextFrequencies = new ArrayList<>();

		for(int i = 0; i < subtexts.size(); i++) {
			subtextFrequencies.add(getFrequencies(subtexts.get(i)));
		}

		List<Double> indicesOfCoincidence = new ArrayList<>();

		for(int i = 0; i < subtextFrequencies.size(); i++) {

			double indexOfCoincidence = 0;
			int totalFrequency = subtexts.get(i).length();

			for(int j = 0; j < subtextFrequencies.get(i).size(); j++) {

				int frequency = subtextFrequencies.get(i).get(j).getFrequency();

				//Uses the formula to calculate the index of coincidence
				indexOfCoincidence += (double) (frequency * (frequency - 1))/(totalFrequency * (totalFrequency - 1));

			}

			indicesOfCoincidence.add(indexOfCoincidence);

		}

		return indicesOfCoincidence;

	}

	public List<String> splitText(String text, int length) {

		List<String> subtexts = new ArrayList<>();

		for(int i = 0; i < length; i++) {
			subtexts.add("");
		}

		int whichSubtext = 0;

		//Loop through length times splitting into length strings
		for(int i = 0; i < text.length(); i++) {

			subtexts.set(whichSubtext, subtexts.get(whichSubtext) + text.charAt(i));

			//Make sure that the whichSubtext variable points to the correct string in the subtexts list
			if(whichSubtext == (length - 1))
				whichSubtext = 0;
			else
				whichSubtext++;

		}

		return subtexts;

	}

	public List<LetterDetails> getFrequencies(String letters) {

		List<LetterDetails> frequencies = new ArrayList<>();

		for(int i = 0; i < LETTERS_IN_ALPHABET; i++) {
			frequencies.add(new LetterDetails((char) (FIRST_LETTER_ASCII_LOWER + i), 0));
		}

		for(int i = 0; i < letters.length(); i++) {

			//This gets the frequencies of each letter in a piece of text
			if(Character.isLetter(letters.charAt(i)) && (((int) letters.charAt(i) >= FIRST_LETTER_ASCII_LOWER) && ((int) letters.charAt(i) < FIRST_LETTER_ASCII_LOWER + LETTERS_IN_ALPHABET) ||
					(int) letters.charAt(i) >= FIRST_LETTER_ASCII_UPPER && ((int) letters.charAt(i) < FIRST_LETTER_ASCII_UPPER + LETTERS_IN_ALPHABET)))
				frequencies.get(Character.toLowerCase(letters.charAt(i)) % FIRST_LETTER_ASCII_LOWER).incrementFrequency();
			else
				continue;

		}

		return frequencies;

	}

}

