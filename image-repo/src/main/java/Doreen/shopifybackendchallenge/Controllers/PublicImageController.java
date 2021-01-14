package Doreen.shopifybackendchallenge.Controllers;

import Doreen.shopifybackendchallenge.Entities.Dto.ImageDto;
import Doreen.shopifybackendchallenge.Entities.Dto.ImageStoreDto;
import Doreen.shopifybackendchallenge.Entities.User;
import Doreen.shopifybackendchallenge.Exceptions.UnauthorizedActionException;
import Doreen.shopifybackendchallenge.Services.ImageStoreService;
import Doreen.shopifybackendchallenge.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class PublicImageController {
    @Autowired
    private ImageStoreService imageStoreService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/images", method = RequestMethod.GET)
    public List<ImageDto> getAllPublicImages(){
        return imageStoreService.getAllPublicImages();
    }

    @RequestMapping(value = "/image_store/{id}/images", method = RequestMethod.GET)
    public List<ImageDto> getAllPublicImagesByImageStore(@PathVariable(name="id") int storeId){
        return imageStoreService.getAllPublicImagesByImageStore(storeId);
    }

    @RequestMapping(value = "/register_user", method = RequestMethod.POST)
    public ImageStoreDto newImageStore(@RequestBody User user){
        return userService.registerNewUser(user);
    }


}
