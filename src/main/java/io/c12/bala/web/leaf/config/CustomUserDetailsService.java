package io.c12.bala.web.leaf.config;

import io.c12.bala.web.leaf.entity.UserEntity;
import io.c12.bala.web.leaf.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * This is used for Remember Me function.
 * <p>
 * A cookie is created with value as below.
 * base64(username + ":" + expirationTime + ":" +
 * md5Hex(username + ":" + expirationTime + ":" password + ":" + key))
 * <p>
 * username:          As identifiable to the UserDetailsService
 * password:          That matches the one in the retrieved UserDetails
 * expirationTime:    The date and time when the remember-me token expires, expressed in milliseconds
 * key:               A private key to prevent modification of the remember-me token
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        UserEntity userEntity = userRepository.findUserEntityByEmailIgnoreCase(email);

        if (userEntity == null) {
            log.warn("User not found in DB for email id {}", email);
            throw new UsernameNotFoundException(email);
        }
        log.info("User found for email {}. Creating UserDetails", email);
        return User.withUsername(userEntity.getEmail()).password(userEntity.getPassword()).authorities(String.join(",", userEntity.getRoles())).build();
    }
}
