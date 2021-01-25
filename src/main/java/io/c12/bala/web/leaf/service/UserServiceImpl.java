package io.c12.bala.web.leaf.service;

import io.c12.bala.web.leaf.entity.UserEntity;
import io.c12.bala.web.leaf.form.RegisterUser;
import io.c12.bala.web.leaf.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserEntity saveUser(RegisterUser user) {
        long startTime = System.currentTimeMillis();
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setCreateDate(LocalDateTime.now());
        userEntity.setActive(true);
        userEntity.setLocked(false);
        userEntity.setResetPassword(false);
        log.info("Time taken to save data - {} ms", System.currentTimeMillis() - startTime);
        log.info("Saving user to database - {}", userEntity);
        return userRepository.save(userEntity);
    }
}
