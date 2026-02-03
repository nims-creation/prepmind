package com.nimscreation.prepmind.service.impl;

import com.nimscreation.prepmind.entity.base.User;
import com.nimscreation.prepmind.exception.UserAlreadyExistsException;
import com.nimscreation.prepmind.exception.UserNotFoundException;
import com.nimscreation.prepmind.repository.UserRepository;
import com.nimscreation.prepmind.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User createUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException(user.getEmail());
        }

        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long userId) {

        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with id: " + userId));
    }

    @Override
    public User getUserByEmail(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with email: " + email));
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
