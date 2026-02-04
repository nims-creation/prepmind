package com.nimscreation.prepmind.repository;

import com.nimscreation.prepmind.entity.base.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 🔍 Active users only
    Page<User> findAllByDeletedFalse(Pageable pageable);

    Optional<User> findByEmailAndDeletedFalse(String email);

    Optional<User> findByIdAndDeletedFalse(Long id);

    boolean existsByEmailAndDeletedFalse(String email);

    Optional<User> findByIdAndDeletedTrue(Long id);

    Page<User> findByDeletedFalseAndNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email, Pageable pageable);


    @Query("""
        SELECT u FROM User u
        WHERE u.deleted = false
        AND (
            LOWER(u.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
        )
    """)
    Page<User> searchActiveUsers(String keyword, Pageable pageable);

}
