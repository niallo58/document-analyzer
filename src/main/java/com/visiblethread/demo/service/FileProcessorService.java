package com.visiblethread.demo.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.apache.commons.lang3.mutable.MutableInt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.visiblethread.demo.exception.FileStorageException;

@Service
public class FileProcessorService {
	
	private final List<String> stopWords = Arrays.asList("The", "Me", "You", "I", "Of", "And", "A", "We");

	public Map<String, MutableInt> getMostFrequentTermsInFile(MultipartFile file, int noOfTermsToReturn) {
		Map<String, MutableInt> mostFrequentWordsList = new LinkedHashMap<>();
		
		try (InputStream inputStream = file.getInputStream(); 
				Scanner sc = new Scanner(inputStream, "UTF-8")) {
			
			Map<String, MutableInt> wordFrequencyList = getWorksFrequencyList(sc);
		    mostFrequentWordsList = getMostFrequentWords(noOfTermsToReturn, wordFrequencyList);
		    
		    if (sc.ioException() != null) {
		    	throw new FileStorageException("Error processing data from file");
		    }
		} catch (IOException e) {
			throw new FileStorageException("Error processing data from file");
		}
		
		return mostFrequentWordsList;
	}

	private Map<String, MutableInt> getMostFrequentWords(int noOfTermsToReturn, 
			Map<String, MutableInt> wordFrequencyList) {
		Map<String, MutableInt> mostFrequentWordsList = new LinkedHashMap<>();
		while(mostFrequentWordsList.size() < noOfTermsToReturn && !wordFrequencyList.isEmpty()) {
		    String mostFrequentWord = getMostFrequentWord(wordFrequencyList);
		    mostFrequentWordsList.put(mostFrequentWord, wordFrequencyList.get(mostFrequentWord));
		    wordFrequencyList.remove(mostFrequentWord);
		}
		return mostFrequentWordsList;
	}
	
	private Map<String, MutableInt> getWorksFrequencyList(Scanner sc) {
		Map<String, MutableInt> wordFrequencyList = new HashMap<>();
		while (sc.hasNextLine()) {
	        String line = sc.nextLine();
			StringTokenizer tokens = new StringTokenizer(line);
			while(tokens.hasMoreTokens()) {
				String word = tokens.nextToken().toLowerCase();
				if(containsCaseInsensitive(word, stopWords)) {
					continue;
				}
				MutableInt count = wordFrequencyList.get(word);
				if (count == null) {
					wordFrequencyList.put(word, new MutableInt(1));
				}
				else {
				    count.increment();
				}
			}
	    }
		return wordFrequencyList;
	}
	
	private boolean containsCaseInsensitive(String s, List<String> l) {
        return l.stream().anyMatch(x -> x.equalsIgnoreCase(s));
    }
	
	private <K, V extends Comparable<V>> K getMostFrequentWord(Map<K, V> map) {
	    Optional<Entry<K, V>> maxEntry = map.entrySet()
	        .stream()
	        .max((Entry<K, V> e1, Entry<K, V> e2) -> e1.getValue()
	            .compareTo(e2.getValue())
	        );
	    
	    return maxEntry.get().getKey();
	}
	
}
