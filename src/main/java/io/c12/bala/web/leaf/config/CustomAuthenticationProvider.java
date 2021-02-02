package io.c12.bala.web.leaf.config;

import io.c12.bala.web.leaf.entity.UserEntity;
import io.c12.bala.web.leaf.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) {
        log.info("Authenticate user inside CustomAuthenticationProvider.authenticate() method");
        String email = authentication.getName();
        String password = (String) authentication.getCredentials();
        List<GrantedAuthority> authorities = new ArrayList<>();

        UserEntity userEntity = userRepository.findUserEntityByEmailIgnoreCase(email);

        if (userEntity == null) {
            log.warn("User not found for emails {}", email);
            throw new AuthenticationServiceException("User not found for email " + email);
        }

        if (passwordEncoder.matches(password, userEntity.getPassword())) {
            userEntity.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
            log.warn("******************************************************************************************");
            log.warn("User id       :: {}", userEntity.getEmail());
            log.warn("User Roles    :: {}", authorities);
            log.warn("******************************************************************************************");
            return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), authorities);
        } else {
            log.warn("User authentication failed for emails {}", email);
            throw new AuthenticationServiceException("User authentication failed");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
