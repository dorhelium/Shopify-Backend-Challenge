package Doreen.shopifybackendchallenge.Entities.Dto;

import Doreen.shopifybackendchallenge.Entities.ImageStore;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ImageStoreDto {

    private int id;
    List<ImageDto> images;

    public ImageStoreDto(ImageStore imageStore) {
        this.id = imageStore.getId();
        List<ImageDto> imageDtos = new ArrayList<>();
        imageStore.getImages().forEach(image-> imageDtos.add(new ImageDto(image)));
        this.images = imageDtos;
    }
}
