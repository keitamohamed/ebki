package com.ebki.bucket;

public enum AWSBaseBucketUrl {
    CAR_BASE_URL("ebik/");
    private final String AWS_BASE_URL;

    AWSBaseBucketUrl(String AWS_BASE_URL) {
        this.AWS_BASE_URL = AWS_BASE_URL;
    }

    public String getAWS_BASE_URL() {
        return AWS_BASE_URL;
    }
}
