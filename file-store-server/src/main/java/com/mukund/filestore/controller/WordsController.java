package com.mukund.filestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mukund.filestore.service.WordsService;

@RestController
@RequestMapping("/words")
public class WordsController {

	@Autowired
	private WordsService wordsService;

	@GetMapping("count")
	public ResponseEntity<?> getWordCount() {

		return ResponseEntity.ok(wordsService.getWordCount());
	}

	@GetMapping("frequency")
	public ResponseEntity<?> getWordFrequencies(@RequestParam(name = "limit", defaultValue = "10") int limit,
			@RequestParam(name = "order", defaultValue = "dsc") String order) {

		return ResponseEntity.ok(wordsService.getWordFrequencies(limit, order));
	}
}
