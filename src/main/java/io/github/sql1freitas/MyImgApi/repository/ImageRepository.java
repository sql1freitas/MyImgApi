package io.github.sql1freitas.MyImgApi.repository;

import io.github.sql1freitas.MyImgApi.entity.Image;
import io.github.sql1freitas.MyImgApi.enums.ImageExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> , JpaSpecificationExecutor<Image> {

    default List<Image> findByExtensionAndNameOrTagsLike(ImageExtension extension, String query){
        if(extension == null){
            //TODO: add in query
        }
        return findAll();
    }
}
