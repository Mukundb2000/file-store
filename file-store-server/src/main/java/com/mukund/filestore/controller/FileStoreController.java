package com.mukund.filestore.controller;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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

	@PostMapping
	public ResponseEntity<?> storeChunk(InputStream dataStream, @RequestHeader(name = "x-file-name") String fileName,
			@RequestHeader(name = "x-start-byte", defaultValue = "0") long startByte,
			@RequestHeader(name = "x-file-size") long fileSize) {

		return ResponseEntity.ok(fileStoreService.storeChunk(dataStream, fileSize, fileName, startByte));
	}

	@PutMapping
	public ResponseEntity<?> updateFile(InputStream dataStream, @RequestHeader(name = "x-file-name") String fileName,
			@RequestHeader(name = "x-file-size") long fileSize) {

		return ResponseEntity.ok(fileStoreService.updateFile(dataStream, fileSize, fileName));
	}
}
