package batiskav.blps_lab1.controller;

import batiskav.blps_lab1.service.SubscribeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


import java.net.URL;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api")
public class SubscribeController {

    private final SubscribeService subscribeService;

    public SubscribeController(SubscribeService subscribeService) {
        this.subscribeService = subscribeService;
    }
    @PostMapping("/subscribe/buy")
    public boolean buySubscribe(@RequestParam UUID id) {

        return subscribeService.paymentCallback(id);
    }


    @PostMapping("/subscribe")
    public URL startSubscribing() {

        return subscribeService.getPayemntURL();
    }


    @PostMapping("/{user_id}/{id}/{status}")
    public void checkPaymentStatus(@PathVariable("user_id") int user_id, @PathVariable("id") UUID id, @PathVariable("status") boolean status) {
        log.info("Check payment status " + id + " where status " + status);
        if (status) {
            subscribeService.updateSubscribeById(user_id);
        } else {
            log.error("Payment failed");
        }
    }

    @GetMapping("/check")
    public boolean checkSubscribe() {
        return subscribeService.hasSubscribe();
    }


}
