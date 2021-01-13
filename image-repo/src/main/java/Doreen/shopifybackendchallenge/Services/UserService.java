package Doreen.shopifybackendchallenge.Services;

import Doreen.shopifybackendchallenge.Entities.User;
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

    public class MyUserDetails implements UserDetails {

        private String username;
        private String password;
        private List<GrantedAuthority> authorities;

        public MyUserDetails(User user) {
            this.username = user.getUsername();
            this.password = user.getPassword();
            this.authorities = Arrays.asList(new SimpleGrantedAuthority("Store-" + user.getImageStore().getId()));
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
