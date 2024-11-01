# Build stage with JDK
FROM openjdk:17-jdk-alpine AS build

# Install curl and glibc

RUN apk --no-cache add curl \
    && curl -L https://github.com/sgerrand/alpine-pkg-glibc/releases/download/2.34-r0/glibc-2.34-r0.apk > /tmp/glibc-2.34-r0.apk \
    && apk add --allow-untrusted /tmp/glibc-2.34-r0.apk

#Install needed packages and protoc
RUN apk add --update wget unzip
ARG PROTOC_VER=3.17.3
ARG PROTOC_ZIP=protoc-$PROTOC_VER-linux-x86_64.zip
RUN wget -q https://github.com/protocolbuffers/protobuf/releases/download/v${PROTOC_VER}/${PROTOC_ZIP} && \
    unzip -o $PROTOC_ZIP -d /usr/local bin/protoc && \
    unzip -o $PROTOC_ZIP -d /usr/local 'include/*' && \
    rm -f $PROTOC_ZIP && \
    apk del wget unzip && \
    rm -rf /var/cache/apk/*
RUN chmod +x /usr/local/bin/protoc
    
# Check the contents of /usr/local/bin/
RUN ls -la /usr/local/bin/
# Copy sources
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN chmod +x ./gradlew
RUN ./gradlew --info  build --no-daemon 

# Production stage with JRE only
FROM openjdk:17-jdk-alpine
EXPOSE 8080
EXPOSE 9090

RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/aws-s3-wrapper-dev.jar

ENTRYPOINT ["java", "-jar","/app/aws-s3-wrapper-dev.jar"]
