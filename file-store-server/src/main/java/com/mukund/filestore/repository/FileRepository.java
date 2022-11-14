package com.mukund.filestore.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mukund.filestore.model.FileModel;

@Repository
@Transactional
public interface FileRepository extends JpaRepository<FileModel, String> {

	public List<FileModel> findByName(String name);

	public List<FileModel> findByHash(String hash);

	public List<FileModel> findByNameAndHash(String fileName, String hash);

}
