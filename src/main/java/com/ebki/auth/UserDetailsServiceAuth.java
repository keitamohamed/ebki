package com.ebki.auth;

import com.ebki.model.Authenticate;
import com.ebki.repository.AuthRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.ebki.security.UserRole.ADMIN;
import static com.ebki.security.UserRole.DRIVER;

@Service
public class UserDetailsServiceAuth implements UserDetailsService {

    private final AuthRepo authRepo;

    @Autowired
    public UserDetailsServiceAuth(AuthRepo authRepo) {
        this.authRepo = authRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Authenticate authenticate = authRepo.findByUsername(username);
        if (authenticate == null) {
            throw new UsernameNotFoundException("User does not exist");
        }
        return new UserAuthDetail(authenticate, grantedAuthorities(authenticate));
    }

    private Set<SimpleGrantedAuthority> grantedAuthorities(Authenticate authenticate) {
        if (authenticate.getRole().getRoleType().equalsIgnoreCase("Admin")) {
            return ADMIN.grantedAuthorities();
        }
        return DRIVER.grantedAuthorities();
    }
}
