package batiskav.paymentmock.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UUIDService {


    public UUID generateUUID() {
        return UUID.randomUUID();
    }

}
