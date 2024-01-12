package io.github.sql1freitas.MyImgApi.controller;

import io.github.sql1freitas.MyImgApi.Mapper.ImageDTO;
import io.github.sql1freitas.MyImgApi.Mapper.ImageMapper;
import io.github.sql1freitas.MyImgApi.entity.Image;
import io.github.sql1freitas.MyImgApi.enums.ImageExtension;
import io.github.sql1freitas.MyImgApi.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/images")
@Slf4j
@RequiredArgsConstructor
public class ImagesController {


    private final ImageService imageService;

    private final ImageMapper imageMapper;



    @PostMapping
    public ResponseEntity save (@RequestParam("file")MultipartFile file,
                                @RequestParam("name") String name,
                                @RequestParam("tags")List<String> tags) throws IOException {

        log.info("Imagem recebida: name: {}, size {}", file.getName(), file.getSize());

        Image image = imageMapper.mapToImage(file, name, tags);

        Image savedImage = imageService.save(image);
        URI imageUri = buildImageURL(savedImage);


        return ResponseEntity.created(imageUri).build();


    }


    @GetMapping("{id}")
    public ResponseEntity<byte[]> getImage (@PathVariable String id){

        var possibleImage = imageService.getById(id);

        if (possibleImage.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        var image = possibleImage.get();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(image.getExtension().getMediaType());
        headers.setContentLength(image.getSize());
        headers.setContentDispositionFormData("inline; filename=\"" + image.getFileName() + "\"", image.getFileName());

        return new ResponseEntity<>(image.getFile(), headers, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ImageDTO>> searchImage(@RequestParam(value = "extension",
    required = false) String extension, @RequestParam (value = "query", required = false) String query){

         var result = imageService.searchImages(ImageExtension.valueOf(extension), query);

         var images = result.stream()
                 .map(image -> {
                     var url =buildImageURL(image);
                    return imageMapper.imageToDTO(image, url.toString());
                 })
                 .collect(Collectors.toList());


         return ResponseEntity.ok(images);
    }


    private URI buildImageURL(Image image){
        String imagePath = "/" + image.getId();
        return ServletUriComponentsBuilder.fromCurrentRequest().path(imagePath)
                .build().toUri();
    }
}
