package model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

// represents a list of words that can be used to generate sets
public class WordList {
    private List<String> sortedWords;
    private Random random = new Random();

    // EFFECTS: Reads file at path and uses each line as a word for the list
    //          sorted by word length
    public WordList(Path wordFilePath) throws IOException {
        List<String> words = Files.readAllLines(wordFilePath);
        Collections.shuffle(words);
        words.sort(Comparator.comparingInt(String::length));
        sortedWords = words;
    }

    // REQUIRES: provided word count / maxDifficulty > 10 * count
    //           (in other words, there are sufficiently many words to choose from to create the set)
    // EFFECTS: creates a subset of count words from the total set by:
    //          drawing any word from the bottom difficulty / maxDifficulty portion of words.
    public Set<String> generateWordSet(int count, int difficulty, int maxDifficulty) {
        Set<String> generatedWords = new HashSet<>();
        int wordMaxIndex = (sortedWords.size() - 1) / maxDifficulty;
        wordMaxIndex *= difficulty;

        // there should be more safeguards here against an infinite recursion, but whatever for now...
        if (count > sortedWords.size()) {
            count = sortedWords.size();
        }
        while (generatedWords.size() < count) {
            int randomIndex = random.nextInt(wordMaxIndex);
            String word = sortedWords.get(randomIndex);

            generatedWords.add(word);
        }

        return generatedWords;
    }
}
