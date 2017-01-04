package com.sdkFinance.training.form.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("credentials")
public class S3Credentials {

    private String accessKeyId= "";

    private String secretAccessKey ="";

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public String getSecretAccessKey() {
        return secretAccessKey;
    }
}

