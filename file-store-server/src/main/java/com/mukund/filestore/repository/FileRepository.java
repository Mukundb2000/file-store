package com.mukund.filestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mukund.filestore.model.FileModel;

public interface FileRepository extends JpaRepository<FileModel, String> {

}
