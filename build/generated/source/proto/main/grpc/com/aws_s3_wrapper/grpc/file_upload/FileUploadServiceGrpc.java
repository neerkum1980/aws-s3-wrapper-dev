package com.aws_s3_wrapper.grpc.file_upload;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.45.1)",
    comments = "Source: fileupload.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class FileUploadServiceGrpc {

  private FileUploadServiceGrpc() {}

  public static final String SERVICE_NAME = "FileUploadService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.aws_s3_wrapper.grpc.file_upload.FileChunk,
      com.aws_s3_wrapper.grpc.file_upload.UploadStatus> getUploadFileMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "uploadFile",
      requestType = com.aws_s3_wrapper.grpc.file_upload.FileChunk.class,
      responseType = com.aws_s3_wrapper.grpc.file_upload.UploadStatus.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<com.aws_s3_wrapper.grpc.file_upload.FileChunk,
      com.aws_s3_wrapper.grpc.file_upload.UploadStatus> getUploadFileMethod() {
    io.grpc.MethodDescriptor<com.aws_s3_wrapper.grpc.file_upload.FileChunk, com.aws_s3_wrapper.grpc.file_upload.UploadStatus> getUploadFileMethod;
    if ((getUploadFileMethod = FileUploadServiceGrpc.getUploadFileMethod) == null) {
      synchronized (FileUploadServiceGrpc.class) {
        if ((getUploadFileMethod = FileUploadServiceGrpc.getUploadFileMethod) == null) {
          FileUploadServiceGrpc.getUploadFileMethod = getUploadFileMethod =
              io.grpc.MethodDescriptor.<com.aws_s3_wrapper.grpc.file_upload.FileChunk, com.aws_s3_wrapper.grpc.file_upload.UploadStatus>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "uploadFile"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.aws_s3_wrapper.grpc.file_upload.FileChunk.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.aws_s3_wrapper.grpc.file_upload.UploadStatus.getDefaultInstance()))
              .setSchemaDescriptor(new FileUploadServiceMethodDescriptorSupplier("uploadFile"))
              .build();
        }
      }
    }
    return getUploadFileMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static FileUploadServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FileUploadServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FileUploadServiceStub>() {
        @java.lang.Override
        public FileUploadServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FileUploadServiceStub(channel, callOptions);
        }
      };
    return FileUploadServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static FileUploadServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FileUploadServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FileUploadServiceBlockingStub>() {
        @java.lang.Override
        public FileUploadServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FileUploadServiceBlockingStub(channel, callOptions);
        }
      };
    return FileUploadServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static FileUploadServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FileUploadServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FileUploadServiceFutureStub>() {
        @java.lang.Override
        public FileUploadServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FileUploadServiceFutureStub(channel, callOptions);
        }
      };
    return FileUploadServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class FileUploadServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public io.grpc.stub.StreamObserver<com.aws_s3_wrapper.grpc.file_upload.FileChunk> uploadFile(
        io.grpc.stub.StreamObserver<com.aws_s3_wrapper.grpc.file_upload.UploadStatus> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getUploadFileMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getUploadFileMethod(),
            io.grpc.stub.ServerCalls.asyncClientStreamingCall(
              new MethodHandlers<
                com.aws_s3_wrapper.grpc.file_upload.FileChunk,
                com.aws_s3_wrapper.grpc.file_upload.UploadStatus>(
                  this, METHODID_UPLOAD_FILE)))
          .build();
    }
  }

  /**
   */
  public static final class FileUploadServiceStub extends io.grpc.stub.AbstractAsyncStub<FileUploadServiceStub> {
    private FileUploadServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FileUploadServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FileUploadServiceStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.aws_s3_wrapper.grpc.file_upload.FileChunk> uploadFile(
        io.grpc.stub.StreamObserver<com.aws_s3_wrapper.grpc.file_upload.UploadStatus> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getUploadFileMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class FileUploadServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<FileUploadServiceBlockingStub> {
    private FileUploadServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FileUploadServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FileUploadServiceBlockingStub(channel, callOptions);
    }
  }

  /**
   */
  public static final class FileUploadServiceFutureStub extends io.grpc.stub.AbstractFutureStub<FileUploadServiceFutureStub> {
    private FileUploadServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FileUploadServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FileUploadServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_UPLOAD_FILE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final FileUploadServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(FileUploadServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_UPLOAD_FILE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.uploadFile(
              (io.grpc.stub.StreamObserver<com.aws_s3_wrapper.grpc.file_upload.UploadStatus>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class FileUploadServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    FileUploadServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.aws_s3_wrapper.grpc.file_upload.FileUploadProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("FileUploadService");
    }
  }

  private static final class FileUploadServiceFileDescriptorSupplier
      extends FileUploadServiceBaseDescriptorSupplier {
    FileUploadServiceFileDescriptorSupplier() {}
  }

  private static final class FileUploadServiceMethodDescriptorSupplier
      extends FileUploadServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    FileUploadServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (FileUploadServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new FileUploadServiceFileDescriptorSupplier())
              .addMethod(getUploadFileMethod())
              .build();
        }
      }
    }
    return result;
  }
}
