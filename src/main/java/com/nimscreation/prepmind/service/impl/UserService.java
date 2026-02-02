package com.nimscreation.prepmind.service.impl;

import com.nimscreation.prepmind.entity.base.User;

public interface UserService {

    User createUser(User user);

    User getUserById(Long userId);

    User getUserByEmail(String email);
}

