package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private HashSet<String> wordSet = new HashSet<String>();
    private ArrayList<String> wordList = new ArrayList<String>();
    private HashMap<String,ArrayList> lettersToWord = new HashMap<String,ArrayList>();
    private  HashMap<Integer, ArrayList> sizetoWords = new HashMap<>();
    private int wordLength= DEFAULT_WORD_LENGTH;

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordSet.add(word);
            wordList.add(word);
            if(lettersToWord.containsKey(sortLetters(word)))
                lettersToWord.get(sortLetters(word)).add(word);  // add the word to the list if the sorted key is already in hash map
            else {
                ArrayList<String> anagrams = new ArrayList<String>();
                anagrams.add(word);
                lettersToWord.put(sortLetters(word), anagrams);
            }
            if(sizetoWords.containsKey(word.length()))
                sizetoWords.get(word.length()).add(word);  // add the word to the list if the sorted key is already in hash map
            else {
                ArrayList<String> list = new ArrayList<String>();
                list.add(word);
                sizetoWords.put(word.length(), list);
            }
           // Log.i("WORD :",word);
        }
//        Log.i("words size : ",wordList.size()+"");
//        Log.i("map size : ",lettersToWord.size()+"");
//        for (String key : lettersToWord.keySet()) {
//            System.out.println("KEY" + key + " ");
//            ArrayList<String> anagrams = lettersToWord.get(key);
//            for(String temp : anagrams)
//                System.out.println(temp);
//            System.out.println("\n\n");
//        }
    }

    public String sortLetters(String word){
            char[] wordToSort = word.toCharArray();
            Arrays.sort(wordToSort);
            return(new String(wordToSort));
    }
    public boolean isGoodWord(String word, String base) {
        if(wordSet.contains(word) && !word.contains(base)){
            return true;
        }
        return false;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        char ch = 'a';
        ArrayList<String> anagrams;
        for(int i =0;i<26 ;i++) {
            if(lettersToWord.containsKey(sortLetters(word+(ch)))){
                anagrams = lettersToWord.get(sortLetters(word+ch));
                for (String temp : anagrams)
                    if(!temp.contains(word))
                    result.add(temp);
            }
            ch++;
        }

        return result;

    }

    public ArrayList<String> getAnagramsWithTwoMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        char ch = 'a';
        char c;
        ArrayList<String> anagrams;
        for(int i =0;i<26 ;i++) {
            c = 'a';
            for(int j =0;j<26 ;j++) {
                if (lettersToWord.containsKey(sortLetters(word + ch + c))) {
                    anagrams = lettersToWord.get(sortLetters(word + ch + c));
                    for (String temp : anagrams)
                        if (!temp.contains(word))
                            result.add(temp);
                }
                c++;
            }
            ch++;
        }
        return result;

    }

    public String pickGoodStarterWord() {
//        return "pot";

        wordLength = random.nextInt(MAX_WORD_LENGTH-DEFAULT_WORD_LENGTH)+DEFAULT_WORD_LENGTH;
        ArrayList<String> wordSizeList = sizetoWords.get(wordLength);
        int i = random.nextInt(wordSizeList.size());
        while(getAnagramsWithTwoMoreLetter(wordSizeList.get(i)).size() < MIN_NUM_ANAGRAMS)
            i = random.nextInt(sizetoWords.size());
//        if(wordLength==MAX_WORD_LENGTH)
//            wordLength = DEFAULT_WORD_LENGTH;
        return wordSizeList.get(i);
//
//        int i = random.nextInt(wordList.size());
//        while(getAnagramsWithOneMoreLetter(wordList.get(i)).size() < MIN_NUM_ANAGRAMS)
//                i = random.nextInt(wordList.size());
//        return wordList.get(i);
    }
}
