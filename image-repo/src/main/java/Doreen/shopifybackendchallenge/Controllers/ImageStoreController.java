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
public class ImageStoreController {
    @Autowired
    private ImageStoreService imageStoreService;

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/image_store/{id}/addImages", method = RequestMethod.POST)
    public ImageStoreDto addImagesToImageStore(@RequestBody List<ImageDto> imageDtos, @PathVariable(name="id") int storeId, Principal principal){
        if(userService.getStoreId(principal.getName())!=storeId){
            throw new UnauthorizedActionException("Unauthorized to access ImageStore-"+ storeId);
        }
        return imageStoreService.addImagesToImageStore(imageDtos, storeId);
    }

    @RequestMapping(value = "/image_store/{id}/deleteImages", method = RequestMethod.DELETE)
    public ImageStoreDto deleteImagesFromImageStore(@RequestBody List<ImageDto> imageDtos, @PathVariable(name="id") int storeId, Principal principal){
        if(userService.getStoreId(principal.getName())!=storeId){
            throw new UnauthorizedActionException("Unauthorized to access ImageStore-"+ storeId);
        }
        return imageStoreService.deleteImagesToImageStore(imageDtos, storeId);
    }

    @RequestMapping(value = "/image_store/{id}", method = RequestMethod.GET)
    public ImageStoreDto getImageStoreById(@PathVariable(name="id") int storeId, Principal principal){
        if(userService.getStoreId(principal.getName())!=storeId){
            throw new UnauthorizedActionException("Unauthorized to access ImageStore-"+ storeId);
        }
        return imageStoreService.getImageStoreById(storeId);
    }

    @RequestMapping(value = "/image_store/{id}/image/{image_id}", method = RequestMethod.PUT)
    public ImageStoreDto deleteImagesFromImageStore(@RequestBody ImageDto imageDto,
                                                    @PathVariable(name="id") int storeId,
                                                    @PathVariable(name="image_id") int imageId,
                                                    Principal principal){
        if(userService.getStoreId(principal.getName())!=storeId){
            throw new UnauthorizedActionException("Unauthorized to access ImageStore-"+ storeId);
        }
        return imageStoreService.updateExistingImage(imageDto, imageId,storeId);
    }

    @RequestMapping(value = "/register_user", method = RequestMethod.POST)
    public ImageStoreDto newImageStore(@RequestBody User user){
        return userService.registerNewUser(user);
    }

    @RequestMapping(value = "/cancel_user/{username}", method = RequestMethod.DELETE)
    public void newImageStore( @PathVariable(name="username") String username, Principal principal){
        if(!principal.getName().equals(username)){
            throw new UnauthorizedActionException("Unauthorized to cancel user-" + username + ". You can only cancel your own user");
        }
        userService.cancelUser(username);
    }


}
