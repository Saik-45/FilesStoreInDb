package com.Sai.Image.FileStoreInDb.Repository;

import com.Sai.Image.FileStoreInDb.Entity.FilesUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<FilesUpload, Long> {

    Optional<FilesUpload> findByFilename(String filename);

    Optional<FilesUpload> findByFilenameAndDescription(String filename, String description);


    @Modifying
    @Query("UPDATE FilesUpload f SET f.file = ?1 WHERE f.id = ?2")
    Long updateFile( String filename, String filetype, String description);

    @Modifying
    @Query("UPDATE FilesUpload f SET f.file = ?3 WHERE f.filename = ?1 AND f.description = ?2")
    void updateByFilenameAndDescription(String filename, String description, byte[] updatedFile);

    void deleteByFilenameAndDescription(String filename, String description);

//    long patchFile(String currentFilename, String newFilename);

    void deleteByFilename(String filename);

    // List<byte[]> findByFilenameContaining(String partialName);
}
