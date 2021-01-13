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

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageStoreService {
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ImageStoreRepository imageStoreRepository;

    @Transactional
    public ImageStoreDto addImagesToImageStore(List<ImageDto> imageDtos, int storeId) {
        ImageStore store = imageStoreRepository.findById(storeId).orElse(null);
        if (store==null){
            throw new InvalidDataException("The image store with id="+storeId+" does not exist.");
        }

        List<Image> imagesInStore = store.getImages();
        imageDtos.forEach(data-> {
            Image newImage = new Image(DatatypeConverter.parseBase64Binary(data.getImageData()));
            newImage.setPublic(data.isPublic());
            imagesInStore.add(newImage);
            imageRepository.save(newImage);
        });

        ImageStore saved = imageStoreRepository.save(store);
        ImageStoreDto imageStoreDto =new ImageStoreDto(saved);
        return imageStoreDto;
    }

    @Transactional
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


    public List<ImageDto> getAllPublicImages() {
        List<Image> publicImages = imageRepository.findAllByIsPublic(true);
        List<ImageDto> publicImageDtos = publicImages.stream()
                .map(image->new ImageDto(image)).collect(Collectors.toList());
        return publicImageDtos;
    }

    public List<ImageDto> getAllPublicImagesByImageStore(int storeId) {
        ImageStore store = imageStoreRepository.findById(storeId).orElse(null);
        if (store==null){
            throw new InvalidDataException("Image store-"+storeId+" does not exist");
        }
        List<Image> publicImages = store.getImages().stream()
                .filter(image->image.isPublic()).collect(Collectors.toList());
        List<ImageDto> publicImageDtos = publicImages.stream()
                .map(image->new ImageDto(image)).collect(Collectors.toList());
        return publicImageDtos;
    }

    @Transactional
    public ImageStoreDto updateExistingImage(ImageDto imageDto, int imageId, int storeId) {
        Image image = imageRepository.findById(imageId).orElse(null);
        if (image==null){
            throw new InvalidDataException("Image-"+imageId+" does not exist.");
        }
        ImageStore store = imageStoreRepository.findById(storeId).orElse(null);
        if (store==null){
            throw new InvalidDataException("Image store-"+storeId+" does not exist");
        }
        if (!store.getImages().contains(image)){
            throw new InvalidDataException("Image store-"+storeId+" does not have image-"+imageId);
        }
        image.setPublic(imageDto.isPublic());
        image.setImageData(DatatypeConverter.parseBase64Binary(imageDto.getImageData()));
        imageRepository.save(image);
        ImageStoreDto imageStoreDto =new ImageStoreDto(store);
        return imageStoreDto;
    }
}
