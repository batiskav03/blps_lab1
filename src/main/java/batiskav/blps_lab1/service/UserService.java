package batiskav.blps_lab1.service;

import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private JdbcUserDetailsManager jdbcUserDetailsManager;
}
