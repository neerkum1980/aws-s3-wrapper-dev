package com.aws_s3_wrapper.aws_s3_wrapper;

import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.UUID;


@Service
public class S3Service {
    private static final Logger logger = Logger.getLogger(S3Controller.class.toString());
    private final S3Client s3Client;
    public S3Service(@Value("${aws.s3.region}") String region,
                     @Value("${aws.s3.accessKey}") String accessKey,
                     @Value("${aws.s3.secretKey}") String secretKey) {

        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

    public String getObjectContent(String bucketName, String key) {
        System.out.println("I am going to read the document from bucket");
        GetObjectResponse response = s3Client.getObject(GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build()).response();
        logger.log(Level.INFO,response.toString());
        return response.toString();
    }

    public List<String> listObjects(String bucketName) {
        logger.log(Level.INFO,"BucketName",bucketName);
        ListObjectsRequest listObjectsRequest = ListObjectsRequest.builder()
                .bucket(bucketName)
                .build();
        ListObjectsResponse listObjectsResponse = s3Client.listObjects(listObjectsRequest);
        List<String> objectKeys = listObjectsResponse.contents().stream()
                .map(S3Object::key)
                .collect(Collectors.toList());
        return objectKeys;
    }
}


