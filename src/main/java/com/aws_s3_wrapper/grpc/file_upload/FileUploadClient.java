package com.aws_s3_wrapper.grpc.file_upload;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class FileUploadClient {
    private final FileUploadServiceGrpc.FileUploadServiceStub asyncStub;
    private final CountDownLatch latch;

    public FileUploadClient(ManagedChannel channel) {
        asyncStub = FileUploadServiceGrpc.newStub(channel);
        latch = new CountDownLatch(1);
    }

    public void uploadFile(String filePath) throws IOException {
        System.out.println("Starting file upload...");

        StreamObserver<UploadStatus> responseObserver = new StreamObserver<>() {

            @Override
            public void onNext(UploadStatus value) {
                // handle onNext
            }

            @Override
            public void onError(Throwable t) {
                // Handle error event and print stacktrace
                t.printStackTrace();
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                // Handle onCompleted event
                latch.countDown();
            }
        };

        StreamObserver<FileChunk> requestObserver = asyncStub.uploadFile(responseObserver);

        try (FileInputStream fis = new FileInputStream(new File(filePath))) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                FileChunk chunk = FileChunk.newBuilder()
                        .setContent(com.google.protobuf.ByteString.copyFrom(buffer, 0, bytesRead))
                        .build();
                requestObserver.onNext(chunk);
                System.out.println("Sent a chunk of size: " + bytesRead);
            }
            requestObserver.onCompleted();
            System.out.println("Finished sending file chunks.");

            // Wait until the server has completed or timeout ocurred
            if (!latch.await(1, TimeUnit.MINUTES)) {
                System.err.println("File upload took too long");
            }
        } catch (Exception e) {
            requestObserver.onError(e);
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        String target = "localhost:9091"; // Your server address and port

        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                .usePlaintext() // Use plaintext communication
                .build();

        FileUploadClient client = new FileUploadClient(channel);
        String filePath = "C:\\dev\\aws-s3-wrapper-dev\\README.md"; // Your file path

        try {
            client.uploadFile(filePath);
        } finally {
            // Handle channel shutdown
            channel.shutdown();
            try {
                if (!channel.awaitTermination(5, TimeUnit.SECONDS)) {
                    channel.shutdownNow();
                }
            } catch (InterruptedException e) {
                channel.shutdownNow();
            }
        }
    }
}