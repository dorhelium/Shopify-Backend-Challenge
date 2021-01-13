package Doreen.shopifybackendchallenge.Services;

import Doreen.shopifybackendchallenge.Entities.Dto.ImageDto;
import Doreen.shopifybackendchallenge.Entities.Dto.ImageStoreDto;
import Doreen.shopifybackendchallenge.Entities.Image;
import Doreen.shopifybackendchallenge.Entities.ImageStore;
import Doreen.shopifybackendchallenge.Exceptions.InvalidDataException;
import Doreen.shopifybackendchallenge.Repositories.ImageRepository;
import Doreen.shopifybackendchallenge.Repositories.ImageStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.util.List;

@Service
public class ImageStoreService {
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ImageStoreRepository imageStoreRepository;

    public ImageStoreDto addImagesToImageStore(List<ImageDto> imageDtos, int storeId) {
        ImageStore store = imageStoreRepository.findById(storeId).orElse(null);
        if (store==null){
            throw new InvalidDataException("The image store with id="+storeId+" does not exist.");
        }

        List<Image> imagesInStore = store.getImages();
        imageDtos.forEach(data-> {
            Image newImage = new Image(DatatypeConverter.parseBase64Binary(data.getImageData()));
            imagesInStore.add(newImage);
            imageRepository.save(newImage);
        });

        ImageStore saved = imageStoreRepository.save(store);
        ImageStoreDto imageStoreDto =new ImageStoreDto(saved);
        return imageStoreDto;
    }

    public ImageStoreDto deleteImagesToImageStore(List<ImageDto> imageDtos, int storeId) {
        ImageStore store = imageStoreRepository.findById(storeId).orElse(null);
        if (store==null){
            throw new InvalidDataException("The image store with id="+storeId+" does not exist.");
        }

        imageDtos.forEach(imageDto -> {
            Image image = imageRepository.findById(imageDto.getId()).orElse(null);
            if(image!=null && store.getImages().contains(image)){
                store.getImages().remove(image);
            }
        });

        ImageStore saved = imageStoreRepository.save(store);
        ImageStoreDto imageStoreDto =new ImageStoreDto(saved);
        return imageStoreDto;
    }

    public ImageStoreDto getImageStoreById(int storeId) {
        ImageStore store = imageStoreRepository.findById(storeId).orElse(null);
        if (store==null){
            throw new InvalidDataException("The image store with id="+storeId+" does not exist.");
        }
        ImageStoreDto imageStoreDto =new ImageStoreDto(store);
        return imageStoreDto;
    }

}
