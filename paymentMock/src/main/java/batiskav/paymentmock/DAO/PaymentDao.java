package batiskav.paymentmock.DAO;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.concurrent.Executor;

@Repository
public class PaymentDao {


    private JdbcTemplate jdbcTemplate;

    public PaymentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void initPayment(UUID uuid) {
        jdbcTemplate.update("""
                    insert into transaction (id, date) values (?, current_date);
                    """, uuid);
    }

    public boolean findPaymentStatus(UUID uuid) {
        return jdbcTemplate.queryForObject("""
                    select status from transaction
                        where id = ?
                """, (rs, rowNum) -> rs.getBoolean(1), uuid);
    }

    public boolean setPaymentStatus(UUID uuid, boolean b) {
        int res = jdbcTemplate.update("""
                update transaction 
                set status = ?
                where id = ?
            """, b, uuid);

        return res > 0;
    }
}
