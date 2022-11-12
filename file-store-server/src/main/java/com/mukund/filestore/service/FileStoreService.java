package com.mukund.filestore.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mukund.filestore.model.FileModel;
import com.mukund.filestore.model.FileStatus;
import com.mukund.filestore.repository.FileRepository;

@Service
public class FileStoreService {

	@Autowired
	private FileRepository fileRepository;

	private String BASE_PATH = "D:/file-store/files";

	private Path root = Paths.get(BASE_PATH);

	public List<FileModel> getFiles() {

		List<FileModel> files = fileRepository.findAll();

		return files;
	}

	public FileModel storeChunk(InputStream data, long fileSize, String fileName, long startByte) {

		Optional<FileModel> fileOptional = fileRepository.findById(fileName);

		if (fileOptional.isPresent()) {

			FileModel fileModel = fileOptional.get();

			long uploadedSize = fileModel.getUploadedSize();

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

				if (updatedSize == fileSize) {

					fileModel.setStatus(FileStatus.COMPLETE);
				}

				fileModel.setUploadedSize(updatedSize);

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

			try {

				byte[] bytes = data.readAllBytes();

				if (startByte == 0) {

					Path newFile = root.resolve(fileName);

					Files.createDirectories(newFile.getParent());
					Files.write(newFile, bytes);

				} else {

					return null; // Invalid start byte
				}

				fileModel.setUploadedSize(bytes.length);

			} catch (IOException e) {

				e.printStackTrace();
			}

			fileRepository.save(fileModel);

			return fileModel;
		}
	}

	public FileModel updateFile(InputStream data, long fileSize, String fileName) {

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
			}

		} catch (IOException e) {

			e.printStackTrace();
		}

		fileRepository.save(fileModel);

		return fileModel;
	}
}
