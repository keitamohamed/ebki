package com.ebki.repository;

import com.ebki.model.ImageFile;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepo extends CrudRepository<ImageFile, Long> {

}
