package batiskav.blps_lab1.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean getSubscribe(String username) {
        final String QUERY = """
                select subscription from blps.subscribe
                                    join users on users.id = user_id
                                where users.username = ?
                """;
        boolean result = jdbcTemplate.queryForObject(QUERY, (rs, rowNum) -> rs.getBoolean(1), username);
        return result;
    }
}
