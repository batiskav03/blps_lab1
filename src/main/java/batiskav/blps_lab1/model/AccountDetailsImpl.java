package batiskav.blps_lab1.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


public class AccountDetailsImpl implements UserDetails {

    private Collection<? extends GrantedAuthority> authorities;

    private String email;

    private String password;

    private String username;

    private Boolean enabled;

    private Boolean accountNonExpired;

    private Boolean accountNonLocked;

    private boolean credentialsNonExpired;

    public AccountDetailsImpl(String email,
                              String username,
                              String password,
                              Boolean enabled,
                              Collection<? extends GrantedAuthority> authorities) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    public AccountDetailsImpl(String email,
                              String username,
                              String password,
                              Boolean enabled,
                              Boolean accountNonExpired,
                              Boolean accountNonLocked,
                              Boolean credentialsNonExpired,
                              Collection<? extends GrantedAuthority> authorities) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.authorities = authorities;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
