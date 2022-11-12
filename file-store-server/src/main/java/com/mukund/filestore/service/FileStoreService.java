package com.mukund.filestore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mukund.filestore.model.File;
import com.mukund.filestore.repository.FileRepository;

@Service
public class FileStoreService {

	@Autowired
	private FileRepository fileRepository;

	public List<File> getFiles() {

		List<File> files = fileRepository.findAll();

		return files;
	}
}
