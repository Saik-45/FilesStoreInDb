package com.Sai.Image.FileStoreInDb.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ImageService {

    Long storeFile(MultipartFile file, String filename, String filetype, String description);

    byte[] downloadFileByName(String filename);

    byte[] downloadFileByNameAndDescription(String filename, String description);

    void deleteByFilename(String filename); // New method for deletion by filename

    Long updateFile(String currentFilename, String newFilename, String filetype,String description);

    @Transactional
    void updateFileByNameAndDescription(String filename, String description, byte[] updatedFile);

    void deleteFileByNameAndDescription(String filename, String description);

//    long patchFile(String currentFilename, String newDescription);


    // List<byte[]> downloadFilesByPartialName(String partialName);
}
