package com.mukund.filestore.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mukund.filestore.model.FileModel;
import com.mukund.filestore.model.FileStatus;
import com.mukund.filestore.repository.FileRepository;
import com.mukund.filestore.repository.WordRepository;

@Service
public class FileStoreService {

	@Autowired
	private FileRepository fileRepository;

	@Autowired
	private WordRepository wordRepository;

	private String BASE_PATH = "/files";

	private Path root = Paths.get(BASE_PATH);

	public List<FileModel> getFiles(String hash, String fileName) {

		List<FileModel> files;

		if (StringUtils.hasText(hash) && StringUtils.hasText(fileName))
			files = fileRepository.findByNameAndHash(fileName, hash);
		else if (StringUtils.hasText(fileName))
			files = fileRepository.findByName(fileName);
		else if (StringUtils.hasText(hash))
			files = fileRepository.findByHash(hash);
		else
			files = fileRepository.findAll();

		return files;
	}

	private void onCompleteFileUpload(FileModel fileModel) {

		try {

			String text = Files.readString(root.resolve(fileModel.getName()));

			TreeMap<String, Integer> freq = new TreeMap<>();

			Scanner sc = new Scanner(text);

			while (sc.hasNext()) {

				String word = sc.next();

				if (freq.containsKey(word)) {

					freq.computeIfPresent(word, (w, c) -> Integer.valueOf(c.intValue() + 1));

				} else {

					freq.computeIfAbsent(word, (w) -> Integer.valueOf(1));
				}
			}

			fileRepository.save(fileModel);

			freq.forEach((k, v) -> {

				wordRepository.incrementWord(k, v);
			});

			sc.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void onFileDelete(FileModel fileModel) {

		try {

			String text = Files.readString(root.resolve(fileModel.getName()));

			TreeMap<String, Integer> freq = new TreeMap<>();

			Scanner sc = new Scanner(text);

			while (sc.hasNext()) {

				String word = sc.next();

				if (freq.containsKey(word)) {

					freq.computeIfPresent(word, (w, c) -> Integer.valueOf(c.intValue() + 1));

				} else {

					freq.computeIfAbsent(word, (w) -> Integer.valueOf(1));
				}
			}

			fileRepository.save(fileModel);

			freq.forEach((k, v) -> {

				wordRepository.decrementWord(k, v);
			});

			sc.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public FileModel storeChunk(InputStream data, long fileSize, String fileName, long startByte, String hash) {

		Optional<FileModel> fileOptional = fileRepository.findById(fileName);

		List<FileModel> sameContentFileOptional = fileRepository.findByHash(hash);

		if (sameContentFileOptional.size() > 0) {

			FileModel fileModel = sameContentFileOptional.get(0);

			if (!fileOptional.isPresent()) {
				return null; // Already another file is present with same content
			}
		}

		if (fileOptional.isPresent()) {

			FileModel fileModel = fileOptional.get();

			if (!fileModel.getHash().equals(hash)) {

				return null; // Invalid hash
			}

			long uploadedSize = fileModel.getUploadedSize();

			if (fileSize != fileModel.getTotalFileSize()) {

				return null; // Invalid fileSize
			}

			try {

				byte[] bytes = data.readAllBytes();

				long updatedSize = uploadedSize + bytes.length;

				if (updatedSize > fileSize) {

					return null; // last chunk size exceeds fileSize
				}

				if (startByte == 0) {

					if (fileModel.getUploadedSize() > 0) {

						return null; // Invalid chunk start
					}

					Files.write(root.resolve(fileName), bytes, StandardOpenOption.CREATE);

				} else {

					if (uploadedSize != startByte) {

						return null; // Invalid chunk start
					}

					Files.write(root.resolve(fileName), bytes, StandardOpenOption.APPEND);

				}

				fileModel.setUploadedSize(updatedSize);

				if (updatedSize == fileSize) {

					fileModel.setStatus(FileStatus.COMPLETE);
					onCompleteFileUpload(fileModel);
				}

				fileRepository.save(fileModel);

			} catch (IOException e) {

				e.printStackTrace();
			}

			return fileModel;

		} else {

			FileModel fileModel = new FileModel();

			fileModel.setCreatedAt(new java.sql.Date((new Date()).getTime()));
			fileModel.setName(fileName);
			fileModel.setPath(root.resolve(fileName).toString());
			fileModel.setTotalFileSize(fileSize);
			fileModel.setStatus(FileStatus.INCOMPLETE);
			fileModel.setHash(hash);

			try {

				byte[] bytes = data.readAllBytes();

				if (startByte == 0) {

					Path newFile = root.resolve(fileName);

					Files.createDirectories(newFile.getParent());
					Files.write(newFile, bytes);

				} else {

					return null; // Invalid start byte
				}

				long updatedSize = bytes.length;

				if (updatedSize == fileSize) {

					fileModel.setStatus(FileStatus.COMPLETE);
					onCompleteFileUpload(fileModel);
				}

				fileModel.setUploadedSize(updatedSize);

			} catch (IOException e) {

				e.printStackTrace();
			}

			fileRepository.save(fileModel);

			return fileModel;
		}
	}

	public FileModel updateFile(InputStream data, long fileSize, String fileName, String hash) {

		Optional<FileModel> fileOptional = fileRepository.findById(fileName);

		FileModel fileModel;

		if (fileOptional.isPresent()) {

			fileModel = fileOptional.get();

		} else {

			fileModel = new FileModel();
		}

		fileModel.setCreatedAt(new java.sql.Date((new Date()).getTime()));
		fileModel.setName(fileName);
		fileModel.setPath(root.resolve(fileName).toString());
		fileModel.setTotalFileSize(fileSize);
		fileModel.setStatus(FileStatus.INCOMPLETE);
		fileModel.setHash(hash);

		try {

			byte[] bytes = data.readAllBytes();

			long uploadedSize = bytes.length;

			if (uploadedSize > fileSize) {

				return null; // last chunk size exceeds fileSize
			}

			Path newFile = root.resolve(fileName);

			Files.createDirectories(newFile.getParent());
			Files.write(newFile, bytes);

			fileModel.setUploadedSize(uploadedSize);

			if (uploadedSize == fileSize) {

				fileModel.setStatus(FileStatus.COMPLETE);
				onCompleteFileUpload(fileModel);
			}

		} catch (IOException e) {

			e.printStackTrace();
		}

		fileRepository.save(fileModel);

		return fileModel;
	}

	public Map<String, String> deleteFile(String fileName) {

		Optional<FileModel> fileOptional = fileRepository.findById(fileName);

		FileModel fileModel;

		if (fileOptional.isPresent()) {

			fileModel = fileOptional.get();

			onFileDelete(fileModel);

			try {

				Files.delete(root.resolve(fileName));

			} catch (IOException e) {

				e.printStackTrace();
			}

			fileRepository.deleteById(fileName);

			Map<String, String> response = new HashMap<>();

			response.put("message", "deleted file '" + fileName + "' successfully");

			return response;

		} else {

			return null; // Invalid fileName
		}
	}
}
