package com.mukund.filestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mukund.filestore.model.File;

public interface FileRepository extends JpaRepository<File, String> {

}
