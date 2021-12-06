package com.ebki.bucket;

public enum BucketDriverImage {

    DRIVER_IMAGE("driver-image");
    private final String BUCKET_DRIVER_IMAGE;

    BucketDriverImage(String BUCKET_DRIVER_IMAGE) {
        this.BUCKET_DRIVER_IMAGE = BUCKET_DRIVER_IMAGE;
    }

    public String getBUCKET_DRIVER_IMAGE() {
        return BUCKET_DRIVER_IMAGE;
    }
}
