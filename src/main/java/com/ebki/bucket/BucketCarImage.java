package com.ebki.bucket;

public enum BucketCarImage {

    CARE_IMAGE("car");

    private final String BUCKET_CAR;

    BucketCarImage(String BUCKET_CAR) {
        this.BUCKET_CAR = BUCKET_CAR;
    }

    public String getBUCKET_CAR() {
        return AWSBaseBucketUrl.CAR_BASE_URL.getAWS_BASE_URL() + BUCKET_CAR;
    }
}
