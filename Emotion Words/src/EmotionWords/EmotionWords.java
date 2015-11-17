/* Software designed for use by University of Waterloo finance department research studies.
 * Designed by Colin Woodworth and Hasanain Habib-Nayany.
 * Copyright Colin Woodworth and Hasanain Habib-Nayany 2015.
 */

//notes: NEED TO MAKE SURE TO CHANGE PATH TO DECODE + FILE NAME SO IT SEARCHES IN JAR DIRECTORY
// ALSO NEED TO TEST TO MAKE SURE THIS WORKS THE WAY I THINK IT DOES
//CANT FIGURE OUT WHY RUNNABLE JAR ISNT WORKING

//notes: need to incorporate words that have dashes into counting
package EmotionWords;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class EmotionWords {

	public static void main(String[] args) {

		UserInterface UI = new UserInterface("Count in progress...");

		// store emotion words in memory
		readEmotionWords();

		// store words from conference call file in memory
		storeConferenceCall();

		// create writer to write out results
		BufferedWriter writer = null;

		try {
			File file = new File(System.getProperty("user.dir") + File.separator + "OUTPUT.csv");

			// if the output file already exists delete it first so it can
			// recreated fresh
			if (file.exists())
				file.delete();

			writer = new BufferedWriter(new FileWriter(file));

			// cycle through emotionWords ArrayList searching for each word
			for (int i = 0; i < emotionWords.size(); i++) {

				String word = emotionWords.get(i);
				String wordOriginal = emotionWordsOriginal.get(i);
				int origLength = wordOriginal.length();
				int noSpaceLength = wordOriginal.replaceAll(" ", "").length();
				int numSpaces = origLength - noSpaceLength;

				if (numSpaces == 2) {
					writer.write(wordOriginal + "," + countOccurenceException2(wordOriginal));
					writer.newLine();
					writer.flush();
				} else if (numSpaces == 1) {
					writer.write(wordOriginal + "," + countOccurenceException(wordOriginal));
					writer.newLine();
					writer.flush();
				} else {
					writer.write(wordOriginal + "," + countOccurence(word));
					writer.newLine();
					writer.flush();
				}

			}

		} catch (IOException e) {
			System.out.println("IOException when during BufferedWriter procedures!");
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException e) {
				System.out.println("IOException closing BufferedWriter!");
			}
		}

		UI.setText("Count complete!");

	}

	// variable to store all words in the text file
	public static ArrayList<String> fileContents = new ArrayList<String>();

	// read file that contains the Q1 conference call and store all separate
	// words in memory
	public static void storeConferenceCall() {

		Scanner scannerLine = null;
		Scanner scannerWord = null;

		try {
			scannerLine = new Scanner(
					new File(System.getProperty("user.dir") + File.separator + "Conference Call.txt"));

			while (scannerLine.hasNextLine()) {
				String line = scannerLine.nextLine();
				scannerWord = new Scanner(line);

				while (scannerWord.hasNext()) {
					String word = scannerWord.next().replaceAll("[^a-zA-Z]+", "").toLowerCase();
					fileContents.add(word);
				}
			}

		} catch (FileNotFoundException e) {
			System.out.println("Could not find conference call file!");
		} finally {
			if (scannerLine != null)
				scannerLine.close();
			if (scannerWord != null)
				scannerWord.close();
		}

	}

	// array that will store all emotion words being searched for
	public static ArrayList<String> emotionWords = new ArrayList<String>();

	// array that will store all emotion words being searched for
	public static ArrayList<String> emotionWordsOriginal = new ArrayList<String>();

	// read file that contains all emotion words that need to be searched for
	// and store them in memory for easier searching later
	public static void readEmotionWords() {

		Scanner scanner = null;

		try {
			scanner = new Scanner(new File(System.getProperty("user.dir") + File.separator + "Emotion Words.csv"));
			while (scanner.hasNextLine()) {
				String wordOriginal = scanner.nextLine();
				String word = wordOriginal.replaceAll("[^a-zA-Z]+", "").toLowerCase();
				emotionWords.add(word);
				emotionWordsOriginal.add(wordOriginal);
			}

		} catch (FileNotFoundException e) {
			System.out.println("Could not find emotion words file!");
		} finally {
			if (scanner != null)
				scanner.close();
		}

	}

	// take contents of text file stored in memory and emotion word and count
	// number of occurences of the emotion word in the text file.
	public static int countOccurence(String word) {

		int count = 0;

		for (int i = 0; i < fileContents.size(); i++) {

			if ((fileContents.get(i)).equals(word))
				count++;

		}

		return count;
	}

	// same as countOccurence but will only count if all substrings before
	// and after space match
	public static int countOccurenceException(String word) {

		int count = 0;
		String[] words = word.toLowerCase().split(" ");

		for (int i = 0; i < fileContents.size(); i++) {
			
			if (i+1 >= fileContents.size())
				break;

			if ((fileContents.get(i)).equals(words[0]) && (fileContents.get(i + 1)).equals(words[1]))
				count++;

		}

		return count;

	}

	public static int countOccurenceException2(String word) {

		int count = 0;
		String[] words = word.toLowerCase().split(" ");

		for (int i = 0; i < fileContents.size(); i++) {
			
			if (i +1 >= fileContents.size() || i+2 >= fileContents.size())
				break;

			if ((fileContents.get(i)).equals(words[0]) && (fileContents.get(i + 1)).equals(words[1])
					&& (fileContents.get(i + 2)).equals(words[2]))
				count++;

		}

		return count;

	}
}
