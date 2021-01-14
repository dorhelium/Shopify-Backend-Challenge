package Doreen.shopifybackendchallenge.Services;

import Doreen.shopifybackendchallenge.Entities.Dto.ImageStoreDto;
import Doreen.shopifybackendchallenge.Entities.Image;
import Doreen.shopifybackendchallenge.Entities.ImageStore;
import Doreen.shopifybackendchallenge.Entities.User;
import Doreen.shopifybackendchallenge.Exceptions.InvalidDataException;
import Doreen.shopifybackendchallenge.Repositories.ImageRepository;
import Doreen.shopifybackendchallenge.Repositories.ImageStoreRepository;
import Doreen.shopifybackendchallenge.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ImageStoreRepository imageStoreRepository;

    public int getStoreId(String username){
        User user =  userRepository.findById(username).orElse(null);
        if (user==null){
            throw new UsernameNotFoundException("Username not found.");
        }
        int storeId = user.getImageStore().getId();
        return storeId;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user =  userRepository.findById(username).orElse(null);
        if (user==null){
            throw new UsernameNotFoundException("Username not found.");
        }
        return new MyUserDetails(user);
    }

    public ImageStoreDto registerNewUser(User user) {
        User storedUser = userRepository.findById(user.getUsername()).orElse(null);
        if (storedUser != null){
            throw new InvalidDataException("Username "+ user.getUsername()+ " is already used. Choose another one.");
        }
        ImageStore imageStore = new ImageStore();
        imageStoreRepository.save(imageStore);
        user.setImageStore(imageStore);
        User newUser = userRepository.save(user);
        return new ImageStoreDto(newUser.getImageStore());
    }

    public void cancelUser(String username) {
        User storedUser = userRepository.findById(username).orElse(null);
        if (storedUser==null){
            throw new InvalidDataException("User "+ username+ " does not exist");
        }
        userRepository.delete(storedUser);
    }

    public class MyUserDetails implements UserDetails {

        private String username;
        private String password;
        private List<GrantedAuthority> authorities;

        public MyUserDetails(User user) {
            this.username = user.getUsername();
            this.password = user.getPassword();
            this.authorities = Arrays.asList(new SimpleGrantedAuthority("USER"));
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorities;
        }

        @Override
        public String getPassword() {
            return password;
        }

        @Override
        public String getUsername() {
            return username;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
