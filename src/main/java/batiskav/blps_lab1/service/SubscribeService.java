package batiskav.blps_lab1.service;


import batiskav.blps_lab1.repository.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class SubscribeService {

    private final UserDao userRepository;
    private final RestTemplate restTemplate;
    private final String URL;

    public SubscribeService(UserDao userRepository,
                            RestTemplate restTemplate,
                            @Value("${api.paymentURL}") String url) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
        this.URL = url;
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

    public boolean paymentCallback(UUID id) {
        log.info("Payment for: " + id);
        Boolean status = restTemplate.postForObject(URL, id, Boolean.class);
        if (Boolean.FALSE.equals(status)) {
            log.error("Payment failed, try again");

            return false;
        }

        log.info("Payment was success, take kaif with your subscribe");
        return updateSubscribe();
    }

    public URL getPayemntURL() {
        try {
            int userId = getUserId();
            String url = restTemplate.postForObject(URL, "http://localhost:8080/api/" + userId, String.class);
            log.info(url);
            assert url != null;

            return new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
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
