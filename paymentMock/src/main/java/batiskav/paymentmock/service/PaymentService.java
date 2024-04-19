package batiskav.paymentmock.service;

import batiskav.paymentmock.DAO.PaymentDao;
import batiskav.paymentmock.model.TransactionForm;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {

    private PaymentDao paymentDao;
    private JdbcTemplate jdbcTemplate;
    private UUIDService UUIDservice;

    public PaymentService(PaymentDao paymentDao, JdbcTemplate jdbcTemplate, UUIDService UUIDservice) {
        this.paymentDao = paymentDao;
        this.jdbcTemplate = jdbcTemplate;
        this.UUIDservice = UUIDservice;
    }

    public TransactionForm makeForm() {
        UUID uuid = UUIDservice.generateUUID();
        paymentDao.initPayment(uuid);
        return new TransactionForm(uuid);
    }

    public boolean startPayment(UUID uuid) {
        if (Math.random() > 0.9) {
            return paymentDao.setPaymentStatus(uuid, true);
        } else {
            return paymentDao.setPaymentStatus(uuid, false);
        }
    }

    public boolean getPaymentStatus(UUID uuid) {
        return paymentDao.findPaymentStatus(uuid);
    }
}
