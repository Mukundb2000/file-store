package com.mukund.filestore.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "files")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileModel {

	@Id
	private String name;

	private String path;

	private long totalFileSize;

	private long uploadedSize;

	private Date createdAt;

	private FileStatus status;

	private String hash;

}
