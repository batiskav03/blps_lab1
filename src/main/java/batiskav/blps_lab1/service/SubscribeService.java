package batiskav.blps_lab1.service;


import batiskav.blps_lab1.repository.UserDao;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SubscribeService {

    private UserDao userRepository;

    public SubscribeService(UserDao userRepository) {
        this.userRepository = userRepository;
    }

    public boolean hasSubscribe() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userRepository.getSubscribe(user.getUsername());
    }

    public boolean checkSubscribeDate() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Date endDate = userRepository.getSubscribeEndTime(user.getUsername());
        if (endDate == null)
            return false;
        if (endDate.compareTo(new Date()) < 0) {
            userRepository.nullifySubscribe(user.getUsername());

            return false;
        }

        return true;
    }

    public boolean updateSubscribe() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userRepository.updateSubscribe(user.getUsername());
    }

    public boolean updateSubscribeById(int userId) {

        return userRepository.updateSubscribe(userId);
    }


    public int getUserId() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userRepository.getIdByUsername(user.getUsername());
    }
}
