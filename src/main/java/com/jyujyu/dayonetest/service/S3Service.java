package com.jyujyu.dayonetest.service;

import java.io.File;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;

@RequiredArgsConstructor
@Service
public class S3Service {
  private final S3Client s3Client;

  // 여기서 bucket 과 key는
  // bucket 은 스토리지중에 하나라는 뜻, 쉽게 생각하면 하드디스크중 하나
  // key 는 디렉터리와 파일이름을 가지고 있는거
  // 따라오면서 AWS 테스트를 해보는 경험을 하면 될거같습니다.
  public void putFile(String bucket, String key, File file) {
    s3Client.putObject(
        (req) -> {
          req.bucket(bucket);
          req.key(key);
        },
        RequestBody.fromFile(file));
  }

  public File getFile(String bucket, String key) {
    var file = new File("build/output/getFile.txt");

    var res =
        s3Client.getObject(
            (req) -> {
              req.bucket(bucket);
              req.key(key);
            });

    try {
      FileUtils.writeByteArrayToFile(file, res.readAllBytes());
    } catch (Exception e) {
      // ignore
    }

    return file;
  }
}
