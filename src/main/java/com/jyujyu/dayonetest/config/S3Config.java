package com.jyujyu.dayonetest.config;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {
  @Value("${aws.endpoint}")
  String awsEndpoint;

  // 두 Bean 은 직접 정의하지 않으면 AWS SDK 에서 @Bean으로 제공해줄수 있스빈다.
  @Bean
  public AwsCredentialsProvider awsCredentialsProvider() {
    // AWS SDK 에서 별도의 인증과정을 거쳐야하는데 이를 제공하기위해 설정
    return AwsCredentialsProviderChain.builder()
        .reuseLastProviderEnabled(true)
        .credentialsProviders(
            List.of(
                DefaultCredentialsProvider.create(),
                StaticCredentialsProvider.create(AwsBasicCredentials.create("foo", "bar"))))
        .build();
  }

  @Bean
  public S3Client s3Client() {
    // S3 를 실제로 사용하기위한 클라이언트 입니다.
    return S3Client.builder()
        .credentialsProvider(awsCredentialsProvider())
        .region(Region.AP_NORTHEAST_2)
        .endpointOverride(URI.create(awsEndpoint))
        .build();
  }
}
