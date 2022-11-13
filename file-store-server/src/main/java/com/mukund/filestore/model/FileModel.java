package com.mukund.filestore.model;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity(name = "files")
@Data
public class FileModel {

	@Id
	private String name;

	private String path;

	private long totalFileSize;

	private long uploadedSize;

	private Date createdAt;

	private FileStatus status;

	private String hash;

	@OneToMany(mappedBy = "file")
	private Set<WordCount> wordCounts;
}
