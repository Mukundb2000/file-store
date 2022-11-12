package com.mukund.filestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mukund.filestore.service.FileStoreService;

@RestController
@RequestMapping("/files")
public class FileStoreController {

	@Autowired
	private FileStoreService fileStoreService;

	@GetMapping
	public ResponseEntity<?> getFiles() {

		return ResponseEntity.ok(fileStoreService.getFiles());
	}
}
