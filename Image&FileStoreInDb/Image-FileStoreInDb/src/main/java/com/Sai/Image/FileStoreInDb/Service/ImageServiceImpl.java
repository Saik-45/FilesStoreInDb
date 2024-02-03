package com.Sai.Image.FileStoreInDb.Service;

import com.Sai.Image.FileStoreInDb.Entity.FilesUpload;
import com.Sai.Image.FileStoreInDb.Exception.HandleException;
import com.Sai.Image.FileStoreInDb.Repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Long storeFile(MultipartFile file, String filename, String filetype, String description) {
        try {
            FilesUpload imageEntity = new FilesUpload();
            imageEntity.setFile(file.getBytes());
            imageEntity.setFilename(filename);
            imageEntity.setFiletype(filetype);
            imageEntity.setDescription(description);

            FilesUpload savedFile = imageRepository.save(imageEntity);

            return savedFile.getId();
        } catch (IOException e) {
            throw new HandleException("Error Storing File");
        }
    }

    @Override
    public byte[] downloadFileByName(String filename) {
        Optional<FilesUpload> filesUploadOptional = imageRepository.findByFilename(filename);

        if (filesUploadOptional.isPresent()) {
            FilesUpload filesUpload = filesUploadOptional.get();
            return filesUpload.getFile();
        } else {
            throw new HandleException("File not found with filename: " + filename);
        }
    }

    @Override
    public byte[] downloadFileByNameAndDescription(String filename, String description) {
        Optional<FilesUpload> filesUploadOptional = imageRepository.findByFilenameAndDescription(filename, description);

        if (filesUploadOptional.isPresent()) {
            FilesUpload filesUpload = filesUploadOptional.get();
            return filesUpload.getFile();
        } else {
            throw new HandleException("File not found with filename: " + filename + " and description: " + description);
        }
    }


    @Override
    public void deleteByFilename(String filename) {
        // Find the file by filename
        FilesUpload file = imageRepository.findByFilename(filename)
                .orElseThrow(() -> new HandleException("File not found with filename: " + filename));

        // Delete the file from the database
        imageRepository.delete(file);
    }


    @Override
    @Transactional
    public Long updateFile(String currentFilename, String newFilename, String filetype, String description) {
        Optional<FilesUpload> existingFileOptional = imageRepository.findByFilename(currentFilename);

        if (existingFileOptional.isPresent()) {
            FilesUpload existingFile = existingFileOptional.get();

            existingFile.setFilename(newFilename);
            existingFile.setFiletype(filetype);
            existingFile.setDescription(description);

            FilesUpload updatedFile = imageRepository.save(existingFile);

            return updatedFile.getId();
        } else {
            throw new HandleException("File not found with filename : " + currentFilename);
        }
    }

    // Other methods...

    @Override
    @Transactional
    public void updateFileByNameAndDescription(String filename, String description, byte[] updatedFile) {
        try {
            FilesUpload existingFile = imageRepository.findByFilenameAndDescription(filename, description)
                    .orElseThrow(() -> new HandleException("File not found with filename: " + filename + " and description: " + description));

            existingFile.setFile(updatedFile);
            imageRepository.save(existingFile);
        } catch (jakarta.persistence.NonUniqueResultException e) {
            throw new HandleException("Multiple files found with filename : " + filename + " and description: " + description);
        }
    }

    @Override
    public void deleteFileByNameAndDescription(String filename, String description) {
        try {
            imageRepository.deleteByFilenameAndDescription(filename, description);
        } catch (Exception e) {
            throw new HandleException("Error deleting file with filename: " + filename +
                    " and description: " + description);
        }
    }

//    @Override
//    @Transactional
//    public long patchFile(String currentFilename, String newDescription) {
//        // Find the file by current filename
//        Optional<FilesUpload> existingFileOptional = imageRepository.findByFilename(currentFilename);
//
//        if (existingFileOptional.isPresent()) {
//            FilesUpload existingFile = existingFileOptional.get();
//
//            // Update the description if a new description is provided
//            if (newDescription != null) {
//                existingFile.setDescription(newDescription);
//            }
//
//            // Save the updated file to the database
//            imageRepository.save(existingFile);
//        } else {
//            throw new RuntimeException("File not found with filename: " + currentFilename);
//        }
//        return 0;
//    }



//    @Override
//    public List<byte[]> downloadFilesByPartialName(String partialName) {
//        List<byte[]> filesList = imageRepository.findByFilenameContaining(partialName);
//        return filesList.stream()
//                .map(FilesUpload::getFile)
//                .collect(ArrayList::new, List::addAll, List::addAll);
//    }

}
