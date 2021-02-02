package io.c12.bala.web.leaf.service;

import io.c12.bala.web.leaf.entity.UserEntity;
import io.c12.bala.web.leaf.exception.UserAlreadyExistsException;
import io.c12.bala.web.leaf.form.RegisterUser;
import io.c12.bala.web.leaf.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserEntity saveUser(RegisterUser user) {
        // Check if the user exists
        if (!userRepository.existsAllByEmailIgnoreCase(user.getEmail())) {
            long startTime = System.currentTimeMillis();
            UserEntity userEntity = new UserEntity();
            userEntity.setFirstName(user.getFirstName());
            userEntity.setLastName(user.getLastName());
            userEntity.setEmail(user.getEmail());
            userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
            userEntity.setRoles(Collections.singletonList("ROLE_USER"));
            userEntity.setCreateDate(LocalDateTime.now());
            userEntity.setActive(true);
            userEntity.setLocked(false);
            userEntity.setResetPassword(false);
            log.info("Time taken to save data - {} ms", System.currentTimeMillis() - startTime);
            log.info("Saving user to database - {}", userEntity);
            return userRepository.save(userEntity);
        } else {
            throw new UserAlreadyExistsException("User already exists for email " + user.getEmail());
        }
    }
}
