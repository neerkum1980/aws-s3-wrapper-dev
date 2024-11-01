package com.aws_s3_wrapper.grpc.file_upload;

import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;

public class FileUploadClientHttp {

    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.get("application/grpc+proto");

    private final OkHttpClient client;

    public FileUploadClientHttp() {
        client = new OkHttpClient.Builder()
                .protocols(Collections.singletonList(Protocol.H2_PRIOR_KNOWLEDGE))
                .build();
    }

    public void run() throws Exception {
        File file = new File("C:\\dev\\aws-s3-wrapper-dev\\README.md");

        // Here we should build binary data from a protobuf object, NOT a markdown file.
        byte[] data = Files.readAllBytes(file.toPath());

        RequestBody requestBody = RequestBody.create(data, MEDIA_TYPE_MARKDOWN);

        Request request = new Request.Builder()
                .url("http://tyk-gateway.localhost:8080/hello.HelloService/")
  //              .url("http://localhost:9090")
                .post(requestBody)
                .addHeader("Content-Type", "application/grpc+proto") // Always gRPC content type
                .addHeader("TE", "trailers")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            System.out.println(response.body().string());
        }
    }

    public static void main(String... args) throws Exception {
        try {
            new FileUploadClientHttp().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}