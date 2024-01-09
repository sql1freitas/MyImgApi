package io.github.sql1freitas.MyImgApi.repository;

import io.github.sql1freitas.MyImgApi.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {
}
