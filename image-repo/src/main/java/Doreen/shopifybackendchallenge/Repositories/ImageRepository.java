package Doreen.shopifybackendchallenge.Repositories;

import Doreen.shopifybackendchallenge.Entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer>  {

    List<Image> findAllByIsPublic(Boolean isPublic);

}
