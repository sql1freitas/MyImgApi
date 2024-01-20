package io.github.sql1freitas.MyImgApi.service;

import io.github.sql1freitas.MyImgApi.Mapper.ImageDTO;
import io.github.sql1freitas.MyImgApi.Mapper.ImageMapper;
import io.github.sql1freitas.MyImgApi.entity.Image;
import io.github.sql1freitas.MyImgApi.enums.ImageExtension;
import io.github.sql1freitas.MyImgApi.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageService {

     private final ImageRepository imageRepository;
     private final ImageMapper mapper;





     public Image save(Image image){

         return imageRepository.save(image);

     }

     public Optional<Image> getById (String id){

         return imageRepository.findById(id);
     }


     public List<Image> searchImages (ImageExtension extension, String query){

         return imageRepository.findByExtensionAndNameOrTagsLike(extension, query);
     }

     public List<Image> searchAllImages(){
         return imageRepository.findAll();
     }


     public List<Image> searchAllByName(String name){
         return imageRepository.searchByNameIgnoreCaseStartingWith(name);
     }

}
