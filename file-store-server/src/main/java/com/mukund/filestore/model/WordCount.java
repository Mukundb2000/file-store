package com.mukund.filestore.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class WordCount {

	@Id
	private long id;

	@ManyToOne
	private Word words;

	@ManyToOne
	private FileModel file;

	private int count;
}
