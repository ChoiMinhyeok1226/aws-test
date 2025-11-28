package com.multi.restproduct.common.util;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Operations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class AwsS3Utils {

    private final S3Operations s3Operations;
    private final S3Client s3Client; // 추가

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;


    /** https://docs.awspring.io/spring-cloud-aws/docs/3.1.1/reference/html/index.html#spring-cloud-aws-s3
     *
     *
     * S3에 파일 업로드
     */
    public String saveFile(String dirName, String fileName, MultipartFile multipartFile) {
        String replaceFileName = fileName + "." + FilenameUtils.getExtension(multipartFile.getResource().getFilename());

        String s3Key = dirName + replaceFileName;

        System.out.println("======>    "+s3Key);
        try (InputStream inputStream = multipartFile.getInputStream()) {
            ObjectMetadata metadata = ObjectMetadata.builder()
                    .contentType(multipartFile.getContentType())
                    .build();

            s3Operations.upload(bucket, s3Key, inputStream, metadata);


            log.info("[AwsS3Utils] File Uploaded Successfully: " + s3Key);
            return replaceFileName;
        } catch (IOException e) {
            log.error("[AwsS3Utils] File Upload Failed: " + s3Key, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
        }
    }
    /**
     * S3에서 파일 삭제
     */
    public boolean deleteFile(String dirName, String fileName) {
        log.info("[AwsS3Utils] deleteFile Start =====================");
        String s3Key = dirName + fileName; // yml 에서  products 까지 경로 줘도되고, 이미지 업로드 구분하려면 폴더명을 서비스에서 받아서 이용
        System.out.println("======>    "+s3Key);

        try {
            s3Operations.deleteObject(bucket, s3Key);
            log.info("[AwsS3Utils] File Deleted Successfully: " + s3Key);
            return true;
        } catch (Exception e) {
            log.error("[AwsS3Utils] File Deletion Failed: " + s3Key, e);
            return false;
        }
    }

}
