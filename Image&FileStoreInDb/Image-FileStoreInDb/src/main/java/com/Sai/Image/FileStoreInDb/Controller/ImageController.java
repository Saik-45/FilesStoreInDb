package com.Sai.Image.FileStoreInDb.Controller;

import com.Sai.Image.FileStoreInDb.Exception.HandleException;
import com.Sai.Image.FileStoreInDb.Service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/file")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping("/home")
    private String home() {
        return "Home Of Application...";
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("filename") String filename,
            @RequestParam("filetype") String filetype,
            @RequestParam("description") String description
    ) {
        Long fileId = imageService.storeFile(file, filename, filetype, description);
        return ResponseEntity.ok("\t\t" + filename + " Uploaded Successfully In Db");
    }

    @GetMapping("/get/{filename}")
    public ResponseEntity<byte[]> downloadFileByName(@PathVariable String filename) {
        try {
            byte[] fileData = imageService.downloadFileByName(filename);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentDispositionFormData("attachment", filename);

            return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
        } catch (HandleException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(("Error: " + e.getMessage()).getBytes());
        }
    }

    @GetMapping("/download/{filename}/{description}")
    public ResponseEntity<byte[]> downloadFileByNameAndDescription(
            @PathVariable String filename,
            @PathVariable String description
    ) {
        try {
            byte[] fileData = imageService.downloadFileByNameAndDescription(filename, description);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentDispositionFormData("attachment", filename);

            return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
        } catch (HandleException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(("Error: " + e.getMessage()).getBytes());
        }
    }

    @DeleteMapping("/delete/{filename}")
    public ResponseEntity<String> deleteByFilename(@PathVariable String filename) {
        try {
            imageService.deleteByFilename(filename);
            return ResponseEntity.ok("File with filename " + filename + " deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting file with filename " + filename + ": " + e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFileByNameAndDescription(
            @RequestParam String filename,
            @RequestParam String description
    ) {
        try {
            imageService.deleteFileByNameAndDescription(filename, description);
            return ResponseEntity.ok("File with filename " + filename + " and description " + description + " deleted successfully.");
        } catch (HandleException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting file: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateFile(
            @RequestParam("currentFilename") String currentFilename,
            @RequestParam("newFilename") String newFilename,
            @RequestParam("filetype") String filetype,
            @RequestParam("description") String description
    ) {
        try {
            Long fileId = imageService.updateFile(currentFilename, newFilename, filetype, description);
            return ResponseEntity.ok("File with filename " + newFilename + " updated successfully ");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating file with filename " + newFilename + ": " + e.getMessage());
        }
    }

    @PutMapping("/update/{filename}/{description}")
                 //  http://localhost:1234/file/update/{filename}/{description}
    public ResponseEntity<String> updateFileByDescription(
            @RequestParam("currentFilename") String currentFilename,
            @RequestParam("newFilename") String newFilename,
            @RequestParam("filetype") String filetype,
            @RequestParam("description") String description
    ) {
        try {
            Long fileId = imageService.updateFile(currentFilename, newFilename, filetype, description);
            return ResponseEntity.ok("File with filename " + newFilename + " and description " + description + " updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating file with filename " + newFilename + " and description " + description + ": " + e.getMessage());
        }
    }



//    @PatchMapping("/update/{currentFilename}")
//    public ResponseEntity<String> patchFile(
//            @PathVariable String currentFilename,
//            @RequestParam(required = false) String newDescription
//    ) {
//        try {
//            imageService.patchFile(currentFilename, newDescription);
//            return ResponseEntity.ok("File with filename " + currentFilename + " patched successfully.");
//        } catch (Exception e) {
//            return ResponseEntity.status(500)
//                    .body("Error patching file with filename " + currentFilename + ": " + e.getMessage());
//        }
//    }

//    @GetMapping("/download/partial/{partialName}")
//    public ResponseEntity<List<byte[]>> downloadFilesByPartialName(@PathVariable String partialName) {
//        List<byte[]> fileDataList = imageService.downloadFilesByPartialName(partialName);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        headers.setContentDispositionFormData("attachment", "partialFiles.zip");
//
//        return new ResponseEntity<>(fileDataList, headers, HttpStatus.OK);
//    }
}
