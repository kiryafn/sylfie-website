package com.sylfie.repository;

import com.sylfie.model.entity.Picture;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureRepository extends CrudRepository<Picture, Long> {
    List<Picture> findAll();
}
