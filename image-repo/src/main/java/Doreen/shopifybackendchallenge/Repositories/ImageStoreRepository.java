package Doreen.shopifybackendchallenge.Repositories;

import Doreen.shopifybackendchallenge.Entities.Image;
import Doreen.shopifybackendchallenge.Entities.ImageStore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageStoreRepository extends JpaRepository<ImageStore, Integer> {
}
