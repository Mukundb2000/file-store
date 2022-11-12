package com.mukund.filestore.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Word {

	@Id
	private String word;

	@OneToMany(mappedBy = "words")
	private Set<WordCount> wordCounts;
}
