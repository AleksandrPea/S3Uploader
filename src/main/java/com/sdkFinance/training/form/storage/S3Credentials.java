package com.sdkFinance.training.form.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("credentials")
public class S3Credentials {

    @Value("${access_key_id}")
    private String accessKeyId;

    @Value("${secret_access_key}")
    private String secretAccessKey;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public String getSecretAccessKey() {
        return secretAccessKey;
    }
}

