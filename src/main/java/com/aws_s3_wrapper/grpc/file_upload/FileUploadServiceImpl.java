package com.aws_s3_wrapper.grpc.file_upload;

import com.aws_s3_wrapper.aws_s3_wrapper.S3FileUploadService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.aws_s3_wrapper.aws_s3_wrapper.S3FileUploadService.createFile;

@GrpcService

public class FileUploadServiceImpl extends FileUploadServiceGrpc.FileUploadServiceImplBase {

    private final S3FileUploadService s3FileUploadService;

    public FileUploadServiceImpl(S3FileUploadService s3FileUploadService) {
        this.s3FileUploadService = s3FileUploadService;
    }


    @Override
    public StreamObserver<FileChunk> uploadFile(StreamObserver<UploadStatus> responseObserver) {

        StreamObserver<FileChunk> requestObserver = new StreamObserver<FileChunk>() {
            FileOutputStream fileOutputStream;
            {
                try {
                    // Open or create the file to write the incoming chunks
                    fileOutputStream = new FileOutputStream("output_file");
                } catch (IOException e) {
                    responseObserver.onError(e);
                }
            }

          // Implement the received chunk handling
            @Override
            public void onNext(FileChunk value) {
                // Process each chunk received from the client
                System.out.println("Received chunk with size: " + value.getContent().size());
                try {
                    // Write the chunk to the file
                    fileOutputStream.write(value.getContent().toByteArray());
                } catch (IOException e) {
                    responseObserver.onError(e);
                }

            }

            @Override
            public void onError(Throwable t) {
                // Handle errors
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {

                // All chunks have been received; respond to the client
                UploadStatus status = UploadStatus.newBuilder()
                        .setMessage("File uploaded successfully")
                        .setSuccess(true)
                        .build();
                responseObserver.onNext(status);
                responseObserver.onCompleted();
                File file = new File("output_file");
                try (FileInputStream fis = new FileInputStream("output_file")) {
                    // Process the file as needed
                    System.out.println("File size New: " + fis.available() + " bytes");
                    String fileUrl = s3FileUploadService.uploadFileAsStream("neeraj-ch-bucket-1",fis);
                    // For example, read the content, perform some operations, etc.
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        return requestObserver;
    }

}
