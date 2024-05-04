package batiskav.blps_lab1.controller;

import batiskav.blps_lab1.service.SubscribeService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.net.URL;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api")
public class SubscribeController {

    private SubscribeService subscribeService;
    private RestTemplate restTemplate;
    private final String URL;

    //todo: логику перенести в сервис

    public SubscribeController(SubscribeService subscribeService, RestTemplate restTemplate, @Value("${api.paymentURL}") String URL) {
        this.subscribeService = subscribeService;
        this.restTemplate = restTemplate;
        this.URL = URL;
    }
    @PostMapping("/subscribe/buy")
    public boolean buySubscribe(@RequestParam UUID id) {
        log.info("Payment for: " + id);
        Boolean status = restTemplate.postForObject(URL, id, Boolean.class);
        if (!status) {
            log.error("Payment failed, try again");

            return false;
        }
        log.info("Payment was success, take kaif with your subscribe");
        return subscribeService.updateSubscribe();
    }

    @SneakyThrows
    @PostMapping("/subscribe")
    public URL startSubscribing() {
        int userId = subscribeService.getUserId();
        String url = restTemplate.postForObject(URL, "http://localhost:8080/api/" + userId, String.class);
        log.info(url);
        return new URL(url);
    }


    @PostMapping("/{user_id}/{id}/{status}")
    public void checkPaymentStatus(@PathVariable("user_id") int user_id, @PathVariable("id") UUID id, @PathVariable("status") boolean status) {
        log.info("Check payment status " + id + " where status " + status);
        if (status) {
            subscribeService.updateSubscribeById(user_id);
        }
    }

    @GetMapping("/check")
    public boolean checkSubscribe() {
        return subscribeService.hasSubscribe();
    }


}
