package com.ebki.service;

import com.ebki.bucket.BucketCarImage;
import com.ebki.bucket.BucketDriverImage;
import com.ebki.coludsecurity.FileStore;
import com.ebki.model.Car;
import com.ebki.model.Driver;
import com.ebki.model.ImageFile;
import com.ebki.repository.CarRepo;
import com.ebki.repository.DriverRepo;
import com.ebki.repository.ImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.apache.http.entity.ContentType.IMAGE_JPEG;
import static org.apache.http.entity.ContentType.IMAGE_PNG;

@Service
public class FileService {
    private final FileStore fileStore;
    private final ImageRepo imageRepo;

    @Autowired
    public FileService(FileStore fileStore, ImageRepo imageRepo) {
        this.fileStore = fileStore;
        this.imageRepo = imageRepo;
    }

    public void saveFile(Long id, MultipartFile file, boolean isCarImage, CarRepo carRepo, DriverRepo driverRepo) {
        boolean isFileImage = isFileAnImage(file);

        if (!isFileImage) {
            throw new IllegalStateException("%s is not an image file. File type must be an image file (JPEG, or PNG)");
        }
        Map<String, String> metadata = new HashMap<>();
        metadata.put("ContentType", file.getContentType());
        metadata.put("FileSize", String.valueOf(file.getSize()));

        String path;
        if (isCarImage) {
            path = String.format("%s/%s", BucketCarImage.CARE_IMAGE.getBUCKET_CAR(), id);
        }else {
            path = String.format("%s/%s", BucketDriverImage.DRIVER_IMAGE.getBUCKET_DRIVER_IMAGE(), id);
        }
        try {
            ImageFile imageFile = new ImageFile();

            if (isCarImage) {
                Optional<Car> car = carRepo.findById(id);
                String imageName = (car.get().getBrand() + "-" + id);
                fileStore.save(path, imageName, Optional.of(metadata), file.getInputStream());
                imageFile.setCarImageLink(imageName);
                car.ifPresent(imageFile::setCarImg);
                imageRepo.save(imageFile);
            }
            else {
               Optional<Driver> driver = driverRepo.findById(id);
            }
        }catch (IOException exception) {
            throw new IllegalStateException(exception);
        }
    }

    public byte[] downloadImage(Long id, boolean isCarImage, CarRepo carRepo, DriverRepo driverRepo) {
        String carImagePath = String.format("%s/%s", BucketCarImage.CARE_IMAGE.getBUCKET_CAR(), id);
        String driverImagePath = String.format("%s/%s", BucketDriverImage.DRIVER_IMAGE.getBUCKET_DRIVER_IMAGE(), id);

        Optional<Car> car = Optional.empty();
        Optional<Driver> driver = Optional.empty();

        if (isCarImage) {
            car = carRepo.findById(id);
        }
        else {
            driver = driverRepo.findById(id);
        }

        if (car.isEmpty() && driver.isEmpty()) {
            return new byte[0];
        }

        if (isCarImage) {
            return car.get()
                    .getImageFile()
                    .map(key -> fileStore.downloadCarImage(carImagePath, key.getCarImageLink()))
                    .orElse(new byte[0]);
        }
        return null;
    }

    public void deleteFile(Long vin, CarRepo carRepo, boolean isCarImage) {
        Optional<Car> findCar = carRepo.findById(vin);
        if (findCar.isEmpty()) {
            return;
        }
        if (isCarImage) {
            String carImagePath = String.format("%s/%s", BucketCarImage.CARE_IMAGE.getBUCKET_CAR(), vin);
            ImageFile imageFile = findCar.get().getCarImg();
            fileStore.deleteFile(carImagePath, imageFile.getCarImageLink());
        }
    }


    private boolean isFileAnImage(MultipartFile file) {
        return Arrays.asList(IMAGE_JPEG.getMimeType(), IMAGE_PNG.getMimeType()).contains(file.getContentType());
    }
}
