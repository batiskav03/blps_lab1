package batiskav.blps_lab1.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
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

    public boolean updateSubscribe(String username) {
        final String UPDATE_QUERY = """
                update blps.subscribe
                    set subscription = true
                where user_id = ?
                """;
        int userId = getIdByUsername(username);
        int result = jdbcTemplate.update(UPDATE_QUERY, userId);
        return result != 0;
    }

    public boolean updateSubscribe(int userId) {
        final String UPDATE_QUERY = """
                update blps.subscribe
                    set subscription = true
                where user_id = ?
                """;
        int result = jdbcTemplate.update(UPDATE_QUERY, userId);
        return result != 0;
    }

    public int getIdByUsername(String username) {
        final String SELECT_QUERY = """
                select user_id from blps.subscribe
                            join users on users.id = user_id
                        where users.username = ?
                """;
        return jdbcTemplate.queryForObject(SELECT_QUERY, (rs, rowNum) -> rs.getInt(1), username);
    }


    public void nullifySubscribe(String username) {
        final String QUERY = """
                update blps.subscribe
                    set end_date = null, start_date = null, subscription = false
                where user_id = ?
                """;
        int userId = getIdByUsername(username);
        jdbcTemplate.update(QUERY, userId);
    }

    public Date getSubscribeEndTime(String username) {
        final String QUERY = """
                select end_date from blps.subscribe
                        where user_id = ?
                """;
        int userId = getIdByUsername(username);

        return jdbcTemplate.queryForObject(QUERY, (rs, rowNum) -> rs.getDate(1), userId);
    }


}
