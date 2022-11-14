package com.mukund.filestore.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mukund.filestore.model.Word;

@Repository
@Transactional
public interface WordRepository extends JpaRepository<Word, String> {

	@Modifying
	@Query(value = "INSERT INTO  word VALUES (?1, ?2) ON DUPLICATE KEY UPDATE count = count + ?2", nativeQuery = true)
	public void incrementWord(String word, int count);

	@Modifying
	@Query(value = "INSERT INTO  word VALUES (?1, ?2) ON DUPLICATE KEY UPDATE count = count - ?2", nativeQuery = true)
	public void decrementWord(String word, int count);

	@Query(value = "SELECT COUNT(*) FROM word WHERE count>0", nativeQuery = true)
	public long countWords();

	public List<Word> findByCountGreaterThanOrderByCountDesc(int count, Pageable page);

	public List<Word> findByCountGreaterThanOrderByCountAsc(int count, Pageable page);
}
