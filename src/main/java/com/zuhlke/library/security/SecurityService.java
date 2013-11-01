package com.zuhlke.library.security;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.substring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Optional;
import com.zuhlke.library.domain.User;
import com.zuhlke.library.domain.UserRole;
import com.zuhlke.library.repositories.UserRepository;

@Service
public class SecurityService {

    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    @Inject
    private SecurityUtils securityUtils;
    
    @Inject
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public User authenticate(final String username, final String password) throws AuthenticationException {
        if (StringUtils.isBlank(username)) {
            throw new AuthenticationException("Attempt to login user with null username");
        }

        if (StringUtils.isBlank(password)) {
            throw new AuthenticationException(format("Attempt to log in account [%s] with a null password", substring(username, 0, 100)));
        }

        User user = userRepository.findByEmail(username);

        if (user == null) {
            throw new AuthenticationException(format("User does not exist [%s]", substring(username, 0, 100)));
        }

        if (!StringUtils.equals(user.getPassword(), securityUtils.hash(password, username))) {
            throw new AuthenticationException(format("Passwords for [%s] do not match", substring(username, 0, 100)));
        }

        // Set the security context so that we can use spring-security.
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, getAuthorities(user));
        SecurityContextHolder.getContext().setAuthentication(auth);

        return user;
    }

    private Collection<GrantedAuthority> getAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(3);

        // all authenticated users have ROLE_USER role
        authorities.add(new SimpleGrantedAuthority(ROLE_USER));

        if (user.getRole() == UserRole.ADMINISTRATOR) {
            authorities.add(new SimpleGrantedAuthority(ROLE_ADMIN));
        }

        return authorities;
    }

    public Optional<User> getCurrentUser() {
        Object principal = getSecurityContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return Optional.of((User) principal);
        } else {
            return Optional.absent();
        }
    }
    
    public void logout() {
        // clear the security context
        getSecurityContext().setAuthentication(null);
        clearSecurityContext();
    }
    
    SecurityContext getSecurityContext() {
        return SecurityContextHolder.getContext();
    }
    
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }
    
}


