package Doreen.shopifybackendchallenge.Entities.Dto;

import Doreen.shopifybackendchallenge.Entities.Image;
import lombok.Getter;
import lombok.Setter;

import java.util.Base64;

@Getter
@Setter
public class ImageDto {
    private int id;
    private boolean isPublic;
    private String imageData;

    public ImageDto (Image image){
        this.id=image.getId();
        this.imageData = Base64.getEncoder().encodeToString(image.getImageData());
        this.isPublic = image.isPublic();
    }

    public ImageDto(int id, String imageData) {
        this.id = id;
        this.imageData = imageData;
    }
}
