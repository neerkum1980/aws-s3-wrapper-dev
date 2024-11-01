package com.aws_s3_wrapper.aws_s3_wrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/s3")
public class S3Controller {
    private static final Logger logger = Logger.getLogger(S3Controller.class.toString());
    private final S3Service s3Service;
    private final S3FileUploadService s3FileUploadService;


    public S3Controller(S3Service s3Service,S3FileUploadService s3FileUploadService) {
        this.s3Service = s3Service;
        this.s3FileUploadService = s3FileUploadService;
    }
    @PostMapping("/upload")
    public ResponseEntity<String>  uploadToS3(@RequestParam String bucketName,
                             @RequestParam("filepath") String filepath) {
        logger.log(Level.INFO,"I am in upload Controller file=", bucketName);
        try {
            String fileUrl = s3FileUploadService.uploadFile(bucketName,filepath);
            return ResponseEntity.ok("File uploaded successfully. URL: " + fileUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());
        }
    }
    @PostMapping("/uploadMultipart")
    public ResponseEntity<String>  uploadToS3Multipart(@RequestParam String bucketName,
                                              @RequestParam("file") MultipartFile file,
                                              @RequestParam(name = "filepath", required = false) String filepath) {
        logger.log(Level.INFO, "I am in uploadMultipart Controller file=", bucketName+filepath);
        if (!file.isEmpty()) {
            try {
                // Convert MultipartFile to File
                String fileUrl = s3FileUploadService.uploadFileWithContent(bucketName, file);
                return ResponseEntity.ok("File uploaded successfully. URL: " + fileUrl);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());
            }
        }
        else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File is empty " );
        }
    }
    @GetMapping("/download")
    public String downloadFromS3(@RequestParam String bucketName,
                                 @RequestParam String key) {
        try {
            logger.log(Level.INFO,"I am in /download controller",key);
            return s3Service.getObjectContent(bucketName, key);
        } catch (Exception e) {
            return "Error downloading file: " + e.getMessage();
        }
    }
    @GetMapping("/list-objects")
    public List<String> listObjectsFromS3(@RequestParam String bucketName){
         logger.log(Level.INFO,"I am in list controller");
            return s3Service.listObjects(bucketName);
    }
}
