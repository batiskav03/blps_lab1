package batiskav.paymentmock.controller;


import batiskav.paymentmock.model.TransactionForm;
import batiskav.paymentmock.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PaymentController {

    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }



    @GetMapping
    public TransactionForm startPayment() {
        return paymentService.makeForm();
    }

    @PostMapping
    public boolean executePayment(@RequestBody UUID uuid) {
        return paymentService.startPayment(uuid);
    }



}
