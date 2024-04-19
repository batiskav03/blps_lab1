package batiskav.blps_lab1.controller;

import batiskav.blps_lab1.model.TransactionFormRepresentation;
import batiskav.blps_lab1.service.SubscribeService;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class SubscribeController {

    private SubscribeService subscribeService;
    private RestTemplate restTemplate;

    public SubscribeController(SubscribeService subscribeService, RestTemplate restTemplate) {
        this.subscribeService = subscribeService;
        this.restTemplate = restTemplate;
    }
    @GetMapping("/subscribe/buy")
    public boolean buySubscribe() {

        return subscribeService.updateSubscribe();
    }

    @GetMapping("/subscribe")
    public UUID startSubscribing() {
        TransactionFormRepresentation form = restTemplate.getForObject("http://localhost:8090/api/payment", TransactionFormRepresentation.class);

        return form.getUuid();
    }

    // TODO: допилить грязный вонючий жидкий слабый мок
    @PostMapping
    public boolean check() {

        return false;
    }

}
