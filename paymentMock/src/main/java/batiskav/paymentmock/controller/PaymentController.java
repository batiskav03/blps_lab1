package batiskav.paymentmock.controller;


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



    @GetMapping
    public TransactionForm startPayment() {
        log.info("Started payment for user");

        return paymentService.makeForm();
    }

    @PostMapping
    public boolean executePayment(@RequestBody UUID uuid) {
        return paymentService.startPayment(uuid);
    }

    @PostMapping("/status")
    public boolean checkPaymentStatus(@RequestBody UUID uuid) {
        return paymentService.getPaymentStatus(uuid);
    }



}
