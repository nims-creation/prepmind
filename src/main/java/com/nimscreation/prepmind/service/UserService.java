package com.nimscreation.prepmind.service;

import com.nimscreation.prepmind.dto.request.UpdateUserRequestDto;
import com.nimscreation.prepmind.entity.base.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    User createUser(User user);

    User getUserById(Long id);

    User getUserByEmail(String email);

    Page<User> getAllUsers(Pageable pageable);

    User updateUser(Long id, UpdateUserRequestDto dto);

    void deleteUser(Long id);

    User restoreUser(Long id);

    Page<User> searchUsers(String keyword, Pageable pageable);
}


