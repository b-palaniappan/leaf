package io.c12.bala.web.leaf.config;

import io.c12.bala.web.leaf.entity.UserEntity;
import io.c12.bala.web.leaf.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserEntityByEmailIgnoreCase(email);

        if (userEntity == null) {
            log.warn("User not found in DB for email id {}", email);
            throw new UsernameNotFoundException(email);
        }
        log.info("User found for email {}. Creating user details", email);
        return User.withUsername(userEntity.getEmail()).password(userEntity.getPassword()).authorities("USER").build();
    }
}
