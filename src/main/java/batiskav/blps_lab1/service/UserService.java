package batiskav.blps_lab1.service;

import batiskav.blps_lab1.model.AccountDetailsImpl;
import batiskav.blps_lab1.repository.UserDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private UserDao userRepository;

    public UserService(UserDao userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    //todo: do
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
