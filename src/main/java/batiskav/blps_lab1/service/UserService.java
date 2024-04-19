package batiskav.blps_lab1.service;

import batiskav.blps_lab1.repository.UserDao;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserDao userRepository;

    public UserService(UserDao userRepository) {
        this.userRepository = userRepository;
    }


}
