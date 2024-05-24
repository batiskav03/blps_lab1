package batiskav.paymentmock.service;

import batiskav.paymentmock.DAO.PaymentDao;
import batiskav.paymentmock.model.TransactionForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

@Slf4j
@Service
public class PaymentService {

    private PaymentDao paymentDao;
    private JdbcTemplate jdbcTemplate;
    private UUIDService UUIDservice;
    private final String OWN_URL;
    private RestTemplate restTemplate;


    public PaymentService(PaymentDao paymentDao, JdbcTemplate jdbcTemplate, UUIDService UUIDservice, @Value("${api.own.url}") String ownUrl, RestTemplate restTemplate) {
        this.paymentDao = paymentDao;
        this.jdbcTemplate = jdbcTemplate;
        this.UUIDservice = UUIDservice;
        this.OWN_URL = ownUrl;
        this.restTemplate = restTemplate;
    }

    //TODO: return string url
    public String makeForm(String CALLBACK_URL) {
        UUID uuid = UUIDservice.generateUUID();
        // todo: здесь вроде закидываеться коллбэк в бд
        paymentDao.initPayment(uuid, CALLBACK_URL);
        log.info("uuid for new payment: " + uuid + " and callback url: " + CALLBACK_URL);
        return OWN_URL + "/api/payment/" + uuid;
    }

    public boolean startPayment(UUID uuid) {
        //todo: тут надо дернуть коллбэк, который лежит в бд
        if (Math.random() < 0.9) {
            log.info("operation " + uuid + " success");
            restTemplate.postForObject(paymentDao.getCallBackUrl(uuid) + "/" + uuid + "/true", null, Boolean.class);
            paymentDao.setPaymentStatus(uuid, true);
            return true;
        } else {
            log.error("operation " + uuid + " failed");
            restTemplate.postForObject(paymentDao.getCallBackUrl(uuid) + "/" + uuid + "/false", null, String.class);
            paymentDao.setPaymentStatus(uuid, false);
            return false;
        }
    }

    public boolean getPaymentStatus(UUID uuid) {
        return paymentDao.findPaymentStatus(uuid);
    }
}
