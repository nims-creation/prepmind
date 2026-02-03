package com.nimscreation.prepmind.service;

import com.nimscreation.prepmind.dto.request.UpdateUserRequestDto;
import com.nimscreation.prepmind.entity.base.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    User createUser(User user);

    User getUserById(Long userId);

    User getUserByEmail(String email);

    User updateUser(Long userId, UpdateUserRequestDto dto);


    Page<User> getAllUsers(Pageable pageable);

    void deleteUser(Long userId);
}

