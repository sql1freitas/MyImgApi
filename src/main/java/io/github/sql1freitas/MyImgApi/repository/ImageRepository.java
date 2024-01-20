package io.github.sql1freitas.MyImgApi.repository;

import io.github.sql1freitas.MyImgApi.entity.Image;
import io.github.sql1freitas.MyImgApi.enums.ImageExtension;
import io.github.sql1freitas.MyImgApi.repository.specs.GenericSpecs;
import io.github.sql1freitas.MyImgApi.repository.specs.ImageSpecs;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.List;

import static io.github.sql1freitas.MyImgApi.repository.specs.GenericSpecs.*;
import static io.github.sql1freitas.MyImgApi.repository.specs.ImageSpecs.*;
import static org.springframework.data.jpa.domain.Specification.anyOf;
import static org.springframework.data.jpa.domain.Specification.where;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> , JpaSpecificationExecutor<Image> {

    /**
     *
     * @param extension
     * @param query
     * @return
     *
     *
     *  SELECT * FROM IMAGE WHERE 1 = 1 AND EXTENSION = 'PNG' AND (NAME LIKE 'NAME' OR TAGS LIKE 'QUERY')
     *
     *
     */


    default List<Image> findByExtensionAndNameOrTagsLike(ImageExtension extension, String query){


        Specification<Image> spec = where(conjunction());

        if(extension == null){
            spec = spec.and(extensionEqual(extension));
        }

        if(StringUtils.hasText(query)){

            spec = spec.and(anyOf(nameLike(query), tagsLike(query)));
        }
        return findAll(spec);
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    List<Image>searchByNameIgnoreCaseStartingWith(String name);
}
