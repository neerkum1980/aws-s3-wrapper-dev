package com.aws_s3_wrapper.aws_s3_wrapper;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
public class S3FileUploadService {
    private  AmazonS3 amazonS3;
    public S3FileUploadService(@Value("${aws.s3.accessKey}") String accessKey,
                     @Value("${aws.s3.secretKey}") String secretKey,
                     @Value("${aws.s3.region}") String region) {
        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .withRegion(region)
                .build();
    }

    public String uploadFile(String bucketName, String filePath) throws IOException {
        File file = new File(filePath);
        String fileName = generateFileName(file.getName());
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.length());
        metadata.setContentType("application/octet-stream");
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, new FileInputStream(file), metadata);
        amazonS3.putObject(putObjectRequest);
        return amazonS3.getUrl(bucketName, fileName).toString();
    }

    public String uploadFileWithContent(String bucketName, MultipartFile file) throws IOException {

        String fileName = file.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType("application/octet-stream");
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName,  file.getInputStream(), metadata);
        amazonS3.putObject(putObjectRequest);
        return amazonS3.getUrl(bucketName, fileName).toString();
    }
    public String uploadFileAsStream(String bucketName, FileInputStream file) throws IOException {

        String fileName = UUID.randomUUID() + ".txt";
        FileOutputStream outputStream = null;
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("application/text");
        metadata.setContentLength(file.available());
        createFile(file,"outfile");
        File new_file = new File("outfile");
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,fileName,new_file);
        amazonS3.putObject(putObjectRequest);
        return amazonS3.getUrl(bucketName, fileName).toString();
    }

    private String generateFileName(String originalFileName) {
        String extension = StringUtils.getFilenameExtension(originalFileName);
        String generatedFileName = UUID.randomUUID().toString();
        if (extension != null && !extension.isEmpty()) {
            generatedFileName += "." + extension;
        }
        return generatedFileName;
    }

    public static void createFile(FileInputStream fis,  String outputFilePath) throws IOException {
        File outputFile = new File(outputFilePath);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8));
             OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_8)) {

            char[] buffer = new char[1024]; // Use a char buffer for text
            int length;

            while ((length = reader.read(buffer)) > 0) {
                writer.write(buffer, 0, length);
            }
        }
    }}
