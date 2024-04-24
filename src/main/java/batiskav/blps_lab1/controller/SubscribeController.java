package batiskav.blps_lab1.controller;

import batiskav.blps_lab1.model.TransactionFormRepresentation;
import batiskav.blps_lab1.service.SubscribeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api")
public class SubscribeController {

    private SubscribeService subscribeService;
    private RestTemplate restTemplate;

    public SubscribeController(SubscribeService subscribeService, RestTemplate restTemplate) {
        this.subscribeService = subscribeService;
        this.restTemplate = restTemplate;
    }
    @PostMapping("/subscribe/buy")
    public boolean buySubscribe(@RequestParam UUID id) {
        log.info("Payment for: " + id);
        Boolean status = restTemplate.postForObject("http://localhost:8090/api/payment", id, Boolean.class);
        if (!status) {
            log.error("Payment failed, try again");

            return false;
        }
        log.info("Payment was success, take kaif with your subscribe");
        return subscribeService.updateSubscribe();
    }

    @GetMapping("/subscribe")
    public UUID startSubscribing() {
        TransactionFormRepresentation form = restTemplate.getForObject("http://localhost:8090/api/payment", TransactionFormRepresentation.class);
        log.info(form.toString());
        return form.getId();
    }


    @GetMapping("/subscribe/status")
    public boolean check(@RequestParam UUID id) {
        log.info("Check payment status " + id);
        Boolean status = restTemplate.postForObject("http://localhost:8090/api/payment/status", id, Boolean.class);
        return status;
    }

}
