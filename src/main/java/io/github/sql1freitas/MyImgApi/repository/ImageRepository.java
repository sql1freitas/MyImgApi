package io.github.sql1freitas.MyImgApi.repository;

import io.github.sql1freitas.MyImgApi.entity.Image;
import io.github.sql1freitas.MyImgApi.enums.ImageExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.swing.text.html.HTMLDocument;
import java.util.List;

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

        Specification<Image> conjunction = (root, q, criteriaBuilder) -> criteriaBuilder.conjunction();
        Specification<Image> spec = Specification.where(conjunction);

        if(extension == null){
            // AND EXTENSION = 'PNG'
            Specification<Image> extensionEqual = (root, q, cb) -> cb.equal(root.get("extension"), extension);
            spec = spec.and(extensionEqual);
        }

        if(StringUtils.hasText(query)){
            //AND (NAME LIKE 'NAME' OR TAGS LIKE 'QUERY')
            Specification<Image> nameLike = (root, q, cb) -> cb.equal(cb.upper(root.get("name")), "%" + query.toUpperCase() + "%");
            Specification<Image> tagsLike = (root, q, cb) -> cb.equal(cb.upper(root.get("tags")), "%" + query.toUpperCase() + "%");

            Specification<Image> nameOrTagsLike = Specification.anyOf(nameLike, tagsLike);
            spec = spec.and(nameOrTagsLike);
        }
        return findAll(spec);
    }
}
