package batiskav.paymentmock.controller;


import batiskav.paymentmock.model.Persona;
import batiskav.paymentmock.model.TransactionForm;
import batiskav.paymentmock.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/api/payment")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PaymentController {

    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }



    @PostMapping
    public String startPayment(@RequestBody String CALLBACK_URL) {
        log.info("Started payment for user");

        return paymentService.makeForm(CALLBACK_URL);
    }



    @PostMapping("/{id}")
    public boolean executePayment(@PathVariable("id") UUID uuid) {
        return paymentService.startPayment(uuid);
    }

    @PostMapping("/status/{id}")
    public boolean checkPaymentStatus(@PathVariable("id") UUID uuid) {
        return paymentService.getPaymentStatus(uuid);
    }



}
