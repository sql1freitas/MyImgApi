package io.github.sql1freitas.MyImgApi.service;

import io.github.sql1freitas.MyImgApi.Mapper.ImageMapper;
import io.github.sql1freitas.MyImgApi.entity.Image;
import io.github.sql1freitas.MyImgApi.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {

     private final ImageRepository imageRepository;





     public Image save(Image image){

         return imageRepository.save(image);

     }

}
