package Doreen.shopifybackendchallenge.Controllers;

import Doreen.shopifybackendchallenge.Entities.Dto.ImageDto;
import Doreen.shopifybackendchallenge.Entities.Dto.ImageStoreDto;
import Doreen.shopifybackendchallenge.Exceptions.UnauthorizedActionException;
import Doreen.shopifybackendchallenge.Services.ImageStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class PublicImageController {
    @Autowired
    private ImageStoreService imageStoreService;

    @RequestMapping(value = "/images", method = RequestMethod.GET)
    public List<ImageDto> getAllPublicImages(){
        return imageStoreService.getAllPublicImages();
    }

    @RequestMapping(value = "/image_store/{id}/images", method = RequestMethod.GET)
    public List<ImageDto> getAllPublicImagesByImageStore(@PathVariable(name="id") int storeId){
        return imageStoreService.getAllPublicImagesByImageStore(storeId);
    }


}
