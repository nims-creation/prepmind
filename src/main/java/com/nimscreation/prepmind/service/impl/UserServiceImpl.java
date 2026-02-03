package com.nimscreation.prepmind.service.impl;

import com.nimscreation.prepmind.dto.request.UpdateUserRequestDto;
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

    @Transactional
    @Override
    public User createUser(User user) {

        if (userRepository.existsByEmailAndDeletedFalse(user.getEmail())) {
            throw new UserAlreadyExistsException(user.getEmail());
        }

        return userRepository.save(user);
    }




    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .filter(user -> !user.isDeleted())
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with id: " + userId));
    }




    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmailAndDeletedFalse(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with email: " + email));
    }





    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAllByDeletedFalse(pageable);
    }



    @Override
    public User updateUser(Long userId, UpdateUserRequestDto dto) {

        User user = getUserById(userId); // already deleted=false check

        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {

            String newEmail = dto.getEmail().trim();

            if (!newEmail.equals(user.getEmail())
                    && userRepository.existsByEmailAndDeletedFalse(newEmail)) {

                throw new UserAlreadyExistsException(newEmail);
            }

            user.setEmail(newEmail);
        }

        if (dto.getName() != null && !dto.getName().isBlank()) {
            user.setName(dto.getName().trim());
        }

        if (dto.getRole() != null) {
            user.setRole(dto.getRole());
        }

        return userRepository.save(user);
    }


    @Override
    public void deleteUser(Long userId) {

        User user = userRepository.findByIdAndDeletedFalse(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with id: " + userId));

        user.setDeleted(true);
        userRepository.save(user);
    }


    @Override
    public User restoreUser(Long userId) {

        User user = userRepository.findByIdAndDeletedTrue(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("Deleted user not found with id: " + userId));

        user.setDeleted(false);
        return userRepository.save(user);
    }





}
