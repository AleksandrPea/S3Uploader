package com.sdkFinance.training.form.storage;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.BucketVersioningConfiguration;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.SetBucketVersioningConfigurationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class AWSS3StorageService implements StorageService {

    private AmazonS3Client s3Client;
    private String bucketName;

    @Autowired
    public AWSS3StorageService(S3Credentials credentials) {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(credentials.getAccessKeyId(),
                credentials.getSecretAccessKey());
        s3Client = new AmazonS3Client(awsCreds);
    }

    @Override
    public String store(MultipartFile file) {
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
        } else {
            File tempFile = null;
            try {
                tempFile = File.createTempFile("temp-file", "tmp");
                file.transferTo(tempFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            PutObjectRequest putRequest = new PutObjectRequest(bucketName,
                    file.getOriginalFilename(), tempFile);
            PutObjectResult response = s3Client.putObject(putRequest);
            tempFile.delete();
            return response.getVersionId();
        }
    }

    @Override
    public void init() {
        bucketName = "bucket" + System.currentTimeMillis();
        s3Client.createBucket(bucketName);
        BucketVersioningConfiguration configuration =
                new BucketVersioningConfiguration().withStatus("Enabled");

        SetBucketVersioningConfigurationRequest setBucketVersioningConfigurationRequest =
                new SetBucketVersioningConfigurationRequest(bucketName,configuration);

        s3Client.setBucketVersioningConfiguration(setBucketVersioningConfigurationRequest);
    }
}
