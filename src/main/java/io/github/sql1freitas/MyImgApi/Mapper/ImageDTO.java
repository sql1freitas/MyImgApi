package io.github.sql1freitas.MyImgApi.Mapper;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ImageDTO {

    private String url;
    private String name;
    private String extension;
    private Long size;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate uploadDate;

}
