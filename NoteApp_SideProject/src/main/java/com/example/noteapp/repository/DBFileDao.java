package com.example.noteapp.repository;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.noteapp.entity.DBFile;

@Repository
public interface DBFileDao extends JpaRepository<DBFile, Integer> {

	@Query(value="SELECT * FROM files f WHERE f.username = :username", nativeQuery=true)
	List<DBFile> findUserFiles (@Param("username")String username);	
	
	@Query(value="SELECT * FROM files f WHERE f.filename LIKE %:criteria% AND f.restricted = 'false'", nativeQuery=true)
	List<DBFile> searchFilesCriteria (@Param("criteria") String criteria);
	
	@Query(value="DELETE FROM files f WHERE f.file_id = :fileId AND f.username = :username", nativeQuery=true)
	@Modifying
	@Transactional
	void deleteFile(@Param("fileId")Integer fileId, @Param("username") String username);
	
	@Query(value="SELECT * FROM files f WHERE f.filename = :filename", nativeQuery=true)
	DBFile findByFileName(@Param("filename") String filename);
}
