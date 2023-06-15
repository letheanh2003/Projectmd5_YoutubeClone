package rikkei.academy.security.userPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rikkei.academy.model.Users;
import rikkei.academy.repository.IUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    IUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) {
        Users user =  userRepository.findByUserName(username);
        if(user ==null){
            throw new UsernameNotFoundException("user not found");
        }else {
            return CustomUserDetails.build(user);
        }
    }
}
