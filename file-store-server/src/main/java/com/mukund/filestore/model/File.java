package com.mukund.filestore.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity(name = "files")
@Data
public class File {

	@Id
	private String name;

	private String path;

	private long totalFileSize;

	private long uploadedSize;

	private Date uploadedAt;

	private Date lastModifiedAt;
}
