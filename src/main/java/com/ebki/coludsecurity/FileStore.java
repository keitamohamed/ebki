package com.ebki.coludsecurity;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@Service
public class FileStore {

    private final AmazonConfigService amazonConfigService;

    @Autowired
    public FileStore(AmazonConfigService amazonConfigService) {
        this.amazonConfigService = amazonConfigService;
    }

    public void save(String filePath, String fileName,
                     Optional<Map<String, String>> optionalMetadata, InputStream inputStream) {
        ObjectMetadata metadata = new ObjectMetadata();
        optionalMetadata.ifPresent(map -> {
            if (!map.isEmpty()) {
                map.forEach(metadata::addUserMetadata);
            }
        });
        try {
            amazonConfigService.amazonS3().putObject(filePath, fileName, inputStream, metadata);
        }catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to store file to amazon s3 " + e);
        }

    }

    public byte[] downloadCarImage(String path, String key) {
        try {
            S3Object object = amazonConfigService.amazonS3().getObject(path, key);
            return IOUtils.toByteArray(object.getObjectContent());
        }catch (AmazonServiceException | IOException e) {
            throw new IllegalStateException("Failed to download file from amazon s3", e);
        }
    }
}
