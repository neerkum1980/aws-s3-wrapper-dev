package com.aws_s3_wrapper.aws_s3_wrapper;
import com.aws_s3_wrapper.grpc.file_upload.FileUploadServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;


@SpringBootApplication
@ComponentScan(basePackages = {"com.aws_s3_wrapper.aws_s3_wrapper", "com.aws_s3_wrapper.grpc.file_upload"})
public class AwsS3WrapperApplication {
	private static S3FileUploadService s3FileUploadService;

	public AwsS3WrapperApplication(S3FileUploadService s3FileUploadService) {
		this.s3FileUploadService = s3FileUploadService;
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		SpringApplication.run(AwsS3WrapperApplication.class, args);
		Server server = ServerBuilder.forPort(9090)// Replace with your service implementation
				.addService(new FileUploadServiceImpl(s3FileUploadService))
				.build();

		server.start();
		System.out.println("gRPC Server started, listening on port 9090.");
		server.awaitTermination();

	}



}
