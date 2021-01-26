package io.c12.bala.web.leaf.config;

import io.c12.bala.web.leaf.entity.UserEntity;
import io.c12.bala.web.leaf.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

@Log4j2
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

        UserEntity userEntity = userRepository.findUserEntityByEmailIgnoreCase(email);

        if (userEntity == null) {
            log.warn("User not found for emails {}", email);
            throw new AuthenticationServiceException("User not found for email " + email);
        }

        if (passwordEncoder.matches(password, userEntity.getPassword())) {
            log.info("Login successful for the email {}", email);
            return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials());
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
