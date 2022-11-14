package com.mukund.filestore.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.mukund.filestore.model.Word;
import com.mukund.filestore.repository.WordRepository;

@Service
public class WordsService {

	@Autowired
	private WordRepository wordRepository;

	public Map<String, Object> getWordCount() {

		Map<String, Object> response = new HashMap<>();

		long count = wordRepository.countWords();

		response.put("count", count);

		return response;
	}

	public List<Word> getWordFrequencies(int limit, String order) {

		if (limit < 0) {

			return null; // Invalid limit value
		}

		switch (order) {

		case "asc":
			return wordRepository.findByCountGreaterThanOrderByCountAsc(0, PageRequest.of(0, limit));
		case "dsc":
			return wordRepository.findByCountGreaterThanOrderByCountDesc(0, PageRequest.of(0, limit));
		default:
			return null; // Invalid order value
		}

	}
}
