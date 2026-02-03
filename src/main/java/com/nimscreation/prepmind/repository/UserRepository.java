package com.nimscreation.prepmind.repository;

import com.nimscreation.prepmind.entity.base.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 🔍 Active users only
    Page<User> findAllByDeletedFalse(Pageable pageable);

    Optional<User> findByEmailAndDeletedFalse(String email);

    Optional<User> findByIdAndDeletedFalse(Long id);

    boolean existsByEmailAndDeletedFalse(String email);

    // ♻️ For restore use-case
    Optional<User> findByIdAndDeletedTrue(Long id);
}
