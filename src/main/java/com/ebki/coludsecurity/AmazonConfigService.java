package com.ebki.coludsecurity;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.ebki.config.AwsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfigService {

    @Autowired
    private AwsConfig awsConfig;

    public AmazonS3 amazonS3 () {
        AWSCredentials awsCredentials = new BasicAWSCredentials(
            awsConfig.getAccessKey(),
            awsConfig.getSecreteKey()
        );
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(awsConfig.getRegion())
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}
